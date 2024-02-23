package com.payhere.shop.domain.manager.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.payhere.shop.domain.manager.Manager;
import com.payhere.shop.domain.manager.dto.ManagerJoinDto;
import com.payhere.shop.domain.manager.dto.ManagerLoginDto;
import com.payhere.shop.domain.manager.repository.ManagerRepository;
import com.payhere.shop.domain.token.JwtTokenProvider;
import com.payhere.shop.domain.token.dto.TokenDto;
import com.payhere.shop.util.CookieManager;
import com.payhere.shop.util.exception.NotAllowedManagerException;
import com.payhere.shop.util.response.MetaData;
import com.payhere.shop.util.service.EncryptionService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final EncryptionService encryptionService;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${jwt-config.cookie-name}")
    private String cookieName;

    public MetaData join(ManagerJoinDto managerData) {

        if (managerRepository.existsByPhone(managerData.getPhone())) {
            return MetaData.MANAGER_DUPLICATED;
        }
        Manager newManager = managerData.toEntity(
                encryptionService.encryptSha256(managerData.getPassword()));
        managerRepository.save(newManager);

        return MetaData.SUCCESS;
    }

    public String login(ManagerLoginDto loginData, HttpServletResponse response) {
        String token = "";

        try {
            Manager findedManager = managerRepository.findByPhone(loginData.getPhone())
                    .orElseThrow(
                            () -> new Exception("manager not found"));

            if (!findedManager.getPassword().equals(encryptionService.encryptSha256(loginData.getPassword()))) {
                return token;
            } else {
                TokenDto tokenDt = jwtTokenProvider.createToken(findedManager.getPhone());
                CookieManager.addNewCookie(response, cookieName, tokenDt.getRefreshToken());
                
                token = tokenDt.getAccessToken();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return token;
    }

    public MetaData logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = CookieManager.getCookieByName(request, cookieName);

        if (Objects.nonNull(cookie)) {
            String managerId = jwtTokenProvider.getManagerIdByToken(cookie.getValue());
            if (managerId.isBlank()) {
                return MetaData.NOT_ALLOWED_MANAGER;
            } else {
                jwtTokenProvider.saveRefreshToken(managerId, cookie.getValue());
                CookieManager.expireCookie(response, cookie);
                return MetaData.SUCCESS;
            }
        }
        return MetaData.NOT_ALLOWED_MANAGER;
    }

    public Manager getManagerByHeaderToken(HttpServletRequest request) throws NotAllowedManagerException {
        Optional<Manager> manager = Optional.empty();

        String token = jwtTokenProvider.getHeaderToken(request);
        String managerId = jwtTokenProvider.getManagerIdByToken(token);
        if (!managerId.isBlank()) {
            manager = managerRepository.findByPhone(managerId);
        }

        return manager.orElseThrow(() -> new NotAllowedManagerException());
    }
}

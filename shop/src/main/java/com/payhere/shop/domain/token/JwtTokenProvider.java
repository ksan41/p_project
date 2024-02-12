package com.payhere.shop.domain.token;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.payhere.shop.domain.token.dto.TokenDto;
import com.payhere.shop.domain.token.repository.TokenRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final TokenRepository tokenRepository;

    @Value("${jwt-config.secret-key}")
    private String secretKey;

    @Value("#{${jwt-config.token-valid-time-refresh}}")
    private long refreshTokenValidTime;

    @Value("#{${jwt-config.token-valid-time-access}}")
    private long accessTokenValidTime;

    @Value("${jwt-config.header-name}")
    private String headerName;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String getHeaderToken(HttpServletRequest request) {
        return request.getHeader(headerName);
    }

    public TokenDto createToken(String phone) {
        Claims claims = Jwts.claims().setSubject(phone);
        Date now = new Date();

        String refreshToken = Jwts.builder().setHeader(tokenHeaderConfig()).setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime)) // 토큰 만료시간 셋팅
                .signWith(SignatureAlgorithm.HS256, secretKey) // 사용할 암호화 알고리즘과
                .compact();

        String accessToken = Jwts.builder().setHeader(tokenHeaderConfig()).setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + accessTokenValidTime)) // 토큰 만료시간 셋팅
                .signWith(SignatureAlgorithm.HS256, secretKey) // 사용할 암호화 알고리즘과
                .compact();

        TokenDto token = TokenDto.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build();
        return token;
    }

    public void saveRefreshToken(String phone, String token) {
        Token deleteToken = Token.builder()
                .managerId(phone)
                .refreshToken(token)
                .deleteDate(new Date())
                .build();
        tokenRepository.save(deleteToken);
    }

    public Authentication getAuthentication(String token) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        try {
            String managerId = getManagerIdByToken(token);
            if (!managerId.isBlank()) {
                authorities.add(new SimpleGrantedAuthority("MANAGER"));
                return new UsernamePasswordAuthenticationToken(managerId, "", authorities);
            }
        } catch (NullPointerException e) {
            log.error("getAuthentication() : " + e);
        } catch (Exception e) {
            log.error("getAuthentication() : " + e);
        }
        return null;
    }

    public String getManagerIdByToken(String token) {
        String managerId = "";
        try {
            managerId = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 형식의 토큰 입니다.", e);
        } catch (SignatureException e) {
            log.error("잘못된 토큰 입니다.", e);
        } catch (ExpiredJwtException e) {
            log.error("만료된 토큰 입니다.", e);
        } catch (MalformedJwtException e) {
            log.error("잘못된 토큰 입니다.", e);
        } catch (IllegalArgumentException e) {
            log.error("잘못된 토큰 입니다.", e);
        }

        return managerId;
    }

    public boolean isTokenValid(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Hashtable<String, Object> tokenHeaderConfig() {
        Hashtable<String, Object> headers = new Hashtable<>();
        headers.put("typ", "JWT"); // 토큰 타입
        headers.put("alg", "HS256"); // 해싱 알고리즘 , 토큰에 사용할 정보를 암호화할 방식

        return headers;
    }
}

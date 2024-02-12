package com.payhere.shop.api;

import java.util.Objects;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payhere.shop.domain.manager.Manager;
import com.payhere.shop.domain.manager.dto.ManagerJoinDto;
import com.payhere.shop.domain.manager.dto.ManagerLoginDto;
import com.payhere.shop.domain.manager.service.ManagerService;
import com.payhere.shop.domain.manager.service.ManagerValidator;
import com.payhere.shop.util.response.MetaData;
import com.payhere.shop.util.response.ResponseMsg;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("manager")
@RestController
public class ManagerController {

    private final ManagerValidator managerValidator;
    private final ManagerService managerService;

    @PostMapping(value = "/join")
    public ResponseMsg<Manager> join(@RequestBody ManagerJoinDto managerData) {
        ResponseMsg<Manager> msg = new ResponseMsg<>();

        String validationRes = managerValidator.validateJoin(managerData);
        if (!validationRes.isBlank()) {
            msg.setMeta(MetaData.MANAGER_PARAMETER_WRONG);
            log.debug(validationRes);
        } else {
            MetaData meta = managerService.join(managerData);
            msg.setMeta(meta);
        }

        return msg;
    }

    @PostMapping(value = "/login")
    public ResponseMsg<String> login(@RequestBody ManagerLoginDto loginData, HttpServletResponse response) {
        ResponseMsg<String> msg = new ResponseMsg<>();

        String validationRes = managerValidator.validateLogin(loginData);
        if (!validationRes.isBlank()) {
            msg.setMeta(MetaData.MANAGER_PARAMETER_WRONG);
            log.debug(validationRes);
        } else {
            String token = managerService.login(loginData, response);
            if (!Objects.nonNull(token)) {
                msg.setMeta(MetaData.MANAGER_LOGIN_FAILED);
            } else {
                msg.setMeta(MetaData.SUCCESS);
                msg.setData(token);
            }
        }

        return msg;
    }

    @PostMapping(value = "/logout")
    public ResponseMsg<String> logout(HttpServletRequest request, HttpServletResponse response) {
        ResponseMsg<String> msg = new ResponseMsg<>();
        MetaData meta = managerService.logout(request, response);
        msg.setMeta(meta);
        return msg;
    }
}

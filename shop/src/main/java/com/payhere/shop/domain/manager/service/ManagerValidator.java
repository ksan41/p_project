package com.payhere.shop.domain.manager.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.payhere.shop.domain.manager.dto.ManagerJoinDto;
import com.payhere.shop.domain.manager.dto.ManagerLoginDto;

@Service
public class ManagerValidator {
    
    public String validateJoin(ManagerJoinDto managerData) {
        StringBuilder sb = new StringBuilder();
        
        sb.append(checkPhone(managerData.getPhone()));
        sb.append(checkPassword(managerData.getPassword()));
        sb.append(checkName(managerData.getName()));

        return sb.toString();
    }

    public String validateLogin(ManagerLoginDto managerData) {
        StringBuilder sb = new StringBuilder();

        sb.append(checkPhone(managerData.getPhone()));
        sb.append(checkPassword(managerData.getPassword()));

        return sb.toString();
    }

    private String checkPhone(String phone) {
        if (!StringUtils.hasText(phone)) {
            return "전화번호가 입력되지 않았습니다.\n";
        }

        if (!phone.matches("\\d+")) {
            return "전화번호는 숫자로만 입력해주세요.\n";
        }

        if (phone.length() < 10 || phone.length() > 11) {
            return "전화번호 길이는 10자 이상 11자 이하로 입력해주세요.\n";
        }

        if (!phone.startsWith("010")) {
            return "전화번호는 010으로 시작되어야 합니다.\n";
        }

        return "";
    }

    private String checkPassword(String password) {
        if (!StringUtils.hasText(password)) {
            return "비밀번호가 입력되지 않았습니다.\n";
        }

        if (password.length() < 4 || password.length() > 16) {
            return "비밀번호는 4자 이상 16자 이상으로 입력해주세요.\n";
        }

        return "";
    }

    private String checkName(String name) {
        if (!StringUtils.hasText(name)) {
            return "이름이 입력되지 않았습니다.\n";
        }

        if (name.length() < 2) {
            return "이름을 2자 이상으로 입력해주세요.\n";
        }

        return "";
    }

}

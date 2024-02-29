package com.cafe.shop.manager;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cafe.shop.domain.manager.Manager;
import com.cafe.shop.domain.manager.dto.ManagerJoinDto;
import com.cafe.shop.domain.manager.repository.ManagerRepository;
import com.cafe.shop.domain.manager.service.ManagerService;
import com.cafe.shop.domain.token.JwtTokenProvider;
import com.cafe.shop.util.response.MetaData;
import com.cafe.shop.util.service.EncryptionService;

@SpringBootTest
public class ManagerServiceTest {

    ManagerService managerService;

    @MockBean
    ManagerRepository managerRepository;
    @MockBean
    JwtTokenProvider jwtTokenProvider;
    @MockBean
    EncryptionService encryptionService;

    @BeforeEach
    public void setup() {
        this.managerService = new ManagerService(managerRepository,
                encryptionService,
                jwtTokenProvider);
    }

    @DisplayName("중복된 전화번호로 가입 시 실패")
    @Test
    public void test_when_manager_phone_duplicated() {
        ManagerJoinDto newManager = ManagerJoinDto.builder()
                .phone("01012341234")
                .name("김영희")
                .password("12345678")
                .build();
        Mockito.when(managerRepository.existsByPhone(newManager.getPhone()))
                .thenReturn(true);

        MetaData result = managerService.join(newManager);

        Assertions.assertThat(result).isEqualTo(MetaData.MANAGER_DUPLICATED);
    }

    @DisplayName("회원가입 성공")
    @Test
    public void test_join() {
        ManagerJoinDto joinManager = ManagerJoinDto.builder()
                .phone("01012341234")
                .name("홍길동")
                .password("12345678")
                .build();
        Mockito.when(managerRepository.existsByPhone(joinManager.getPhone()))
                .thenReturn(false);
        Mockito.when(managerRepository.findByPhone(joinManager.getPhone()))
                .thenReturn(Optional.of(joinManager.toEntity("")));
        Mockito.when(managerRepository.save(joinManager.toEntity("")))
                .thenReturn(new Manager());


        MetaData result = managerService.join(joinManager);
        Optional<Manager> foundManager = managerRepository.findByPhone(joinManager.getPhone());

        Assertions.assertThat(result)
                .isEqualTo(MetaData.SUCCESS);
        Assertions.assertThat(joinManager.getPhone())
                .isEqualTo(foundManager.get().getPhone());
    }
}

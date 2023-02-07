package com.hyunki.pointapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyunki.pointapi.domain.dto.CreatePointRequest;
import com.hyunki.pointapi.repository.AccountRepository;
import com.hyunki.pointapi.repository.PointRepository;
import com.hyunki.pointapi.service.PointService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PointApiControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext ctx;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PointService pointService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PointRepository pointRepository;


    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("정상적으로 포인트를 생성하는 테스트")
    public void createPoint() throws Exception {
        //given
        CreatePointRequest point = CreatePointRequest.builder()
                .username("hkjung")
                .pointAmt(1000)
                .build();

        //when & then
        mockMvc.perform(post("/api/point/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(point)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("pointId").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE));
    }

    @Test
    @DisplayName("api 호출 시, 입력값이 잘못됐을 경우 테스트")
    public void createPointWrongInput() throws Exception {
        //given
        CreatePointRequest point = CreatePointRequest.builder()
                .username("")
                .pointAmt(0)
                .build();

        //when & then
        mockMvc.perform(post("/api/point/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(point)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("_links.index").exists())
                .andExpect(jsonPath("errors[0].code").exists())
                .andExpect(jsonPath("errors[0].msg").exists())
                .andExpect(jsonPath("errors[0].errorCode").exists());
    }
}
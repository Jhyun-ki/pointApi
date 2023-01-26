package com.hyunki.pointapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyunki.pointapi.domain.dto.CreatePointRequest;
import com.hyunki.pointapi.repository.AccountRepository;
import com.hyunki.pointapi.repository.PointRepository;
import com.hyunki.pointapi.service.PointService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
    ObjectMapper objectMapper;

    @Autowired
    PointService pointService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PointRepository pointRepository;


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
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("pointId").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
        ;
    }
}
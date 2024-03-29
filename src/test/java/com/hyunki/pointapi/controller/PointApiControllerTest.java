package com.hyunki.pointapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyunki.pointapi.domain.dto.PointRequestDto;
import com.hyunki.pointapi.domain.dto.PointSearchCondition;
import com.hyunki.pointapi.domain.enums.PointType;
import com.hyunki.pointapi.repository.AccountRepository;
import com.hyunki.pointapi.repository.PointRepository;
import com.hyunki.pointapi.service.PointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.sound.sampled.AudioFormat;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        PointRequestDto point = PointRequestDto.builder()
                .username("hkjung")
                .pointAmt(1000)
                .build();

        //when & then
        mockMvc.perform(post("/api/point/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(point)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"));
    }

    @Test
    @DisplayName("createPoint 호출 시, 입력값이 잘못됐을 경우 테스트")
    public void createPointWrongInput() throws Exception {
        //given
        PointRequestDto point = PointRequestDto.builder()
                .username("AAA")
                .pointAmt(0)
                .build();

        //when & then
        mockMvc.perform(post("/api/point/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(point)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors[0].code").exists())
                .andExpect(jsonPath("errors[0].msg").exists())
                .andExpect(jsonPath("errors[0].errorCode").exists());
    }

    @Test
    @DisplayName("Point 조회 테스트")
    public void queryPoint() throws Exception {
        //given
        IntStream.range(0, 30).forEach(this::generatePoint);

        PointSearchCondition pointSearchCondition = PointSearchCondition.builder()
                .username("hkjung")
                .pointType(PointType.PAY)
                .fromIssueDate("2023-02-15")
                .toIssueDate("2023-02-15")
                .build();
        //when
        mockMvc.perform(get("/api/point/")
                        .param("username", pointSearchCondition.getUsername())
                        .param("pointType", String.valueOf(pointSearchCondition.getPointType()))
                        .param("fromIssueDate", pointSearchCondition.getFromIssueDate())
                        .param("toIssueDate", pointSearchCondition.getToIssueDate())
                        .param("page", "1")
                        .param("size", "10")
                        .param("sort", "issueDate,DESC")
                        .param("sort", "id,DESC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("content").exists())
                .andExpect(jsonPath("content[0].username").exists())
                .andExpect(jsonPath("content[0].id").exists())
                .andExpect(jsonPath("content[0].remainPointAmt").exists())
                .andExpect(jsonPath("content[0].pointStatus").exists())
                .andExpect(jsonPath("content[0].issueDate").exists())
                .andExpect(jsonPath("pageable").exists())

                ;

        //then
    }

    private void generatePoint(int index) {
        pointService.createPoint(new PointRequestDto("hkjung", 500));
        pointService.createPoint(new PointRequestDto("otherhumans" + index, 2000 + index));
    }
}
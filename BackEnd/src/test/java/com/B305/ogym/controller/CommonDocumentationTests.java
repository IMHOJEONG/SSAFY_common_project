package com.B305.ogym.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.sharedHttpSession;

import com.B305.ogym.CustomResponseFieldsSnippet;
import com.B305.ogym.api.controller.EnumViewController;
import com.B305.ogym.common.config.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;


@ExtendWith(RestDocumentationExtension.class) // JUnit 5 사용시 문서 스니펫 생성용
@WebMvcTest(controllers = EnumViewController.class, excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)}
)
@MockBean(JpaMetamodelMappingContext.class)
class CommonDocumentationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext,
        RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .apply(sharedHttpSession())
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .build();
    }

    @Test
    public void commonResponse() throws Exception {

        mockMvc.perform(get("/docs/commonResponse"))
            .andDo(print())
            .andExpect(status().isOk()) // 201 isCreated()
            .andDo(
                document(
                    "common/commonResponse",
                    customResponseFields("custom-response", null, // (1)
                        attributes(key("title").value("공통응답")),
                        fieldWithPath("statusCode").type(JsonFieldType.STRING).description("결과코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                        subsectionWithPath("data").description("데이터"))
                ));

    }

    @Test
    public void apiResponseCode() throws Exception {

        mockMvc.perform(get("/docs/apiResponseCode"))
            .andDo(print())
            .andExpect(status().isOk()) // 201 isCreated()
            .andDo(
                document(
                    "common/apiResponseCode",
                    customResponseFields("custom-response", null, // (1)
                        attributes(key("title").value("응답코드")),
                        fieldWithPath("VALIDATION_FAILED").type(JsonFieldType.STRING)
                            .description("유효성 조건을 만족하지 못한 경우 발생하는 에러입니다."),
                        fieldWithPath("DUPLICATION_EMAIL").type(JsonFieldType.STRING)
                            .description("이메일 중복 시 발생하는 에러입니다."),
                        fieldWithPath("DUPLICATION_NICKNAME").type(JsonFieldType.STRING)
                            .description("닉네임 중복 시 발생하는 에러입니다."),
                        fieldWithPath("DUPLICATION_RESERVATION").type(JsonFieldType.STRING)
                            .description("예약 중복 시 발생하는 에러입니다."),
                        fieldWithPath("DUPLICATION_MONTH").type(JsonFieldType.STRING)
                            .description("월별 데이터 중복 시 발생하는 에러입니다."),
                        fieldWithPath("TEACHER_NOT_FOUND").type(JsonFieldType.STRING)
                            .description("대상 트레이너를 찾을 수 없을 때 발생하는 에러입니다."),
                        fieldWithPath("RESERVATION_NOT_FOUND").type(JsonFieldType.STRING)
                            .description("예약을 찾을 수 없을 때 발생하는 에러입니다."),
                        fieldWithPath("USER_NOT_FOUND").type(JsonFieldType.STRING)
                            .description("유저를 찾을 수 없을 때 발생하는 에러입니다."),
                        fieldWithPath("NOT_VALID_PARAM").type(JsonFieldType.STRING)
                            .description("파라미터가 유효하지 않을 때 발생하는 에러입니다."),
                        fieldWithPath("UNAUTHORIZED_USER").type(JsonFieldType.STRING)
                            .description("인증되지 않는 유저에게 발생하는 에러입니다."),
                        fieldWithPath("OK").type(JsonFieldType.STRING).description("요청이 성공하였습니다.")
                    )));

    }

    public static CustomResponseFieldsSnippet customResponseFields(String type,
        PayloadSubsectionExtractor<?> subsectionExtractor,
        Map<String, Object> attributes, FieldDescriptor... descriptors) {
        return new CustomResponseFieldsSnippet(type, subsectionExtractor,
            Arrays.asList(descriptors), attributes
            , true);
    }


}
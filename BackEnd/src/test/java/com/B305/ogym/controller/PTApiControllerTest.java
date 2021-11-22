package com.B305.ogym.controller;

import static com.B305.ogym.ApiDocumentUtils.getDocumentRequest;
import static com.B305.ogym.ApiDocumentUtils.getDocumentResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.sharedHttpSession;

import com.B305.ogym.common.annotation.WithAuthUser;
import com.B305.ogym.common.config.SecurityConfig;
import com.B305.ogym.controller.dto.PTDto.AllTeacherListResponse;
import com.B305.ogym.controller.dto.PTDto.PTTeacherDto;
import com.B305.ogym.controller.dto.PTDto.SearchDto;
import com.B305.ogym.controller.dto.PTDto.nowReservationDto;
import com.B305.ogym.controller.dto.PTDto.reservationDto;
import com.B305.ogym.controller.dto.PTDto.reservationRequest;
import com.B305.ogym.domain.users.common.Gender;
import com.B305.ogym.exception.user.UserNotFoundException;
import com.B305.ogym.service.PTService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;


@ExtendWith(RestDocumentationExtension.class) // JUnit 5 사용시 문서 스니펫 생성용
@WebMvcTest(controllers = PTApiController.class, excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)}
)
@MockBean(JpaMetamodelMappingContext.class) // @EnableJPaAuditing 사용시 추가해야하는 어노테이션
class PTApiControllerTest {

    @MockBean
    private PTService ptService;

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

    public reservationRequest createReservationRequset(String email) {
        return reservationRequest.builder()
            .ptTeacherEmail(email)
            .reservationTime(LocalDateTime.parse("2021-08-28T13:00:00"))
            .description("상체")
            .build();
    }

    @WithAuthUser(email = "student@naver.com", role = "ROLE_PTSTUDENT")
    @DisplayName("선생님 리스트 불러오기 - 성공")
    @Test
    public void getTeacherList_Success() throws Exception {
        List<PTTeacherDto> result = new ArrayList<>();
        result.add(PTTeacherDto.builder()
            .age(21)
            .username("서현진")
            .gender(Gender.WOMAN)
            .nickname("오해영")
            .tel("010-2021-0105")
            .email("eggkim5@ssafy.com")
            .starRating((float) 4.8)
            .major("특공무술")
            .price(200000)
            .description("좋았어. 거짓은 머리털만큼도 없다! 신뢰와 정직으로 모시겠습니다")
            .snsList(new ArrayList<>())
            .careers(new ArrayList<>())
            .reservations(new ArrayList<>())
            .certificates(new ArrayList<>())
            .build());
        AllTeacherListResponse allTeacherListResponse = AllTeacherListResponse.builder()
            .teacherList(result)
            .build();
        SearchDto searchDto = SearchDto.builder().build();
        given(ptService.getTeacherList(any(), any()))
            .willReturn(allTeacherListResponse);

        mockMvc.perform(
            get("/api/pt/teacherlist?page=0&size=10&name=서현진&gender=WOMAN&minPrice=100&maxPrice=200000&minAge=1&maxAge=100")
                .header("Authorization", "JWT ACCESS TOKEN"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document(
                "PTApi/getTeacherList/successful",
                getDocumentRequest(),
                getDocumentResponse(),
                requestParameters(
                    parameterWithName("page").description("현재 페이지 번호").optional(),
                    parameterWithName("size").description("한번에 보낼 컬럼의 개수").optional(),
                    parameterWithName("name").description("검색할 선생님 이름").optional(),
                    parameterWithName("gender").description("성별").optional(),
                    parameterWithName("minPrice").description("최소 가격").optional(),
                    parameterWithName("maxPrice").description("최대 가격").optional(),
                    parameterWithName("minAge").description("최소 나이").optional(),
                    parameterWithName("maxAge").description("최대 나이").optional()
                )
            ));

    }

    @WithAuthUser(email = "teacher@naver.com", role = "ROLE_PTSTUDENT")
    @DisplayName("PT 예약하기 - 성공")
    @Test
    public void makeReservation_Success() throws Exception {
        reservationRequest req = createReservationRequset("teacher@naver.com");

        doNothing().when(ptService).makeReservation(any(), any());

        mockMvc.perform(post("/api/pt/reservation")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(req))
            .header("Authorization", "JWT ACCESS TOKEN"))
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document(
                "PTApi/makeReservation/successful",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("ptTeacherEmail").description("예약할 대상 PT 트레이너의 이메일"),
                    fieldWithPath("reservationTime").description("PT 예약 시간")
                        .attributes(key("constraint")
                            .value("yyyy-MM-dd'T'HH:mm:ss")),
                    fieldWithPath("description").description("예약시간에 진행될 PT 내용에 대한 간략한 설명")
                )
            ));
    }

    @WithAuthUser(email = "student@naver.com", role = "ROLE_PTSTUDENT")
    @DisplayName("PT 예약하기 - 해당하는 선생님 이메일이 존재하지 않아 실패")
    @Test
    public void makeReservation_teacherNotFound() throws Exception {
        reservationRequest req = createReservationRequset("student@naver.com");

        doThrow(new UserNotFoundException("존재하지 않는 트레이너입니다.")).when(ptService)
            .makeReservation(any(), any());

        mockMvc.perform(post("/api/pt/reservation")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(req))
            .header("Authorization", "JWT ACCESS TOKEN"))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andDo(document(
                "PTApi/getTeacherList/TeacherNotFound",
                getDocumentRequest(),
                getDocumentResponse()
            ));
    }

    @WithAuthUser(email = "teacher@naver.com", role = "ROLE_PTTEACHER")
    @DisplayName("PT 예약하기 - 해당하는 학생 이메일이 존재하지 않아 실패")
    @Test
    public void makeReservation_studentNotFound() throws Exception {
        reservationRequest req = createReservationRequset("teacher@naver.com");

        doThrow(new UserNotFoundException("존재하지 않는 학생입니다.")).when(ptService)
            .makeReservation(any(), any());

        mockMvc.perform(post("/api/pt/reservation")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(req))
            .header("Authorization", "JWT ACCESS TOKEN"))
            .andDo(print())
            .andExpect(status().isNotFound()).andDo(document(
            "PTApi/getTeacherList/StudentNotFound",
            getDocumentRequest(),
            getDocumentResponse()
        ));
    }

    @WithAuthUser(email = "student@naver.com", role = "ROLE_PTSTUDENT")
    @DisplayName("PT 예약 취소하기 - 성공")
    @Test
    public void cancelReservation_Success() throws Exception {
        reservationRequest req = createReservationRequset("student@naver.com");

        doNothing().when(ptService).cancelReservation(eq("student@naver.com"), eq(req));

        mockMvc.perform(delete("/api/pt/reservation")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(req))
            .header("Authorization", "JWT ACCESS TOKEN"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document(
                "PTApi/cancelReservation/successful",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("ptTeacherEmail").description("예약된 PT 트레이너의 이메일"),
                    fieldWithPath("reservationTime").description("PT 취소 예약 시간")
                        .attributes(key("constraint")
                            .value("yyyy-MM-dd'T'HH:mm:ss")),
                    fieldWithPath("description").description("PT 예약 취소 사유")
                )
            ));
    }

    @WithAuthUser(email = "student@naver.com", role = "ROLE_PTSTUDENT")
    @DisplayName("PT 예약 취소하기 - 요청한 학생이 존재하지 않아 실패")
    @Test
    public void cancelReservation_studentNotFound() throws Exception {
        reservationRequest req = createReservationRequset("student@naver.com");

        doThrow(new UserNotFoundException("존재하지 않는 학생입니다.")).when(ptService)
            .cancelReservation(any(), any());

        mockMvc.perform(delete("/api/pt/reservation")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(req))
            .header("Authorization", "JWT ACCESS TOKEN"))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @WithAuthUser(email = "student@naver.com", role = "ROLE_PTSTUDENT")
    @DisplayName("PT 예약 취소하기 - 요청한 트레이너가 존재하지 않아 실패")
    @Test
    public void cancelReservation_teacherNotFound() throws Exception {
        reservationRequest req = createReservationRequset("student@naver.com");

        doThrow(new UserNotFoundException("존재하지 않는 트레이너입니다.")).when(ptService)
            .cancelReservation(any(), any());

        mockMvc.perform(delete("/api/pt/reservation")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(req))
            .header("Authorization", "JWT ACCESS TOKEN"))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @WithAuthUser(email = "student@naver.com", role = "ROLE_PTSTUDENT")
    @DisplayName("PT 예약 취소하기 - 제거 요청한 예약이 존재하지 않아 실패")
    @Test
    public void cancelReservation_reservationNotFound() throws Exception {
        reservationRequest req = createReservationRequset("student@naver.com");

        doThrow(new UserNotFoundException("존재하지 않는 예약입니다.")).when(ptService)
            .cancelReservation(any(), any());

        mockMvc.perform(delete("/api/pt/reservation")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(req))
            .header("Authorization", "JWT ACCESS TOKEN"))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @WithAuthUser(email = "student@naver.com", role = "ROLE_PTSTUDENT")
    @DisplayName("선생님 예약 불가 시간 조회 - 성공")
    @Test
    public void getTeacherReservationTime_success() throws Exception {
        String teacherEmail = "teacher@naver.com";
        List<LocalDateTime> reservationList = new ArrayList<>(
            Collections.singletonList(LocalDateTime.parse("2021-08-28T13:00:00")));
        given(ptService.getTeacherReservationTime(teacherEmail)).willReturn(reservationList);

        assertEquals(ptService.getTeacherReservationTime(teacherEmail), reservationList);

        mockMvc.perform(get("/api/pt/reservation/{teacherEmail}", teacherEmail)
            .header("Authorization", "JWT ACCESS TOKEN"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document(
                "PTApi/getTeacherReservationTime/successful",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                    parameterWithName("teacherEmail").description("예약이 불가능한 시간을 조회할 트레이너 이메일")
                )
            ));
    }

    @WithAuthUser(email = "student@naver.com", role = "ROLE_PTSTUDENT")
    @DisplayName("선생님 예약 불가 시간 조회 - 실패")
    @Test
    public void getTeacherReservationTime_failure() throws Exception {
        String teacherEmail = "teacher@naver.com";
        given(ptService.getTeacherReservationTime(teacherEmail))
            .willThrow(new UserNotFoundException("해당 선생님은 이미 탈퇴한 회원입니다."));

        assertThrows(UserNotFoundException.class,
            () -> ptService.getTeacherReservationTime(teacherEmail));

        mockMvc.perform(get("/api/pt/reservation/teacher@naver.com")
            .header("Authorization", "JWT ACCESS TOKEN"))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @WithAuthUser(email = "userEmail@naver.com", role = "ROLE_PTSTUDENT")
    @DisplayName("유저의 예약된 시간 조회 - 성공")
    @Test
    public void getReservationTime_success() throws Exception {
        String userEmail = "userEmail@naver.com";
        List<reservationDto> reservationList = new ArrayList<>();
        reservationList.add(reservationDto.builder()
            .description("이두 운동")
            .nickname("김계란")
            .username("김성식")
            .email("eggkim@ssafy.com")
            .profileUrl("profileURL/profileIMG.jpg")
            .reservationTime(LocalDateTime.parse("2021-07-28T04:00:00"))
            .build());
        given(ptService.getReservationTime(any())).willReturn(reservationList);

        assertEquals(ptService.getReservationTime(any()), reservationList);

        mockMvc.perform(get("/api/pt/reservation")
            .header("Authorization", "JWT ACCESS TOKEN"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document(
                "PTApi/getReservationTime/successful",
                getDocumentRequest(),
                getDocumentResponse()
            ));
    }

    @WithAuthUser(email = "userEmail@naver.com", role = "ROLE_PTSTUDENT")
    @DisplayName("유저의 예약된 시간 조회 - 실패")
    @Test
    public void getReservationTime_failure() throws Exception {
        given(ptService.getReservationTime(any()))
            .willThrow(new UserNotFoundException("해당 유저는 이미 탈퇴한 회원입니다."));

        mockMvc.perform(get("/api/pt/reservation")
            .header("Authorization", "JWT ACCESS TOKEN"))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @WithAuthUser(email = "userEmail@naver.com", role = "ROLE_PTSTUDENT")
    @DisplayName("요청한 시간을 기준으로 10분 이전/이후의 예약을 조회 - 성공")
    @Test
    public void getNowReservation_success() throws Exception {
        String userEmail = "userEmail@naver.com";

        nowReservationDto nowReservation = nowReservationDto.builder()
            .studentNickname("학생")
            .teacherNickname("트레이너")
            .build();

        given(ptService.getNowReservation(any())).willReturn(nowReservation);

        mockMvc.perform(get("/api/pt/nowreservation")
            .header("Authorization", "JWT ACCESS TOKEN"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document(
                "PTApi/getNowReservation/successful",
                getDocumentRequest(),
                getDocumentResponse()
            ));
    }

}
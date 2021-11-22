package com.B305.ogym.controller;

import static com.B305.ogym.ApiDocumentUtils.getDocumentRequest;
import static com.B305.ogym.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.sharedHttpSession;

import com.B305.ogym.common.annotation.WithAuthUser;
import com.B305.ogym.common.config.SecurityConfig;
import com.B305.ogym.controller.dto.UserDto.ProfileDto;
import com.B305.ogym.controller.dto.UserDto.SaveUserRequest;
import com.B305.ogym.exception.user.UserDuplicateEmailException;
import com.B305.ogym.exception.user.UserDuplicateNicknameException;
import com.B305.ogym.exception.user.UserNotFoundException;
import com.B305.ogym.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@ExtendWith(RestDocumentationExtension.class) // JUnit 5 사용시 문서 스니펫 생성용
@WebMvcTest(controllers = UserApiController.class, excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)}
)
@MockBean(JpaMetamodelMappingContext.class) // @EnableJPaAuditing 사용시 추가해야하는 어노테이션
class UserApiControllerTest {

    @MockBean
    private UserService userService;

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


    private SaveUserRequest createTeacherRequest() {
        return SaveUserRequest.builder()
            .email("hello@naver.com")
            .password("asdasd")
            .username("juhu")
            .nickname("juhu")
            .gender(0)
            .tel("010-0000-0000")
            .zipCode("12345")
            .street("road 17")
            .detailedAddress("juhu")
            .role("ROLE_PTTEACHER")
            .major("재활")
            .certificates(new ArrayList<>())
            .careers(new ArrayList<>())
            .price(1000)
            .description("트레이너")
            .age(20)
            .snsAddrs(new ArrayList<>())
            .build();
    }

    private SaveUserRequest createStudentRequest() {
        return SaveUserRequest.builder()
            .email("hello@naver.com")
            .password("asdasd")
            .username("한솥")
            .nickname("nononoo1")
            .gender(0)
            .tel("010-0000-0000")
            .zipCode("12345")
            .street("도로명")
            .age(16)
            .detailedAddress("상세주소")
            .role("ROLE_PTSTUDENT")
            .monthlyWeights(new ArrayList<>(Arrays
                .asList(180, 200, 210, 180, 200, 210, 180, 200, 210, 180, 200, 210)))
            .monthlyHeights(new ArrayList<>(Arrays
                .asList(180, 200, 210, 180, 200, 210, 180, 200, 210, 180, 200, 210)))
            .build();
    }

    @DisplayName("선생님 회원가입 - 모든 유효성 검사에 통과했다면 회원가입 성공")
    @Test
    public void createTeacher_success() throws Exception {
        //given
        SaveUserRequest teacherRequest = createTeacherRequest();

        //when
        doNothing().when(userService).signup(teacherRequest);

        //then
        mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(teacherRequest)))
            .andDo(print())
            .andExpect(status().isCreated()) // 201 isCreated()
            .andDo(
                document(
                    "userApi/signup/teacher/successful",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    requestFields(
                        fieldWithPath("email").type(JsonFieldType.STRING)
                            .description("트레이너의 이메일 주소")
                            .attributes(key("constraint")
                                .value("최소 3글자, 최대 50글자 이내로 입력해주세요. @*.com의 양식을 갖추어야 합니다.")),
                        fieldWithPath("password").type(JsonFieldType.STRING)
                            .description("트레이너의 비밀번호")
                            .attributes(key("constraint")
                                .value("최소 3글자, 최대 20글자 이내로 입력해주세요.")),
                        fieldWithPath("username").type(JsonFieldType.STRING)
                            .description("트레이너의 이름")
                            .attributes(key("constraint")
                                .value("최소 2글자, 최대 10글자 이내로 입력해주세요.")),
                        fieldWithPath("nickname").type(JsonFieldType.STRING)
                            .description("트레이너의 별명")
                            .attributes(key("constraint")
                                .value("최소 2글자, 최대 10글자 이내로 입력해주세요.")),
                        fieldWithPath("age").type(JsonFieldType.NUMBER)
                            .description("트레이너의 나이")
                            .attributes(key("constraint")
                                .value("150세 이하로 입력해주세요.")),
                        fieldWithPath("gender").type(JsonFieldType.NUMBER)
                            .description("트레이너의 성별")
                            .attributes(key("constraint")
                                .value("0 (MAN), 1 (WOMAN) 중 하나로 입력해주세요.")),
                        fieldWithPath("tel").type(JsonFieldType.STRING)
                            .description("트레이너의 전화번호")
                            .attributes(key("constraint")
                                .value("000-000(3글자 혹은 4글자)-000의 형식으로 입력해주세요.")),
                        fieldWithPath("zipCode").type(JsonFieldType.STRING)
                            .description("트레이너의 우편번호")
                            .attributes(key("constraint")
                                .value("5글자로 입력해주세요.")),
                        fieldWithPath("street").type(JsonFieldType.STRING)
                            .description("트레이너의 도로명 주소"),
                        fieldWithPath("detailedAddress").type(JsonFieldType.STRING)
                            .description("트레이너의 상세 주소"),
                        fieldWithPath("role").type(JsonFieldType.STRING)
                            .description("트레이너의 권한"),
                        fieldWithPath("major").type(JsonFieldType.STRING)
                            .description("트레이너의 전공"),
                        fieldWithPath("certificates").type(JsonFieldType.ARRAY)
                            .description("트레이너의 자격증 목록"),
                        fieldWithPath("careers").type(JsonFieldType.ARRAY)
                            .description("트레이너의 경력 목록"),
                        fieldWithPath("price").type(JsonFieldType.NUMBER)
                            .description("트레이너의 가격"),
                        fieldWithPath("description").type(JsonFieldType.STRING)
                            .description("트레이너의 자기 소개"),
                        fieldWithPath("snsAddrs").type(JsonFieldType.ARRAY)
                            .description("트레이너의 SNS 주소 목록")
                    ).and(
                        fieldWithPath("monthlyHeights").type(JsonFieldType.NULL)
                            .description("트레이너의 경우 입력받지 않습니다."),
                        fieldWithPath("monthlyWeights").type(JsonFieldType.NULL)
                            .description("트레이너의 경우 입력받지 않습니다.")
                    )
                ));
    }

    @DisplayName("학생 회원가입 - 모든 유효성 검사에 통과했다면 회원가입 성공")
    @Test
    public void createStudent_success() throws Exception {
        //given
        SaveUserRequest studentRequest = createStudentRequest();
        //when
        doNothing().when(userService).signup(studentRequest);

        //then
        mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(studentRequest)))
            .andDo(print())
            .andExpect(status().isCreated()) // 201 isCreated()
            .andDo(document("userApi/signup/student/successful",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("email").type(JsonFieldType.STRING)
                        .description("학생의 이메일 주소")
                        .attributes(key("constraint")
                            .value("최소 3글자, 최대 50글자 이내로 입력해주세요. @*.com의 양식을 갖추어야 합니다.")),
                    fieldWithPath("password").type(JsonFieldType.STRING)
                        .description("학생의 비밀번호")
                        .attributes(key("constraint")
                            .value("최소 3글자, 최대 20글자 이내로 입력해주세요.")),
                    fieldWithPath("username").type(JsonFieldType.STRING)
                        .description("학생의 이름")
                        .attributes(key("constraint")
                            .value("최소 2글자, 최대 10글자 이내로 입력해주세요.")),
                    fieldWithPath("nickname").type(JsonFieldType.STRING)
                        .description("학생의 별명")
                        .attributes(key("constraint")
                            .value("최소 2글자, 최대 10글자 이내로 입력해주세요.")),
                    fieldWithPath("age").type(JsonFieldType.NUMBER)
                        .description("학생의 나이")
                        .attributes(key("constraint")
                            .value("150세 이하로 입력해주세요.")),
                    fieldWithPath("gender").type(JsonFieldType.NUMBER)
                        .description("학생의 성별")
                        .attributes(key("constraint")
                            .value("0 (MAN), 1 (WOMAN) 중 하나로 입력해주세요.")),
                    fieldWithPath("tel").type(JsonFieldType.STRING)
                        .description("학생의 전화번호")
                        .attributes(key("constraint")
                            .value("000-000(3글자 혹은 4글자)-000의 형식으로 입력해주세요.")),
                    fieldWithPath("zipCode").type(JsonFieldType.STRING)
                        .description("학생의 우편번호")
                        .attributes(key("constraint")
                            .value("5글자로 입력해주세요.")),
                    fieldWithPath("street").type(JsonFieldType.STRING)
                        .description("학생의 도로명 주소"),
                    fieldWithPath("detailedAddress").type(JsonFieldType.STRING)
                        .description("학생의 상세 주소"),
                    fieldWithPath("role").type(JsonFieldType.STRING)
                        .description("학생의 권한"),
                    fieldWithPath("monthlyHeights").type(JsonFieldType.ARRAY)
                        .description("학생의 월별 신장 목록"),
                    fieldWithPath("monthlyWeights").type(JsonFieldType.ARRAY)
                        .description("학생의 월별 체중 목록"))
                    .and(
                        fieldWithPath("major").type(JsonFieldType.STRING)
                            .description("학생의 경우 입력받지 않습니다").optional(),
                        fieldWithPath("certificates").type(JsonFieldType.ARRAY)
                            .description("학생의 경우 입력받지 않습니다").optional(),
                        fieldWithPath("careers").type(JsonFieldType.ARRAY)
                            .description("학생의 경우 입력받지 않습니다").optional(),
                        fieldWithPath("price").type(JsonFieldType.NUMBER)
                            .description("학생의 경우 입력받지 않습니다").optional(),
                        fieldWithPath("description").type(JsonFieldType.STRING)
                            .description("학생의 경우 입력받지 않습니다").optional(),
                        fieldWithPath("snsAddrs").type(JsonFieldType.NULL)
                            .description("학생의 경우 입력받지 않습니다").optional()
                    ), responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER)
                        .description("HTTP 상태 코드"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("반환 데이터")
                )));
    }

    @DisplayName("유저 회원가입 - 이메일 중복으로 인한 회원가입 실패")
    @Test
    public void createUser_email_failure() throws Exception {
        //given
        SaveUserRequest teacherRequest = createTeacherRequest();

        //when
        doThrow(new UserDuplicateEmailException("이메일 중복")).when(userService).signup(any());
        //then
        mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(teacherRequest)))
            .andDo(print())
            .andExpect(status().isConflict())
            .andDo(document("userApi/signup/duplicate/email",
                getDocumentRequest(),
                getDocumentResponse()
            ));
    }

    @DisplayName("유저 회원가입 - 닉네임 중복으로 인한 회원가입 실패")
    @Test
    public void createUser_nickName_failure() throws Exception {
        //given
        SaveUserRequest teacherRequest = createTeacherRequest();

        //when
        doThrow(new UserDuplicateNicknameException("이메일 중복")).when(userService).signup(any());
        //then
        mockMvc.perform(post("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(teacherRequest)))
            .andDo(print())
            .andExpect(status().isConflict())
            .andDo(document("userApi/signup/duplicate/nickname",
                getDocumentRequest(),
                getDocumentResponse()
            ));
    }

    @WithAuthUser(email = "teacher@naver.com", role = "ROLE_PTTEACHER")
    @DisplayName("선생님 회원탈퇴 - 회원탈퇴 성공")
    @Test
    public void deleteTeacher_success() throws Exception {
        //given
        String email = "teacher@naver.com";

        //when
        doNothing().when(userService).deleteUserBase(eq(email), any());

        //then
        mockMvc.perform(delete("/api/user")
            .header("Authorization", "JWT ACCESS TOKEN"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("userApi/delete/teacher/successful",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(headerWithName("Authorization").description("JWT Access Token"))
            ));
    }

    @WithAuthUser(email = "teacher@naver.com", role = "ROLE_PTSTUDENT")
    @DisplayName("학생 회원탈퇴 - 존재하지 않는 이메일로 인한 회원탈퇴 실패(이미 탈퇴한 회원)")
    @Test
    public void deleteTeacher_failure() throws Exception {
        doThrow(new UserNotFoundException("해당하는 이메일이 존재하지 않습니다")).when(userService)
            .deleteUserBase(any(), any());

        mockMvc.perform(delete("/api/user")
            .header("Authorization", "JWT ACCESS TOKEN"))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andDo(document("userApi/delete/teacher/failure",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(headerWithName("Authorization").description("JWT Access Token"))
            ));
    }

    @WithAuthUser(email = "student@naver.com", role = "ROLE_PTSTUDENT")
    @DisplayName("학생 회원탈퇴 - 회원탈퇴 성공")
    @Test
    public void deleteStudent_success() throws Exception {
        //given
        String email = "student@naver.com";

        //when
        doNothing().when(userService).deleteUserBase(eq(email), any());

        //then
        mockMvc.perform(delete("/api/user")
            .header("Authorization", "JWT ACCESS TOKEN"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("userApi/delete/student/successful",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(headerWithName("Authorization").description("JWT Access Token")),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER)
                        .description("HTTP 상태 코드"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("반환 데이터")
                )
            ));
    }

    @WithAuthUser(email = "student@naver.com", role = "ROLE_PTSTUDENT")
    @DisplayName("학생 회원탈퇴 - 존재하지 않는 이메일로 인한 회원탈퇴 실패(이미 탈퇴한 회원)")
    @Test
    public void deleteStudent_failure() throws Exception {
        doThrow(new UserNotFoundException("해당하는 이메일이 존재하지 않습니다")).when(userService)
            .deleteUserBase(any(), any());

        mockMvc.perform(delete("/api/user")
            .header("Authorization", "JWT ACCESS TOKEN"))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andDo(document("userApi/delete/student/failure",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(headerWithName("Authorization").description("JWT Access Token"))
            ));
    }

    @WithAuthUser(email = "teacher@naver.com", role = "ROLE_PTTEACHER")
    @DisplayName("회원 정보조회 - 정보조회 성공")
    @Test
    public void getUserInfo_success() throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("id", 1);
        data.put("email", "teacher@naver.com");

        List<String> req = Arrays.asList("id", "email");
        System.out.println(req);

        given(userService.getUserInfo(any(), any())).willReturn(data);

        mockMvc.perform(get("/api/user/{req}", req)
            .header("Authorization", "JWT ACCESS TOKEN"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(
                document(
                    "userApi/getUserInfo/successful",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    requestHeaders(headerWithName("Authorization").description("JWT Access Token")),
                    pathParameters(
                        parameterWithName("req").description("요청할 유저 정보의 항목이 담긴 리스트")
                    )
                ));

//        verify(userService, atLeastOnce()).getUserInfo(eq(email), eq(req));
    }

    @WithAuthUser(email = "teacher@naver.com", role = "ROLE_PTTEACHER")
    @DisplayName("회원 정보조회 - 이메일이 존재하지 않아 정보조회 실패")
    @Test
    public void getUserInfo_failure() throws Exception {
        List<String> req = Arrays.asList("id", "email");

        given(userService.getUserInfo(any(), any()))
            .willThrow(new UserNotFoundException("해당하는 유저가 존재하지 않습니다"));

        mockMvc.perform(get("/api/user/{req}", req)
            .header("Authorization", "JWT ACCESS TOKEN"))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andDo(document("userApi/getUserInfo/failure",
                getDocumentRequest(),
                getDocumentResponse()
            ));
    }

    @WithAuthUser(email = "userEmail@naver.com", role = "ROLE_PTTEACHER")
    @DisplayName("유저 프로필 이미지 등록 - 성공")
    @Test
    public void putProfile_success() throws Exception {
        ProfileDto profileDto = ProfileDto.builder()
            .url("fileDIR/fileURL")
            .build();
        doNothing().when(userService).putProfile(any(), any());

        mockMvc.perform(patch("/api/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(profileDto)))
            .andDo(print())
            .andExpect(status().isOk()) // 201 isCreated()
            .andDo(
                document(
                    "userApi/putProfile/successful",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    requestFields(
                        fieldWithPath("url").type(JsonFieldType.STRING)
                            .description("프로필 이미지 주소")
                            .attributes(key("constraint")
                                .value("AWS S3에 저장된 이미지의 URL"))
                    )
                ));
    }

}

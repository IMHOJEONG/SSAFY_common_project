package com.B305.ogym.controller;

import com.B305.ogym.controller.dto.SuccessResponseDto;
import com.B305.ogym.controller.dto.UserDto;
import com.B305.ogym.controller.dto.UserDto.ProfileDto;
import com.B305.ogym.domain.users.common.UserBase;
import com.B305.ogym.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserApiController {

    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    /*
     * 회원탈퇴 기능을 구현한 메서드
     */
    @DeleteMapping("/user")
    @PreAuthorize("hasAnyRole('PTTEACHER','PTSTUDENT')")
    public ResponseEntity<SuccessResponseDto> deleteMyUser(
        @AuthenticationPrincipal String userEmail, HttpServletRequest req
    ) {
        userService.deleteUserBase(userEmail, req.getHeader("Authorization").substring(7));
        return ResponseEntity.ok(new SuccessResponseDto<Map>(
            200, "회원정보 삭제에 성공했습니다", new HashMap()
        ));
    }

    /*
     * 회원가입 기능을 구현한 메서드
     */
    @PostMapping("/user")
    public ResponseEntity<SuccessResponseDto> signup(
        @RequestBody @Valid UserDto.SaveUserRequest userRequestDto) {
        userService.signup(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new SuccessResponseDto<Map>(201, "회원 가입에 성공했습니다.", new HashMap()));
    }


    /*
     * 사용자의 회원정보를 조회하기 위한 메서드
     */
    @GetMapping("/user/{req}")
    @PreAuthorize("hasAnyRole('PTTEACHER','PTSTUDENT')")
    public ResponseEntity<SuccessResponseDto> getUserInfo(
        @AuthenticationPrincipal String userEmail,
        @PathVariable @NotEmpty List<String> req) {
        return ResponseEntity.ok(new SuccessResponseDto<Map>(
            200, "회원 정보를 불러오는데 성공했습니다", userService.getUserInfo(userEmail, req)
        ));
    }

    /*
     * 프로필 사진 변경을 위한 메서드
     */
    @PatchMapping("/user")
    @PreAuthorize("hasAnyRole('PTTEACHER', 'PTSTUDENT')")
    public ResponseEntity<SuccessResponseDto> putProfile(
        @AuthenticationPrincipal String userEmail,
        @RequestBody ProfileDto profileDto
    ) {
        userService.putProfile(userEmail, profileDto);
        return ResponseEntity.ok(new SuccessResponseDto<Map>(
            200, "프로필 저장에 성공했습니다.", new HashMap()
        ));
    }
}

package BITAmin.BE.member.controller;


import BITAmin.BE.global.dto.ApiResponse;
import BITAmin.BE.member.dto.auth.LoginRequestDto;
import BITAmin.BE.member.dto.auth.SignupReqeustDto;
import BITAmin.BE.member.dto.auth.UserResponseDto;
import BITAmin.BE.member.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<String> signUp(@RequestBody SignupReqeustDto dto){
        authService.signup(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponseDto>> login(@RequestBody LoginRequestDto dto){
        UserResponseDto userDto = authService.login(dto);
        return ResponseEntity.ok(ApiResponse.success("로그인 성공", userDto));
    }


}

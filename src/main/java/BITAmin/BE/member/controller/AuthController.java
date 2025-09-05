package BITAmin.BE.member.controller;


import BITAmin.BE.member.dto.auth.SignupReqeustDto;
import BITAmin.BE.member.service.AuthService;
import lombok.RequiredArgsConstructor;
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
    @PostMapping("/sign-up")
    public ResponseEntity<> signUp(@RequestBody SignupReqeustDto dto){
        authService.signup(dto);
        return
    }
}

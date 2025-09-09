package BITAmin.BE.member.service;

import BITAmin.BE.global.exception.CustomException;
import BITAmin.BE.global.exception.ErrorCode;
import BITAmin.BE.global.security.JwtProvider;
import BITAmin.BE.global.util.RedisClient;
import BITAmin.BE.member.MemberRepository;
import BITAmin.BE.member.dto.auth.LoginRequestDto;
import BITAmin.BE.member.dto.auth.SignupReqeustDto;
import BITAmin.BE.member.dto.auth.UserResponseDto;
import BITAmin.BE.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtProvider jwtProvider;
    private final RedisClient redisClient;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    /**
     * DB에 User 정보 저장 + token 발급
     * @param dto
     */
    public void signup(SignupReqeustDto dto){
        String encodedPassword = passwordEncoder.encode(dto.password());
        Member member=dto.toEntity(encodedPassword);
        memberRepository.save(member);
    }
    public UserResponseDto login(LoginRequestDto dto){
        Member member = memberRepository.findByUsername(dto.username())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        String accessToken = jwtProvider.createAccessToken(member);
        String refreshToken = jwtProvider.createRefreshToken(member);
        redisClient.setValue("RefreshToken:" + dto.username(), refreshToken, 43200L);
        UserResponseDto userResponseDto = UserResponseDto.from(member, accessToken);
        return userResponseDto;
    }

}

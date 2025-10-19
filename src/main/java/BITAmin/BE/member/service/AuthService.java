package BITAmin.BE.member.service;

import BITAmin.BE.global.exception.CustomException;
import BITAmin.BE.global.exception.ErrorCode;
import BITAmin.BE.global.security.JwtProvider;
import BITAmin.BE.global.util.RedisClient;
import BITAmin.BE.member.enums.Status;
import BITAmin.BE.member.repository.MemberRepository;
import BITAmin.BE.member.dto.auth.LoginRequestDto;
import BITAmin.BE.member.dto.auth.SignupReqeustDto;
import BITAmin.BE.member.dto.auth.UserResponseDto;
import BITAmin.BE.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        member.setStatusPending();
        memberRepository.save(member);
    }

    /**
     * status(pending, approved)확인, pw 일치
     * @param dto
     * @return
     */
    public UserResponseDto login(LoginRequestDto dto){
        Member member = memberRepository.findByUsername(dto.username())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        validateMember(member);
        if(!passwordEncoder.matches(dto.password(), member.getPassword())){
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        String accessToken = jwtProvider.createAccessToken(member);
        String refreshToken = jwtProvider.createRefreshToken(member);
        redisClient.setValue("RefreshToken:" + dto.username(), refreshToken, 43200L);
        UserResponseDto userResponseDto = UserResponseDto.from(member, accessToken);
        return userResponseDto;
    }
    public void validateMember(Member member){
        Status status = member.getStatus();
        if(status == Status.PENDING){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "운영진 승인을 대기 중입니다.");
        }
        if(status!=Status.APPROVED){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "승인되지 않은 사용자입니다. (status="+status+")");
        }
        System.out.println("회원 승인 상태 확인 완료: "+member.getUsername());
    }
}

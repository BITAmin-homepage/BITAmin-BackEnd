package BITAmin.BE.member.service;

import BITAmin.BE.global.security.JwtProvider;
import BITAmin.BE.member.MemberRepository;
import BITAmin.BE.member.dto.auth.SignupReqeustDto;
import BITAmin.BE.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    /**
     * DB에 User정보 저장 + token 발급
     * @param dto
     */
    public void signup(SignupReqeustDto dto){
        String encodedPassword = passwordEncoder.encode(dto.password());
        Member member=dto.toEntity(encodedPassword);
        memberRepository.save(member);
        jwtProvider.createAccessToken(member);
        jwtProvider.createRefreshToken(member);
    }

}

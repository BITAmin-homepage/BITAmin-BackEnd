package BITAmin.BE.member.repository;

import BITAmin.BE.member.dto.member.MemberSearchCondition;
import BITAmin.BE.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberQueryRepository {
    Page<Member> searchByCondition(MemberSearchCondition condition, Pageable pageable);
}

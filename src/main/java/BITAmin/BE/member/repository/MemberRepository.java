package BITAmin.BE.member.repository;

import BITAmin.BE.member.entity.Member;
import BITAmin.BE.member.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    Optional<Member> findByMemberId(Long memberId);
    Optional<Member> deleteByMemberId(Long memberId);
    int countByRole(Role role);

    @Query("SELECT COUNT(DISTINCT m.cohort) FROM Member m WHERE m.cohort IS NOT NULL")
    int countDistinctCohort();
}

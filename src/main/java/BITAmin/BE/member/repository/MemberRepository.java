package BITAmin.BE.member.repository;

import BITAmin.BE.member.entity.Member;
import BITAmin.BE.member.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByMemberId(Long memberId);
    int countByRole(Role role);

    @Query("SELECT COUNT(DISTINCT m.cohort) FROM Member m WHERE m.cohort IS NOT NULL")
    int countDistinctCohort();
}

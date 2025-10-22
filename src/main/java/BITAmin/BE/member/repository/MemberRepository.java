package BITAmin.BE.member.repository;

import BITAmin.BE.member.entity.Member;
import BITAmin.BE.member.enums.Role;
import BITAmin.BE.member.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByUsername(String username);
    Optional<Member> findByMemberId(Long memberId);
    int countByRole(Role role);
    List<Member> findByStatusIn(List<Status> statuses);
    @Query("SELECT COUNT(DISTINCT m.cohort) FROM Member m WHERE m.cohort IS NOT NULL")
    int countDistinctCohort();
}

package BITAmin.BE.member.repository;

import BITAmin.BE.member.dto.member.MemberIntro;
import BITAmin.BE.member.entity.Member;
import BITAmin.BE.member.enums.Role;
import BITAmin.BE.member.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByStatus(Status status);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByUsername(String username);
    Optional<Member> findByMemberId(Long memberId);
    List<Member> findByStatusIn(List<Status> statuses);
    @Query("""
    select new BITAmin.BE.member.dto.member.MemberIntro(
      m.cohort, m.name, m.link1, m.link2, m.depart, m.image
    )
    from Member m
    where m.status = :status
    """)
    List<MemberIntro> findIntroByStatus(@Param("status") Status status);
}

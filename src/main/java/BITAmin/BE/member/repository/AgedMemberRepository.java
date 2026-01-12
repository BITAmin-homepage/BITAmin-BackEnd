package BITAmin.BE.member.repository;

import BITAmin.BE.member.dto.member.MemberIntro;
import BITAmin.BE.member.entity.AgedMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgedMemberRepository extends JpaRepository<AgedMember, Long> {
    @Query("""
        select new BITAmin.BE.member.dto.member.MemberIntro(
            a.cohort,
            a.name,
            a.link1,
            a.link2,
            a.depart,
            a.image
        )
        from AgedMember a
    """)
    List<MemberIntro> findAllIntro();
}
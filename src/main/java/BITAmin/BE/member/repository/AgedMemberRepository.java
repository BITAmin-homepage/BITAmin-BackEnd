package BITAmin.BE.member.repository;

import BITAmin.BE.member.entity.AgedMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgedMemberRepository extends JpaRepository<AgedMember, Long> {

}

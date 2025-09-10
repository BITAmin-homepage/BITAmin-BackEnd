package BITAmin.BE.member.repository;
import BITAmin.BE.member.dto.member.MemberSearchCondition;
import BITAmin.BE.member.entity.Member;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import org.springframework.data.domain.Pageable;
import java.util.List;

import static BITAmin.BE.member.entity.QMember.member;


@Repository
@RequiredArgsConstructor
public class MemberQueryRepositoryImpl implements MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Member> searchByCondition(MemberSearchCondition condition, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(condition.name())) {
            builder.and(member.name.containsIgnoreCase(condition.name()));
        }
        if (StringUtils.hasText(condition.email())) {
            builder.and(member.email.containsIgnoreCase(condition.email()));
        }
        if (StringUtils.hasText(condition.school())) {
            builder.and(member.school.containsIgnoreCase(condition.school()));
        }
        if (condition.cohort() != null && condition.cohort() != 0) {
            builder.and(member.cohort.eq(condition.cohort()));
        }

        List<Member> content = queryFactory
                .selectFrom(member)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(member.memberId.desc())
                .fetch();

        long total = queryFactory
                .select(member.count())
                .from(member)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }
}

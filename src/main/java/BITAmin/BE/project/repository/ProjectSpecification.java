package BITAmin.BE.project.repository;

import BITAmin.BE.project.entity.Project;
import BITAmin.BE.project.enums.Award;
import BITAmin.BE.project.enums.Period;
import org.springframework.data.jpa.domain.Specification;

public class ProjectSpecification {

    public static Specification<Project> hasCohort(String cohort) {
        return (root, query, cb) -> cohort == null ? null : cb.equal(root.get("cohort"), cohort);
    }

    public static Specification<Project> hasPeriod(Period period) {
        return (root, query, cb) -> period == null ? null : cb.equal(root.get("period"), period);
    }

    public static Specification<Project> hasAward(Award award) {
        return (root, query, cb) -> award == null ? null : cb.equal(root.get("award"), award);
    }
}
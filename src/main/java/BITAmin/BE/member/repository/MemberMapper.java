package BITAmin.BE.member.repository;

import BITAmin.BE.member.dto.member.UpdateMemberRequestDto;
import BITAmin.BE.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MemberMapper {
    //@Mapping(target = "name", ignore = true)
    void updateFromDto(UpdateMemberRequestDto dto, @MappingTarget Member member);
}

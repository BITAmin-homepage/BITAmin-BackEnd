package BITAmin.BE.member.mapper;

import BITAmin.BE.member.dto.member.UpdateMemberRequestDto;
import BITAmin.BE.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MemberMapper {
    @Mapping(target = "link1", source = "link1")
    @Mapping(target = "link2", source = "link2")
    void updateFromDto(UpdateMemberRequestDto dto, @MappingTarget Member member);
}

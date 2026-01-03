package com.srt.FreelanceMarketplace.mapper;


import com.srt.FreelanceMarketplace.domain.dto.request.JobTitleRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.JobTitleResponse;
import com.srt.FreelanceMarketplace.domain.entities.JobTitleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface JobTitleMapper {
    JobTitleResponse toResponse(JobTitleEntity entity);
    JobTitleEntity toEntity(JobTitleRequest request);
}

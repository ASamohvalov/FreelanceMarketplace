package com.srt.FreelanceMarketplace.mapper;

import com.srt.FreelanceMarketplace.domain.dto.FreelancerResponse;
import com.srt.FreelanceMarketplace.domain.dto.ServiceResponse;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.ServiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ServiceMapper {
    @Mapping(target = "freelancer", expression = "java(freelancerEntityToResponse(service.getFreelancer()))")
    ServiceResponse serviceEntityToResponse(ServiceEntity service);

    @Mapping(target = "jobTitle", expression = "java(freelancer.getJobTitle().getName())")
    @Mapping(target = "firstName", expression = "java(freelancer.getUser().getFirstName())")
    @Mapping(target = "lastName", expression = "java(freelancer.getUser().getLastName())")
    FreelancerResponse freelancerEntityToResponse(FreelancerEntity freelancer);
}

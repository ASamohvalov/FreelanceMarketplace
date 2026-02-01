package com.srt.FreelanceMarketplace.mapper;

import com.srt.FreelanceMarketplace.domain.dto.request.service.ServiceRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.FreelancerResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.service.ServiceResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.user.UserServiceResponse;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FreelanceMapper {
    @Mapping(target = "freelancer", expression = "java(freelancerEntityToResponse(service.getFreelancer()))")
    ServiceResponse serviceEntityToResponse(ServiceEntity service);

    @Mapping(target = "jobTitle", expression = "java(freelancer.getJobTitle().getName())")
    @Mapping(target = "firstName", expression = "java(freelancer.getUser().getFirstName())")
    @Mapping(target = "lastName", expression = "java(freelancer.getUser().getLastName())")
    FreelancerResponse freelancerEntityToResponse(FreelancerEntity freelancer);

    ServiceEntity serviceRequestToEntity(ServiceRequest response);

    UserServiceResponse entityToUserServiceResponse(ServiceEntity entity);
}

package com.srt.FreelanceMarketplace.service.application.user;

import com.srt.FreelanceMarketplace.domain.dto.response.FreelancerResponse;
import com.srt.FreelanceMarketplace.mapper.FreelanceMapper;
import com.srt.FreelanceMarketplace.repository.FreelancerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FreelancerService {
    private final FreelancerRepository repository;
    private final FreelanceMapper mapper;

    public List<FreelancerResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::freelancerEntityToResponse)
                .toList();
    }
}

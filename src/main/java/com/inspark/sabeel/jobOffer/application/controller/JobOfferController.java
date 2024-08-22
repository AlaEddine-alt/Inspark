package com.inspark.sabeel.jobOffer.application.controller;

import com.inspark.sabeel.jobOffer.application.dto.request.JobOfferRequestDto;
import com.inspark.sabeel.jobOffer.application.dto.response.JobOfferResponseDto;
import com.inspark.sabeel.jobOffer.domain.model.JobOffer;
import com.inspark.sabeel.jobOffer.domain.port.input.JobOfferUseCases;
import com.inspark.sabeel.jobOffer.infrastructure.mapper.JobOfferMapper;
import com.inspark.sabeel.user.domain.model.User;
import com.inspark.sabeel.user.infrastructure.entity.UserEntity;
import com.inspark.sabeel.user.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobOfferController {
    private final JobOfferUseCases jobOfferUseCases;
    private final UserMapper userMapper;
    private final JobOfferMapper jobOfferMapper;

    @GetMapping("/all")
    public ResponseEntity<Page<JobOfferResponseDto>> getJobOffers(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size,
            @RequestParam(defaultValue = "id", required = false) String sort,
            @RequestParam(defaultValue = "", required = false) String criteria,
            @RequestParam(defaultValue = "asc") String sortDirection
            ) {
        Pageable pageable;
        if (sortDirection.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return ResponseEntity.ok(jobOfferUseCases.findAllWithPaginationAndFiltering(criteria, pageable).map(jobOfferMapper::toJobOfferResponseDto));
    }

    @GetMapping("/recommandations")
    public ResponseEntity<Set<JobOfferResponseDto>> getRecommandedJobOffers()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser =(UserEntity) authentication.getPrincipal();
        User user = userMapper.toUser(currentUser);
        return ResponseEntity.ok(jobOfferUseCases.findRecommandedJobs(user).stream().map(jobOfferMapper::toJobOfferResponseDto).collect(Collectors.toSet()));
    }


    @GetMapping("/{id}")
    public ResponseEntity<JobOfferResponseDto> getJobOfferById(@PathVariable UUID id) {
        return ResponseEntity.ok(jobOfferMapper.toJobOfferResponseDto(jobOfferUseCases.findJobOfferById(id)));
    }

    @PostMapping("/create")
    public ResponseEntity<JobOfferResponseDto> createJobOffer(@RequestBody JobOfferRequestDto jobOfferRequestDto) {
        JobOffer createdJobOffer = jobOfferUseCases.createJobOffer(jobOfferMapper.toJobOfferFromDto(jobOfferRequestDto));
        return ResponseEntity.ok(jobOfferMapper.toJobOfferResponseDto(createdJobOffer));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<JobOfferResponseDto> updateJobOffer(@PathVariable UUID id, @RequestBody JobOfferRequestDto jobOfferRequestDto) {
        JobOffer updatedJobOffer = jobOfferUseCases.updateJobOffer(id, jobOfferMapper.toJobOfferFromDto(jobOfferRequestDto));
        return ResponseEntity.ok(jobOfferMapper.toJobOfferResponseDto(updatedJobOffer));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteJobOffer(@PathVariable UUID id) {
        jobOfferUseCases.deleteJobOffer(id);
        return ResponseEntity.ok().build();
    }

}

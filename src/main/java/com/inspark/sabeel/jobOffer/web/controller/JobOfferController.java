package com.inspark.sabeel.jobOffer.web.controller;

import com.inspark.sabeel.jobOffer.domain.model.JobOffer;
import com.inspark.sabeel.jobOffer.domain.port.input.JobOfferUseCases;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
public class JobOfferController {
    private final JobOfferUseCases jobOfferUseCases;

    @GetMapping
    public ResponseEntity<Page<JobOffer>> getJobOffers(
            @RequestParam(required = false) String criteria,
            Pageable pageable) {
        Page<JobOffer> jobOffers = jobOfferUseCases.findAllWithPaginationAndFiltering(criteria, pageable);
        return ResponseEntity.ok(jobOffers);
    }



    @GetMapping("/{id}")
    public ResponseEntity<JobOffer> getJobOfferById(@PathVariable UUID id) {
        JobOffer jobOffer = jobOfferUseCases.findJobOfferById(id);
        return ResponseEntity.ok(jobOffer);
    }

    @PostMapping
    public ResponseEntity<JobOffer> createJobOffer(@RequestBody JobOffer jobOffer) {
        JobOffer createdJobOffer = jobOfferUseCases.createJobOffer(jobOffer);
        return ResponseEntity.ok(createdJobOffer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobOffer> updateJobOffer(@PathVariable UUID id, @RequestBody JobOffer jobOffer) {
        JobOffer updatedJobOffer = jobOfferUseCases.updateJobOffer(id, jobOffer);
        return ResponseEntity.ok(updatedJobOffer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobOffer(@PathVariable UUID id) {
        jobOfferUseCases.deleteJobOffer(id);
        return ResponseEntity.noContent().build();
    }
}

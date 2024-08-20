package com.inspark.sabeel.jobOffer.infrastructure.adapter;

import com.inspark.sabeel.auth.infrastructure.adapter.specifications.UserSpec;
import com.inspark.sabeel.jobOffer.domain.model.JobOffer;
import com.inspark.sabeel.jobOffer.domain.port.output.JobOffers;
import com.inspark.sabeel.jobOffer.infrastructure.entity.JobOfferEntity;
import com.inspark.sabeel.jobOffer.infrastructure.mapper.JobOfferMapper;
import com.inspark.sabeel.jobOffer.infrastructure.repository.JobOfferRepository;
import com.inspark.sabeel.jobOffer.infrastructure.utils.JobRecommendationService;
import com.inspark.sabeel.user.domain.model.User;
import com.inspark.sabeel.user.infrastructure.entity.UserEntity;
import com.inspark.sabeel.user.infrastructure.mapper.UserMapper;
import com.inspark.sabeel.user.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.similarity.JaccardSimilarity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JobOfferJpaAdapter implements JobOffers {
    private final UserRepository userRepository;
    private final JobOfferRepository jobOfferRepository;
    private final JobOfferMapper jobOfferMapper;
    private final JobRecommendationService jobRecommendationService;
    private final UserMapper userMapper;


    @Override
    public Optional<JobOffer> findById(UUID id) {
        return jobOfferRepository.findById(id)
                .map(jobOfferMapper::toJobOffer);
    }

    @Override
    public JobOffer update(JobOffer jobOffer) {
        return jobOfferMapper.toJobOffer(jobOfferRepository.save(jobOfferMapper.toJobOfferEntity(jobOffer)));
    }

    @Override
    public Page<JobOffer> findAll(String criteria, Pageable pageable) {
        return jobOfferRepository.findAll(JobOfferSpec.hasCriteria(criteria), pageable)
                .map(jobOfferMapper::toJobOffer);
    }

    @Override
    public void deleteById(UUID id) {
        jobOfferRepository.deleteById(id);
    }

    @Override
    public JobOffer create(JobOffer jobOffer) {
        return jobOfferMapper.toJobOffer(jobOfferRepository.save(jobOfferMapper.toJobOfferEntity(jobOffer)));
    }

    @Override
    public List<JobOffer> findRecommanded(UUID id) {
        // Retrieve the UserEntity by id
        UserEntity userEntity = userRepository.findById(id).orElseThrow();

        // Map the UserEntity to a User object
        User user = userMapper.toUser(userEntity);

        // Retrieve and map all JobOfferEntity objects to JobOffer objects
        List<JobOffer> jobOfferList = jobOfferRepository.findAll()
                .stream()
                .map(jobOfferMapper::toJobOffer)
                .collect(Collectors.toList());

        // Return the recommended jobs based on the User object
        return jobRecommendationService.recommendJobs(user, jobOfferList);
    }

}

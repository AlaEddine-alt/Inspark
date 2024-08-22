package com.inspark.sabeel.jobOffer.infrastructure.mapper;

import com.inspark.sabeel.jobOffer.application.dto.response.JobOfferResponseDto;
import com.inspark.sabeel.jobOffer.domain.model.JobOffer;
import com.inspark.sabeel.jobOffer.application.dto.request.JobOfferRequestDto;
import com.inspark.sabeel.jobOffer.infrastructure.entity.JobOfferEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class JobOfferMapper {

    public abstract JobOffer toJobOfferFromDto(JobOfferRequestDto jobOfferRequestDto);

    public abstract JobOfferEntity toJobOfferEntity(JobOffer jobOffer);

    public abstract JobOffer toJobOffer(JobOfferEntity jobOfferEntity);

    public abstract JobOfferRequestDto toJobOfferDto(JobOffer jobOffer);
    public abstract JobOfferResponseDto toJobOfferResponseDto(JobOffer jobOffer);

}

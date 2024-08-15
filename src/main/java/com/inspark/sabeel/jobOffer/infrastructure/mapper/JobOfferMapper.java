package com.inspark.sabeel.jobOffer.infrastructure.mapper;

import com.inspark.sabeel.jobOffer.domain.model.JobOffer;
import com.inspark.sabeel.jobOffer.infrastructure.dto.JobOfferDto;
import com.inspark.sabeel.jobOffer.infrastructure.entity.JobOfferEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class JobOfferMapper {

    public abstract JobOffer toJobOfferFromDto(JobOfferDto jobOfferDto);

    public abstract JobOfferEntity toJobOfferEntity(JobOffer jobOffer);

    public abstract JobOffer toJobOffer(JobOfferEntity jobOfferEntity);

    public abstract JobOfferDto toJobOfferDto(JobOffer jobOffer);

}

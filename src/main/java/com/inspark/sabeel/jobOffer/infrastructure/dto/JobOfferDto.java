package com.inspark.sabeel.jobOffer.infrastructure.dto;

import java.util.Date;

public record JobOfferDto(
         String title,
         String description,
         Date date,
         String place
) {
}

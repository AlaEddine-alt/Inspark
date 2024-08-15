package com.inspark.sabeel.jobOffer.infrastructure.entity;

import com.inspark.sabeel.common.BaseEntity;
import com.inspark.sabeel.user.infrastructure.entity.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class JobOfferEntity extends BaseEntity {

    private String title;
    private String description;
    private Date date;
    private String place;

    @ManyToMany(mappedBy = "jobOffers")
    List<UserEntity> users;
}

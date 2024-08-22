package com.inspark.sabeel.jobOffer.infrastructure.entity;

import com.inspark.sabeel.common.BaseEntity;
import com.inspark.sabeel.user.infrastructure.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "job_offer")
public class JobOfferEntity extends BaseEntity {

    private String title;
    private String description;
    private Date date;//to verify
    private String place;
    private String source;
    @Column(name = "nb_posts")
    private String nbPosts;
    private String contact;
    @Column(name = "post_date")
    private String postDate;
    @Column(name = "send_resume_link")
    private String sendResumeLink;

    @ManyToMany(mappedBy = "jobOffers")
    Set<UserEntity> users;
}

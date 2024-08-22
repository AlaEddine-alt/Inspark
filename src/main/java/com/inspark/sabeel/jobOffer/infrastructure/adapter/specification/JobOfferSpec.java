package com.inspark.sabeel.jobOffer.infrastructure.adapter.specification;

import com.inspark.sabeel.jobOffer.infrastructure.entity.JobOfferEntity;
import org.springframework.data.jpa.domain.Specification;

/**
 * Specifications for querying JobOfferEntity.
 */
public class JobOfferSpec {

    /**
     * Creates a specification to find job offers by title.
     *
     * @param title the title to search for
     * @return a specification for finding job offers by title
     */
    public static Specification<JobOfferEntity> hasTitle(String title) {
        return (root, query, cb) -> cb.like(root.get("title"), "%" + title + "%");
    }

    /**
     * Creates a specification to find job offers by description.
     *
     * @param description the description to search for
     * @return a specification for finding job offers by description
     */
    public static Specification<JobOfferEntity> hasDescription(String description) {
        return (root, query, cb) -> cb.like(root.get("description"), "%" + description + "%");
    }

    /**
     * Creates a specification to find job offers by place.
     *
     * @param place the place to search for
     * @return a specification for finding job offers by place
     */
    public static Specification<JobOfferEntity> hasPlace(String place) {
        return (root, query, cb) -> cb.like(root.get("place"), "%" + place + "%");
    }

    /**
     * Creates a specification to find job offers by search criteria.
     * The criteria is matched against title, description, and place.
     * If criteria is null or empty, it returns a specification that matches all records.
     *
     * @param criteria the search criteria
     * @return a specification for finding job offers by criteria
     */
    public static Specification<JobOfferEntity> hasCriteria(String criteria) {
        if (criteria == null || criteria.trim().isEmpty()) {
            // Return a specification that matches all records
            return (root, query, cb) -> cb.conjunction();
        } else {
            // Return a specification based on the criteria
            return (root, query, cb) -> cb.or(
                    cb.like(root.get("title"), "%" + criteria + "%"),
                    cb.like(root.get("description"), "%" + criteria + "%"),
                    cb.like(root.get("place"), "%" + criteria + "%")
            );
        }
    }
}

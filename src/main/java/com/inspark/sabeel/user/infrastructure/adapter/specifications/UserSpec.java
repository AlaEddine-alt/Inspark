package com.inspark.sabeel.user.infrastructure.adapter.specifications;

import com.inspark.sabeel.user.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;
import java.util.UUID;

/**
 * Specifications for querying UserEntity.
 * - root is the entity that we are working with
 * - query is the query that we are building
 * - cb is the criteria builder that we use to build the query
 */
public class UserSpec {


    /**
     * Creates a specification to find a user by email.
     *
     * @param email the email to search for
     * @return a specification for finding a user by email
     */
    public static Specification<UserEntity> hasEmail(String email) {
        return (root, query, cb) -> cb.equal(root.get("email"), email);
    }

    /**
     * Creates a specification to find a user by ID.
     *
     * @param id the ID to search for
     * @return a specification for finding a user by ID
     */
    public static Specification<UserEntity> hasId(UUID id) {
        return (root, query, cb) -> cb.equal(root.get("id"), id);
    }

    /**
     * Creates a specification to find a user by role.
     *
     * @param role the role to search for
     * @return a specification for finding a user by role
     */
    public static Specification<UserEntity> hasRole(String role) {
        return (root, query, cb) -> cb.equal(root.join("roles").get("name"), role);
    }

    /**
     * Creates a specification to find users by a set of roles.
     *
     * @param roles the set of roles to search for
     * @return a specification for finding users by roles
     */
    public static Specification<UserEntity> hasRoles(Set<String> roles) {
        return (root, query, cb) -> root.join("roles").get("name").in(roles);
    }

    /**
     * Creates a specification to find users by a search criteria.
     * The criteria is matched against email, first name, last name, and phone number.
     *
     * @param criteria the search criteria
     * @return a specification for finding users by criteria
     */
    public static Specification<UserEntity> hasCriteria(String criteria) {
        return (root, query, cb) -> cb.or(
                cb.like(root.get("email"), "%" + criteria + "%"),
                cb.like(root.get("firstName"), "%" + criteria + "%"),
                cb.like(root.get("lastName"), "%" + criteria + "%"),
                cb.like(root.get("phoneNumber"), "%" + criteria + "%")
        );
    }
}
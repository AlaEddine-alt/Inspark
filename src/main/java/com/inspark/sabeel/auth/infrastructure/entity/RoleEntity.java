package com.inspark.sabeel.auth.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue()
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

}

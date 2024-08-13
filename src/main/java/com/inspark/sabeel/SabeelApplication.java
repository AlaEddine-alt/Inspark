package com.inspark.sabeel;

import com.inspark.sabeel.auth.infrastructure.entity.RoleEntity;
import com.inspark.sabeel.auth.infrastructure.repository.RolesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@EnableAsync
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SabeelApplication {

    public static void main(String[] args) {
        SpringApplication.run(SabeelApplication.class, args);
    }

    @Bean
    @Transactional
    public CommandLineRunner run(
            RolesRepository rolesRepository
    ) {
        return (args) -> {
            if (rolesRepository.findByName("USER") == null) {
                rolesRepository.save(RoleEntity.builder()
                        .name("USER")
                        .build());
            }
            if (rolesRepository.findByName("ADMIN") == null) {
                rolesRepository.save(RoleEntity.builder()
                        .name("ADMIN")
                        .build());
            }
            if (rolesRepository.findByName("SUPER-ADMIN") == null) {
                rolesRepository.save(RoleEntity.builder()
                        .name("SUPER-ADMIN")
                        .build());
            }
        };
    }

}

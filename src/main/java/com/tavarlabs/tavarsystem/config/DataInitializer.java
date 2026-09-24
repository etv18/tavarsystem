package com.tavarlabs.tavarsystem.config;

import com.tavarlabs.tavarsystem.entity.Individual;
import com.tavarlabs.tavarsystem.entity.User;
import com.tavarlabs.tavarsystem.enums.DocumentType;
import com.tavarlabs.tavarsystem.enums.RoleName;
import com.tavarlabs.tavarsystem.repository.IndividualRepository;
import com.tavarlabs.tavarsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    @Bean
    CommandLineRunner initUser(UserRepository userRepo, IndividualRepository individualRepo){
        return args -> {
            Individual individual = Individual.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .documentType(DocumentType.CEDULA)
                    .documentNumber("732XXXXXXXX")
                    .email(null)
                    .telephone("4019826532")
                    .address("123 main st apt 2, My City, Big State")
                    .build();

            Individual newIndividual = individualRepo.save(individual);

            User user = User.builder()
                    .individualId(newIndividual.getId())
                    .username("jdoe")
                    .password("admin")
                    .role(RoleName.ROLE_OWNER)
                    .isActive(true)
                    .build();

            userRepo.save(user);
        };
    }
}

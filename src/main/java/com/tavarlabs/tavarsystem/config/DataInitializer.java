package com.tavarlabs.tavarsystem.config;

import com.tavarlabs.tavarsystem.entity.Action;
import com.tavarlabs.tavarsystem.entity.Individual;
import com.tavarlabs.tavarsystem.entity.Role;
import com.tavarlabs.tavarsystem.entity.User;
import com.tavarlabs.tavarsystem.enums.DocumentType;
import com.tavarlabs.tavarsystem.enums.RoleName;
import com.tavarlabs.tavarsystem.repository.ActionRepository;
import com.tavarlabs.tavarsystem.repository.IndividualRepository;
import com.tavarlabs.tavarsystem.repository.RoleRepository;
import com.tavarlabs.tavarsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initUser(
            UserRepository userRepo,
            IndividualRepository individualRepo,
            RoleRepository roleRepo,
            ActionRepository actionRepo
    ){
        return args -> {
            User savedAdmin = userRepo.findUndeletedByUsername("admin").orElse(null);
            if(savedAdmin != null) return;

            Individual individual = Individual.builder()
                    .firstName("JOHN")
                    .lastName("DOE")
                    .documentType(DocumentType.CEDULA)
                    .documentNumber("732XXXXXXXX")
                    .email(null)
                    .telephone("4019826532")
                    .address("123 main st apt 2, My City, Big State".toUpperCase())
                    .build();

            //PERSISTING INDIVIDUAL
            Individual newIndividual = individualRepo.save(individual);

            // Setting up roles
            List<Role> roles = new ArrayList<>();
            for(RoleName role : RoleName.values()){
                roles.add(new Role(role.name()));
            }

            // Setting up actions
            Set<String> actionCodes = Set.of("ACTION_0", "ACTION_1", "ACTION_2", "ACTION_3", "ACTION_4", "EX_ACTION_1", "EX_ACTION_2");
            Set<Action> actions = new HashSet<>();
            for(String ac : actionCodes){
                actions.add(new Action(ac.toUpperCase()));
            }

            // PERSISTING ACTIONS
            List<Action> savedActions = actionRepo.saveAll(actions);


            // PERSISTING ROLES
            // Setting up roles: assigning actions
            roles.get(0).setActions(new HashSet<>(savedActions)); // ROLE_ADMIN
            roles.get(1).setActions(new HashSet<>(List.of(savedActions.get(0), savedActions.get(1), savedActions.get(2)))); // ROLE_OWNER
            roles.get(2).setActions(new HashSet<>(List.of(savedActions.get(3), savedActions.get(4)))); // ROLE_CASHIER
            List<Role> savedRoles = roleRepo.saveAll(roles);

            // Setting up user
            User user = User.builder()
                    .individual(newIndividual)
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .role(savedRoles.getFirst())
                    .actions(new HashSet<>(List.of(savedActions.get(5), savedActions.get(6))))
                    .isActive(true)
                    .build();

            userRepo.save(user);

            System.out.println("---------------> User data saved!!");
        };
    }
}

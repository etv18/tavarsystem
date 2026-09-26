package com.tavarlabs.tavarsystem.entity;

import com.tavarlabs.tavarsystem.enums.RoleName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(nullable = false, length = 300)
    private String password;

    @Column(nullable = false)
    private RoleName role;

    private boolean isActive = false;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /* Relationships */

    /*
     * User is the owning side of the one-to-one relationship because the
     * app_user table physically stores the foreign key (individual_id).
     *
     * @JoinColumn tells Hibernate that the "individual" association is mapped
     * through the app_user.individual_id column, which references individual.id object.
     *
     * Conceptually, when Hibernate needs to join these entities, the SQL
     * relationship is:
     *
     * SELECT au.*, i.*
     * FROM app_user au
     * JOIN individual i ON i.id = au.individual_id;
     */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "individual_id", nullable = false, unique = true)
    private Individual individual;

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}

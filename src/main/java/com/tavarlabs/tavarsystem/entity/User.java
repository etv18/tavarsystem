package com.tavarlabs.tavarsystem.entity;

import com.tavarlabs.tavarsystem.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "public_id", nullable = false, unique = true, updatable = false)
    private UUID publicId;

    @Column(nullable = false, length = 100, unique = true)
    private String username;

    @Column(nullable = false, length = 300)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = false;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted = false;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /* Relationships */

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserAction> actions = new HashSet<>();

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

        if(this.publicId == null){
            this.publicId = UUID.randomUUID();
        }
    }
}

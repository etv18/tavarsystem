package com.tavarlabs.tavarsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "user_action",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "action_id"})
)
public class UserAction {

    public UserAction(User user, Action action, boolean granted){
        this.user = user;
        this.action = action;
        this.granted = granted;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) // TODO: Look info about: why optional, why JoinColumn after ManyToOne annotation
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "action_id")
    private Action action;

    @Column(nullable = false)
    private boolean granted;
}

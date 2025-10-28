package com.ojasaar.fairshareapi.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ojasaar.fairshareapi.util.IdGenerator;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @Column(name = "user_id", unique = true, updatable = false, length = 6)
    private String id;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Group> groups;

    // Generate the id right before persisting to db
    @PrePersist
    public void addId() {
        if (this.id == null) {
            this.id = IdGenerator.generateId();
        }
    }
}

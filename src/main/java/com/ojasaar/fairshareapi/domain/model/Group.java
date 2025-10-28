package com.ojasaar.fairshareapi.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ojasaar.fairshareapi.util.IdGenerator;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "GROUPS") // consider renaming to "groups" to avoid quoting/case issues
public class Group {
    @Id
    @Column(name = "group_id", unique = true, updatable = false, length = 6)
    private String id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User owner;

    @PrePersist
    public void addId() {
        if (this.id == null) {
            this.id = IdGenerator.generateId();
        }
    }
}

package com.ojasaar.fairshareapi.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ojasaar.fairshareapi.util.IdGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Equivalent to @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode.
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "GROUPS")
public class Group {
    @Id
    @Column(name = "group_id", unique = true, updatable = false, length = 6)
    private String id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @PrePersist
    public void addId() {
        if (this.id == null) {
            this.id = IdGenerator.generateId();
        }
    }
}

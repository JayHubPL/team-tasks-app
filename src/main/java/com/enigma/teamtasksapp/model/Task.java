package com.enigma.teamtasksapp.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "task")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id"
)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    @Column(
        nullable = false,
        unique = true,
        updatable = false
    )
    private UUID uuid;

    @Column(
        nullable = false
    )
    private String title;

    @Column(
        nullable = false
    )
    private String description;

    @Column(
        nullable = true
    )
    private LocalDateTime completionDate;

    @ManyToMany(mappedBy = "tasks")
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<User> users = new HashSet<>();

    @Column
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.TODO;

    public void assignUser(User user) {
        users.add(user);
        user.getTasks().add(this);
    }

    public void removeUser(User user) {
        users.remove(user);
        user.getTasks().remove(this);
    }

    public enum Status {
        TODO,
        WORK_IN_PROGRESS,
        COMPLETED,
    }

}
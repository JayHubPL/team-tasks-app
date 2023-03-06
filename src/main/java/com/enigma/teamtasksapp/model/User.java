package com.enigma.teamtasksapp.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
@Table(name = "usr")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id"
)
public class User {

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
    private String name;

    @Column(
        nullable = false
    )
    private String surname;

    @Column(
        nullable = false,
        unique = true
    )
    private String email;

    @ManyToMany(
        cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    @JoinTable(
        name = "usr_task",
        joinColumns = @JoinColumn(name = "usr_id"),
        inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<Task> tasks = new HashSet<>();

    public void assignTask(Task task) {
        tasks.add(task);
        task.getUsers().add(this);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        task.getUsers().remove(this);
    }

}
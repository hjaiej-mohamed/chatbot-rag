package com.ia.chatbot.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="t_roles")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    //@ManyToMany
    //@JoinTable(name="t_roles_permissions",
     //   joinColumns = @JoinColumn(name="role_id"),
      //  inverseJoinColumns =@JoinColumn(name="permission_id"))
    //private Set<Permission> permissions = new HashSet<>();

}

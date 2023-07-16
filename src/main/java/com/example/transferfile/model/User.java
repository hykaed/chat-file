package com.example.transferfile.model;

import com.example.transferfile.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private boolean loggedIn;
    private boolean active;
    @OneToMany(mappedBy = "user")
    private List<FileEntity> files;
}



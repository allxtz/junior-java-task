package com.example.juniorjavatask.ORM;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false, length = 45)
    private String username;

    @Column(name = "password", nullable = false, length = 45)
    private String password;

    @Column(name = "permission", nullable = false, length = 45)
    private String role;

    @Column(name = "readonly", nullable = false, length = 45)
    private String readonly;

    public UserEntity(String username, String password, String role, String ronly) {
        this.username = username;
        this.password = password;
        this.role = role.toUpperCase();
        this.readonly = ronly;
    }
}
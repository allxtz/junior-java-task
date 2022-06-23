package com.example.juniorjavatask.ORM;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@Entity
@Table(name = "blog")

public class BlogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userid", nullable = false)
    private com.example.juniorjavatask.ORM.UserEntity userid;

    @Column(name = "text", nullable = false)
    private String text;

    public BlogEntity(String text) {
        this.text=text;
    }
}
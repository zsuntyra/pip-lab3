package com.suntyra.pip.lab3.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String password;

    @OneToMany(mappedBy = "user", targetEntity = Point.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Point> points;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        points = new ArrayList<>();
    }
}

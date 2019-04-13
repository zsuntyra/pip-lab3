package com.suntyra.pip.lab3.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "points")
@Getter
@Setter
@NoArgsConstructor
public class Point implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private Double x;
    private Double y;
    private Double r;
    private Boolean isInArea;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Point(Double x, Double y, Double r, Boolean isInArea, User user) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.isInArea = isInArea;
        this.user = user;
    }
}

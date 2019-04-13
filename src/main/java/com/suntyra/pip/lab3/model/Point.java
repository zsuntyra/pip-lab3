package com.suntyra.pip.lab3.model;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class Point implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private Double x;
    private Double y;
    private Double r;
    private Boolean isInArea;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private User user;
}

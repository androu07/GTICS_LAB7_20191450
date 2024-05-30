package com.example.lab7.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Entity
@Getter
@Setter
@Table(name="players")
public class Jugadores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="player_id")
    private int id;
    @Column(nullable = false)
    private String name;
    private BigInteger mmr;
    private Integer position;
    private String region;
}

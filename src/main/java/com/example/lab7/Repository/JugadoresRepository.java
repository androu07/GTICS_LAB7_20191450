package com.example.lab7.Repository;

import com.example.lab7.entity.Jugadores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JugadoresRepository extends JpaRepository<Jugadores, Integer> {
}

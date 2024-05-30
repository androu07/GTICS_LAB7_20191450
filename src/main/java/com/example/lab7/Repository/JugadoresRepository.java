package com.example.lab7.Repository;

import com.example.lab7.entity.Jugadores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JugadoresRepository extends JpaRepository<Jugadores, Integer> {

    @Query(value = "SELECT * FROM lab7.players\n" +
            "where mmr > 6500 and (region = 'Américas' or region = 'Europa' or region = 'SE Asíatico' or region = 'China');", nativeQuery = true)
    List<Jugadores> jugadoresConLosRequisitosMinParaElLB();

}

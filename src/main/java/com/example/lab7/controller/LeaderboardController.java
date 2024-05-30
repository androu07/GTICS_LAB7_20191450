package com.example.lab7.controller;

import com.example.lab7.Repository.JugadoresRepository;
import com.example.lab7.entity.Jugadores;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class LeaderboardController {

    final
    JugadoresRepository jugadoresRepository;

    public LeaderboardController(JugadoresRepository jugadoresRepository) {
        this.jugadoresRepository = jugadoresRepository;
    }

    @GetMapping("/dota/jugadores/leaderboard")
    public List<Jugadores> listaLeaderboard(){

        List<Jugadores> jugadoresConLosRequisitosMinParaElLB = jugadoresRepository.jugadoresConLosRequisitosMinParaElLB();


        return jugadoresConLosRequisitosMinParaElLB;
    }

    @PostMapping("/dota/jugadores/save")
    public ResponseEntity<HashMap<String, Object>> agregarJugador(
            @RequestBody Jugadores jugadores,
            @RequestParam(value = "fetchid", required = false) boolean fetchId) {

        HashMap<String, Object> responseMap = new HashMap<>();

        jugadoresRepository.save(jugadores);
        if(fetchId){
            responseMap.put("ID", jugadores.getId());
        }
        responseMap.put("jugador agregado con exito", jugadores);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);

    }

    @PutMapping("/dota/jugadores/save")
    public ResponseEntity<HashMap<String, Object>> actualizarJugador(@RequestBody Jugadores jugadores) {

        HashMap<String, Object> responseMap = new HashMap<>();

        Optional<Jugadores> optJugadores = jugadoresRepository.findById(jugadores.getId());
        if(optJugadores.isPresent()){
            jugadoresRepository.save(jugadores);
            responseMap.put("jugador actualizado con exito", jugadores);
            return ResponseEntity.ok(responseMap);
        }
        else{
            responseMap.put("error", "El jugador para actualizar no existe.");
            return ResponseEntity.badRequest().body(responseMap);
        }
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String, String>> gestionExcepcion(HttpServletRequest request) {
        HashMap<String, String> responseMap = new HashMap<>();
        if(request.getMethod().equals("POST") || request.getMethod().equals("PUT")){
            responseMap.put("error", "Error en validación de Datos.");
        }
        return ResponseEntity.badRequest().body(responseMap);

    }

    @DeleteMapping("/dota/jugadores/delete/{id}")
    public ResponseEntity<HashMap<String, Object>> eliminarJugador(@PathVariable("id") String idStr){
        HashMap<String, Object> responseMap = new HashMap<>();

        try {
            int id = Integer.parseInt(idStr);
            if(jugadoresRepository.existsById(id)){
                jugadoresRepository.deleteById(id);
                responseMap.put("delete", "jugador eliminado con exito.");
                return ResponseEntity.ok(responseMap);
            }
            else {
                responseMap.put("error", "ID de jugador no encontrado.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMap);
            }
        }
        catch(NumberFormatException ex){
            responseMap.put("error", "ID de jugador no encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMap);
        }
    }

}


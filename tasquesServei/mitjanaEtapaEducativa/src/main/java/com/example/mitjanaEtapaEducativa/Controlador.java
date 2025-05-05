package com.example.mitjanaEtapaEducativa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class Controlador {

    private final Servei servei;

    public Controlador(Servei servei) {
        this.servei = servei;
    }

    @PostMapping("/mitjanaEtapaEducativa")
    public ResponseEntity<Map<String, Double>> assignaNotaGlobal(@RequestBody Map<String, Double> entradaNovaNota) {
        Double notaE = entradaNovaNota.get("novaNota"); //6.33, 9.66
        System.out.println("Nova nota "+notaE);
        Map<String, Double> notaMitjana = servei.calculNotaMitjana(notaE);
        return new ResponseEntity<>(notaMitjana, HttpStatus.OK);
    }

}

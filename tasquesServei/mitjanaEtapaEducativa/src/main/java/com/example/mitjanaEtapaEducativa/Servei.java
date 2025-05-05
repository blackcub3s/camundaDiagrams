package com.example.mitjanaEtapaEducativa;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class Servei {

    //PRE: una nova nota de l'assignatura d'un estudiant que volem afegir
    //     al còmput de mitjanes de diverses assignatures que ja són dins arrNotes.
    //POST: map<String, Double> de tipus {"nota": 4.4252356} on el valor es la
    //      mitjana aritmètica d'arrNotes
    public Map<String, Double> calculNotaMitjana(Double novaNota) {
        HashMap<String, Double> notaMitjana = new HashMap<>();

        Double[] arrNotes = {8.33, 1.43, 8.5, 10.0, novaNota};
        String[] arrAssig = {"mates", "llatí", "Català", "Física", "novaAssignatura"};

        int nreNotes = arrNotes.length;
        Double numeradorMitjana = 0.0;
        for (int i = 0; i < nreNotes; ++i) {
            numeradorMitjana += arrNotes[i];
        }

        notaMitjana.put("notaMitjana", numeradorMitjana/nreNotes);
        return notaMitjana; //{"notaMitjana" : 1.33}
    }
}

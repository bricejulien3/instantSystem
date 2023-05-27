package com.bricejulien.instantSystem.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.bricejulien.instantSystem.models.Parking;
import com.bricejulien.instantSystem.services.ParkingService;

@RestController
@RequestMapping("/parkings")
public class ParkingController {

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    // exemple de requete:
    // http://localhost:8080/parkings/nearby?latitude=46.58922605070947&longitude=0.342201120082188
    @GetMapping("/nearby")
    public List<Parking> getParkingsNearby(@RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude) {
        // Utilisation des coordonnées de latitude et longitude passées en paramètres
        // pour récupérer les parkings à proximité du point spécifié
        // et afficher le nombre de places dispo et leur distance depuis la position de
        // l'utilisateur
        // coordonnées géo: permet la réutilisation dans n'importe quelle ville.
        List<Parking> nearbyParkings = parkingService.getParkings(latitude, longitude);

        return nearbyParkings;
    }
}

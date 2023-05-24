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

    @GetMapping("/nearby")
    public List<Parking> getParkingsNearby(@RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude) {
        // Appel de votre service ParkingService pour obtenir les parkings à proximité
        List<Parking> nearbyParkings = parkingService.getParkings(latitude, longitude);

        return nearbyParkings;
    }
}

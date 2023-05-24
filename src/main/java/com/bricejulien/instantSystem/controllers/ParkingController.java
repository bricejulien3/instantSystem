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

    @GetMapping
    public List<Parking> getAllParkings() {
        return parkingService.getParkings();
    }
}

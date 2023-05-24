package com.bricejulien.instantSystem.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bricejulien.instantSystem.models.Parking;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ParkingService {

    private static final String PARKING_DATA_URL = "https://data.grandpoitiers.fr/api/records/1.0/search/?dataset=mobilite-parkings-grand-poitiers-donnees-metiers&rows=1000&facet=nom_du_parking&facet=zone_tarifaire&facet=statut2&facet=statut3";
    private static final String PARKING_AVAILABILITY_URL = "https://data.grandpoitiers.fr/api/records/1.0/search/?dataset=mobilites-stationnement-des-parkings-en-temps-reel&facet=nom";

    public List<Parking> getParkings() {
        List<Parking> parkingList = new ArrayList<>();

        try {
            String parkingDataJson = getJsonResponse(PARKING_DATA_URL);
            parkingList = parseParkingData(parkingDataJson);

            String parkingAvailabilityJson = getJsonResponse(PARKING_AVAILABILITY_URL);
            updateParkingAvailability(parkingList, parkingAvailabilityJson);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return parkingList;
    }

    private String getJsonResponse(String url) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }

    private List<Parking> parseParkingData(String jsonData) throws IOException {
        List<Parking> parkingList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode recordsNode = rootNode.get("records");

        if (recordsNode.isArray()) {
            for (JsonNode recordNode : recordsNode) {
                JsonNode fieldsNode = recordNode.get("fields");
                if (fieldsNode != null) {
                    String parkingName = getTextValue(fieldsNode, "nom_du_par");
                    String zoneTarifaire = getTextValue(fieldsNode, "zone_tarifaire");
                    String id = getTextValue(fieldsNode, "id");
                    int availableSpaces = getIntValue(fieldsNode, "nb_places");
                    Parking parking = new Parking(id, parkingName, zoneTarifaire, availableSpaces);
                    parkingList.add(parking);
                }
            }
        }

        return parkingList;
    }

    private void updateParkingAvailability(List<Parking> parkingList, String availabilityJson)
            throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(availabilityJson);
        JsonNode recordsNode = rootNode.get("records");

        if (recordsNode.isArray()) {
            for (JsonNode recordNode : recordsNode) {
                JsonNode fieldsNode = recordNode.get("fields");
                String parkingName = getTextValue(fieldsNode, "nom");
                int availableSpaces = getIntValue(fieldsNode, "places");

                if (availableSpaces < 1) {
                    continue;
                }
                for (Parking parking : parkingList) {
                    if (parking.getName().equals(parkingName)) {
                        parking.setAvailableSpaces(availableSpaces);
                        break;
                    }
                }
            }
        }
    }

    private String getTextValue(JsonNode node, String fieldName) {
        if (node.has(fieldName) && !node.get(fieldName).isNull()) {
            return node.get(fieldName).asText();
        }
        return "";
    }

    private int getIntValue(JsonNode node, String fieldName) {
        if (node.has(fieldName) && !node.get(fieldName).isNull()) {
            return node.get(fieldName).asInt();
        }
        return 0;
    }
}
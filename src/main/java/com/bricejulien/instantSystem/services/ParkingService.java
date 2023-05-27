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
    private static final double MAX_DISTANCE = 400; // Distance maximale en mètres

    public List<Parking> getParkings(double userLatitude, double userLongitude) {
        List<Parking> parkingList = new ArrayList<>();

        try {
            String parkingDataJson = getJsonResponse(PARKING_DATA_URL);
            parkingList = parseParkingData(parkingDataJson, userLatitude, userLongitude);

            String parkingAvailabilityJson = getJsonResponse(PARKING_AVAILABILITY_URL);
            updateParkingAvailability(parkingList, parkingAvailabilityJson);
            // showOnlyNearbyParkings(parkingList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return parkingList;
    }

    // Effectue une requête HTTP GET et obtient une réponse JSON
    private String getJsonResponse(String url) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }

    // Récupère la liste des parkings et quelques attributs (position notamment)
    private List<Parking> parseParkingData(String jsonData, double userLatitude, double userLongitude)
            throws IOException {
        List<Parking> parkingList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode recordsNode = rootNode.get("records");

        if (recordsNode.isArray()) {
            for (JsonNode recordNode : recordsNode) {
                JsonNode fieldsNode = recordNode.get("fields");
                if (fieldsNode != null) {

                    double parkingLatitude = getDoubleValue(fieldsNode, "ylat");
                    double parkingLongitude = getDoubleValue(fieldsNode, "xlong");

                    // Calcul de la distance entre les coordonnées de l'utilisateur et celles du
                    // parking
                    double distance = calculateDistance(userLatitude, userLongitude, parkingLatitude, parkingLongitude);

                    String parkingName = getTextValue(fieldsNode, "nom_du_par");
                    String id = getTextValue(fieldsNode, "id");
                    int availableSpaces = getIntValue(fieldsNode, "nb_places");

                    // Création d'un objet Parking avec les informations récupérées
                    Parking parking = new Parking(id, parkingName, availableSpaces, availableSpaces, distance);
                    parkingList.add(parking);
                }
            }
        }

        return parkingList;
    }

    // Méthode pour mettre à jour la disponibilité des parkings en utilisant les
    // données en temps réel
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
                int capacity = getIntValue(fieldsNode, "capacite");

                // si le parking n'a pas de disponibilité, il est ignoré
                if (availableSpaces < 1) {
                    continue;
                }

                // Recherche du parking correspondant dans la liste et mise à jour de sa
                // disponibilité
                for (Parking parking : parkingList) {
                    if (parking.getName().equals(parkingName)) {
                        parking.setAvailableSpaces(availableSpaces);
                        parking.setCapacity(capacity);
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

    private double getDoubleValue(JsonNode node, String fieldName) {
        if (node.has(fieldName) && !node.get(fieldName).isNull()) {
            return node.get(fieldName).asDouble();
        }
        return 0;
    }

    // Méthode pour calculer la distance entre deux coordonnées géographiques en
    // utilisant la formule de la distance haversine
    private double calculateDistance(double userLat, double userLong, double parkingLat, double parkingLong) {
        double latDistance = Math.toRadians(parkingLat - userLat);
        double lonDistance = Math.toRadians(parkingLong - userLong);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(parkingLat))
                        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // 6371 = Rayon moyen de la Terre en kilomètres
        double distance = 6371 * c * 1000; // Conversion en mètres

        return distance;
    }

    // Méthode pour filtrer la liste des parkings et ne conserver que ceux qui sont
    // à proximité de l'utilisateur
    private void showOnlyNearbyParkings(List<Parking> parkingList) {
        parkingList.removeIf(p -> p.getDistanceToUser() > MAX_DISTANCE);
    }
}
# Service de parkings à proximité

Application “serveur” exposant une API REST qui affiche la liste des parkings à proximité
Ce service permet d'obtenir une liste de parkings à proximité de l'utilisateur en fonction de sa position géographique.

## Fonctionnalités

Le service offre les fonctionnalités suivantes :

- Récupérer les parkings à proximité : En utilisant les coordonnées de latitude et de longitude de l'utilisateur, le service appelle un service externe pour obtenir les données des parkings disponibles. Les données sont ensuite filtrées pour ne conserver que les parkings à une distance maximale prédéfinie de l'utilisateur.

## Utilisation

Le service est accessible via une requête HTTP GET à l'URL suivante :
`http://localhost:8080/parkings/nearby?latitude=<latitude>&longitude=<longitude>`

Remplacez `<latitude>` et `<longitude>` par les coordonnées géographiques de l'utilisateur.

### Exemple de requête

`http://localhost:8080/parkings/nearby?latitude=46.58922605070947&longitude=0.342201120082188`

Cette requête récupère les parkings à proximité de l'utilisateur situé aux coordonnées géographiques (latitude: 46.58922605070947, longitude: 0.342201120082188).

## Configuration

Le service utilise les URLs suivantes pour récupérer les données des parkings :

- URL des données des parkings : [https://data.grandpoitiers.fr/api/records/1.0/search/?dataset=mobilite-parkings-grand-poitiers-donnees-metiers&rows=1000&facet=nom_du_parking&facet=zone_tarifaire&facet=statut2&facet=statut3](https://data.grandpoitiers.fr/api/records/1.0/search/?dataset=mobilite-parkings-grand-poitiers-donnees-metiers&rows=1000&facet=nom_du_parking&facet=zone_tarifaire&facet=statut2&facet=statut3)
- URL de disponibilité des parkings en temps réel : [https://data.grandpoitiers.fr/api/records/1.0/search/?dataset=mobilites-stationnement-des-parkings-en-temps-reel&facet=nom](https://data.grandpoitiers.fr/api/records/1.0/search/?dataset=mobilites-stationnement-des-parkings-en-temps-reel&facet=nom)

Assurez-vous que ces URLs sont accessibles et à jour dans la configuration du service.

## Dépendances

Le service utilise les dépendances suivantes :

- Spring Framework : Pour la création de l'API REST avec Spring Boot.
- Jackson : Pour la manipulation des données JSON.
- RestTemplate : Pour effectuer des requêtes HTTP.

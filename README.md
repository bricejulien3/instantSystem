# Service de parkings à proximité

Application “serveur” exposant une API REST qui affiche la liste des parkings à proximité
Ce service permet d'obtenir une liste de parkings à proximité de l'utilisateur en fonction de sa position géographique.

## Fonctionnalités

Le service offre les fonctionnalités suivantes :

- Récupérer les parkings à proximité : En utilisant les coordonnées de latitude et de longitude de l'utilisateur, le service appelle un service externe pour obtenir les données des parkings disponibles. Les données sont ensuite filtrées pour ne conserver que les parkings à une distance maximale prédéfinie de l'utilisateur.

## Choix technologiques

Les choix technologiques pour ce projet sont les suivants :

- **Spring Boot** : Nous avons choisi Spring Boot pour le développement de ce service en raison de sa simplicité de configuration et de déploiement, ainsi que de son intégration étroite avec le framework Spring, ce qui facilite la création d'une API REST.
- **RestTemplate** : Nous utilisons RestTemplate, fourni par Spring, pour effectuer des requêtes HTTP vers les services externes et récupérer les données des parkings. Cela nous permet de consommer facilement les API REST et de manipuler les réponses JSON.
- **Jackson** : Jackson est une bibliothèque Java pour la manipulation des données JSON. Nous l'utilisons ici pour la désérialisation des réponses JSON des services externes en objets Java (parkings).
- **Spring Framework** : Nous utilisons également d'autres composants du Spring Framework pour faciliter le développement de l'application, notamment l'injection de dépendances via les constructeurs.

## Problèmes identifiés

Bien que ce service permette de récupérer les parkings à proximité de l'utilisateur, il convient de noter certains problèmes qui n'ont pas été traités dans cette implémentation :

- **Authentification et sécurité** : Ce service n'inclut pas de mécanismes d'authentification ou de sécurité. Pour une utilisation en production, il serait important d'implémenter des mesures de sécurité appropriées, telles que l'authentification des utilisateurs, la validation des requêtes, etc.
- **Gestion des erreurs** : Bien que certaines exceptions soient gérées dans le code, une gestion plus robuste des erreurs pourrait être mise en place pour fournir des réponses plus précises et des messages d'erreur significatifs en cas de problèmes.
- **Optimisation des performances** : Si le nombre de parkings ou d'utilisateurs augmente considérablement, des améliorations pourraient être apportées pour optimiser les performances, telles que la mise en cache des données ou l'utilisation de requêtes asynchrones.

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

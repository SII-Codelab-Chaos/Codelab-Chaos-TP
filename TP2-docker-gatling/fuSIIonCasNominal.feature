Feature: FuSIIon cas nominal
Scenario: Connection a FuSIIon puis parcours sur l'application

Given Soit un utilisateur de FuSIIon
And un username "pgaultier@sii.fr"
And un password "password"
When l'utilisateur se connecte via la mire d'authentification de FuSIIon avec ses identifiants
Then il récupere un token d'authentification JWT

Given Soit un utilisateur de FuSIIon avec un token d'authentification
When l'utilisateur demande la liste des collaborateurs
Then il récupere une liste de collaborateurs

Given Soit un utilisateur de FuSIIon avec un token d'authentification
When l'utilisateur demande la liste des competences
Then il récupere une liste de competence

Given Soit un utilisateur de FuSIIon avec un token d'authentification
When l'utilisateur demande le profil d'un collaborateur avec son identifiant
Then il récupere le collaborateur correspondant à son identifiant 

Given Soit un utilisateur de FuSIIon avec un token d'authentification
When l'utilisateur crée une nouvelle competence "Docker"
Then il récupere un code retour "200 OK"

Given Soit un utilisateur de FuSIIon avec un token d'authentification
When l'utilisateur associe la competence "Docker" à son utilisateur avec une notation de "5"
Then il récupere un code retour "200 OK"

Given Soit un utilisateur de FuSIIon avec un token d'authentification
When l'utilisateur demande le profil d'un collaborateur avec son identifiant
Then il récupere le collaborateur correspondant à son identifiant 

Given Soit un utilisateur de FuSIIon avec un token d'authentification
When l'utilisateur demande la liste des clients
Then il récupere une liste de clients

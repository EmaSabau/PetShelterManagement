#INTEROGARI
USE AdapostAnimale;

#Afișarea tuturor animalelor și adăposturilor asociate
SELECT Animal.*, Adapost.*
FROM Animal
JOIN Adapost ON Animal.id_adapost = Adapost.id_adapost;


#Afișarea informațiilor despre donații și donatori
SELECT Donatie.*, Donator.*
FROM Donatie
JOIN Donator ON Donatie.id_donator = Donator.id_donator;

#Afișarea detaliilor despre animalele care au avut tratament veterinar
SELECT Animal.*, IstoricVeterinar.*
FROM Animal
JOIN Animal_Istoric ON Animal.id_animal = Animal_Istoric.id_animal
JOIN IstoricVeterinar ON Animal_Istoric.id_istoric = IstoricVeterinar.id_istoric;


#Calcularea bugetului total și a sumei donate
SELECT SUM(buget) AS buget_total, SUM(intrari) AS sum_donate
FROM Fonduri;

#Trigger-e

USE AdapostAnimale;

#Trigger pentru actualizarea automată a bugetului în tabelul Fonduri

DELIMITER //

CREATE TRIGGER ActualizareBuget
AFTER INSERT ON Donatie
FOR EACH ROW
BEGIN
    UPDATE Fonduri
    SET intrari = intrari + NEW.suma_donata,
        buget = buget + NEW.suma_donata;
END //

DELIMITER ;


#Trigger pentru gestionarea istoricului veterinar al animalelor in tabelul IstoricVeterinar
DELIMITER //

CREATE TRIGGER GestionareIstoricVeterinar
AFTER INSERT ON Animal_Istoric
FOR EACH ROW
BEGIN
    DECLARE detalii_veterinare TEXT;

    -- Obține detalii veterinarare asociate animalului
    SELECT detalii_veterinare INTO detalii_veterinare
    FROM IstoricVeterinar
    WHERE id_istoric = NEW.id_istoric
    LIMIT 1;

    -- Actualizează istoricul veterinar cu noile detalii
    UPDATE IstoricVeterinar
    SET detalii_veterinare = CONCAT(detalii_veterinare, ' | ', 'Noi detalii veterinarare: ', NEW.id_animal)
    WHERE id_istoric = NEW.id_istoric;
END //

DELIMITER ;

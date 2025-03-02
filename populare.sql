#Populare 

USE AdapostAnimale;

-- Populare tabela Adapost
INSERT INTO Adapost (numar_locuri, nume_adapost, adresa_adapost) VALUES
    (20, 'Casuta Fericirii', 'Strada Pufoseniei, Nr. 42'),
    (30, 'Hotel Animalute', 'Strada Blănițelor, Nr. 7'),
    (15, 'Adapostul Zambaret', 'Strada Cozorocului, Nr. 13'),
    (25, 'Coliba Prieteniei', 'Strada Mișcotoarei, Nr. 21'),
    (10, 'Prietenii Pufosi', 'Strada Codobelcilor, Nr. 3');

-- Populare tabela Proprietar
INSERT INTO Proprietar (id_adapost, nume_proprietar, adresa_proprietar, telefon_proprietar) VALUES
    (1, 'Ileana Pufuleț', 'Strada Florilor, Nr. 25', '123456789'),
    (2, 'Marian Blănosu', 'Strada Veverițelor, Nr. 14', '987654321'),
    (1, 'Elena Codobelcu', 'Strada Lătrătoarei, Nr. 33', '555666777'),
    (3, 'Victor Cârca', 'Strada Mustăților, Nr. 9', '111222333'),
    (2, 'Ana Pufoaică', 'Strada Pernuțelor, Nr. 11', '444333222');

-- Populare tabela Loc
INSERT INTO Loc (nume_loc, stare, id_adapost) VALUES
    ('Hamaca Pufășească', 'Liber', 1),
    ('Igloo Blănos', 'Ocupat', 2),
    ('Pat Pufos', 'Liber', 3),
    ('Cuibul Vesel', 'Ocupat', 1),
    ('Casa Liniștită', 'Liber', 2);

-- Populare tabela Donator
INSERT INTO Donator (nume_donator, adresa_donator, telefon_donator) VALUES
    ('Florin Moale', 'Strada Pufăneilor, Nr. 55', '111000111'),
    ('Andreea Veveriță', 'Strada Ciufuților, Nr. 27', '222000222'),
    ('Radu Cozoroc', 'Strada Lebedelor, Nr. 18', '333000333'),
    ('Maria Mustățilă', 'Strada Blândă, Nr. 44', '444000444'),
    ('Ionel Bufniță', 'Strada Moțăielor, Nr. 8', '555000555');

-- Populare tabela Fonduri
INSERT INTO Fonduri (buget, intrari, iesiri) VALUES
    (12000, 5500, 2300),
    (18000, 7000, 3000),
    (10000, 5000, 2000),
    (20000, 8000, 3500),
    (15000, 6000, 2500);

-- Populare tabela Donatie
INSERT INTO Donatie (id_donator, id_fond, suma_donata) VALUES
    (1, 1, 500.00),
    (2, 2, 800.00),
    (3, 3, 300.00),
    (4, 4, 1000.00),
    (5, 5, 600.00);

-- Populare tabela Sponsor
INSERT INTO Sponsor (nume_sponsor, adresa_sponsor, telefon_sponsor, id_fond) VALUES
    ('Pet Super', 'Strada Lătrăturilor, Nr. 10', '123123123', 1),
    ('Cute Paws Co.', 'Strada Blănitelor, Nr. 5', '456456456', 2),
    ('Happy Tails Inc.', 'Strada Codobelcilor, Nr. 2', '789789789', 3),
    ('Fluffy Friends Ltd.', 'Strada Ghemotoacelor, Nr. 15', '987987987', 4),
    ('Purrfect Homes', 'Strada Pernuțelor, Nr. 8', '654654654', 5);

-- Populare tabela Client
INSERT INTO Client (nume_client, adresa_client, telefon_client) VALUES
    ('Alex Animalover', 'Strada Dragoste, Nr. 20', '111222333'),
    ('Diana Adopter', 'Strada Veseliei, Nr. 30', '444555666'),
    ('George Furryfriend', 'Strada Cozy, Nr. 12', '777888999'),
    ('Eva Petpal', 'Strada Companiei, Nr. 18', '333444555'),
    ('Bogdan Fuzzybuddy', 'Strada Warmth, Nr. 22', '666777888');

-- Populare tabela Tranzactii
INSERT INTO Tranzactii (id_client, id_fond, suma_tranzactie) VALUES
    (1, 1, 200),
    (2, 2, 350),
    (3, 3, 150),
    (4, 4, 500),
    (5, 5, 250);

-- Populare tabela Animal
INSERT INTO Animal (nume_animal, specie, id_loc, id_tranzactie, id_adapost) VALUES
    ('Whiskers', 'Cat', 1, 1, 1),
    ('Buddy', 'Dog', 2, 2, 2),
    ('Spike', 'Hedgehog', 3, 3, 1),
    ('Fluffy', 'Rabbit', 4, 4, 3),
    ('Mittens', 'Cat', 5, 5, 2);

-- Populare tabela CabinetVeterinar
INSERT INTO CabinetVeterinar (nume_cabinet, adresa_cabinet, telefon_cabinet) VALUES
    ('Paws and Claws Vet Clinic', 'Strada Veterinarii, Nr. 3', '123456789'),
    ('Furry Friends Veterinary Care', 'Strada Patrupede, Nr. 8', '987654321'),
    ('Happy Paws Animal Hospital', 'Strada Sănătății, Nr. 12', '555666777');

-- Populare tabela IstoricVeterinar
INSERT INTO IstoricVeterinar (detalii_veterinare, id_cabinet) VALUES
    ('Checked overall health.', 1),
    ('Administered vaccines.', 2),
    ('Performed dental cleaning.', 3),
    ('Conducted routine examination.', 1),
    ('Treated minor injuries.', 2);

-- Populare tabela Animal_Istoric
INSERT INTO Animal_Istoric (id_animal, id_istoric) VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5);

-- Populare tabela Provizii
INSERT INTO Provizii (nume_provizie, cantitate, pret, id_fond) VALUES
    ('Pet Food', 100, 50, 1),
    ('Dog Toys', 20, 30, 2),
    ('Hay', 30, 10, 3),
    ('Rabbit Pellets', 50, 25, 4),
    ('Catnip', 10, 15, 5);

-- Populare tabela Inventar
INSERT INTO Inventar (id_provizie, id_adapost, cantitate) VALUES
    (1, 1, 20),
    (2, 2, 5),
    (3, 3, 15),
    (4, 4, 30),
    (5, 5, 8);

-- Populare tabela DoctorCabinet
INSERT INTO DoctorCabinet (nume_doctor, id_cabinet) VALUES
    ('Dr. Fluffington', 1),
    ('Dr. Pawsome', 2),
    ('Dr. Cuddles', 3);

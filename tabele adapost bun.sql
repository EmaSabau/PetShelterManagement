-- Creare baza de date pentru adapostul de animale
CREATE DATABASE IF NOT EXISTS AdapostAnimale;

-- Folosirea bazei de date
USE AdapostAnimale;

-- Tabela pentru Adăpost
CREATE TABLE IF NOT EXISTS Adapost (
    id_adapost INT PRIMARY KEY AUTO_INCREMENT,
    nume_adapost VARCHAR(255) NOT NULL UNIQUE,
    adresa_adapost VARCHAR(255) NOT NULL
);

-- Tabela pentru Proprietar
CREATE TABLE IF NOT EXISTS Proprietar (
    id_proprietar INT PRIMARY KEY AUTO_INCREMENT,
    nume_proprietar VARCHAR(255) NOT NULL,
    adresa_proprietar VARCHAR(255) NOT NULL,
    telefon_proprietar VARCHAR(15)
);

-- Tabela pentru Loc
CREATE TABLE IF NOT EXISTS Loc (
    id_loc INT PRIMARY KEY AUTO_INCREMENT,
    nume_loc VARCHAR(255) NOT NULL,
    stare VARCHAR(255) NOT NULL
);

-- Tabela pentru Donator
CREATE TABLE IF NOT EXISTS Donator (
    id_donator INT PRIMARY KEY AUTO_INCREMENT,
    nume_donator VARCHAR(255) NOT NULL,
    adresa_donator VARCHAR(255) NOT NULL,
    telefon_donator VARCHAR(15)
);

-- Tabela pentru Donatie
CREATE TABLE IF NOT EXISTS Donatie (
    id_donatie INT PRIMARY KEY AUTO_INCREMENT,
    id_donator INT,
    suma_donata DECIMAL(10, 2),
    FOREIGN KEY (id_donator) REFERENCES Donator(id_donator)
);

-- Tabela pentru Fonduri
CREATE TABLE IF NOT EXISTS Fonduri (
    id_fond INT PRIMARY KEY AUTO_INCREMENT,
    buget INT,
    intrari INT,
    iesiri INT
);

-- Tabela pentru Sponsor
CREATE TABLE IF NOT EXISTS Sponsor (
    id_sponsor INT PRIMARY KEY AUTO_INCREMENT,
    nume_sponsor VARCHAR(255) NOT NULL,
    adresa_sponsor VARCHAR(255) NOT NULL,
    telefon_sponsor VARCHAR(15),
    id_fond INT,
    FOREIGN KEY (id_fond) REFERENCES Fonduri(id_fond)
);

-- Tabela pentru Animal
CREATE TABLE IF NOT EXISTS Animal (
    id_animal INT PRIMARY KEY AUTO_INCREMENT,
    nume_animal VARCHAR(255) NOT NULL,
    specie VARCHAR(50) NOT NULL,
    id_loc INT,
    FOREIGN KEY (id_loc) REFERENCES Loc(id_loc),
    id_adapost INT,
    FOREIGN KEY (id_adapost) REFERENCES Adapost(id_adapost)
);

-- Tabela pentru IstoricVeterinar
CREATE TABLE IF NOT EXISTS IstoricVeterinar (
    id_istoric INT PRIMARY KEY AUTO_INCREMENT,
    detalii_veterinare TEXT
);

-- Tabela intermediară pentru a gestiona relația dintre Animal și IstoricVeterinar
CREATE TABLE IF NOT EXISTS Animal_Istoric (
    id_animal INT,
    id_istoric INT,
    PRIMARY KEY (id_animal, id_istoric),
    FOREIGN KEY (id_animal) REFERENCES Animal(id_animal),
    FOREIGN KEY (id_istoric) REFERENCES IstoricVeterinar(id_istoric)
);

-- Tabela pentru CabinetVeterinar
CREATE TABLE IF NOT EXISTS CabinetVeterinar (
    id_cabinet INT PRIMARY KEY AUTO_INCREMENT,
    nume_cabinet VARCHAR(255) NOT NULL,
    adresa_cabinet VARCHAR(255) NOT NULL,
    telefon_cabinet VARCHAR(15)
);

-- Tabela pentru Provizii
CREATE TABLE IF NOT EXISTS Provizii (
    id_provizie INT PRIMARY KEY AUTO_INCREMENT,
    nume_provizie VARCHAR(255) NOT NULL,
    cantitate INT
);

-- Tabela pentru Inventar
CREATE TABLE IF NOT EXISTS Inventar (
    id_inventar INT PRIMARY KEY AUTO_INCREMENT,
    id_provizie INT,
    id_adapost INT,
    cantitate INT,
    FOREIGN KEY (id_provizie) REFERENCES Provizii(id_provizie),
    FOREIGN KEY (id_adapost) REFERENCES Adapost(id_adapost)
);

-- Tabela pentru DoctorCabinet
CREATE TABLE IF NOT EXISTS DoctorCabinet (
    id_doctor INT PRIMARY KEY AUTO_INCREMENT,
    nume_doctor VARCHAR(255) NOT NULL,
    id_cabinet INT,
    FOREIGN KEY (id_cabinet) REFERENCES CabinetVeterinar(id_cabinet)
);

-- Tabela pentru Client
CREATE TABLE IF NOT EXISTS Client (
    id_client INT PRIMARY KEY AUTO_INCREMENT,
    nume_client VARCHAR(255) NOT NULL,
    adresa_client VARCHAR(255) NOT NULL,
    telefon_client VARCHAR(15)
);

-- Tabela pentru Tranzactii
CREATE TABLE IF NOT EXISTS Tranzactii (
    id_tranzactie INT PRIMARY KEY AUTO_INCREMENT,
    id_client INT,
    suma_tranzactie INT,
    FOREIGN KEY (id_client) REFERENCES Client(id_client)
);



#populare

-- Adăugare date în tabelul Adapost
INSERT INTO Adapost (nume_adapost, adresa_adapost) VALUES
    ('Adapost Fericit', 'Strada Veseliei nr. 123'),
    ('Adapost Calduros', 'Bulevardul Pufos nr. 456');

-- Adăugare date în tabelul Proprietar
INSERT INTO Proprietar (nume_proprietar, adresa_proprietar, telefon_proprietar) VALUES
    ('John Doe', 'Strada Principală nr. 789', '123-456-7890'),
    ('Jane Smith', 'Bulevardul Frumos nr. 321', '987-654-3210');

-- Adăugare date în tabelul Loc
INSERT INTO Loc (nume_loc, stare) VALUES
    ('Boxa 1', 'Liber'),
    ('Boxa 2', 'Ocupat');

-- Adăugare date în tabelul Donator
INSERT INTO Donator (nume_donator, adresa_donator, telefon_donator) VALUES
    ('Alice Johnson', 'Strada Ajutării nr. 111', '555-1234'),
    ('Bob Anderson', 'Bulevardul Sperantei nr. 222', '555-5678');

-- Adăugare date în tabelul Donatie
INSERT INTO Donatie (id_donator, suma_donata) VALUES
    (1, 1000.00),
    (2, 500.00);

-- Adăugare date în tabelul Fonduri
INSERT INTO Fonduri (buget, intrari, iesiri) VALUES
    (15000, 1500, 800);

-- Adăugare date în tabelul Sponsor
INSERT INTO Sponsor (nume_sponsor, adresa_sponsor, telefon_sponsor, id_fond) VALUES
    ('Company XYZ', 'Strada Generozitatii nr. 789', '555-9999', 1),
    ('ABC Corporation', 'Bulevardul Sustinabilitatii nr. 101', '555-8888', 1);

-- Adăugare date în tabelul Animal
INSERT INTO Animal (nume_animal, specie, id_loc, id_adapost) VALUES
    ('Rex', 'Câine', 1, 1),
    ('Whiskers', 'Cățel', 2, 2);

-- Adăugare date în tabelul IstoricVeterinar
INSERT INTO IstoricVeterinar (detalii_veterinare) VALUES
    ('Vaccinare împotriva rabiei'),
    ('Tratament pentru paraziți');

-- Adăugare date în tabelul Animal_Istoric
INSERT INTO Animal_Istoric (id_animal, id_istoric) VALUES
    (1, 1),
    (2, 2);

-- Adăugare date în tabelul CabinetVeterinar
INSERT INTO CabinetVeterinar (nume_cabinet, adresa_cabinet, telefon_cabinet) VALUES
    ('VetClinic123', 'Strada Veterinară nr. 456', '555-7777'),
    ('PetCare Center', 'Bulevardul Sănătății nr. 789', '555-6666');

-- Adăugare date în tabelul Provizii
INSERT INTO Provizii (nume_provizie, cantitate) VALUES
    ('Hrană uscată', 100),
    ('Jucării pentru animale', 50);

-- Adăugare date în tabelul Inventar
INSERT INTO Inventar (id_provizie, id_adapost, cantitate) VALUES
    (1, 1, 20),
    (2, 2, 30);

-- Adăugare date în tabelul DoctorCabinet
INSERT INTO DoctorCabinet (nume_doctor, id_cabinet) VALUES
    ('Dr. Smith', 1),
    ('Dr. Johnson', 2);

-- Adăugare date în tabelul Client
INSERT INTO Client (nume_client, adresa_client, telefon_client) VALUES
    ('Mary Johnson', 'Strada Iubirii nr. 555', '555-1111'),
    ('Michael Brown', 'Bulevardul Prieteniei nr. 888', '555-2222');

-- Adăugare date în tabelul Tranzactii
INSERT INTO Tranzactii (id_client, suma_tranzactie) VALUES
    (1, 50),
    (2, 100);

set sql_safe_updates = 0;
call InregistreazaDonatie (1, 500);

/*
							Trigger-e
	Trigger pentru actualizarea automată a bugetului în tabelul Fonduri
	Trigger pentru gestionarea stării unui loc in tabelul Loc
	Trigger pentru gestionarea istoricului veterinar al animalelor in tabelul IstoricVeterinar
    
							Proceduri
	Procedură pentru înregistrarea unei donații in tabelul Donatii
	Procedură pentru adăugarea unui nou animal în adăpost in tabelul Animal
	Procedură pentru efectuarea unei tranzacții in tabelul Tranzactii
    
							Interogari
	Afișarea tuturor animalelor și adăposturilor asociate
    Afișarea animalelor adoptate și a detaliilor despre proprietari
    Afișarea informațiilor despre donații și donatori
    Afișarea detaliilor despre animalele care au avut tratament veterinar
    Calcularea bugetului total și a sumei donate
    
    
*/
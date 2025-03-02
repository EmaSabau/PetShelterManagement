#Proceduri
USE AdapostAnimale;
set sql_safe_updates = 0;

CALL AdaugaDonatie (1, 1, 500);

CALL AdaugaAnimal('Whiskers Jr.', 'Cat', 1, 3, 1);

CALL EfectueazaTranzactie(3, 1, 200);
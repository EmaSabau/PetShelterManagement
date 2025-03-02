#Proceduri

USE AdapostAnimale;

set sql_safe_updates = 0;
CALL AdaugaDonatie (1, 1, 500);

CALL AdaugaAnimal('Whiskers Jr.', 'Cat', 1, 3, 1);

CALL EfectueazaTranzactie(3, 1, 200);

set sql_safe_updates = 0;
CALL StergeAnimal(1, 2, 3);

CALL ActualizeazaDetaliiAnimal(1, 'NumeNou', 'SpecieNoua');

CALL AdaugaAdapost(50, "Adapost nou", "Strada Frumusetii");

set sql_safe_updates = 0;
CALL StergeAnimal(1, 1, 1);

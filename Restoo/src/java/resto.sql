-- create table plat(
--     idPlat SERIAL,
--     nom varchar(15)
-- );


-- Insert into plat (nom) values('Ravitoto');
-- Insert into plat (nom) values('Anana');
-- Insert into plat (nom) values('Tsaramaso');
-- Insert into plat (nom) values('Hen omby');

-- SELECT * from plat;


Create Database Resto;

use Resto;

CREATE SEQUENCE seqProduit ;
CREATE SEQUENCE seqCategorie;

CREATE table Produit(
    idProduit varchar(6),
    nom varchar(15),
    idCategorie int,
    prixUnitaire float
);

CREATE table Categorie(
    idCategorie varchar(6),
    nom varchar(15)
);

INSERT INTO public.categorie(
	idcategorie, nom)
	VALUES (nextval('seqCategorie'),'Soupe');
INSERT INTO public.categorie(
    idcategorie,nom)
VALUES (nextval('seqCategorie'),'Vary');
INSERT INTO public.categorie(
    idcategorie,nom)
VALUES (nextval('seqCategorie'),'Misao');

-- Select * from Categorie

INSERT INTO public.produit(
	 idproduit,nom, idcategorie, prixunitaire)
	VALUES (nextval('seqProduit'), 'Soupe Atody',1, 1000);
INSERT INTO public.produit(
	 idproduit,nom, idcategorie, prixunitaire)
	VALUES (nextval('seqProduit'), 'Soupe Akoho',1, 5000);
INSERT INTO public.produit(
	 idproduit,nom, idcategorie, prixunitaire)
	VALUES (nextval('seqProduit'), 'Soupe Chinoise',1, 500);

INSERT INTO public.produit(
	 idproduit,nom, idcategorie, prixunitaire)
	VALUES (nextval('seqProduit'), 'vary sy Akoho',2, 3000);

INSERT INTO public.produit(
	 idproduit,nom, idcategorie, prixunitaire)
	VALUES (nextval('seqProduit'), 'vary s Saucisse',2, 3000);

INSERT INTO public.produit(
	 idproduit,nom, idcategorie, prixunitaire)
	VALUES (nextval('seqProduit'), 'Misao simple',3, 1000);

INSERT INTO public.produit(
	 idproduit,nom, idcategorie, prixunitaire)
	VALUES (nextval('seqProduit'), 'Misao special',3, 1000);



-- Select * from produit;
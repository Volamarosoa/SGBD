**Toutes les fonctions:
    --Pour creer un table
        ==> create table nomTable[nomAttributes1,nomAttributes2,...,nomAttributesn];
    --Insertion de donnees dans un table
        ==> insert into nomTable[value1,value2,...,valuen];
    --Selecton de donnees dans un table
        ==> select * from nomTable;
        ==> select nomAttribute1,nomAttribute2,...,nomAttributen from nomTable;
    --Union de deux table
        ==> create table classe1[idEleve1,nomEleve1];
        ==> create table classe2[idEleve2,nomEleve2];
        ==> select * from classe1 union classe2 on ideleve2=ideleve1 and nomeleve2=nomeleve1;
            --on ecrit le nom du attribu du deuxieme table et lui donner un autre nom pour qu'il puisse etre joiner avec la premiere table 
    --Intersection de deux table
        ==> select * from classe1 intersection classe2 on ideleve2=ideleve1 and nomeleve2=nomeleve1;
    --Difference de deux table
        ==> select * from classe1 difference classe2 on ideleve2=ideleve1 and nomeleve2=nomeleve1;
    --Division de deux table
        ==> select * from classe1 intersection classe2 on ideleve2=ideleve1 and nomeleve2=nomeleve1;
        ==> select * from classe1 intersection classe2;
    --Produit de deux table
        ==> select * from classe1 produit classe2 on ideleve2=ideleve1 and nomeleve2=nomeleve1;
        ==> select * from classe1 intersection classe2;

Reamarque: les tables creer sont situees dans le dossier "tables".
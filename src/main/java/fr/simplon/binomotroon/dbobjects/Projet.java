package fr.simplon.binomotroon.dbobjects;

import java.sql.Date;

public record Projet(int id, String libelle, Date dateDebut, Date dateFin){}


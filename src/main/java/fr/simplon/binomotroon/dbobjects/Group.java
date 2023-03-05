package fr.simplon.binomotroon.dbobjects;

import java.util.List;

public record Group(int id, String nom, List<User> apprenants)
{}

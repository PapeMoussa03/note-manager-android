package com.esp.notemanager;

import java.io.Serializable;

/**
 * Modèle de données pour une Note.
 * Implémente Serializable pour pouvoir être passé entre les Activités via Intent.
 */
public class Note implements Serializable {
    private int id;
    private String titre;
    private String contenu;
    private String couleur;
    private boolean favori;
    private String date;

    // Constructeur par défaut
    public Note() {
    }

    // Constructeur sans ID (pour l'insertion)
    public Note(String titre, String contenu, String couleur, boolean favori, String date) {
        this.titre = titre;
        this.contenu = contenu;
        this.couleur = couleur;
        this.favori = favori;
        this.date = date;
    }

    // Constructeur complet
    public Note(int id, String titre, String contenu, String couleur, boolean favori, String date) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.couleur = couleur;
        this.favori = favori;
        this.date = date;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public boolean isFavori() {
        return favori;
    }

    public void setFavori(boolean favori) {
        this.favori = favori;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

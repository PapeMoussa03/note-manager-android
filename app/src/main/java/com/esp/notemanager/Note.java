package com.esp.notemanager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Note {

    private int id;
    private String titre;
    private String contenu;
    private String couleur;
    private boolean favori;
    private String date;

    public Note() {
        this.date = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH)
                .format(new Date());
        this.couleur = "#219653";
        this.favori = false;
    }

    public Note(String titre, String contenu, String couleur) {
        this();
        this.titre   = titre;
        this.contenu = contenu;
        this.couleur = couleur;
    }

    public Note(int id, String titre, String contenu,
                String couleur, boolean favori, String date) {
        this.id      = id;
        this.titre   = titre;
        this.contenu = contenu;
        this.couleur = couleur;
        this.favori  = favori;
        this.date    = date;
    }

    public int getId()                  { return id; }
    public void setId(int id)           { this.id = id; }

    public String getTitre()            { return titre; }
    public void setTitre(String titre)  { this.titre = titre; }

    public String getContenu()          { return contenu; }
    public void setContenu(String c)    { this.contenu = c; }

    public String getCouleur()          { return couleur; }
    public void setCouleur(String c)    { this.couleur = c; }

    public boolean isFavori()           { return favori; }
    public void setFavori(boolean f)    { this.favori = f; }

    public String getDate()             { return date; }
    public void setDate(String date)    { this.date = date; }

    public String getDateFormatee()     { return date != null ? date : ""; }
}
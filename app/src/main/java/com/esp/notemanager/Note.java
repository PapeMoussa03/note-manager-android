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
    private long dateCreation;

    public Note() {
        this.dateCreation = System.currentTimeMillis();
        this.couleur = "#219653";
        this.favori = false;
    }

    public Note(String titre, String contenu, String couleur) {
        this();
        this.titre   = titre;
        this.contenu = contenu;
        this.couleur = couleur;
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

    public long getDateCreation()       { return dateCreation; }
    public void setDateCreation(long d) { this.dateCreation = d; }

    public String getDateFormatee() {
        SimpleDateFormat sdf = new SimpleDateFormat(
                "dd MMMM yyyy", Locale.FRENCH);
        return sdf.format(new Date(dateCreation));
    }
}
package com.example.pw;

public class Film {

    private int idFilm;
    private String title;
    private String description;
    private String imagePath;
    private String backDropPath;
    private String toSee;



    public Film() {}


    public String getToSee() {
        return toSee;
    }

    public void setToSee(String toSee) {
        this.toSee = toSee;
    }

    public String getBackDropPath() {
        return backDropPath;
    }

    public void setBackDropPath(String backDropPath) {
        this.backDropPath = backDropPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
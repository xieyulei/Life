package com.xyl.life.entity.movie;

import java.io.Serializable;

/*******************************
 * Created by liuqiang          *
 *******************************
 * data: 2018/1/11               *
 *******************************/

public class SimpleMovie implements Serializable {//

    private Rating rating;
    private String year;
    private Images images;
    private String id;
    private String title;
    private String[] countries;
    private String[] genres;
    private Casts[] casts;
    private String summary;
    private Casts[] directors;

    public SimpleMovie(Movie movie){
        this.rating=movie.getRating();
        this.year=movie.getYear();
        this.images=movie.getImages();
        this.id=movie.getId();
        this.title=movie.getTitle();
        this.countries=movie.getCountries();
        this.genres=movie.getGenres();
        this.casts=movie.getCasts();
        this.summary=movie.getSummary();
        this.directors=movie.getDirectors();

    }

    public Rating getRating() {
        return rating;
    }

    public String getYear() {
        return year;
    }

    public Images getImages() {
        return images;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String[] getCountries() {
        return countries;
    }

    public String[] getGenres() {
        return genres;
    }

    public Casts[] getCasts() {
        return casts;
    }

    public String getSummary() {
        return summary;
    }

    public Casts[] getDirectors() {
        return directors;
    }


}

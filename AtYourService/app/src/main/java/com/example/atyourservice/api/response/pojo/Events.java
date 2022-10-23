package com.example.atyourservice.api.response.pojo;

import java.util.List;

public class Events {
    private String name;
    private String type;
    private String id;
    private String url;
    private List<Images> images;
    private Dates dates;

//    public Events(String name, String type,String id, String url,List<Images> images, Dates dates){
//        this.name = name;
//        this.id = id;
//        this.type = type;
//        this.url = url;
//        this.images = images;
//        this.dates = dates;
//    }

    public Events(){};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    public Dates getDates() {
        return dates;
    }

    public void setDates(Dates dates) {
        this.dates = dates;
    }
}

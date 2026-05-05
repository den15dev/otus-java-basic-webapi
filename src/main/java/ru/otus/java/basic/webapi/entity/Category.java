package ru.otus.java.basic.webapi.entity;

public class Category {
    private Integer id;
    private String name;
    private String slug;


    public Category(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }


    public Category(Integer id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }


    public Integer getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getSlug() {
        return slug;
    }


    public void setSlug(String slug) {
        this.slug = slug;
    }
}

package com.abdellatif.biblio;

public class Book {
    private String id;
    private String title;
    private String author;
    private String categoryId;

    public Book() {
    }

    public Book(String id, String title, String author, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.categoryId = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}

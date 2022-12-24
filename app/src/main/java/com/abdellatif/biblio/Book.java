package com.abdellatif.biblio;

public class Book {
    private String title;
    private String author;
    private String category_id;

    public Book() {
    }

    public Book(String title, String author, String category) {
        this.title = title;
        this.author = author;
        this.category_id = category;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }
}

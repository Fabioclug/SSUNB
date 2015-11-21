package mds.ufscar.br.ssunb.model;

import java.util.Date;

public class Book {
    private String title;
    private String author;
    private String category;
    private String synopsis;
    private int code;
    private String publication;
    private int edition;
    private int pages;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public Book(String title, String author, String category, String synopsis, int code, String publication, int edition, int pages) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.synopsis = synopsis;
        this.code = code;
        this.publication = publication;
        this.edition = edition;
        this.pages = pages;
    }


    public Book(String title, String author, String category, String synopsis, String publication, int edition, int pages) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.synopsis = synopsis;
        this.publication = publication;
        this.edition = edition;
        this.pages = pages;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}

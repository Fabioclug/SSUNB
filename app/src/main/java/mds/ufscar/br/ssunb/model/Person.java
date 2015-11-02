package mds.ufscar.br.ssunb.model;


import java.util.ArrayList;
import java.util.List;

public class Person {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String city;
    private String senha;
    private List<Book> ownedBooks;

    public Person(String name, String surname, String city, String email, String senha) {
        //this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.city = city;
        this.senha = senha;
        ownedBooks = new ArrayList<Book>();
    }

    public Person(int id, String name, String surname, String city, String email, String senha) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.city = city;
        this.senha = senha;
        ownedBooks = new ArrayList<Book>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Book> getOwnedBooks() {
        return ownedBooks;
    }

    public void setOwnedBooks(List<Book> ownedBooks) {
        this.ownedBooks = ownedBooks;
    }

    public void addBook(Book b) {
        ownedBooks.add(b);
    }
}

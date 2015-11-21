package mds.ufscar.br.ssunb.model;

import java.util.Date;

/**
 * Created by Fabioclug on 2015-11-02.
 */
public class Emprestimo {
    private User requester;
    private User bookOwner;
    private Book requestedBook;
    private Date date;
    private String status;

    public Emprestimo(User requester, User bookOwner, Book requestedBook, Date date, String status) {
        this.requester = requester;
        this.bookOwner = bookOwner;
        this.requestedBook = requestedBook;
        this.status = status;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public User getBookOwner() {
        return bookOwner;
    }

    public void setBookOwner(User bookOwner) {
        this.bookOwner = bookOwner;
    }

    public Book getRequestedBook() {
        return requestedBook;
    }

    public void setRequestedBook(Book requestedBook) {
        this.requestedBook = requestedBook;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

package mds.ufscar.br.ssunb.model;

import java.util.Date;

/**
 * Created by Fabioclug on 2015-11-02.
 */
public class Emprestimo {
    private User requester;
    private User bookOwner;
    private Book requestedBook;
    private Date requestDate;
    private Date lendDate;
    private Date returnDate;
    private String status;

    public Emprestimo(User requester, User bookOwner, Book requestedBook, Date requestDate, Date lendDate, Date returnDate, String status) {
        this.requester = requester;
        this.bookOwner = bookOwner;
        this.requestedBook = requestedBook;
        this.requestDate = requestDate;
        this.lendDate = lendDate;
        this.returnDate = returnDate;
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

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getLendDate() {
        return lendDate;
    }

    public void setLendDate(Date lendDate) {
        this.lendDate = lendDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

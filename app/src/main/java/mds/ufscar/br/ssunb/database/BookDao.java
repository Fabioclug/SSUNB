package mds.ufscar.br.ssunb.database;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mds.ufscar.br.ssunb.model.Book;

public class BookDao implements Dao<Book> {

    private DatabaseHandler handler;
    private SimpleDateFormat dateFormat;

    public BookDao(DatabaseHandler handler) {
        this.handler = handler;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

    public Book build(Cursor cursor) {
        String title, author, category, synopsis, publisher;
        int code, edition, pages;
        Date publication = null;
        title = cursor.getString(cursor.getColumnIndex("title"));
        author = cursor.getString(cursor.getColumnIndex("author"));
        category = cursor.getString(cursor.getColumnIndex("category"));
        synopsis = cursor.getString(cursor.getColumnIndex("synopsis"));
        publisher = cursor.getString(cursor.getColumnIndex("publisher"));
        code = cursor.getInt(cursor.getColumnIndex("code"));
        edition = cursor.getInt(cursor.getColumnIndex("edition"));
        pages = cursor.getInt(cursor.getColumnIndex("pages"));
//      try {
//          publication = dateFormat.parse(cursor.getString((cursor.getColumnIndex("publication"))));
//      } catch (ParseException e) {
//          e.printStackTrace();
//      }
        return new Book(title, author, category, synopsis, code, publication, edition, publisher, pages);
    }

    @Override
    public boolean save(Book object) {
        SQLiteDatabase db = handler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", object.getTitle());
        values.put("author", object.getAuthor());
        values.put("category", object.getCategory());
        values.put("synopsis", object.getSynopsis());
//        try {
//            values.put("publication", dateFormat.format(object.getPublication()));
//        }
//        catch (Exception e) {
//            System.out.println(e);
//        }
        values.put("edition", object.getEdition());
        values.put("publisher", object.getPublisher());
        values.put("pages", object.getPages());
        int result = (int) db.insert("book", null, values);
        if(result > 0)
            object.setCode(result);

        return (result > 0);
    }

    public List<Book> executeQuery(String query, String[] subs) {
        List<Book> bookList = new ArrayList<Book>();
        Cursor cursor = handler.getReadableDatabase().rawQuery(query, subs);
        if(cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                Book b = build(cursor);
                bookList.add(b);
                cursor.moveToNext();
            }
        }
        return bookList;
    }

    @Override
    public List<Book> listAll() {
        String query = "SELECT * FROM book";
        return executeQuery(query, null);
    }

    public List<Book> listPending() {
        String query = "SELECT * FROM book WHERE pending = 1";
        return executeQuery(query, null);
    }

    public List<Book> listConfirmed() {
        String query = "SELECT * FROM book WHERE pending = 0";
        return executeQuery(query, null);
    }

    @Override
    public List<Book> listBy(String criteria) {
        return null;
    }

    public List<Book> listByUser(int user_code) {
        String query = "SELECT * FROM exemplar_livro AS E JOIN book B ON " +
        "E.livro = C.code WHERE E.usuario = ?";
        String[] subs = new String[] {Integer.toString(user_code)};
        return executeQuery(query, subs);
    }
}

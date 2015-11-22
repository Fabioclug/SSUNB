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
    }

    public Book build(Cursor cursor) {
        if(!cursor.isAfterLast()) {
            String title, author, category, synopsis, publication;
            int code, edition, pages;
            title = cursor.getString(cursor.getColumnIndex("title"));
            author = cursor.getString(cursor.getColumnIndex("author"));
            category = cursor.getString(cursor.getColumnIndex("category"));
            synopsis = cursor.getString(cursor.getColumnIndex("synopsis"));
            code = cursor.getInt(cursor.getColumnIndex("code"));
            edition = cursor.getInt(cursor.getColumnIndex("edition"));
            pages = cursor.getInt(cursor.getColumnIndex("pages"));
            publication = cursor.getString(cursor.getColumnIndex("publication"));
            //double rating = getRating(code);
         /*   if(!cursor.isNull(cursor.getColumnIndex("publication"))) {
                try {
                    publication = dateFormat.parse(cursor.getString((cursor.getColumnIndex("publication"))));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }*/
            return new Book(title, author, category, synopsis, code, publication, edition, pages /*rating*/);
        }
        else return null;
    }

    // insere um livro no banco
    @Override
    public boolean save(Book object) {
        SQLiteDatabase db = handler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", object.getTitle());
        values.put("author", object.getAuthor());
        values.put("category", object.getCategory());
        values.put("synopsis", object.getSynopsis());
      /*  if(object.getPublication() != null) {
            try {
                values.put("publication", dateFormat.format(object.getPublication()));
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }*/
        values.put("publication", object.getPublication());
        values.put("edition", object.getEdition());
        values.put("pages", object.getPages());
        int result = (int) db.insert("book", null, values);
        if(result > 0)
            object.setCode(result);

        return (result > 0);
    }

//    public double getRating(int id) {
//        String query = "SELECT avg(nota) AS nota FROM book as b JOIN avaliacoes AS a ON " +
//                "b.code = avaliacoes.livro WHERE b.code = ?";
//        String[] subs = new String[]{String.valueOf(id)};
//        Cursor cursor = handler.getReadableDatabase().rawQuery(query, subs);
//        if(cursor.moveToFirst()) {
//            return cursor.getDouble(cursor.getColumnIndex("nota"));
//        }
//        return 0.0;
//    }

    // insere um exemplar de livro no banco, juntamente com o usuário dono
    public boolean saveBookCopy(int bookid, int userid) {
        ContentValues values = new ContentValues();
        values.put("usuario", userid);
        values.put("livro", bookid);
        return (handler.getWritableDatabase().insert("exemplar_livro", null, values) > 0);
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

    // listagem dos livros pendentes de aprovação
    public List<Book> listPending() {
        String query = "SELECT * FROM book WHERE pending = 1";
        return executeQuery(query, null);
    }

    // listagem dos livros aprovados por um colaborador
    public List<Book> listConfirmed() {
        String query = "SELECT * FROM book WHERE pending = 0";
        return executeQuery(query, null);
    }

    // listagem de livros que contenham a substring name no título
    public List<Book> listByName(String name) {
        String query = "SELECT * FROM book WHERE title LIKE ?";
        String[] subs = new String[] {name + "%"};
        return executeQuery(query, subs);
    }

    // listagem de todos os livros de um usuário
    public List<Book> listByUser(int user_code) {
        String query = "SELECT * FROM exemplar_livro AS E JOIN book B ON " +
        "E.livro = B.code WHERE E.usuario = ?";
        String[] subs = new String[] {Integer.toString(user_code)};
        return executeQuery(query, subs);
    }

    public Book findById(int id) {
        Cursor cursor = handler.getReadableDatabase().rawQuery("SELECT * FROM book WHERE code = ?",
                new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        Book b = build(cursor);
        cursor.close();
        return b;
    }

    public Book findByTitle(String name) {
        //SQLiteDatabase db = handler.getWritableDatabase();
//        String sql = "SELECT * FROM " + "usuario" + " WHERE email = ? AND senha = ?";
//        String[] selectionArgs = new String[] { email, senha };
        Cursor cursor = handler.getReadableDatabase().rawQuery("SELECT * FROM book WHERE title = ?",
                new String[] { name });
        cursor.moveToFirst();
        Book b = build(cursor);
        cursor.close();
        return b;
    }

    // confirma o cadastro de um livro, usado pelo colaborador
    public boolean confirmBook(String title) {
        //String query = "UPDATE book SET pending = 0 WHERE title = ?";
        ContentValues values = new ContentValues();
        values.put("pending", 0);
        SQLiteDatabase db = handler.getWritableDatabase();
        int result = db.update("book", values, "title = ?", new String[]{title});
        return (result > 0);
    }


    public boolean confirmBookbyId(int code, String nome, String autor, String categoria, String sinopse, int edicao, String publicacao, int numpag) {
        //String query = "UPDATE book SET pending = 0 WHERE title = ?";
        ContentValues values = new ContentValues();
        values.put("title", nome);
        values.put("author",autor);
        values.put("category", categoria);
        values.put("synopsis", sinopse);
        values.put("edition", edicao);
        values.put("publication", publicacao);
        values.put("pages", numpag);
        values.put("pending", 0);
        SQLiteDatabase db = handler.getWritableDatabase();
        int result = db.update("book", values, "code = ?", new String[]{String.valueOf(code)});
        return (result > 0);
    }

    // insere uma avaliação de usuario para um livro
    public boolean rateBook(int id_user, int id_book, double stars) {
        ContentValues values = new ContentValues();
        values.put("usuario",id_user);
        values.put("livro", id_book);
        values.put("nota", stars);
        values.put("data_avaliacao", dateFormat.format(new Date()));
        SQLiteDatabase db = handler.getWritableDatabase();
        int result = (int) db.insert("avaliacoes", null, values);
        return (result > 0);
    }

    public List<Book> ListRanking() {
        String query = "SELECT code, avg(data) AS classificacao FROM book AS b JOIN avaliacoes AS a ON " +
                "b.code = a.livro GROUP BY b.code ORDER BY classificacao DESC";
        return executeQuery(query, null);
    }
}

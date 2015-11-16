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
import mds.ufscar.br.ssunb.model.Emprestimo;
import mds.ufscar.br.ssunb.model.User;

/**
 * Created by Fabioclug on 2015-11-02.
 */
public class EmprestimoDao implements Dao<Emprestimo> {
    private DatabaseHandler handler;
    private SimpleDateFormat dateFormat;

    public EmprestimoDao(DatabaseHandler handler) {
        this.handler = handler;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

    @Override
    public Emprestimo build(Cursor cursor) {
        UserDao userDao = new UserDao(handler);
        BookDao bookDao = new BookDao(handler);

        Integer requesterId = cursor.getInt(cursor.getColumnIndex("solicitante"));
        Integer ownerId = cursor.getInt(cursor.getColumnIndex("dono_livro"));
        Integer bookId = cursor.getInt(cursor.getColumnIndex("livro"));
        User requester = userDao.findById(requesterId);
        User bookOwner = userDao.findById(ownerId);
        Book requestedBook = bookDao.findById(bookId);
        Date requestDate = null;
        Date lendDate = null;
        Date returnDate = null;
        try {
            requestDate = dateFormat.parse(cursor.getString((cursor.getColumnIndex("solicitado"))));
            lendDate = dateFormat.parse(cursor.getString((cursor.getColumnIndex("retirada"))));
            requestDate = dateFormat.parse(cursor.getString((cursor.getColumnIndex("devolucao"))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String status = (cursor.getInt(cursor.getColumnIndex("autorizado")) == 1)? "Autorizado" : "Pendente";
        Emprestimo e = new Emprestimo(requester, bookOwner, requestedBook, requestDate, lendDate, returnDate, status);
        return e;
    }

    @Override
    public boolean save(Emprestimo object) {
        SQLiteDatabase db = handler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("solicitante", object.getRequester().getId());
        values.put("dono_livro", object.getBookOwner().getId());
        values.put("livro", object.getRequestedBook().getCode());
//      try {
//            values.put("solicitado", dateFormat.format(object.getRequestDate()));
//            values.put("retirada", dateFormat.format(object.getLendDate()));
//            values.put("devolucao", dateFormat.format(object.getReturnDate()));
//      } catch (ParseException e) {
//          e.printStackTrace();
//      }

        int autorizado = (object.getStatus().equals("Autorizado"))? 1 : 0;
        values.put("autorizado", autorizado);
        return ((int) db.insert("emprestimo", null, values) > 0);
    }

    @Override
    public List<Emprestimo> listAll() {
        List<Emprestimo> emprestimoList = new ArrayList<Emprestimo>();
        Cursor cursor = handler.getReadableDatabase().rawQuery("SELECT * FROM emprestimo", null);
        if(cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                Emprestimo e = build(cursor);
                emprestimoList.add(e);
                cursor.moveToNext();
            }
        }
        return emprestimoList;
    }

    @Override
    public List<Emprestimo> listBy(String criteria) {
        return null;
    }
}

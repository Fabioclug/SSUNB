package mds.ufscar.br.ssunb.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import mds.ufscar.br.ssunb.model.Emprestimo;

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
    public Emprestimo build(Cursor c) {
        return null;
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

        int autorizado = (object.getStatus() == "Autorizado")? 1 : 0;
        values.put("autorizado", autorizado);
        return ((int) db.insert("emprestimo", null, values) > 0);
    }

    @Override
    public List<Emprestimo> listAll() {
        return null;
    }

    @Override
    public List<Emprestimo> listBy(String criteria) {
        return null;
    }
}

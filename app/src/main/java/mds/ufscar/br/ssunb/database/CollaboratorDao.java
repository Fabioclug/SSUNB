package mds.ufscar.br.ssunb.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mds.ufscar.br.ssunb.model.Book;
import mds.ufscar.br.ssunb.model.Collaborator;

/**
 * Created by Fabioclug on 2015-11-08.
 */
public class CollaboratorDao implements Dao<Collaborator> {
    private DatabaseHandler handler;

    public CollaboratorDao(DatabaseHandler handler) {
        this.handler = handler;
    }

    @Override
    public Collaborator build(Cursor c) {
        if(c.moveToFirst()) {
            Integer id = c.getInt(c.getColumnIndex("id"));
            String PrimNome = c.getString(c.getColumnIndex("PrimNome"));
            String SobreNome = c.getString(c.getColumnIndex("SobreNome"));
            String cidade = c.getString(c.getColumnIndex("cidade"));
            String email = c.getString(c.getColumnIndex("email"));
            String senha = c.getString(c.getColumnIndex("senha"));
            String cpf = c.getString(c.getColumnIndex("cpf"));

            // aqui talvez tenha que ser criado um novo handler
            BookDao bdao = new BookDao(this.handler);
            List<Book> blist = bdao.listByUser(id);
            Collaborator colaborador = new Collaborator(id, PrimNome, SobreNome, cidade, email, senha, cpf);
            colaborador.setOwnedBooks(blist);
            return colaborador;
        }
        else return null;

    }

    @Override
    public boolean save(Collaborator object) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = handler.getWritableDatabase();

        //nao precisa inserir o id, o sqlite faz um autoincrement automatico
        //values.put("id", 0);
        values.put("PrimNome", object.getName());
        values.put("SobreNome", object.getSurname());
        values.put("cidade", object.getCity());
        values.put("email", object.getEmail());
        values.put("senha", object.getSenha());

        int result = (int) db.insert("usuario", null, values);
        db.close();

        if(result > 0) {
            object.setId(result);
            ContentValues nvalues = new ContentValues();
            nvalues.put("id", result);
            nvalues.put("cpf", object.getCpf());
            result = (int) db.insert("colaborador", null, nvalues);
        }
        return (result > 0);
    }

    @Override
    public List<Collaborator> listAll() {
        String query = "SELECT * FROM usuario AS u JOIN colaborador as c ON u.id = c.id";
        List<Collaborator> colaboradorList = new ArrayList<Collaborator>();
        Cursor cursor = handler.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                colaboradorList.add(build(cursor));
                cursor.moveToNext();
            }
        }
        return null;
    }

    @Override
    public List<Collaborator> listBy(String criteria) {
        return null;
    }

    public Collaborator findByLogin(String email, String senha) {
        Cursor cursor = handler.getReadableDatabase().rawQuery("SELECT * FROM usuario AS u " +
                        "JOIN colaborador as c ON u.id = c.id WHERE email = ? AND senha = ?",
                new String[] { email, senha });
        Collaborator c = build(cursor);
        cursor.close();
        return c;
    }
}

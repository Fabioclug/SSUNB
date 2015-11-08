package mds.ufscar.br.ssunb.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

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
        return null;
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
        return null;
    }

    @Override
    public List<Collaborator> listBy(String criteria) {
        return null;
    }
}

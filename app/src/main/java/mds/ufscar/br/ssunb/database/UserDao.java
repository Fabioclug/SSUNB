package mds.ufscar.br.ssunb.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import mds.ufscar.br.ssunb.model.User;

/**
 * Created by breno on 24/10/15.
 */
public class UserDao implements Dao<User> {
    private DatabaseHandler handler;

    public UserDao(DatabaseHandler handler) {
        this.handler = handler;
    }

    @Override
    public boolean save(User object) {
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

        if(result > 0)
        {
            object.setId(result);
        }
        return (result > 0);
    }

    @Override
    public List<User> listAll() {
        return null;
    }

    @Override
    public List<User> listBy(String criteria) {
        return null;
    }

//    public User findById(Integer id) {
//
//        SQLiteDatabase db = handler.getWritableDatabase();
//        Cursor cursor = handler.getReadableDatabase().query("usuario",null, null, null, null, null,"id");
//        cursor.moveToFirst();
//
//        return montaUsuario(cursor);
//    }

//    public List<User> findAll() throws Exception {
//        List<User> retorno = new ArrayList<User>();
//        String sql = "SELECT * FROM " + "usuario";
//        Cursor cursor = getDatabase().rawQuery(sql, null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            retorno.add(montaUsuario(cursor));
//            cursor.moveToNext();
//        }
//        return retorno;
//    }

    public User montaUsuario(Cursor cursor) {
        if(cursor.moveToFirst()) {
            Integer id = cursor.getInt(cursor.getColumnIndex("id"));
            String PrimNome = cursor.getString(cursor.getColumnIndex("PrimNome"));
            String SobreNome = cursor.getString(cursor.getColumnIndex("SobreNome"));
            String cidade = cursor.getString(cursor.getColumnIndex("cidade"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String senha = cursor.getString(cursor.getColumnIndex("senha"));

            return new User(PrimNome, SobreNome, cidade, email, senha);
        }
        else return null;

    }

    public User findByLogin(String email, String senha) {
        //SQLiteDatabase db = handler.getWritableDatabase();
//        String sql = "SELECT * FROM " + "usuario" + " WHERE email = ? AND senha = ?";
//        String[] selectionArgs = new String[] { email, senha };
        Cursor cursor = handler.getReadableDatabase().rawQuery("SELECT * FROM usuario WHERE email = ? AND senha = ?",
                new String[] { email, senha });
        User u = montaUsuario(cursor);
        cursor.close();
        return u;
    }

    public User findByEmail(String email)
    {
        Cursor cursor = handler.getReadableDatabase().rawQuery("SELECT * FROM usuario WHERE email = ?",
                new String[] { email });
        User u = montaUsuario(cursor);
        cursor.close();
        return u;
    }

}

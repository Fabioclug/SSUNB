package mds.ufscar.br.ssunb.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.util.Pair;

import mds.ufscar.br.ssunb.UserDistance;
import mds.ufscar.br.ssunb.model.Book;
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
        values.put("latitude", object.getLatitude());
        values.put("longitude", object.getLongitude());

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
        Cursor cursor = handler.getReadableDatabase().rawQuery("SELECT * FROM usuario", null);
        List<User> userList = new ArrayList<User>();
        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                userList.add(build(cursor));
                cursor.moveToNext();
            }
        }
        return userList;
    }

    // listagem de usuários que possuem um determinado livro
    public List<User> listByBook(String title) {
        String query = "SELECT * FROM usuario AS U JOIN exemplar_livro AS E ON U.id = E.usuario " +
                "JOIN book AS B ON E.livro = B.code WHERE B.title = ?";
        Cursor cursor = handler.getReadableDatabase().rawQuery(query, new String[] {title});
        List<User> userList = new ArrayList<User>();
        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                userList.add(build(cursor));
                cursor.moveToNext();
            }
        }
        return userList;
    }

    @Override
    public User build(Cursor cursor) {
        if(!cursor.isAfterLast()) {
            Integer id = cursor.getInt(cursor.getColumnIndex("id"));
            String PrimNome = cursor.getString(cursor.getColumnIndex("PrimNome"));
            String SobreNome = cursor.getString(cursor.getColumnIndex("SobreNome"));
            String cidade = cursor.getString(cursor.getColumnIndex("cidade"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String senha = cursor.getString(cursor.getColumnIndex("senha"));

            BookDao bdao = new BookDao(this.handler);
            List<Book> blist = bdao.listByUser(id);
            User u = new User(id, PrimNome, SobreNome, cidade, email, senha);
            u.setOwnedBooks(blist);
            Double myValue;
            if (!cursor.isNull(cursor.getColumnIndex("latitude")) && !cursor.isNull(cursor.getColumnIndex("longitude"))) {
                double latitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
                double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
                u.setLatitude(latitude);
                u.setLongitude(longitude);
            }
            return u;
        }
        else return null;
    }

    // lista usuários por ordem de distância em relação à localização recebida
    public List<UserDistance> listByDistance(double latitude, double longitude) {
        String query = "SELECT * FROM usuario AS u1 " +
                "JOIN SELECT acos(sin(?)*sin(radians(latitude)) + cos(?)*cos(radians(latitude))cos(radians(longitude)-?))$R As dist" +
                "FROM usuario AS u2 ON u1.id = u2.id ORDER BY dist";
        String[] subs = new String[]{String.valueOf(latitude), String.valueOf(latitude), String.valueOf(longitude)};
        List<UserDistance> userList = new ArrayList<UserDistance>();
        Cursor cursor = handler.getReadableDatabase().rawQuery(query, subs);
        if(cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                userList.add(new UserDistance(build(cursor), cursor.getDouble(cursor.getColumnIndex("dist"))));
                cursor.moveToNext();
            }
        }
        return userList;
    }


    public User findById(int id) {
        Cursor cursor = handler.getReadableDatabase().rawQuery("SELECT * FROM usuario WHERE id = ?",
                new String[] {String.valueOf(id)});
        cursor.moveToFirst();
        User u = build(cursor);
        cursor.close();
        return u;
    }

    public User findByLogin(String email, String senha) {
        Cursor cursor = handler.getReadableDatabase().rawQuery("SELECT * FROM usuario WHERE email = ? AND senha = ?",
                new String[] { email, senha });
        cursor.moveToFirst();
        User u = build(cursor);
        cursor.close();
        return u;
    }

    public User findByEmail(String email)  {
        Cursor cursor = handler.getReadableDatabase().rawQuery("SELECT * FROM usuario WHERE email = ?",
                new String[] { email });
        cursor.moveToFirst();
        User u = build(cursor);
        cursor.close();
        return u;
    }

}

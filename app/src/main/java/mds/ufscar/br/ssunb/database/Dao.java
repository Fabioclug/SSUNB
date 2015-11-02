package mds.ufscar.br.ssunb.database;

import android.database.Cursor;

import java.util.List;

public interface Dao<T> {

    T build(Cursor c);

    boolean save(T object);

    List<T> listAll();

    List<T> listBy(String criteria);
}

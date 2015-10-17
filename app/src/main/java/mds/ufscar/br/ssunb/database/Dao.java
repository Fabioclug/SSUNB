package mds.ufscar.br.ssunb.database;

import java.util.List;

public interface Dao<T> {

    boolean save(T object);

    List<T> listAll();

    List<T> listBy(String criteria);
}

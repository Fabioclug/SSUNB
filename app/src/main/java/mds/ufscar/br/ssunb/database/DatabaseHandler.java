package mds.ufscar.br.ssunb.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "ssunb";
    private static final int DB_VERSION = 1;
    private static final String CREATE_USER = "CREATE TABLE usuario (id integer not null primary key, " +
            "PrimNome text not null, SobreNome text not null,cidade text not null," +
            "email text not null, senha text not null )";

    private static final String CREATE_BOOK = "create table book(code integer not null primary key," +
            "title text not null, author text not null, category text, synopsis text, publication date, " +
            "edition integer, publisher text, pages integer, pending integer default 1)";

    private static final String CREATE_COLLABORATOR = "CREATE TABLE colaborador (id integer primary key," +
            "cpf text not null, FOREIGN KEY(id) REFERENCES usuario(id))";

    private static final String CREATE_LENDING = "CREATE TABLE emprestimo (solicitante integer not null," +
            "dono_livro integer not null, livro integer not null, solicitado date not null, " +
            "retirada date not null, devolucao date not null, autorizado integer default 0, " +
            "FOREIGN KEY(solicitante) REFERENCES usuario(id), FOREIGN KEY(dono_livro) REFERENCES usuario(id)," +
            "FOREIGN KEY(livro) REFERENCES book(code),PRIMARY KEY(solicitante, dono_livro, livro))";

    private static final String CREATE_BOOKCOPY = "CREATE TABLE exemplar_livro (usuario integer not null, " +
            "livro integer not null, status text default 'Disponivel', " +
            "FOREIGN KEY(usuario) REFERENCES usuario(id), FOREIGN KEY(livro) REFERENCES book(code), " +
            "PRIMARY KEY(usuario, livro))";

    private static final String CREATE_EVALUATION = "CREATE TABLE avaliacoes (usuario integer not null, " +
            "livro integer not null, nota real not null, data_avaliacao date not null, " +
            "FOREIGN KEY(usuario) REFERENCES usuario(id), FOREIGN KEY(livro) REFERENCES book(code), " +
            "PRIMARY KEY(usuario, livro))";

    protected SQLiteDatabase database;

    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_COLLABORATOR);
        db.execSQL(CREATE_LENDING);
        db.execSQL(CREATE_BOOKCOPY);
        db.execSQL(CREATE_EVALUATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists book");
        onCreate(db);
    }

}

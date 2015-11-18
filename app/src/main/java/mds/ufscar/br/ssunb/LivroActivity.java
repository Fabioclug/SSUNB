package mds.ufscar.br.ssunb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import mds.ufscar.br.ssunb.database.DatabaseHandler;
import mds.ufscar.br.ssunb.database.UserDao;
import mds.ufscar.br.ssunb.model.User;

public class LivroActivity extends AppCompatActivity{

    String emailUsuarioDaSessao;
    String livroEscolhido;
    List<ItemRow> itemData = new ArrayList<>();
    User usuarioPortador;
    DatabaseHandler db;
    UserDao usuarios;
    private ArrayAdapter<ItemRow> adaptador = null;
    public LivroActivity() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent().hasExtra("Livro")){
            Bundle extras = getIntent().getExtras();
            livroEscolhido = extras.getString("Livro");
            System.out.println(extras.getString("EMAIL_USER"));
        }



//        Intent r = getIntent();
//
//
//        BookListAdapter bla = new BookListAdapter(this);
//        int id = Integer.parseInt(r.getStringExtra("book_id"));
//
//        Book b = (Book) bla.getItem(id);
//
//        TextView book_title = (TextView) findViewById(R.id.book_title);
//        book_title.setText(b.getTitle());
//
//        TextView book_description = (TextView) findViewById(R.id.book_description);
//        book_description.setText(b.getAuthor());

        if(getIntent().hasExtra("Livro")){
            Bundle extras = getIntent().getExtras();
            String nomeLivro = extras.getString("Livro");
            System.out.println(extras.getString("Livro"));
        }

    }
}
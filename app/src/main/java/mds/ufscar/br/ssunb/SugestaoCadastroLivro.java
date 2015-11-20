package mds.ufscar.br.ssunb;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import mds.ufscar.br.ssunb.database.BookDao;
import mds.ufscar.br.ssunb.database.DatabaseHandler;
import mds.ufscar.br.ssunb.model.Book;
import mds.ufscar.br.ssunb.model.User;

public class SugestaoCadastroLivro extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    UserController usuarioDaSessao;
    String emailUsuarioDaSessao;
    Context context;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugestao_cadastro_livro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        if(getIntent().hasExtra("EMAIL_USER")){
            Bundle extras = getIntent().getExtras();
            emailUsuarioDaSessao = extras.getString("EMAIL_USER");
            System.out.println(extras.getString("EMAIL_USER"));
            Log.w("EmailUser", extras.getString("EMAIL_USER"));
        }

        usuarioDaSessao = new UserController(this);
        User atual = usuarioDaSessao.findByEmail(emailUsuarioDaSessao);
        String nome = atual.getName();

        TextView NomeDoUsuario = (TextView)findViewById(R.id.NameUser);
        NomeDoUsuario.setText(nome);


        findViewById(R.id.buttonSugestaoLivro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //recuperar itens que foram digitados e pensar numa maneira de salvar sugestoes
                EditText nomeLivro = (EditText) findViewById(R.id.nomeLivro);
                EditText nomeAutor = (EditText) findViewById(R.id.nomeAutor);

                String livro = nomeLivro.getText().toString();
                String autor = nomeAutor.getText().toString();

                Sugestao(livro, autor);

                Toast.makeText(getApplicationContext(), "Sugestão enviada para análise",
                        Toast.LENGTH_SHORT).show();
            }
        });

        context = this;

    }

    public void Sugestao(String livro, String autor)
    {

        Book novoLivro = new Book(livro, autor);
        db = new DatabaseHandler(context);
        BookDao bookDao = new BookDao(db);

        try{
            bookDao.save(novoLivro);
        }catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "Falha ao inserir novo livro no banco",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
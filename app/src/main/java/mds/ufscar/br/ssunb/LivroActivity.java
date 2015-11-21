package mds.ufscar.br.ssunb;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import mds.ufscar.br.ssunb.database.BookDao;
import mds.ufscar.br.ssunb.database.DatabaseHandler;
import mds.ufscar.br.ssunb.database.UserDao;
import mds.ufscar.br.ssunb.model.Book;
import mds.ufscar.br.ssunb.model.User;

public class LivroActivity extends AppCompatActivity{

    String emailUsuarioDaSessao;
    String livroEscolhido, nomeUsuario;
    List<ItemRow> itemData = new ArrayList<>();
    UserController usuarioDaSessao;
    DatabaseHandler db;
    UserDao usuarios;
    BookDao BD;
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

        if(getIntent().hasExtra("EMAIL_USER")){
            Bundle extras = getIntent().getExtras();
            livroEscolhido = extras.getString("Livro");
            emailUsuarioDaSessao = extras.getString("EMAIL_USER");
            System.out.println(extras.getString("EMAIL_USER"));
            System.out.println(extras.getString("Livro"));
            Log.w("EmailUser", extras.getString("EMAIL_USER"));
        }

        usuarioDaSessao = new UserController(this);
        User atual = usuarioDaSessao.findByEmail(emailUsuarioDaSessao);
        String nome = atual.getName();


        TextView NomeDoUsuario = (TextView)findViewById(R.id.NameUser);
        NomeDoUsuario.setText(nome);


        db = new DatabaseHandler(this);
        BD = new BookDao(db);
        Book livro = BD.findByTitle(livroEscolhido);
        System.out.println("Encontrou o livro:");
        int idLivro = livro.getCode();


        TextView textLivro = (TextView)findViewById(R.id.nomeLivro);
        TextView textAutor = (TextView)findViewById(R.id.nomeAutor);
        TextView textCategoria = (TextView)findViewById(R.id.nomeCategoria);
        TextView textEdicao = (TextView)findViewById(R.id.nomeEdicao);
        TextView textPgs = (TextView)findViewById(R.id.nomePaginas);
        TextView textPublicacao = (TextView)findViewById(R.id.nomePublicacao);
        TextView textResumo = (TextView)findViewById(R.id.nomeResumo);

        textLivro.setText(livro.getTitle());
        textAutor.setText(livro.getAuthor());
        textCategoria.setText(livro.getCategory());
        textEdicao.setText(Integer.toString(livro.getEdition()));
        textPgs.setText(Integer.toString(livro.getPages()));
        textPublicacao.setText(livro.getPublication());
        textResumo.setText(livro.getSynopsis());


    }
}
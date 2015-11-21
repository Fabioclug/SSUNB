package mds.ufscar.br.ssunb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mds.ufscar.br.ssunb.database.BookDao;
import mds.ufscar.br.ssunb.database.DatabaseHandler;
import mds.ufscar.br.ssunb.model.Book;
import mds.ufscar.br.ssunb.model.Collaborator;
import mds.ufscar.br.ssunb.model.User;

public class ConfirmacaoCadastroLivro extends AppCompatActivity {

    private String emailColaboradorDaSessao;
    private CollaboratorController colaboradorAtual;
    private int cod_livro;
    Context context;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacao_cadastro_livro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.buttonCadastroLivro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EfetuarCadastro(v);
            }
        });

        context = this;

        if(getIntent().hasExtra("EMAIL_COLABORADOR")){
            Bundle extras = getIntent().getExtras();
            emailColaboradorDaSessao = extras.getString("EMAIL_COLABORADOR");
//            System.out.println(extras.getString("EMAIL_COLABORADOR"));
//            Log.w("EmailUser", extras.getString("EMAIL_COLABORADOR"));
        }

        if(getIntent().hasExtra("ID_LIVRO"))
        {
            Bundle extras = getIntent().getExtras();
            cod_livro = extras.getInt("ID_LIVRO");

        }

        colaboradorAtual = new CollaboratorController(this);
 //       Collaborator atual = colaboradorAtual.findByEmail(emailColaboradorDaSessao);
//        String nome = atual.getName();

        TextView NomeDoColaborador = (TextView)findViewById(R.id.NameCollaborator);
 //       NomeDoColaborador.setText(nome);

    }

    public void EfetuarCadastro(View view) {

        // Recuperamos os valores dos campos da tela

        db = new DatabaseHandler(context);
        BookDao bookDao = new BookDao(db);

        Book livro = bookDao.findById(cod_livro);

        EditText campoNomeLivro = (EditText) findViewById(R.id.editTextNomeLivro);
        EditText campoAutor = (EditText) findViewById(R.id.editTextAutor);
        EditText campoCategoria = (EditText) findViewById(R.id.editTextCategoria);
        EditText campoEdicao = (EditText) findViewById(R.id.editTextEdicao);
        EditText campoPaginas = (EditText) findViewById(R.id.editTextPage);
        EditText campoSinopse = (EditText) findViewById(R.id.editTextSinopse);
        EditText campoDataPublicacao = (EditText) findViewById(R.id.editDataPublicacao);


        campoNomeLivro.setHint(livro.getTitle());
        campoAutor.setHint(livro.getAuthor());


        String nome = campoNomeLivro.getText().toString();
        if (nome.length() == 0) {
            nome = livro.getTitle();
        }
        String autor = campoAutor.getText().toString();
        if (autor.length() == 0) {
            autor = livro.getAuthor();
        }
        String categoria = campoCategoria.getText().toString();
        String edicao = campoEdicao.getText().toString();
        String paginas = campoPaginas.getText().toString();
        String Sinopse = campoSinopse.getText().toString();
        String publicacao = campoDataPublicacao.getText().toString();

        int ed = Integer.parseInt(edicao);
        int pag = Integer.parseInt(paginas);

        try {
            bookDao.confirmBookbyId(cod_livro, nome, autor, categoria, Sinopse, ed, publicacao, pag);
            Toast.makeText(getApplicationContext(), "Confirmação de cadastro com sucesso",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Falha ao inserir novo livro no banco",
                    Toast.LENGTH_SHORT).show();
        }


    }

    }

package mds.ufscar.br.ssunb;

import android.content.Context;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import mds.ufscar.br.ssunb.database.BookDao;
import mds.ufscar.br.ssunb.database.DatabaseHandler;
import mds.ufscar.br.ssunb.database.EmprestimoDao;
import mds.ufscar.br.ssunb.database.UserDao;
import mds.ufscar.br.ssunb.model.Book;
import mds.ufscar.br.ssunb.model.Emprestimo;
import mds.ufscar.br.ssunb.model.User;

public class PaginaEmprestimo extends AppCompatActivity {

    private UserController usuarioDaSessao;
    private String emailUsuarioDaSessao;
    private int idUsuarioPortador;
    private String nomeDoLivroEscolhido;
    private BookDao bookDao;
    private Context context;
    private EmprestimoDao emprestimoDao;
    private DatabaseHandler db;
    private UserDao usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_emprestimo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().hasExtra("EMAIL_USER")) {
            Bundle extras = getIntent().getExtras();
            emailUsuarioDaSessao = extras.getString("EMAIL_USER");
            idUsuarioPortador = extras.getInt("USER_ESCOLHIDO");
            nomeDoLivroEscolhido = extras.getString("LIVRO_ESCOLHIDO");
            System.out.println(extras.getString("EMAIL_USER"));
            Log.w("EmailUser", extras.getString("EMAIL_USER"));
        }

        usuarioDaSessao = new UserController(this);
        final User atual = usuarioDaSessao.findByEmail(emailUsuarioDaSessao);
        String nome = atual.getName();

        TextView NomeDoUsuario = (TextView) findViewById(R.id.NameUser);
        NomeDoUsuario.setText(nome);

        findViewById(R.id.buttonRequisicao).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
            RealizarEmprestimo(atual.getId(), idUsuarioPortador, nomeDoLivroEscolhido);

        }});

        usuario = new UserDao(db);
        User userPortador = usuario.findById(idUsuarioPortador);

        TextView portador = (TextView) findViewById(R.id.NamePortador);
        portador.setText(userPortador.getName());

        TextView nomeDoLivro = (TextView) findViewById(R.id.NameLivro);
        nomeDoLivro.setText(nomeDoLivroEscolhido);




        context = this;
    }


    public void RealizarEmprestimo(int idSolicitante, int idPortadodor, String livroEscolhido) {

        emprestimoDao = new EmprestimoDao(db);
        db = new DatabaseHandler(context);
        bookDao = new BookDao(db);
        Book livro = bookDao.findByTitle(livroEscolhido);
        System.out.println("Encontrou o livro:");
        int idLivro = livro.getCode();
        EditText textData = (EditText) findViewById(R.id.editTextData);

        // * TRANSFORMAR PARA DATA AQUI * //
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date data = null;
        try {
            data = dateFormat.parse(textData.getText().toString());
        } catch (ParseException e) {
            data = null;
        }
        UserDao udao = new UserDao(db);
        User solicitante = udao.findById(idSolicitante);
        User portador = udao.findById(idPortadodor);
        Emprestimo emprestimo = new Emprestimo(solicitante, portador, livro, data, "REQUISITADO");


        try {
            emprestimoDao.save(emprestimo);
            Toast.makeText(getApplicationContext(), "Sugestão enviada para análise",
                    Toast.LENGTH_SHORT).show();
            textData.setText("");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Falha ao inserir novo livro no banco",
                    Toast.LENGTH_SHORT).show();
        }




    }
}

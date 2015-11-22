package mds.ufscar.br.ssunb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mds.ufscar.br.ssunb.database.DatabaseHandler;
import mds.ufscar.br.ssunb.database.UserDao;
import mds.ufscar.br.ssunb.model.User;

public class PaginaListagemUsuarios extends AppCompatActivity {
    String emailUsuarioDaSessao;
    String livroEscolhido;
    List<ItemRow> itemData = new ArrayList<>();
    User usuarioPortador;
    DatabaseHandler db;
    UserDao usuarios;
    private ArrayAdapter<ItemRow> adaptador = null;
    UserController usuarioDaSessao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_listagem_usuarios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent().hasExtra("EMAIL_USER")){
            Bundle extras = getIntent().getExtras();
            emailUsuarioDaSessao = extras.getString("EMAIL_USER");
            livroEscolhido = extras.getString("LIVRO_ESCOLHIDO");
            System.out.println(extras.getString("EMAIL_USER"));
            Log.w("EmailUser", extras.getString("EMAIL_USER"));
        }

        usuarioDaSessao = new UserController(this);
        User atual = usuarioDaSessao.findByEmail(emailUsuarioDaSessao);
        String nome = atual.getName();

        TextView NomeDoUsuario = (TextView)findViewById(R.id.NameUser);
        NomeDoUsuario.setText(nome);


        ListView lista = (ListView) findViewById(R.id.user_list_view);
        adaptador = new ArrayAdapter<ItemRow>(this,
                android.R.layout.simple_list_item_1, itemData);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                ItemRow userEscolhido = adaptador.getItem(position);
                String nome = userEscolhido.getItemName();
                int ident = userEscolhido.getId();
                System.out.println("Nome Usuario " + nome);
                Intent intent = new Intent(PaginaListagemUsuarios.this, PaginaEmprestimo.class);
                intent.putExtra("EMAIL_USER", emailUsuarioDaSessao);
                intent.putExtra("USER_ESCOLHIDO", ident);
                intent.putExtra("LIVRO_ESCOLHIDO", livroEscolhido);
                startActivity(intent);
                //usuarioPortador =
            }
        });

        db = new DatabaseHandler(this);
        populaLista();
    }

    public void populaLista()
    {
        //ListView lista = (ListView) findViewById(R.id.user_list_view);
        //itemData = new ArrayList<>();


        usuarios = new UserDao(db);
        List<User> usuariosQuePossuemOLivro = usuarios.listByBook(livroEscolhido);
        for(int i=0;i<usuariosQuePossuemOLivro.size();i++)
        {
            System.out.println("Usuarios "+ usuariosQuePossuemOLivro.get(i).getName());
            adaptador.add(new ItemRow(usuariosQuePossuemOLivro.get(i).getName(), usuariosQuePossuemOLivro.get(i).getId() ));

        }


    }

}

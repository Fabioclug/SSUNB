package mds.ufscar.br.ssunb;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import mds.ufscar.br.ssunb.database.EmprestimoDao;
import mds.ufscar.br.ssunb.database.UserDao;
import mds.ufscar.br.ssunb.model.Emprestimo;
import mds.ufscar.br.ssunb.model.User;

public class PaginaListagemEmprestimos extends AppCompatActivity {

    private UserController usuarioDaSessao;
    String emailUsuarioDaSessao;
    private DatabaseHandler db;
    List<ItemRow> itemData = new ArrayList<>();
    EmprestimoDao emprestimos;
    private ArrayAdapter<ItemRow> adaptador = null;
    User atual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_listagem_emprestimos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent().hasExtra("EMAIL_USER")){
            Bundle extras = getIntent().getExtras();
            emailUsuarioDaSessao = extras.getString("EMAIL_USER");
            System.out.println(extras.getString("EMAIL_USER"));
            Log.w("EmailUser", extras.getString("EMAIL_USER"));
        }

        usuarioDaSessao = new UserController(this);
        atual = usuarioDaSessao.findByEmail(emailUsuarioDaSessao);
        String nome = atual.getName();

        TextView NomeDoUsuario = (TextView)findViewById(R.id.NameUser);
        NomeDoUsuario.setText(nome);

        ListView lista = (ListView) findViewById(R.id.emprestimo_list_view);
        adaptador = new ArrayAdapter<ItemRow>(this,
                android.R.layout.simple_list_item_1, itemData);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                ItemRow EmprestimoEscolhido = adaptador.getItem(position);
                String livroEscolhido = EmprestimoEscolhido.getItemName();
                int idSolicitante = EmprestimoEscolhido.getIdSolicitante();
                Intent intent = new Intent(PaginaListagemEmprestimos.this, PaginaConfirmacao.class);
                intent.putExtra("EMAIL_USER", emailUsuarioDaSessao);
                intent.putExtra("USER_SOLICITANTE", idSolicitante);
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

        emprestimos = new EmprestimoDao(db);
        List<Emprestimo> emprestimosSolicitados = emprestimos.listPendingByUser(atual.getId());
        for(int i=0;i<emprestimosSolicitados.size();i++)
        {
            adaptador.add(new ItemRow(emprestimosSolicitados.get(i).getRequestedBook().getTitle(), emprestimosSolicitados.get(i).getRequester().getId(),emprestimosSolicitados.get(i).getBookOwner().getId() ));

        }


    }
}

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
import android.widget.ListView;
import android.widget.TextView;

import mds.ufscar.br.ssunb.model.Collaborator;
import mds.ufscar.br.ssunb.model.User;

public class HomeColaborador extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listView;
    CollaboratorController colaboradorAtual;
    String emailColaboradorDaSessao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_colaborador);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.ListMenuColaborador);
        listView.setOnItemClickListener(this);


        if(getIntent().hasExtra("EMAIL_USER")){
            Bundle extras = getIntent().getExtras();
            emailColaboradorDaSessao = extras.getString("EMAIL_USER");
            System.out.println(extras.getString("EMAIL_USER"));
            Log.w("EmailUser", extras.getString("EMAIL_USER"));
        }

        colaboradorAtual = new CollaboratorController(this);
        //Collaborator atual = colaboradorAtual.findByEmail(emailUsuarioDaSessao);
        //String nome = atual.getName();

        TextView NomeDoUsuario = (TextView)findViewById(R.id.NameColaborator);
        //NomeDoUsuario.setText(nome);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                Intent intentSearch = new Intent(HomeColaborador.this, ListaLivrosPendentes.class);
                intentSearch.putExtra("EMAIL_USER", emailColaboradorDaSessao);
                startActivity(intentSearch);
                break;
            case 1:
                break;
            case 2:
                break;
        }

    }
}
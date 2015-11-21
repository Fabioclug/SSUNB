package mds.ufscar.br.ssunb;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import mds.ufscar.br.ssunb.model.User;

public class HomeUsuarioActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    UserController usuarioDaSessao;
    String emailUsuarioDaSessao;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.ListMenuUser);
        listView.setOnItemClickListener(this);


        if(getIntent().hasExtra("EMAIL_USER")){
            Bundle extras = getIntent().getExtras();
            emailUsuarioDaSessao = extras.getString("EMAIL_USER");
            System.out.println(extras.getString("EMAIL_USER"));
            Log.w("EmailUser",extras.getString("EMAIL_USER"));
        }

        usuarioDaSessao = new UserController(this);
        User atual = usuarioDaSessao.findByEmail(emailUsuarioDaSessao);
        String nome = atual.getName();

        TextView NomeDoUsuario = (TextView)findViewById(R.id.NameUser);
        NomeDoUsuario.setText(nome);

    }



    public void PaginaCadastroLivro(View view)
    {
        Intent ActivityCadastroLivro;
        ActivityCadastroLivro = new Intent(this, SugestaoCadastroLivro.class);
        startActivity(ActivityCadastroLivro);
    }

    public void PaginaBusca(View view)
    {
        Intent ActivitySearch;
        ActivitySearch = new Intent(this, Search.class);
        ActivitySearch.putExtra("EMAIL_USER",emailUsuarioDaSessao);
        startActivity(ActivitySearch);
    }




    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

        switch (position){
            case 0:
                Intent intentSearch = new Intent(HomeUsuarioActivity.this, Search.class);
                intentSearch.putExtra("EMAIL_USER", emailUsuarioDaSessao);
                startActivity(intentSearch);
                break;
            case 1:
                Intent intentLivros = new Intent(HomeUsuarioActivity.this, LivrosUsuarioActivity.class);
                intentLivros.putExtra("EMAIL_USER", emailUsuarioDaSessao);
                startActivity(intentLivros);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:

                break;
            case 5:
                Intent intent = new Intent(HomeUsuarioActivity.this, SugestaoCadastroLivro.class);
                intent.putExtra("EMAIL_USER", emailUsuarioDaSessao);
                startActivity(intent);
                break;
        }
//        Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
//                Toast.LENGTH_SHORT).show();
    }

}

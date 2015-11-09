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

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

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

        SearchView etSearch = (SearchView) findViewById(R.id.options_menu_main_search);
        etSearch.setSubmitButtonEnabled(true);

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                // Called when the user submits the query
                Intent intent = new Intent(HomeUsuarioActivity.this, Search.class);
                intent.putExtra("query", query);
                HomeUsuarioActivity.this.startActivity(intent);
                return true;
            }
        };

        etSearch.setOnQueryTextListener(queryTextListener);

    }



    public void PaginaCadastroLivro(View view)
    {
        Intent ActivityCadastroLivro;
        ActivityCadastroLivro = new Intent(this, SugestaoCadastroLivro.class);
        startActivity(ActivityCadastroLivro);
    }




    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

        switch (position){
            case 0:
                Intent intentLivros = new Intent(HomeUsuarioActivity.this, LivrosUsuarioActivity.class);
                intentLivros.putExtra("EMAIL_USER", emailUsuarioDaSessao);
                startActivity(intentLivros);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                Intent intent = new Intent(HomeUsuarioActivity.this, SugestaoCadastroLivro.class);
                intent.putExtra("EMAIL_USER", emailUsuarioDaSessao);
                startActivity(intent);
                break;
        }
//        Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
//                Toast.LENGTH_SHORT).show();
    }

}

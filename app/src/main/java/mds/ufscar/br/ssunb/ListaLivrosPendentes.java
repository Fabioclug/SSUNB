package mds.ufscar.br.ssunb;

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

import java.util.ArrayList;
import java.util.List;

import mds.ufscar.br.ssunb.database.BookDao;
import mds.ufscar.br.ssunb.database.DatabaseHandler;
import mds.ufscar.br.ssunb.database.UserDao;
import mds.ufscar.br.ssunb.model.Book;
import mds.ufscar.br.ssunb.model.User;

public class ListaLivrosPendentes extends AppCompatActivity {

    String emailColaboradorSessao;
    String livroEscolhido;
    List<ItemRow> itemData = new ArrayList<>();
    DatabaseHandler db;
    BookDao livros;
    private ArrayAdapter<ItemRow> adaptador = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_livros_pendentes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent().hasExtra("EMAIL_USER")){
            Bundle extras = getIntent().getExtras();
            emailColaboradorSessao = extras.getString("EMAIL_USER");
            //System.out.println(extras.getString("EMAIL_USER"));
            //Log.w("EmailUser", extras.getString("EMAIL_USER"));
        }

        ListView lista = (ListView) findViewById(R.id.pending_list_view);
        adaptador = new ArrayAdapter<ItemRow>(this,
                android.R.layout.simple_list_item_1, itemData);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                ItemRow livroEscolhido = adaptador.getItem(position);
                String nome = livroEscolhido.getItemName();
                int ident = livroEscolhido.id;
                System.out.println("Nome Livro " + nome+ " id:"+ident);
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


        livros = new BookDao(db);
        List<Book> livrosPendentes = livros.listPending();
        for(int i=0;i<livrosPendentes.size();i++)
        {
            System.out.println("Usuarios "+ livrosPendentes.get(i).getTitle());
            adaptador.add(new ItemRow(livrosPendentes.get(i).getTitle(), livrosPendentes.get(i).getCode() ));

        }


    }

}

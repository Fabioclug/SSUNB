package mds.ufscar.br.ssunb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;
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
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_listagem_emprestimos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

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
                int idLivro = EmprestimoEscolhido.getId();
                int idSolicitante = EmprestimoEscolhido.getIdSolicitante();

                try{

                    Emprestimo emprestimo = emprestimos.findEmprestimo(idSolicitante, atual.getId(), idLivro);
                    emprestimos.confirmEmprestimo(emprestimo);
                    Toast.makeText(context, "Empréstimo confirmado!", Toast.LENGTH_SHORT).show();

                    Intent calIntent = new Intent(Intent.ACTION_INSERT);
                    calIntent.setType("vnd.android.cursor.item/event");
                    calIntent.putExtra(CalendarContract.Events.TITLE, "Empréstimo - " + emprestimo.getRequestedBook().getTitle());
                    calIntent.putExtra(CalendarContract.Events.DESCRIPTION, "Empréstimo do livro " + emprestimo.getRequestedBook().getTitle() +
                    " para " + emprestimo.getRequester().getName() + " " + emprestimo.getRequester().getSurname());

                    int dia = emprestimo.getDate().getDay();
                    int mes = emprestimo.getDate().getMonth();
                    int ano = emprestimo.getDate().getYear();
                    GregorianCalendar calDate = new GregorianCalendar(dia, mes, ano);
                    calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
                    calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                            calDate.getTimeInMillis());
                    calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                            calDate.getTimeInMillis());
                    startActivity(calIntent);

                }catch(Exception e)
                {
                    Toast.makeText(context, "Falha ao confirmar!", Toast.LENGTH_SHORT).show();
                }


//                Intent intent = new Intent(PaginaListagemEmprestimos.this, PaginaConfirmacao.class);
//                intent.putExtra("EMAIL_USER", emailUsuarioDaSessao);
//                intent.putExtra("USER_SOLICITANTE", idSolicitante);
//                intent.putExtra("LIVRO_ESCOLHIDO", livroEscolhido);
//                startActivity(intent);
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
            adaptador.add(new ItemRow(emprestimosSolicitados.get(i).getRequestedBook().getTitle(), emprestimosSolicitados.get(i).getRequestedBook().getCode(),
                    emprestimosSolicitados.get(i).getRequester().getId(),emprestimosSolicitados.get(i).getBookOwner().getId() ));

        }


    }
}

package mds.ufscar.br.ssunb;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void EfetuarCadastro(View view)
    {
        // Recuperamos os valores dos campos da tela.
        EditText campoNome = (EditText) findViewById(R.id.editTextPrimNome);
        EditText campoSurname = (EditText) findViewById(R.id.editTextSobreNome);
        EditText campoCidade = (EditText) findViewById(R.id.editTextCidade);
        EditText campoEmail = (EditText) findViewById(R.id.editTextEmail);
        EditText campoSenha = (EditText) findViewById(R.id.editTextSenha);

        String nome = campoNome.getText().toString();
        String surname = campoSurname.getText().toString();
        String city = campoCidade.getText().toString();
        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();

        System.out.println("Informacoes User: " + nome + " " + surname + " " + city + " " + email + " " + senha);

        clear((ViewGroup) findViewById(R.id.LinearLayout1));


        //long numero = Long.parseLong(campoNumero.getText().toString());
    }

    public void clear(ViewGroup group) {

        int count = group.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText)view).setText("");
            }
        }
    }

}

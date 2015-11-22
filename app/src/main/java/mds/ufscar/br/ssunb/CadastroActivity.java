package mds.ufscar.br.ssunb;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mds.ufscar.br.ssunb.model.User;

public class CadastroActivity extends AppCompatActivity {
    private Context context;
    private AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

    }

    public void EfetuarCadastro(View view) {
        // Recuperamos os valores dos campos da tela.
        EditText campoNome = (EditText) findViewById(R.id.editTextPrimNome);
        EditText campoSurname = (EditText) findViewById(R.id.editTextSobreNome);
        EditText campoCidade = (EditText) findViewById(R.id.editTextCidade);
        EditText campoEmail = (EditText) findViewById(R.id.editTextEmail);
        EditText campoSenha = (EditText) findViewById(R.id.editTextSenha);
        EditText campoRepSenha =  (EditText) findViewById(R.id.editTextRepSenha);

        List<String> campos= new ArrayList<>();
        int cont = 0;
        int cont2 = 0;

        String nome = campoNome.getText().toString();
        if(nome.length()==0)
        {
            campos.add("Nome");
            cont++;
        }
        String surname = campoSurname.getText().toString();
        if(surname.length()==0)
        {
            campos.add("Sobrenome");
            cont++;
        }
        String city = campoCidade.getText().toString();
        if(city.length()==0)
        {
            campos.add("Cidade");
            cont++;
        }
        String email = campoEmail.getText().toString();
        if(email.length()==0)
        {
            campos.add("e-mail");
            cont++;
        }
        if(!email.toLowerCase().contains("@"))
        {
            exibeDialogo("Favor entrar com um email válido");
            cont2++;
        }
        String senha = campoSenha.getText().toString();
        if(senha.length()==0)
        {
            campos.add("senha");
            cont++;
        }
        String repsenha = campoRepSenha.getText().toString();
        if(repsenha.length()==0)
        {
            campos.add("confirmação da senha");
            cont++;
        }

        if(!senha.equals(repsenha))
        {
            exibeDialogo("Verificar senhas");
            cont2++;
        }

        if(!(cont == 0 && cont2==0))
        {
            if(cont > 1)
            {
                String c = "";
                for(int i=0; i < campos.size(); i++)
                {
                    c = c + " " + campos.get(i) + ",";
                }

                exibeDialogo("Campos"+c+" são obrigatórios");

            }else{
                if(cont == 1)
                    exibeDialogo("Campo "+campos.get(0)+" é obrigatório");
            }
        }else{
            User novoUsuario = new User(nome, surname, city, email, senha);
            UserController novoUserControler = new UserController(this);
            Double latitude = null;
            Double longitude = null;
            MyLocationListener gps = new MyLocationListener(CadastroActivity.this);
            if(gps.canGetLocation()) {
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
                //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            } else {
                // Can't get location.
                // GPS or network is not enabled.
                // Ask user to enable GPS/network in settings.
                gps.showSettingsAlert();
            }

            if(latitude != null && longitude != null) {
                novoUsuario.setLatitude(latitude.doubleValue());
                novoUsuario.setLongitude(longitude.doubleValue());
            }

            try{
                novoUserControler.insert(novoUsuario);
            }catch(Exception e)
            {
                System.out.println("Falha ao inserir usuario no BD");
            }



            System.out.println("Informacoes User: " + nome + " " + surname + " " + city + " " + email + " " + senha);

            clear((ViewGroup) findViewById(R.id.LinearLayout1));

        }





        //long numero = Long.parseLong(campoNumero.getText().toString());
    }

    public void exibeDialogo(String mensagem) {
        alert = new AlertDialog.Builder(context);
        alert.setPositiveButton("OK", null);
        alert.setMessage(mensagem);
        alert.create().show();
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

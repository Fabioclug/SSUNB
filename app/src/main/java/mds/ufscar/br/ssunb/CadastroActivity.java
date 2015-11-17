package mds.ufscar.br.ssunb;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import mds.ufscar.br.ssunb.model.User;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void EfetuarCadastro(View view) {
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

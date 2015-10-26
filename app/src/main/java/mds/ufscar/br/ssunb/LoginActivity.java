package mds.ufscar.br.ssunb;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import mds.ufscar.br.ssunb.model.User;

public class LoginActivity extends Activity {

    private EditText campoLogin, campoSenha;
    private Context context;
    private UserController usuarioController;
    private AlertDialog.Builder alert;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        usuarioController = new UserController(this);
       // campoLogin = (EditText) findViewById(R.id.LoginText);
        //campoSenha = (EditText) findViewById(R.id.senha);

        try {
            //testaInicializacao();
        } catch (Exception e) {
            exibeDialogo("Erro inicializando banco de dados");
            e.printStackTrace();
        }

    }

    /**
     * @throws Exception
     */
//    public void testaInicializacao() throws Exception {
//        if (usuarioController.findAll().isEmpty()) {
//            User usuario = new User("nome", "sobrenome", "email", "cidade", "senha");
//            usuarioController.insert(usuario);
//        }
//    }

    /**
     *
     */
    public void exibeDialogo(String mensagem) {
        alert = new AlertDialog.Builder(context);
        alert.setPositiveButton("OK", null);
        alert.setMessage(mensagem);
        alert.create().show();
    }

    public void validar(View view) {
        String login = campoLogin.getText().toString();
        String senha = campoSenha.getText().toString();

        try {
            boolean isValid = usuarioController.validaLogin(login, senha);
            if (isValid) {
                exibeDialogo("Usuario e senha validados com sucesso!");
            } else {
                exibeDialogo("Verifique usuario e senha!");
            }
        } catch (Exception e) {
            exibeDialogo("Erro validando usuario e senha");
            e.printStackTrace();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_login, menu);
//        return true;
//    }

}
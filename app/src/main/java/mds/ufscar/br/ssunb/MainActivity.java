package mds.ufscar.br.ssunb;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.view.View.OnClickListener;
import android.widget.Toast;

import mds.ufscar.br.ssunb.database.DatabaseHandler;
import mds.ufscar.br.ssunb.model.Book;

public class MainActivity extends Activity implements PopupMenu.OnMenuItemClickListener {

   // private ListView book_list;
    private EditText campoLogin, campoSenha;
    private Context context;
    private UserController usuarioController;
    private AlertDialog.Builder alert;
    private String emailUserSecao;

    public MainActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
//        DatabaseHandler db = new DatabaseHandler(this);
//        db.getWritableDatabase().execSQL("CREATE TABLE usuario (id integer not null primary key, " +
//                "PrimNome text not null, SobreNome text not null,cidade text not null," +
//                "email text not null, senha text not null )");

        //LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //LocationListener mlocListener = new MyLocationListener(getApplicationContext());
        //mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);

//        book_list = (ListView) findViewById(R.id.book_list_view);
//        book_list.setAdapter(new BookListAdapter(this));
//        //findViewById(R.id.action_settings)
//        findViewById(R.id.menu_button).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
//                popupMenu.setOnMenuItemClickListener(MainActivity.this);
//                popupMenu.inflate(R.menu.menu_main);
//                popupMenu.show();
//            }
//        });

        context = this;
        //context.deleteDatabase("ssunb");
        usuarioController = new UserController(this);
        // campoLogin = (EditText) findViewById(R.id.LoginText);
        //campoSenha = (EditText) findViewById(R.id.senha);

        findViewById(R.id.loginButton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validar()) {
                    Intent intent = new Intent(MainActivity.this, HomeUsuarioActivity.class);
                    intent.putExtra("EMAIL_USER", emailUserSecao);
                    startActivity(intent);
                }
            }
        });

        try {
            //testaInicializacao();
        } catch (Exception e) {
            exibeDialogo("Erro inicializando banco de dados");
            e.printStackTrace();
        }




    }

//    public void enterBookPage(View v) {
//        //Book book = (Book) v.getTag();
//        Intent intent = new Intent(MainActivity.this, BookPage.class);
//        startActivity(intent);
//    }


    public void exibeDialogo(String mensagem) {
        alert = new AlertDialog.Builder(context);
        alert.setPositiveButton("OK", null);
        alert.setMessage(mensagem);
        alert.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    public void startActivityLogin(View view) {
//
//        Intent ActivityLogin;
//        ActivityLogin = new Intent(this, LoginActivity.class);
//        startActivity(ActivityLogin);
//    }

    public void startActivityCadastro(View view) {

        Intent ActivityCadastro;
        ActivityCadastro = new Intent(this, CadastroActivity.class);
        startActivity(ActivityCadastro);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_profile:
                Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }


    public boolean validar() {

        campoLogin = (EditText) findViewById(R.id.loginText);
        campoSenha = (EditText) findViewById(R.id.senhaText);

        String login = campoLogin.getText().toString();
        String senha = campoSenha.getText().toString();
        boolean answer = false;
        try {
            boolean isValid = usuarioController.validaLogin(login, senha);
            if (isValid) {
                answer = true;
                emailUserSecao = login;
                exibeDialogo("Usuario e senha validados com sucesso!");
            } else {
                exibeDialogo("Verifique usuario e senha!");
            }
        } catch (Exception e) {
            exibeDialogo("Erro validando usuario e senha");
            e.printStackTrace();
        }
        return answer;
    }

}

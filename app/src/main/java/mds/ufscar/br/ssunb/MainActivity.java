package mds.ufscar.br.ssunb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import mds.ufscar.br.ssunb.database.BookDao;
import mds.ufscar.br.ssunb.database.DatabaseHandler;
import mds.ufscar.br.ssunb.model.Book;
import mds.ufscar.br.ssunb.model.Collaborator;
import mds.ufscar.br.ssunb.model.User;


public class MainActivity extends Activity implements PopupMenu.OnMenuItemClickListener
{

    private EditText campoLogin, campoSenha;
    private Context context;
    private UserController usuarioController;
    private CollaboratorController colaboradorControler;
    private AlertDialog.Builder alert;
    private String emailUserSecao;
    private String part1, part2;

    private CallbackManager callbackManager;


    public MainActivity() {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // API Facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_main);
        context = this;


       /* Book b1 = new Book("Lord of The Rings", "J. R. R. Tolkien", "");
        Book b2 = new Book("Fight Club", "Chuck Palahniuk");
        Book b3 = new Book("The Da Vinci Code", "Dan Brown");
        Book b4 = new Book("Frankenstein", "Mary Shelley");
        Book b5 = new Book("A Game of Thrones", "George R. R. Martin");
        Book b6 = new Book("The Divine Comedy", "Dante Alighieri");*/

        Book b1 = new Book("O Senhor dos Anéis", "J. R. R. Tolkien", "Literatura Estrangeira", "O Senhor dos Anéis " +
                "(The Lord of the Rings) é um romance de fantasia criado pelo escritor, professor e filólogo britânico J.R.R. Tolkien" +
                ". A história começa como seqüência de um livro anterior de Tolkien, O Hobbit (The Hobbit), e logo se desenvolve numa" +
                " história muito maior. Foi escrito entre 1937 e 1949, com muitas partes criadas durante a Segunda Guerra Mundial. " +
                "Embora Tolkien tenha planejado realizá-lo em volume único, foi originalmente publicado em três volumes entre 1954 e " +
                "1955, e foi assim, em três volumes, que se tornou popular. Desde então foi reimpresso várias vezes e foi traduzido " +
                "para mais de 40 línguas, tornando-se um dos trabalhos mais populares da literatura do século XX.","29/07/1954",1,100);
     /*   Book b2 = new Book("Fight Club", "Chuck Palahniuk");
        Book b3 = new Book("The Da Vinci Code", "Dan Brown");
        Book b4 = new Book("Frankenstein", "Mary Shelley");
        Book b5 = new Book("A Game of Thrones", "George R. R. Martin");
        Book b6 = new Book("The Divine Comedy", "Dante Alighieri");*/

        DatabaseHandler db = new DatabaseHandler(context);

        BookDao bookDao = new BookDao(db);
        bookDao.save(b1);
   /*     bookDao.save(b2);
        bookDao.save(b3);
        bookDao.save(b4);
        bookDao.save(b5);
        bookDao.save(b6);*/



        //context.deleteDatabase("ssunb");

        Collaborator novoColaborador = new Collaborator("Yoda", "=]", "Dagobah", "yoda@ssunb.com", "123","1239");
        CollaboratorController novoCollaboratorControler = new CollaboratorController(this);

        try{
            novoCollaboratorControler.insert(novoColaborador);
        }catch(Exception e)
        {
            System.out.println(e.toString());
        }


        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        System.out.println("Success");
                        GraphRequest.newMeRequest(
                                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject json, GraphResponse response) {
                                        if (response.getError() != null) {
                                            // handle error
                                            System.out.println("ERROR");
                                        } else {
                                            System.out.println("Success");
                                            try {

                                                String jsonresult = String.valueOf(json);
                                                System.out.println("JSON Result" + jsonresult);

                                                String str_email = json.getString("email");
                                                String str_id = json.getString("id");
                                                String str_firstname = json.getString("first_name");
                                                String str_lastname = json.getString("last_name");
                                                String str_localizacao = json.getString("locale");
                                                String str_senha = json.getString("password");


                                                User novoUsuario = new User(str_firstname, str_lastname, str_localizacao, str_email, str_senha);
                                                UserController novoUserControler = new UserController(context);
                                                emailUserSecao = str_email;

                                                try {

                                                    novoUserControler.insert(novoUsuario);
                                                    Intent intent = new Intent(MainActivity.this, HomeUsuarioActivity.class);
                                                    intent.putExtra("EMAIL_USER", emailUserSecao);
                                                    startActivity(intent);

                                                } catch (Exception e) {
                                                    System.out.println("Falha ao inserir usuario no BD");
                                                }


                                                System.out.println("Informacoes User: ");

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                }).executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
        //context.deleteDatabase("ssunb");


        findViewById(R.id.loginButton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validar()) {
                    if (part2.equals("ssunb.com")) {
                        Intent intent = new Intent(MainActivity.this, HomeColaborador.class);
                        intent.putExtra("EMAIL_USER", emailUserSecao);
                        startActivity(intent);

                    } else {
                        Intent intent = new Intent(MainActivity.this, HomeUsuarioActivity.class);
                        intent.putExtra("EMAIL_USER", emailUserSecao);
                        startActivity(intent);
                    }

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
        boolean isValid;
        boolean emailOk = false;

        if(login.toLowerCase().contains("@"))
        {
            String[] parts = login.split("@");
            part1 = parts[0];
            part2 = parts[1];
            emailOk = true;

        }

        if(emailOk)
        {
            if(part2.equals("ssunb.com"))
            {
                try {
                    colaboradorControler = new CollaboratorController(context);
                    isValid = colaboradorControler.validaLogin(login, senha);
                    if (isValid) {
                        answer = true;
                        emailUserSecao = login;
                        exibeDialogo("Colaborador e senha validados com sucesso!");
                    } else {
                        exibeDialogo("Verifique colaborador e senha!");
                    }
                } catch (Exception e) {
                    exibeDialogo("Erro validando colaborador e senha");
                    e.printStackTrace();
                }
            }
            else{
                try {
                    usuarioController = new UserController(context);
                    isValid = usuarioController.validaLogin(login, senha);
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
            }

        }else
        {
            exibeDialogo("Você deve informar um email válido para realizar login");
        }

        return answer;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}



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

//usuarioController = new UserController(this);
//colaboradorControler = new CollaboratorController(this);
// campoLogin = (EditText) findViewById(R.id.LoginText);
//campoSenha = (EditText) findViewById(R.id.senha);
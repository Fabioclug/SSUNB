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


        //context.deleteDatabase("ssunb");

        DatabaseHandler db = new DatabaseHandler(context);
        //this.populate_database();


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

    public void populate_database() {
        DatabaseHandler db = new DatabaseHandler(context);
        db.getWritableDatabase().execSQL("INSERT INTO usuario (PrimNome,SobreNome,cidade,email,senha, latitude, longitude) VALUES ('Fabio', 'Clug', 'Ribeirao Preto', 'fabioclug@hotmail.com', '1234', 21.32, 118.55);");
        db.getWritableDatabase().execSQL("INSERT INTO usuario (PrimNome,SobreNome,cidade,email,senha, latitude, longitude) VALUES ('Breno', 'Silveira', 'São Carlos', 'brenosilveira@gmail.com', 'abcd', 35.18, 47.65);");
        db.getWritableDatabase().execSQL("INSERT INTO usuario (PrimNome,SobreNome,cidade,email,senha, latitude, longitude) VALUES ('Gabriela','Mattos','São Carlos','gabrielamattos@gmail.com','semsenha', 40.98, -90.908);");
        db.getWritableDatabase().execSQL("INSERT INTO usuario (PrimNome,SobreNome,cidade,email,senha, latitude, longitude) VALUES ('Enoc', 'Pierre', 'São Carlos', 'enoc@hotmail.com', 'ab12', 70.95, 100.25);");
        db.getWritableDatabase().execSQL("INSERT INTO usuario (PrimNome,SobreNome,cidade,email,senha, latitude, longitude) VALUES ('Minch', 'Yoda', 'Dagobah', 'yoda@ssunb.com', '1239', 59.42, 22.55);");
        db.getWritableDatabase().execSQL("INSERT INTO colaborador (id, cpf) VALUES(4, '406.578.829-61');");
        db.getWritableDatabase().execSQL("INSERT INTO book (title, author, category,synopsis,publication,edition,publisher,pages,pending) " +
                        "VALUES ('O Senhor dos Anéis', 'J. R. R. Tolkien', 'Literatura Estrangeira', 'O Senhor dos Anéis (The Lord of the Rings) é um romance" +
                        "    de fantasia criado pelo escritor, professor e filólogo britânico J.R.R. Tolkien. A história começa como seqüência de um livro anterior de Tolkien, O Hobbit (The Hobbit), e logo se desenvolve numa " +
                        "    história muito maior. Foi escrito entre 1937 e 1949, com muitas partes criadas durante a Segunda Guerra Mundial. Embora Tolkien tenha planejado realizá-lo em volume único, foi originalmente " +
                        "    publicado em três volumes entre 1954 e 1955, e foi assim, em três volumes, que se tornou popular. Desde então foi reimpresso várias vezes e foi traduzido " +
                        "    para mais de 40 línguas, tornando-se um dos trabalhos mais populares da literatura do século XX.','29/07/1954',1,'Editora Atica',250,0);");

        db.getWritableDatabase().execSQL("INSERT INTO book (title, author, category,synopsis,publication,edition,publisher,pages,pending) " +
                        "VALUES ('Clube da Luta', 'Chuck Palahniuk', 'Suspense/Drama', 'O clube da luta é idealizado por Tyler Durden, que acredita ter encontrado uma maneira de viver fora dos limites da sociedade e das regras sem sentido. " +
                        "    Mas o que está por vir de sua mente pode piorar muito. O livro serviu de base para um filme de 1999, procurando adaptar a atmosfera do livro, o mundo caótico do personagem e o humor negro do autor.', " +
                        "    '01/04/1996', 1, 'LEYA Brasil', 270,0);");
        db.getWritableDatabase().execSQL("INSERT INTO book (title, author, category,synopsis,publication,edition,publisher,pages,pending) " +
                        "VALUES ('Frankenstein', 'Mary Shelley', 'Terror/Ficção Científica', 'Frankenstein é o primeiro clássico da literatura de horror. A autora, casada com o poeta Percy Shelly, tinha dezenove anos quando o escreveu em 1818. " +
                        "    É a história de um estudante da mesma idade - Victor Frankenstein - que constrói uma criatura horrenda. Ao despertar para o mundo, o monstro se vê rejeitado por todos. Daí sua tragédia e a terrível vingança que imporá ao seu criador.'," +
                        "    '01/01/1818',1, 'Cia das Letras',150,0);");
        db.getWritableDatabase().execSQL("INSERT INTO emprestimo(solicitante, dono_livro, livro, retirada, autorizado)" +
                        "VALUES (2, 1, 2,'10/12/2015',0);");
        db.getWritableDatabase().execSQL("INSERT INTO exemplar_livro (usuario,livro,status) " +
                        "VALUES(1,2,'Emprestado');");
        db.getWritableDatabase().execSQL("INSERT INTO exemplar_livro (usuario,livro,status) " +
                        "VALUES(2, 1, 'Disponivel');");
        db.getWritableDatabase().execSQL("INSERT INTO exemplar_livro (usuario,livro,status) VALUES(3, 2, 'Disponivel');");

    }

}
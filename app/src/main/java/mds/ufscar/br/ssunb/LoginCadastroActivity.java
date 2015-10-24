package mds.ufscar.br.ssunb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class LoginCadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_cadastro);
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

    public void startActivityLogin(View view) {

        Intent ActivityLogin;
        ActivityLogin = new Intent(this, LoginActivity.class);
        startActivity(ActivityLogin);
    }

    public void startActivityCadastro(View view) {

        Intent ActivityCadastro;
        ActivityCadastro = new Intent(this, LoginActivity.class);
        startActivity(ActivityCadastro);
    }



}

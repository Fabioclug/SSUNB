package mds.ufscar.br.ssunb;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.view.View.OnClickListener;
import android.widget.Toast;

import mds.ufscar.br.ssunb.model.Book;

public class MainActivity extends Activity implements PopupMenu.OnMenuItemClickListener {

   // private ListView book_list;

    public MainActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

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
    }

//    public void enterBookPage(View v) {
//        //Book book = (Book) v.getTag();
//        Intent intent = new Intent(MainActivity.this, BookPage.class);
//        startActivity(intent);
//    }

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

    public void startActivityLogin(View view) {

        Intent ActivityLogin;
        ActivityLogin = new Intent(this, LoginActivity.class);
        startActivity(ActivityLogin);
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
}

package mds.ufscar.br.ssunb;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;

import java.util.ArrayList;
import java.util.List;

import mds.ufscar.br.ssunb.database.BookDao;
import mds.ufscar.br.ssunb.database.DatabaseHandler;
import mds.ufscar.br.ssunb.model.Book;
import mds.ufscar.br.ssunb.model.User;

public class LivrosUsuarioActivity extends AppCompatActivity {

    SwipeListView swipelistview;
    ItemAdapter adapter;
    List<ItemRow> itemData;
    UserController usuarioDaSessao;
    String emailUsuarioDaSessao;
    BookDao livrosUsuario;
    User atual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livros_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        if(getIntent().hasExtra("EMAIL_USER")){
            Bundle extras = getIntent().getExtras();
            emailUsuarioDaSessao = extras.getString("EMAIL_USER");
            System.out.println(extras.getString("EMAIL_USER"));
            Log.w("EmailUser",extras.getString("EMAIL_USER"));
        }

        usuarioDaSessao = new UserController(this);
        atual = usuarioDaSessao.findByEmail(emailUsuarioDaSessao);
        String nome = atual.getName();
        int codigo = atual.getId();

        TextView NomeDoUsuario = (TextView)findViewById(R.id.NameUser);
        NomeDoUsuario.setText(nome);

        swipelistview=(SwipeListView)findViewById(R.id.example_swipe_lv_list);
        itemData=new ArrayList<ItemRow>();
        adapter=new ItemAdapter(this,R.layout.custom_row,itemData,atual);

        swipelistview.setSwipeListViewListener(new BaseSwipeListViewListener() {
            @Override
            public void onOpened(int position, boolean toRight) {
            }

            @Override
            public void onClosed(int position, boolean fromRight) {
            }

            @Override
            public void onListChanged() {
            }

            @Override
            public void onMove(int position, float x) {
            }

            @Override
            public void onStartOpen(int position, int action, boolean right) {
                Log.d("swipe", String.format("onStartOpen %d - action %d", position, action));
            }

            @Override
            public void onStartClose(int position, boolean right) {
                Log.d("swipe", String.format("onStartClose %d", position));
            }

            @Override
            public void onClickFrontView(int position) {
                Log.d("swipe", String.format("onClickFrontView %d", position));

                swipelistview.openAnimate(position); //when you touch front view it will open

            }

            @Override
            public void onClickBackView(int position) {
                Log.d("swipe", String.format("onClickBackView %d", position));

                swipelistview.closeAnimate(position);//when you touch back view it will close
            }

            @Override
            public void onDismiss(int[] reverseSortedPositions) {

            }

        });

        //These are the swipe listview settings. you can change these
        //setting as your requrement
        swipelistview.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT); // there are five swiping modes
        swipelistview.setSwipeActionLeft(SwipeListView.SWIPE_ACTION_REVEAL); //there are four swipe actions
        swipelistview.setSwipeActionRight(SwipeListView.SWIPE_ACTION_REVEAL);
        swipelistview.setOffsetLeft(convertDpToPixel(60f)); // left side offset
        swipelistview.setOffsetRight(convertDpToPixel(0f)); // right side offset
        swipelistview.setAnimationTime(50); // animarion time
        swipelistview.setSwipeOpenOnLongPress(true); // enable or disable SwipeOpenOnLongPress

        swipelistview.setAdapter(adapter);

//        DatabaseHandler db = new DatabaseHandler(this);
//        livrosUsuario = new BookDao(db);
//        List<Book> livros = livrosUsuario.listByUser(codigo);

//        for(int i=0;i<livros.size();i++)
//        {
//            itemData.add(new ItemRow(livros.get(i).getTitle() ));
//
//        }

        itemData.add(new ItemRow("Livro"));

        adapter.notifyDataSetChanged();



    }

    public int convertDpToPixel(float dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

}

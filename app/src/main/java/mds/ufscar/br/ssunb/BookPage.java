package mds.ufscar.br.ssunb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import mds.ufscar.br.ssunb.model.Book;

public class BookPage extends Activity {

    public BookPage() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_page);

        Intent r = getIntent();


        BookListAdapter bla = new BookListAdapter(this);
        int id = Integer.parseInt(r.getStringExtra("book_id"));

        Book b = (Book) bla.getItem(id);

        TextView book_title = (TextView) findViewById(R.id.book_title);
        book_title.setText(b.getTitle());

        TextView book_description = (TextView) findViewById(R.id.book_description);
        book_description.setText(b.getAuthor());
    }
}

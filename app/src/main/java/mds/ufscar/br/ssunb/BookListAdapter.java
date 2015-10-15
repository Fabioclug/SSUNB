package mds.ufscar.br.ssunb;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import mds.ufscar.br.ssunb.model.Book;

public class BookListAdapter extends BaseAdapter{

    private Context context;
    private List<Book> BookList;

    public BookListAdapter(Context context) {
        this.context = context;
        BookList = new ArrayList<Book>();

        Book b1 = new Book("Lord of The Rings", "J. R. R. Tolkien");
        Book b2 = new Book("Fight Club", "Chuck Palahniuk");
        Book b3 = new Book("The Da Vinci Code", "Dan Brown");
        Book b4 = new Book("Frankenstein", "Mary Shelley");
        Book b5 = new Book("A Game of Thrones", "George R. R. Martin");
        Book b6 = new Book("The Divine Comedy", "Dante Alighieri");

        BookList.add(b1);
        BookList.add(b2);
        BookList.add(b3);
        BookList.add(b4);
        BookList.add(b5);
        BookList.add(b6);

    }

    @Override
    public int getCount() {
        return BookList.size();
    }

    @Override
    public Object getItem(int position) {
        return BookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.book_list_row, null);

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, BookPage.class);
                intent.putExtra("book_id", Integer.toString(position));
                System.out.println(position);
                context.startActivity(intent);
            }
        });

        //ImageButton ib = (ImageButton) root.findViewById(R.id.book_list_row_icon);
        //ib.setImageResource(R.drawable.);

        TextView name = (TextView) root.findViewById(R.id.book_list_row_name);
        name.setText(BookList.get(position).getTitle());

        TextView author = (TextView) root.findViewById(R.id.book_list_row_author);
        author.setText(BookList.get(position).getAuthor());

        return root;
    }
}

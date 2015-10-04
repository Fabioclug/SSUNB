package mds.ufscar.br.ssunb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import mds.ufscar.br.ssunb.model.Book;

/**
 * Created by Fabioclug on 2015-10-03.
 */
public class BookListAdapter extends BaseAdapter{

    private Context context;
    private List<Book> BookList;

    public BookListAdapter(Context context) {
        this.context = context;
        BookList = new ArrayList<Book>();

        Book b1 = new Book("Lord of The Rings", "J. R. R. Tolkien");
        Book b2 = new Book("Fight Club", "Chuck Palahniuk");

        BookList.add(b1);
        BookList.add(b2);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.book_list_row, null);

        TextView name = (TextView) root.findViewById(R.id.book_list_row_name);
        name.setText(BookList.get(position).getName());

        TextView author = (TextView) root.findViewById(R.id.book_list_row_author);
        author.setText(BookList.get(position).getAuthor());
        return root;
    }
}

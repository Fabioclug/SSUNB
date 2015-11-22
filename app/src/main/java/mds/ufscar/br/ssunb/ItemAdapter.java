package mds.ufscar.br.ssunb;

/**
 * Created by breno on 04/11/15.
 */
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import mds.ufscar.br.ssunb.database.BookDao;
import mds.ufscar.br.ssunb.database.DatabaseHandler;
import mds.ufscar.br.ssunb.model.Book;
import mds.ufscar.br.ssunb.model.User;

public class ItemAdapter extends ArrayAdapter {

    List   data;
    Context context;
    int layoutResID;
    Button botao1, botao2, botao3;
    //ItemRow itemdata;
    User userAtual;
    BookDao Bd;
    DatabaseHandler db;
    Search PaginaPesquisa;

    public ItemAdapter(Context context, int layoutResourceId,List data, User atual) {
        super(context, layoutResourceId, data);

        this.data=data;
        this.context = context;
        this.layoutResID=layoutResourceId;
        this.userAtual = atual;
        this.db = new DatabaseHandler(this.context);


        // TODO Auto-generated constructor stub
    }

    public ItemAdapter(Context context, int layoutResourceId,List data, User atual, Search paginaPesquisa) {
        super(context, layoutResourceId, data);

        this.data=data;
        this.context=context;
        this.layoutResID=layoutResourceId;
        this.userAtual = atual;
        this.db = new DatabaseHandler(this.context);
        this.PaginaPesquisa = paginaPesquisa;


        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        NewsHolder holder = null;
        View row = convertView;
        holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResID, parent, false);

            holder = new NewsHolder();

            holder.itemName = (TextView)row.findViewById(R.id.example_itemname);
            holder.icon=(ImageView)row.findViewById(R.id.example_image);
            holder.button1=(Button)row.findViewById(R.id.swipe_button1);
            holder.button2=(Button)row.findViewById(R.id.swipe_button2);
            holder.button3=(Button)row.findViewById(R.id.swipe_button3);

            botao1 = holder.button1;
            botao2 = holder.button2;
            botao3 = holder.button3;

            row.setTag(holder);
        }
        else
        {
            holder = (NewsHolder)row.getTag();
        }

        final ItemRow itemdata = (ItemRow) data.get(position);
        holder.itemName.setText(itemdata.getItemName());
        holder.icon.setImageDrawable(itemdata.getIcon());

        holder.button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                System.out.println("AquiiiiiiiiiiiiiiiiiI!");
                // TODO Auto-generated method stub
                //Toast.makeText(context, "Button 1 Clicked",Toast.LENGTH_SHORT).show();
                String nomeDoLivro = itemdata.getItemName();
                Intent Paginalivro = new Intent(context, LivroActivity.class);
                Paginalivro.putExtra("Livro", nomeDoLivro);
                Paginalivro.putExtra("EMAIL_USER", userAtual.getEmail());
                context.startActivity(Paginalivro);
            }
        });

        holder.button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(botao2.getText().toString().equals("Requisitar"))
                {
                    String nomeDoLivro = itemdata.getItemName();
                    PaginaPesquisa.setLivroEscolhido(nomeDoLivro);
                    PaginaPesquisa.listaUsuarios();
                    Toast.makeText(context, "Requisitar",Toast.LENGTH_SHORT).show();
                }else
                {
                    if(botao2.getText().toString().equals("Eliminar"))
                    {

                    }
                    //Toast.makeText(context, "não e requisitar",Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Toast.makeText(context, "Button 3 Clicked",Toast.LENGTH_SHORT).show();
                if(botao3.getText().toString().equals("Adicionar"))
                {
                    Bd = new BookDao(db);
                    String nomeDoLivro = itemdata.getItemName();
                    Book livro = Bd.findByTitle(nomeDoLivro);
                    System.out.println("Encontrou o livro:");
                    int idLivro = livro.getCode();
                    System.out.println("Pegou o codigo: "+idLivro);
                    int idUser = userAtual.getId();
                    if(Bd.saveBookCopy(idLivro,idUser))
                    {
                        Toast.makeText(context, "Livro Inserido com sucesso!",Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Toast.makeText(context, "Falha ao inserir livro.",Toast.LENGTH_SHORT).show();
                    }
                    //Toast.makeText(context, "Requisitar",Toast.LENGTH_SHORT).show();
                }else
                {
                    //Toast.makeText(context, "não e requisitar",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return row;

    }


    static class NewsHolder{

        TextView itemName;
        ImageView icon;
        Button button1;
        Button button2;
        Button button3;
    }

}

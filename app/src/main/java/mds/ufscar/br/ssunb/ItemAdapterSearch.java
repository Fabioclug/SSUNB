package mds.ufscar.br.ssunb;

/**
 * Created by breno on 04/11/15.
 */
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ItemAdapterSearch extends ArrayAdapter {

    List   data;
    Context context;
    int layoutResID;
    Button botao1, botao2, botao3;

    public ItemAdapterSearch(Context context, int layoutResourceId,List data) {
        super(context, layoutResourceId, data);

        this.data=data;
        this.context=context;
        this.layoutResID=layoutResourceId;

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

            //Button botao1, botao2, botao3;

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

        ItemRow itemdata = (ItemRow) data.get(position);
        holder.itemName.setText(itemdata.getItemName());
        holder.icon.setImageDrawable(itemdata.getIcon());

        holder.button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "Button 1 Clicked",Toast.LENGTH_SHORT).show();
            }
        });

        holder.button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(botao1.getText().toString().equals("Requisitar"))
                {
                    Toast.makeText(context, "Requisitar",Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(context, "não e requisitar",Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "Button 3 Clicked",Toast.LENGTH_SHORT).show();
            }
        });

        return row;

    }

    final static class NewsHolder{

        TextView itemName;
        ImageView icon;
        Button button1;
        Button button2;
        Button button3;
    }

}

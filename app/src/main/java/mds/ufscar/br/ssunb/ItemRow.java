package mds.ufscar.br.ssunb;

/**
 * Created by breno on 04/11/15.
 */
import android.graphics.drawable.Drawable;

public class ItemRow {

    String itemName;
    Drawable icon;
    int id;

    public ItemRow(String itemName) {
        super();
        this.itemName = itemName;
        //this.icon = icon;
    }

    public ItemRow(String itemName, int id) {
        super();
        this.itemName = itemName;
        this.id = id;
        //this.icon = icon;
    }

    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public Drawable getIcon() {
        return icon;
    }
    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return itemName;
    }

}
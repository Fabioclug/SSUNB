package mds.ufscar.br.ssunb;

/**
 * Created by breno on 04/11/15.
 */
import android.graphics.drawable.Drawable;

public class ItemRow {

    String itemName;
    Drawable icon;
    int id;

    int idSolicitante;
    int idPortador;

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

    public ItemRow(String itemName, int idSol, int idPor) {
        super();
        this.itemName = itemName;
        this.idSolicitante = idSol;
        this.idPortador = idPor;
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
    public int getId() {return this.id; }
    public int getIdSolicitante() {
        return idSolicitante;
    }

    public int getIdPortador() {
        return idPortador;
    }

    @Override
    public String toString() {
        return itemName;
    }

}
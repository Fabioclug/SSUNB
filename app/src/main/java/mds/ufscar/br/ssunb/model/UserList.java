package mds.ufscar.br.ssunb.model;

/**
 * Created by breno on 29/10/15.
 */
public class UserList {
    private String texto;
    private int iconeRid;

    public UserList()
    { this("", -1); }

    public UserList(String texto, int iconeRid)
    { this.texto = texto;
        this.iconeRid = iconeRid; }
    public int getIconeRid() { return iconeRid; }
    public void setIconeRid(int iconeRid) { this.iconeRid = iconeRid; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
}

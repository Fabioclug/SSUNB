package mds.ufscar.br.ssunb;

/**
 * Created by breno on 15/11/15.
 */
public class UserRow {
    String itemName;

    public UserRow(String itemName) {
        this.itemName = itemName;
    }

    public String getName()
    {
        return this.itemName;
    }

    @Override
    public String toString() {
        return itemName;
    }
}

package mds.ufscar.br.ssunb.model;

/**
 * Created by breno on 24/10/15.
 */
public class User extends Person {

    double latitude;
    double longitude;

    public User(String name, String surname, String Cidade, String email, String senha) {
        super(name, surname, Cidade,email, senha);
    }

    public User(int id, String name, String surname, String Cidade, String email, String senha) {
        super(id, name, surname, Cidade,email, senha);
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

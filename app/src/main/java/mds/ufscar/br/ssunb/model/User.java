package mds.ufscar.br.ssunb.model;

/**
 * Created by breno on 24/10/15.
 */
public class User extends Person {

    String senha;

    public User(String name, String surname, String email, String Cidade, String senha)
    {
        super(name, surname, email, Cidade);
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

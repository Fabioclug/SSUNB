package mds.ufscar.br.ssunb.model;


public class Collaborator extends Person {
    private String cpf;

    public Collaborator(int id, String name, String surname, String email, String city, String senha, String cpf) {
        super(id, name, surname, email, city, senha);
        this.cpf = cpf;
    }

    public Collaborator(String name, String surname, String email, String city, String senha, String cpf) {
        super(name, surname, email, city, senha);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
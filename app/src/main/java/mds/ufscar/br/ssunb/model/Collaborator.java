package mds.ufscar.br.ssunb.model;


public class Collaborator extends Person {
    private String cpf;

    public Collaborator(String name, String surname, String email, String city, String cpf) {
        super(name, surname, email, city);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}

package mds.ufscar.br.ssunb;

import android.content.Context;

import mds.ufscar.br.ssunb.database.CollaboratorDao;
import mds.ufscar.br.ssunb.database.DatabaseHandler;
import mds.ufscar.br.ssunb.database.UserDao;
import mds.ufscar.br.ssunb.model.Collaborator;
import mds.ufscar.br.ssunb.model.User;

/**
 * Created by Fabioclug on 2015-11-12.
 */
public class CollaboratorController {

    private static CollaboratorDao colaboradorDAO;
    private static CollaboratorController instance;
    private Context context;

    public CollaboratorController(Context context)
    {
        this.context = context;
        DatabaseHandler db = new DatabaseHandler(context);
        colaboradorDAO = new CollaboratorDao(db);
    }

    public void insert(Collaborator colaborador) throws Exception {
        colaboradorDAO.save(colaborador);
    }

    public boolean validaLogin(String email, String senha) throws Exception {
        Collaborator colaborador = colaboradorDAO.findByLogin(email, senha);
        //System.out.println("Email: "+user.getEmail()+ " Senha: "+user.getSenha());

        if (colaborador == null || colaborador.getEmail() == null || colaborador.getSenha() == null) {
            return false;
        }
        if (colaborador.getEmail().equals(email) && colaborador.getSenha().equals(senha)) {
            return true;
        }
        return false;

    }

}

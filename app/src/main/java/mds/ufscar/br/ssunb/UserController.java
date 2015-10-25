package mds.ufscar.br.ssunb;

import java.util.List;

import android.content.Context;

import mds.ufscar.br.ssunb.database.DatabaseHandler;
import mds.ufscar.br.ssunb.database.UserDao;
import mds.ufscar.br.ssunb.model.User;

/**
 * @author Cristian Chies 10/12/2012
 *
 */
public class UserController {
    private static UserDao usuarioDAO;
    private static UserController instance;
    private Context context;

//    public static UserController getInstance(Context context) {
//        if (instance == null) {
//            instance = new UserController();
//            usuarioDAO = new UserDao(context);
//        }
//        return instance;
//    }

    public UserController(Context context)
    {
        this.context = context;
        DatabaseHandler db = new DatabaseHandler(context);
        //db.getWritableDatabase().execSQL("delete from book");
        usuarioDAO = new UserDao(db);
    }

    public void insert(User usuario) throws Exception {
//        DatabaseHandler db = new DatabaseHandler(context);
//        //db.getWritableDatabase().execSQL("delete from book");
//        usuarioDAO = new UserDao(db);
        usuarioDAO.save(usuario);
    }

//    public List<User> findAll() throws Exception {
//        return usuarioDAO.findAll();
//    }

    public boolean validaLogin(String email, String senha) throws Exception {
        User user = usuarioDAO.findByLogin(email, senha);
        System.out.println("Email: "+email+ " Senha: "+senha);

        if (user == null || user.getEmail() == null || user.getSenha() == null) {
            return false;
        }
        String informado = email + senha;
        String esperado = user.getEmail() + user.getSenha();
        if (informado.equals(esperado)) {
            return true;
        }
        return false;

    }

}

package application;

import model.User;
import model.infra.DAO;

import java.sql.SQLException;

public class App {

    public static void main(String[] args) throws SQLException {
        DAO dao = new DAO();
        //new user
        User user = new User("Pedro Augusto", "pedro@gmail.com");
        //insert -> Create a new user
        if(dao.insertUser(user) > 0) System.out.println("usuário adicionado com sucesso!");
        else System.out.println("usuário não adicionado, verifique se tem algo errado...");
    }
}

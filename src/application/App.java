package application;

import model.User;
import model.infra.DAO;

import java.sql.SQLException;

public class App {

    public static void main(String[] args) throws SQLException {
        DAO dao = new DAO();
        //new user
        User user = new User("Carlito Brinho", "carlos@gmail.com");
        //insert -> Create
        if(dao.insertUser(user) > 0){
            System.out.println("usuário adicionado com sucesso");
        }
    }
}

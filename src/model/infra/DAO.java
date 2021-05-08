package model.infra;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DAO {

    private Connection connection;

    public DAO(){
        connection = this.getConnection();
    }

    //if connection is close, get a new connection
    public Connection getConnection(){
        try{
            if(connection == null || connection.isClosed()){
                connection = FabricaDeConexao.getConnection();
                return connection;
            } else return connection;
        } catch (SQLException e){throw new RuntimeException(e);}
    }

    //close connection
    public void closeConnection() {
        try{
            if(!connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    //CREATE new User
    public int insertUser(User user) throws SQLException{
        String SQL = "INSERT INTO user (name, email) values (?, ?)";
        PreparedStatement ps = connection.prepareStatement(SQL);
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        int alterLines  = ps.executeUpdate();
        ps.close();
        return alterLines;
    }

    //Update Name of user
    public DAO updateUserName(User user) throws SQLException{
        String SQL = "UPDATE user SET name = ? where id = ?";
        PreparedStatement ps = connection.prepareStatement(SQL);
        ps.setString(1, user.getName());
        ps.setLong(2, user.getId());
        int result  = ps.executeUpdate();
        if(result > 0) System.out.println("Atualização de nome concluída!");
        ps.close();
        return this;

    }

    //Update email of user
    public DAO updateUserMail(User user) throws SQLException{
        String SQL = "UPDATE user SET email = ? where id = ?";
        PreparedStatement ps = connection.prepareStatement(SQL);
        ps.setString(1, user.getEmail());
        ps.setLong(2, user.getId());
        int result  = ps.executeUpdate();
        if(result > 0) System.out.println("Atualização de email concluída!");
        ps.close();
        return this;

    }
    //Delete by id pass object
    public void removeuserById(User user) throws SQLException{
        String SQL = "DELETE  FROM user WHERE id = ? ";
        PreparedStatement ps = connection.prepareStatement(SQL);
        ps.setLong(1, user.getId());
        int result  = ps.executeUpdate();
        if(result > 0) System.out.println("remoção do usuário \""+
                user.getName() + "\", concluída com sucesso!");
        else System.out.println("ID não cadastrado, verifique novamente...");
        ps.close();
    }

    //Delete by id pass long number
    public void removeuserById(Long id) throws SQLException{
        String SQL = "DELETE FROM user WHERE id = ? ";
        PreparedStatement ps = connection.prepareStatement(SQL);
        ps.setLong(1, id);
        int result  = ps.executeUpdate();
        if(result > 0) System.out.println("remoção do usuário concluída com sucesso!");
        else System.out.println("ID não encontrado, verifique novamente...");
        ps.close();
    }

    public User findById(Long id){
        String SQL = "SELECT * FROM user WHERE ID = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setLong(1, id);
            ResultSet result = ps.executeQuery();
             if(result.next()){
                return new User(result.getLong("id"),
                        result.getString("name"),
                        result.getString("email"));
            }else {
                 System.out.println("Não foram obtidos nenhum dado a partir desse usuário");
                 return null;
             }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<User> findAlluser() throws SQLException{
        List<User> users =  new ArrayList<>();
        String SQL = "SELECT * FROM user";
        Statement stmt = connection.createStatement();
        ResultSet result = stmt.executeQuery(SQL);

        while(result.next()){
            Long id = result.getLong(1);
            String name = result.getString("name");
            String email = result.getString("email");
            users.add(new User(id, name, email));

        }
        return  users;
    }

}

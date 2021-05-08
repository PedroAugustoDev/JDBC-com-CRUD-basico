package model.infra;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class FabricaDeConexao {

    private static Properties getProperties() throws IOException {
        Properties props = new Properties();
        String path = "../../resources/connection.properties";
        props.load(FabricaDeConexao.class.getResourceAsStream(path));
        return props;
    }

    public static Connection getConnection(){
        try{
            Properties props = getProperties();
            final String URL = props.getProperty("url");
            final String USER = props.getProperty("user");
            final String PASS = props.getProperty("password");

            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException | IOException e){
                throw new RuntimeException(e);
        }
    }

}

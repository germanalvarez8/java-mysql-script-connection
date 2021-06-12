
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
 
public class ConnectionDemo {
    public static void main(String[] argv) {

        System.out.println("-------- MySQL JDBC Connection Demo ------------");
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } 
        catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found !!");
            return;
        }
        System.out.println("MySQL JDBC Driver Registered!");
        Connection connection = null;
        try {
            connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/universidad", "german", "tecnicia");
            System.out.println("SQL Connection to database established!");
            
            Boolean res = addPerson(connection);

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            System.out.println(e.getMessage());
            return;
        } finally {
            try
            {
                if(connection != null)
                    connection.close();
                System.out.println("Connection closed !!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static Boolean addPerson(Connection connection) throws SQLException
    {
        String query = " insert into persona (dni, nombre, apellido, dirección, teléfono)"
        + " values (?, ?, ?, ?, ?)";

        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setInt (1, 40928479);
        preparedStmt.setString (2, "Bart");
        preparedStmt.setString (3, "Simpson");
        preparedStmt.setString (4, "Some Address 243");
        preparedStmt.setInt (5, 12345780);

        preparedStmt.execute();
        
        connection.close();

        return true;
    }

    private static Boolean getAllPersons(Connection connection) throws SQLException
    {
        // our SQL SELECT query. 
        // if you only need a few columns, specify them by name instead of using "*"
        String query = "SELECT * FROM persona";

        // create the java statement
        Statement st = connection.createStatement();

        // execute the query, and get a java resultset
        ResultSet rs = st.executeQuery(query);
        
        // iterate through the java resultset
        while (rs.next())
        {
            int id = rs.getInt("dni");
            String firstName = rs.getString("nombre");
            String lastName = rs.getString("apellido");
            String address = rs.getString("dirección");
            String phone = rs.getString("teléfono");

            // print the results
            System.out.format("%s, %s, %s, %s, %s\n", id, firstName, lastName, address, phone);
        }
        st.close();

        return true;
    }
}
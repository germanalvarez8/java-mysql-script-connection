
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

public class MysqCrud {

    public static void main(String[] argv) throws IOException
    {
        String url  = "jdbc:mysql://localhost:3306/universidad";
        String user = "german";
        String pass = "tecnicia";

        System.out.println("-------- MySQL JDBC Connection ------------");
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
                .getConnection(url, user, pass);
            System.out.println("Bienvenido al sistema de la universiad\n\n");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Ingrese que tarea desea realizar:\n[1] Igresar una actividad\n[2] Eliminar una materia\n[3] Listar alumnos");
            String clave = br.readLine();

            switch (Integer.parseInt(clave)) {
                case 1:
                    addActivity(connection, br);
                    
                    break;
                case 2:
                    deleteByCode(connection, br);

                    break;
                case 3:
                    System.out.println("Listar alumnos (TODO)");
                    
                    break;
                default:
                    break;
            }
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

    private static void addActivity(Connection connection, BufferedReader br) throws SQLException, IOException
    {
        Hashtable<Integer, String> assignments = new Hashtable<Integer, String>();
        String query = "SELECT cod, nombre FROM materia";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);

        System.out.println("Materia a la que pertenece:");
        while (rs.next())
        {
            int id = rs.getInt("cod");
            String name = rs.getString("nombre");

            assignments.put(id, name);

            System.out.println("[" + id + "] " + name);
        }
        st.close();

        String assignmentId = br.readLine();

        System.out.println("Nombre de la actividad:");
        String assignmentName = br.readLine();

        String insertQuery = " insert into actividad (descripción, cod_materia)"
        + " values (?, ?)";

        PreparedStatement preparedStmt = connection.prepareStatement(insertQuery);
        preparedStmt.setString (1, assignmentName);
        preparedStmt.setInt (2, Integer.parseInt(assignmentId));

        preparedStmt.execute();
        
        connection.close();
    }

    private static void deleteByCode(Connection connection, BufferedReader br) throws SQLException, IOException
    {
        Hashtable<Integer, String> assignments = new Hashtable<Integer, String>();
        String query = "SELECT cod, nombre FROM materia";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);

        System.out.println("Materia a la que pertenece:");
        while (rs.next())
        {
            int id = rs.getInt("cod");
            String name = rs.getString("nombre");

            assignments.put(id, name);

            System.out.println("[" + id + "] " + name);
        }
        st.close();

        String assignmentId = br.readLine();

        System.out.println("Nombre de la actividad:");
        String assignmentName = br.readLine();

        String insertQuery = " insert into actividad (descripción, cod_materia)"
        + " values (?, ?)";

        PreparedStatement preparedStmt = connection.prepareStatement(insertQuery);
        preparedStmt.setString (1, assignmentName);
        preparedStmt.setInt (2, Integer.parseInt(assignmentId));

        preparedStmt.execute();
        
        connection.close();
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
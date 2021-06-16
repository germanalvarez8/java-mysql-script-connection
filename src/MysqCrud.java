
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
        Connection connection = null;

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("MySQL JDBC Driver Registered!");

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
                    getStudentsByAssignment(connection, br);
                    
                    break;
                default:
                    System.out.println("Valor incorrecto, por favor verifique las tareas disponibles");
                    break;
            }
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            System.out.println(e.getMessage());
            return;
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found !!");
            return;
        }finally {
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

        String query = "select cod, nombre from materia ";

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);

        System.out.println("Materias activas:\n");
        while (rs.next())
        {
            int id = rs.getInt("cod");
            String name = rs.getString("nombre");

            assignments.put(id, name);

            System.out.println("[" + id + "] " + name);
        }

        System.out.println("\nCodigo de actividad a eliminar:");
        String assignmentId = br.readLine();

        String deleteQuery = "delete from materia " +
            "where cod=" + assignmentId;

        st.executeUpdate(deleteQuery);
        st.close();

        System.out.println("Materia eliminada correctamente");

        connection.close();
    }

    private static void getStudentsByAssignment(Connection connection, BufferedReader br) throws SQLException, IOException
    {
        Hashtable<Integer, String> assignments = new Hashtable<Integer, String>();

        String query = "select cod, nombre from materia ";

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);

        System.out.println("Materias activas:\n");
        while (rs.next())
        {
            int id = rs.getInt("cod");
            String name = rs.getString("nombre");

            assignments.put(id, name);

            System.out.println("[" + id + "] " + name);
        }

        System.out.println("\nCodigo de materia para mostrar alumnos:");
        String assignmentId = br.readLine();

        String studentsQuery = "SELECT alumno.dni, alumno.nombre FROM materia " +
            "JOIN cursa ON materia.cod = cursa.cod_materia " +
            "JOIN alumno ON cursa.nro_alumno = alumno.nro_alumno " +
            "WHERE materia.cod =" + assignmentId;

        ResultSet studentList = connection.createStatement().executeQuery(studentsQuery);

        System.out.println("Lista de estudiantes para la materia " + assignments.get(Integer.parseInt(assignmentId)));
        while (studentList.next())
        {
            int dni = studentList.getInt("dni");
            String name = studentList.getString("nombre");

            System.out.println(dni + " | " + name);
        }

        st.close();
        connection.close();
    }
}
package JdbcExample;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        }
        catch (Exception ex) {
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            ex.printStackTrace(System.err);
        }
        Connection connection = null;
        Statement selectStatement = null, insertStatement = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        try {
            connection = DriverManager.
                    getConnection("jdbc:mysql://localhost/adapostanimale?user=root&password=root");
            if(connection != null) System.out.println("done");

        }
        catch(SQLException sqlex) {
            System.err.println("An SQL Exception occured. Details are provided below:");
            sqlex.printStackTrace(System.err);
        }
        finally {
            if (rs != null) {
                try {
                    rs.close();
                }
                catch(SQLException e) {
                }
                rs = null;
            }
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                }
                catch(SQLException e) {}
                selectStatement = null;
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                }
                catch(SQLException e) {}
                insertStatement = null;
            }
            if (connection != null) {
                try {
                    connection.close();
                }
                catch(SQLException e) {}
                connection = null;
            }
        }
    }
}

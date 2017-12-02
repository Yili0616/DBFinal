import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionConfig {
    Connection con = null;
    public ResultSet getResult(String sql) {

        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_52",
                    "cs4400_Group_52",
                    "qLPjRnDL");
            Statement st = con.createStatement();
            st.executeQuery(sql);
            rs = st.getResultSet();
            //ResultSetMetaData rsmd = rs.getMetaData();
//            while (rs.next()) {
//                System.out.println("number of rows-" + rs.getString("Username"));
//            }

            if(!con.isClosed()) {
                System.out.println("Successfully connected to " +
                        "MySQL server using TCP/IP...");

            }
        } catch(Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }

        return rs;

    }
    public void update(String sql) {


        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_52",
                    "cs4400_Group_52",
                    "qLPjRnDL");
            Statement st = con.createStatement();
            st.executeUpdate(sql);
            //ResultSetMetaData rsmd = rs.getMetaData();
//            while (rs.next()) {
//                System.out.println("number of rows-" + rs.getString("Username"));
//            }

            if(!con.isClosed()) {
                System.out.println("Successfully connected to " +
                        "MySQL server using TCP/IP...");

            }
        } catch(Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }

    }




    public void close() {
        try {
            con.close();
        } catch (Exception e) {
            System.out.println("Exception: "+ e.getMessage());
        }
    }

}

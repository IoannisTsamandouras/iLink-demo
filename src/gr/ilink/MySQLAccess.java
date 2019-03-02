package gr.ilink;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLAccess {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public void readDataBase() throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB "test" 
            // administrator username: root, password: root
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/test?"
                            + "user=root&password=root");

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery("select * from test.user");
            writeResultSet(resultSet);

            // PreparedStatements can use variables and are more efficient
            preparedStatement = connect
                    .prepareStatement("insert into  test.user values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            // Parameters start with 1
            preparedStatement.setString(1, "Ioannis");
            preparedStatement.setString(2, "Tsamandouras");
            preparedStatement.setString(3, "ioannis.tsamandouras@gmail.com");
            preparedStatement.setInt(4, 41);
            preparedStatement.setInt(5, 110012345);
            preparedStatement.setString(6, "Despotatou Mistra");
            preparedStatement.setInt(7, 7);
            preparedStatement.setString(8, "13121");
            preparedStatement.setString(9, "Athens");
            preparedStatement.executeUpdate();

            preparedStatement = connect
                    .prepareStatement("SELECT FIRSTNAME, LASTNAME, EMAIL, AGE, TIN, STRNAME, "
                    		+ "STRNUM, POSTCODE, CITY from test.user");
            resultSet = preparedStatement.executeQuery();
            writeResultSet(resultSet);

            // Remove again the insert comment
            preparedStatement = connect
            .prepareStatement("delete from test.user where tin= ? ; ");
            preparedStatement.setString(1, "Test");
            preparedStatement.executeUpdate();

            resultSet = statement
            .executeQuery("select * from test.user");
            writeMetaData(resultSet);

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }

    }

    private void writeMetaData(ResultSet resultSet) throws SQLException {
        //  Now get some metadata from the database
        // Result set get the result of the SQL query

        System.out.println("The columns in the table are: ");

        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
            System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
        }
    }

    private void writeResultSet(ResultSet resultSet) throws SQLException {
        
        while (resultSet.next()) {            
            String firstname = resultSet.getString("FIRSTNAME");
            String lastname = resultSet.getString("LASTNAME");
            String email = resultSet.getString("EMAIL");
            Integer age  = resultSet.getInt("AGE");
            Integer tin  = resultSet.getInt("TIN");
            String strname = resultSet.getString("STRNAME");
            Integer strnum = resultSet.getInt("STRNUM");
            String postcode = resultSet.getString("POSTCODE");
            String city = resultSet.getString("CITY");
            System.out.println("FIRSTNAME: " + firstname);
            System.out.println("LASTNAME: " + lastname);
            System.out.println("EMAIL: " + email);
            System.out.println("AGE: " + age);
            System.out.println("TIN: " + tin);
            System.out.println("STRNAME: " + strname);
            System.out.println("STRNUM: " + strnum);
            System.out.println("POSTCODE: " + postcode);
            System.out.println("CITY: " + city);
        }
    }

    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }
    
    // AFM check
    public static boolean isValidAFM(final String afm) {
        int afmLength = 9;
        if (afm == null || afm.length() != afmLength) {
            return false;
        }
        int total = 0;
        int checkDigit = -1;
        for (int i = afmLength - 1; i >= 0; i--) {
            char c = afm.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
            int digit = c - '0';
            if (i == afmLength - 1) {
                checkDigit = digit;
                continue;
            }
            total += digit << (afmLength - i - 1);
        }
        return checkDigit == total % 11 % 10;
    }

}
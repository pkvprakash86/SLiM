/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slim.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import slim.exception.DataAccessException;

/**
 *
 * @author pkvprakash
 */
public class JDBCUtil {

    private static Connection connection = null;

    public static Connection getConnection() throws DataAccessException {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.jdbc.Driver");
                return DriverManager.getConnection("jdbc:mysql://localhost:3306/slim", "root", "root");
            }
        } catch (SQLException ex) {
            Logger.getLogger(JDBCUtil.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataAccessException("In " + JDBCUtil.class.getName() + "\n" + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JDBCUtil.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataAccessException("In " + JDBCUtil.class.getName() + "\n" + ex.getMessage());
        }
        return connection;
    }
}

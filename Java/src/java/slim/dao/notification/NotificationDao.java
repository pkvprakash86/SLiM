/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package slim.dao.notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import slim.exception.DataAccessException;
import slim.util.JDBCUtil;
import slim.vo.notification.Notification;

/**
 *
 * @author pkvprakash
 */
public class NotificationDao {
    public void addNotification(Notification notification) throws DataAccessException{
        try{
            Connection c = JDBCUtil.getConnection();
            String query = "insert into notification(nfrom, nto, ndata, nread) values('" + notification.getNfrom() + "','" + notification.getNto() + "','"+ notification.getData() + "'," + notification.getNread() + ")";
            PreparedStatement st = c.prepareStatement(query);
            st.execute();
        } catch (SQLException ex) {
            Logger.getLogger(NotificationDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataAccessException("In " + NotificationDao.class.getName()+ "\n" + ex.getMessage());
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package slim.dao.subscription;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import slim.exception.DataAccessException;
import slim.util.JDBCUtil;
import slim.vo.subscription.Subscription;
import slim.vo.subscription.SubscriptionId;

/**
 *
 * @author pkvprakash
 */
public class SubscriptionDao {
    public void create(Subscription  subscription) throws DataAccessException{
        try{
            Connection c = JDBCUtil.getConnection();
            String query = "insert into subscription(source, target,sourcews) values('" + subscription.getId().getSource() + "','" + subscription.getId().getTarget()+ "','" + subscription.getSourcews() + "')";
            PreparedStatement st = c.prepareStatement(query);
            st.execute();
        } catch (SQLException ex) {
            Logger.getLogger(SubscriptionDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataAccessException("In " + SubscriptionDao.class.getName()+ "\n" + ex.getMessage());
        }
    }

    public List<Subscription> selectAll(Subscription subscription) throws DataAccessException{
        List<Subscription> subs = new ArrayList<Subscription>();
        try{

            Connection c = JDBCUtil.getConnection();
            StringBuilder query = new StringBuilder("select * from subscription ");
            if(subscription!=null){
                if (subscription.getId().getSource() != null &&  subscription.getId().getTarget() != null){
                    query.append("where ");
                    query.append("source='").append(subscription.getId().getSource()).append("' ");
                    query.append("and ");
                    query.append("target='").append(subscription.getId().getTarget()).append("' ");
                }else if (subscription.getId().getSource() == null &&  subscription.getId().getTarget() != null){
                    query.append("where ");
                    query.append("target='").append(subscription.getId().getTarget()).append("' ");
                }else if (subscription.getId().getSource() != null &&  subscription.getId().getTarget() == null){
                    query.append("where ");
                    query.append("source='").append(subscription.getId().getSource()).append("' ");
                }
            }
            PreparedStatement st = c.prepareStatement(query.toString());
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Subscription s = new Subscription();
                SubscriptionId id = new SubscriptionId();
                id.setSource(rs.getString("source"));
                id.setTarget(rs.getString("target"));
                s.setId(id);
                s.setSourcews(rs.getString("sourcews"));
                subs.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubscriptionDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataAccessException("In " + SubscriptionDao.class.getName()+ "\n" + ex.getMessage());
        }
        return subs;
    }
}

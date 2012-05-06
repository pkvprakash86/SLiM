/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slim.dao.jdbc;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import slim.dao.GenericDao;

import java.lang.reflect.*;

/**
 *
 * @author pkvprakash
 */
public class JDBCDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

    private String getInsertQuery(T newInstance) throws IllegalArgumentException, IllegalAccessException {
        StringBuffer query = new StringBuffer();

        if (newInstance == null) {
            return null;
        }
        Field[] fields = newInstance.getClass().getDeclaredFields();
        StringBuilder values = new StringBuilder();
        StringBuilder names = new StringBuilder();

        for (Field f : fields) {
            
            if(f.get(newInstance) !=  null){
                values.append(f.get(f)).append(",");
                names.append(f.getName()).append(",");
            }
        }
        values.deleteCharAt(values.length()-1);
        names.deleteCharAt(names.length()-1);

        return query.toString();
    }

    @Override
    public PK create(T newInstance){
        try {
            Connection c = getConnection();
            String query = getInsertQuery(newInstance);
            PreparedStatement st = c.prepareStatement(query);
            st.execute();

        } catch (SQLException ex) {
            Logger.getLogger(JDBCDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(JDBCDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(JDBCDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(JDBCDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    @Override
    public T read(PK id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(T transientObject) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(T persistentObject) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private DataSource getSlim() throws NamingException {
        Context c = new InitialContext();
        return (DataSource) c.lookup("java:comp/env/slim");
    }

    private Connection getConnection() throws SQLException, NamingException {
        return getSlim().getConnection("root", "root");

    }
}

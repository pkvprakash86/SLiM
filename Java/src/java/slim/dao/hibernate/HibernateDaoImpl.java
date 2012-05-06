/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slim.dao.hibernate;

import java.io.Serializable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import slim.dao.GenericDao;
import slim.util.HibernateUtil;

/**
 *
 * @author pkvprakash
 */
public class HibernateDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

    private SessionFactory sf;
    private Class<T> type;

    public HibernateDaoImpl(Class<T> type) {
        sf = HibernateUtil.getSessionFactory();
        this.type = type;
    }

    public PK create(T newInstance) {
        Session s = sf.getCurrentSession();
        PK pk = null;
        try {
            Transaction t = s.beginTransaction();
            t.begin();
            pk = (PK) s.save(newInstance);
            t.commit();
        } catch (Exception e) {
        } finally {
            s.close();
        }
        return pk;
    }

    public T read(PK id) {
        Session s = sf.getCurrentSession();
        T pk = null;
        try {
            Transaction t = s.beginTransaction();
            t.begin();
            pk = (T) s.get(type, id);
            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            s.close();
        }
        return pk;
    }

    public void update(T transientObject) {
        Session s = sf.getCurrentSession();
        try {
            Transaction t = s.beginTransaction();
            t.begin();
            s.update(transientObject);
            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            s.close();
        }

    }

    public void delete(T persistentObject) {
        Session s = sf.getCurrentSession();
        try {
            Transaction t = s.beginTransaction();
            t.begin();
            s.delete(persistentObject);
            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            s.close();
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package slim.dao;

import java.io.Serializable;

/**
 *
 * @author pkvprakash
 */
public interface GenericDao<T, PK extends Serializable> {
    PK create (T newInstance);
    T read(PK id);
    void update(T transientObject);
    void delete(T persistentObject);
}

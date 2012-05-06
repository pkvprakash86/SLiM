/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package slim.exception;

/**
 *
 * @author pkvprakash
 */
public class DataAccessException extends Exception{

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException() {
    }

}

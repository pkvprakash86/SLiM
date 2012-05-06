/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package slim.util;

import java.util.ResourceBundle;

/**
 *
 * @author pkvprakash
 */
public class ResourceBundleUtils {
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("SLiM.properties");
    
    public static String get(String key){
        if(key == null ) return null;
        String value = null;
        value = resourceBundle.getString(key);
        return value;
    }
}

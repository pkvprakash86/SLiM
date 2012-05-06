/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package slim.vo.notification;

import java.util.Date;

/**
 *
 * @author pkvprakash
 */
public class Notification {
    private String nfrom;
    private String nto;
    private String data;
    private int nread;
    private Date timestamp;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNfrom() {
        return nfrom;
    }

    public void setNfrom(String nfrom) {
        this.nfrom = nfrom;
    }

    public int getNread() {
        return nread;
    }

    public void setNread(int nread) {
        this.nread = nread;
    }

    public String getNto() {
        return nto;
    }

    public void setNto(String nto) {
        this.nto = nto;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }


}

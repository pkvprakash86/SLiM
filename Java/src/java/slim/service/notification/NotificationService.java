/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package slim.service.notification;

import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import slim.dao.notification.NotificationDao;
import slim.exception.DataAccessException;
import slim.vo.notification.Notification;

/**
 *
 * @author pkvprakash
 */
@Path("/notify")
public class NotificationService {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public void notify(
            @FormParam("source") String source,
            @FormParam("target") String target,
            @FormParam("data") String data,
            @Context HttpServletResponse response) throws DataAccessException{
            Notification notification = new Notification();
            notification.setNfrom(target);
            notification.setTimestamp(new Date());
            notification.setNto(source);
            notification.setData(data);
            notification.setNread(0);
            new NotificationDao().addNotification(notification);
    }
}

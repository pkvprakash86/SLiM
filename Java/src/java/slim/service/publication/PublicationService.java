/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package slim.service.publication;

import java.util.List;
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
import slim.dao.publication.PublicationDao;
import slim.exception.DataAccessException;
import slim.vo.subscription.Subscription;
import slim.vo.subscription.SubscriptionId;

/**
 *
 * @author pkvprakash
 */
@Path("/publish")
public class PublicationService {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public void notify(
            @FormParam("source") String source,
            @FormParam("data") String data,
            @Context HttpServletResponse response) throws DataAccessException{
        Subscription s = new Subscription();
        s.setId((new SubscriptionId()));
        s.getId().setTarget(source);
        List<Subscription> subs = new PublicationDao().selectAll(s);
        new ChangeNotifier().notify(subs, data);
    }
}

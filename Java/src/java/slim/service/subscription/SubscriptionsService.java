/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slim.service.subscription;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import slim.dao.subscription.SubscriptionDao;
import slim.exception.DataAccessException;
import slim.vo.subscription.Subscription;
import slim.vo.subscription.SubscriptionId;

/**
 *
 * @author pkvprakash
 */
@Path("/subscribe")
public class SubscriptionsService {

    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public void newSubscription(
            @FormParam("source") String source,
            @FormParam("target") String target,
            @FormParam("ws") String ws,
            @Context HttpServletResponse servletResponse) throws DataAccessException {
       // try {
            SubscriptionId id = new SubscriptionId(source, target);
            Subscription subscription = new Subscription(id);
            subscription.setSourcews(ws);
            new SubscriptionDao().create(subscription);
            //new HibernateDaoImpl<Subscription, SubscriptionId>(Subscription.class).create(subscription);
            //servletResponse.sendRedirect("/SLiM/sub");
        /*} catch (IOException ex) {
            Logger.getLogger(SubscriptionsService.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataAccessException("In " + SubscriptionsService.class.getName()+ "\n" + ex.getMessage());
        }*/
    }

    @GET
    @Produces(MediaType.TEXT_XML)
    public List<Subscription> getSubscriptionsBrowser() throws DataAccessException {
        System.out.println("Get All Subs");
        return new SubscriptionDao().selectAll(null);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Subscription> getSubscriptions() throws DataAccessException {
        return getSubscriptionsBrowser();
    }



    
    @GET
    @Path("t={target}")
    @Produces(MediaType.TEXT_XML)
    public List<Subscription> getSubscriptionByTargetBrowser(@PathParam("target") String target) throws DataAccessException {
        Subscription s = new Subscription();
        s.setId((new SubscriptionId()));
        s.getId().setTarget(target);
        return new SubscriptionDao().selectAll(s);
    }

    @GET
    @Path("t={target}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Subscription> getSubscriptionByTarget(@PathParam("target") String target) throws DataAccessException {
        Subscription s = new Subscription();
        s.setId((new SubscriptionId()));
        s.getId().setTarget(target);
        return new SubscriptionDao().selectAll(s);
    }

    
    @GET
    @Produces(MediaType.TEXT_XML)
    @Path("s={source}")
    public List<Subscription> getSubscriptionBySourceBrowser(@PathParam("source") String source) throws DataAccessException {
        Subscription s = new Subscription();
        s.setId((new SubscriptionId()));
        s.getId().setSource(source);
        return new SubscriptionDao().selectAll(s);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("s={source}")
    public List<Subscription> getSubscriptionBySource(@PathParam("source") String source) throws DataAccessException {
        Subscription s = new Subscription();
        s.setId((new SubscriptionId()));
        s.getId().setSource(source);
        return new SubscriptionDao().selectAll(s);
    }


    @GET
    @Path("s={source},t={target}")
    @Produces(MediaType.TEXT_XML)
    public List<Subscription> getSubscriptionBySourceAndTargetBrowser(@PathParam("source") String source, @PathParam("target") String target) throws DataAccessException {
        Subscription s = new Subscription();
        SubscriptionId id = new SubscriptionId(source, target);
        s.setId(id);
        return new SubscriptionDao().selectAll(s);
    }

    @GET
    @Path("s={source},t={target}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Subscription> getSubscriptionBySourceAndTarget(@PathParam("source") String source, @PathParam("target") String target) throws DataAccessException {
        Subscription s = new Subscription();
        SubscriptionId id = new SubscriptionId(source, target);
        s.setId(id);
        return new SubscriptionDao().selectAll(s);
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package slim.service.publication;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
//import slim.util.ResourceBundleUtils;
import slim.vo.subscription.Subscription;

/**
 *
 * @author pkvprakash
 */
public class ChangeNotifier {

    public ChangeNotifier() {
    }

    public void notify(List<Subscription> subs, String data){

        // TODO: Make this method a Map-Reduce job

        if(subs == null || subs.isEmpty()) return;
        ClientConfig config = new DefaultClientConfig();
	Client client = Client.create(config);
	//WebResource webResource = client.resource(ResourceBundleUtils.get("base-uri"));
        WebResource webResource = null;
        for(Subscription sub : subs){
            MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
            formData.add("target", sub.getId().getTarget());
            formData.add("source", sub.getId().getSource());
            formData.add("data", data);
            webResource  =  client.resource(UriBuilder.fromPath(sub.getSourcews()).build());
            ClientResponse response  = webResource.accept(MediaType.TEXT_HTML).post(ClientResponse.class, formData);
            if(response.getStatus() != 200){
                // Log Error here.

                // TODO: process error here A map reduce job to read the log and resend the message.
                
            }
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package slim.test;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;

import slim.vo.subscription.Subscription;
import slim.vo.subscription.SubscriptionId;

/**
 *
 * @author pkvprakash
 */
public class TestSlimSub {
    	public static void main(String[] args) {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());
		// Create one todo
		/*Todo todo = new Todo("3", "Blabla" );
		ClientResponse response = service.path("rest").path("todos").path(todo.getId()).accept(MediaType.APPLICATION_XML).put(ClientResponse.class, todo);
		// Return code should be 201 == created resource
		System.out.println(response.getStatus());
		// Get the Todos
		System.out.println(service.path("rest").path("todos").accept(
				MediaType.TEXT_XML).get(String.class));
		// Get XML for application
		System.out.println(service.path("rest").path("todos").accept(
				MediaType.APPLICATION_JSON).get(String.class));
		// Get JSON for application
		System.out.println(service.path("rest").path("todos").accept(
				MediaType.APPLICATION_XML).get(String.class));

		// Get the  Todo with id 1
		System.out.println(service.path("rest").path("todos/1").accept(
				MediaType.APPLICATION_XML).get(String.class));
		// get Todo with id 1
		service.path("rest").path("todos/3").delete();
		// Get the all todos, id 1 should be deleted
		System.out.println(service.path("rest").path("todos").accept(
				MediaType.APPLICATION_XML).get(String.class));

                                */
		// Create a Todo

                /*System.out.println( service.path("service").path("subscription").path("s=1").accept(MediaType.APPLICATION_XML)
								   .get(String.class));

                System.out.println( service.path("service").path("subscription").path("t=a").accept(MediaType.APPLICATION_XML)
								   .get(String.class));
                System.out.println( service.path("service").path("subscription").path("s=1&t=a").accept(MediaType.APPLICATION_XML)
								   .get(String.class));*/

                System.out.println( service.path("service").path("subscription").accept(MediaType.APPLICATION_JSON)
								   .get(String.class));

                System.out.println( service.path("service").path("subscription").path("t=a").accept(MediaType.APPLICATION_JSON)
								   .get(String.class));
                System.out.println( service.path("service").path("subscription").path("s=1&t=a").accept(MediaType.APPLICATION_JSON)
								   .get(String.class));                                           

		

	}
	private static URI getBaseURI() {
		return UriBuilder.fromUri(
				"http://localhost:8084/SLiM").build();
	}
}




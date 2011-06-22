package fr.free.lemerdy.eric;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/sample")
public class SampleResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String get() {
		return "Hello World !";
	}
}
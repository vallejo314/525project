package org.apache.hadoop.crypto.key.kms.server;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.hadoop.crypto.key.KeyProvider;
import org.apache.hadoop.crypto.key.KeyProvider.KeyVersion;
import org.apache.hadoop.crypto.key.kms.KMSRESTConstants;

@Path(KMSRESTConstants.DKMS_URL)
public class DKMS {

	private Map<String, KeyProvider.KeyVersion> keys;
	
	public static final String INSERT = "insert";
	public static final String REMOVE = "remove";
	public static final String UPDATE = "update";
	public static final String GET = "get";
	
	public DKMS() {
		keys = new HashMap<String, KeyProvider.KeyVersion>();
	}
	
  @POST
  @Path("/" + INSERT)
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.TEXT_PLAIN)
  @SuppressWarnings("unchecked")
	public Response insertKey(Map jsonKey) {
		keys.put(
				(String) jsonKey.get(KMSRESTConstants.NAME_FIELD),
				(KeyProvider.KeyVersion) jsonKey.get(KMSRESTConstants.MATERIAL_FIELD));
		
		return Response.ok().build();
	}
  
  @POST
  @Path("/" + REMOVE)
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.TEXT_PLAIN)
  @SuppressWarnings("unchecked")
	public Response removeKey(Map jsonKey) {
		keys.remove((String) jsonKey.get(KMSRESTConstants.NAME_FIELD));
		return Response.ok().build();
	}
  
  @POST
  @Path("/" + UPDATE)
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.TEXT_PLAIN)
  @SuppressWarnings("unchecked")
	public Response updateKey(Map jsonKey) {
		keys.replace(
				(String) jsonKey.get(KMSRESTConstants.NAME_FIELD),
				(KeyProvider.KeyVersion) jsonKey.get(KMSRESTConstants.MATERIAL_FIELD));
		
		return Response.ok().build();
	}
  
  @POST
  @Path("/" + GET)
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @SuppressWarnings("unchecked")
	public Response getKey(Map jsonKey) {
		KeyVersion key = keys.get((String) jsonKey.get(KMSRESTConstants.NAME_FIELD));
		return Response.ok(key.toString()).build();
	}

}

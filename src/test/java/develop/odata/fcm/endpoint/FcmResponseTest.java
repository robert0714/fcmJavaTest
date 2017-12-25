package develop.odata.fcm.endpoint;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.Charsets;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import develop.odata.fcm.endpoint.FcmResponse;

public class FcmResponseTest {

private   ObjectMapper mapper;
	

	@Before
	public void setUp() throws Exception {
		this.mapper = new ObjectMapper();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetIds() throws Exception {
		String resource = "/fcm-response.json";
		String json = new String(Files.readAllBytes(Paths.get(getClass().getResource(resource).toURI())),Charsets.UTF_8);
		System.out.println(json);
		;

		FcmResponse object = this.mapper.readValue(json, FcmResponse.class);
		System.out.println(object);;
		
		Assert.assertNotNull(object.getCanonicalIds());
		String json2 = this.mapper.writeValueAsString(object);
		System.out.println(json2);;

	}

}

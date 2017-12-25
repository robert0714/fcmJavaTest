package develop.odata.fcm.service;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.Charsets;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import develop.odata.fcm.endpoint.FcmRequest;
import develop.odata.fcm.endpoint.FcmResponse;

public class FCMServiceTest2 {
	private String resourceUrl = "https://fcm.googleapis.com/fcm/send";
	private ObjectMapper mapper;
	private RestTemplate restTemplate;

	private CloseableHttpClient httpClient;
	
	@Value("${develop.odata.service.fcm.key:AAAASx2OLM0:APA91bFqwpQGp-Gq0iDcE9TYZvFPIXfTWKBov397-Bgjj7b8AEip8IrLM4GJj-mhenR2-zOF3TtvkKJXswwVDaSRA1vvTVjXXJW7nMoHt6MA3zRiMDWfjqgpUCYr6Fkrjxy6suIRgKhh}")
	private String fcmKey;

	@Before
	public void setUp() throws Exception {
		this.mapper = new ObjectMapper();
		CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier())
				.build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);
		//
		this.restTemplate = new RestTemplate(requestFactory); 
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSendMsg() throws Exception {		 
		if(fcmKey==null) {
			fcmKey="AAAASx2OLM0:APA91bFqwpQGp-Gq0iDcE9TYZvFPIXfTWKBov397-Bgjj7b8AEip8IrLM4GJj-mhenR2-zOF3TtvkKJXswwVDaSRA1vvTVjXXJW7nMoHt6MA3zRiMDWfjqgpUCYr6Fkrjxy6suIRgKhh";
		}
		String key="key="+fcmKey;  		 
		String resource = "/fcm-request.json";
		String jsonInput = new String(Files.readAllBytes(Paths.get(getClass().getResource(resource).toURI())),Charsets.UTF_8);
		FcmRequest object = this.mapper.readValue(jsonInput, FcmRequest.class);		
		
		MultiValueMap<String, String> headerss = new LinkedMultiValueMap<String, String>();
		headerss.add(HttpHeaders.AUTHORIZATION,key);
		headerss.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		HttpEntity<FcmRequest> request1 = new HttpEntity<FcmRequest>(object, headerss);

		FcmResponse answer = restTemplate.postForObject(resourceUrl, request1, FcmResponse.class);
		
		System.out.println(answer);;
	}

}

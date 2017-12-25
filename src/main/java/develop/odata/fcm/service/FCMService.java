package develop.odata.fcm.service;
 
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.bouncycastle.asn1.cms.OriginatorIdentifierOrKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value; 
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType; 
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import develop.odata.fcm.domain.FcmRecord;
import develop.odata.fcm.endpoint.FcmRequest;
import develop.odata.fcm.endpoint.FcmResponse;
import develop.odata.fcm.repository.IFcmRecordRepository;
@Component
public class FCMService {
  

	private RestTemplate restTemplate;

	private String resourceUrl = "https://fcm.googleapis.com/fcm/send";
	
	@Value("${develop.odata.service.fcm.key:AAAASx2OLM0:APA91bFqwpQGp-Gq0iDcE9TYZvFPIXfTWKBov397-Bgjj7b8AEip8IrLM4GJj-mhenR2-zOF3TtvkKJXswwVDaSRA1vvTVjXXJW7nMoHt6MA3zRiMDWfjqgpUCYr6Fkrjxy6suIRgKhh}")
	private String fcmKey;
	
	@Autowired
	private IFcmRecordRepository repository;

	@PostConstruct
	public void postConstruct() { 
		CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier())
				.build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);
		//
		this.restTemplate = new RestTemplate(requestFactory); 
	}

	public FcmResponse sendMsg(FcmRequest object) {
		 
		String key="key="+fcmKey;  		 
		 
		MultiValueMap<String, String> headerss = new LinkedMultiValueMap<String, String>();
		
		headerss.add(HttpHeaders.AUTHORIZATION,key);
		headerss.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
  

		HttpEntity<FcmRequest> request1 = new HttpEntity<FcmRequest>(object, headerss);

		FcmResponse answer = restTemplate.postForObject(resourceUrl, request1, FcmResponse.class);
		
		return answer;
	}
	public void save(FcmRequest object) {
		FcmRecord unit = new FcmRecord();
		unit.setSendData(object);
		repository.save(unit);
	}
	 
	
	/**
	 * Daily job.
	 * https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/scheduling/support/CronSequenceGenerator.html
	 */
	@Scheduled(cron="* */5 * * * *")
//	@Scheduled(cron="*/10 * * * * *")
	public void dailyJob() { 
		List<FcmRecord> list = repository.findBydoneIsFalse();
		Iterator<FcmRecord> iterator = list.iterator();
		while (iterator.hasNext()) {
			FcmRecord unit = iterator.next();
			FcmRequest origin = unit.getSendData();
			int greate = DateUtils.truncatedCompareTo( new Date(),origin.getSendDate(), Calendar.MINUTE);
			switch (greate) {
			case 1:
				FcmResponse result = sendMsg(origin);
				unit.setDone(true);
				unit.setResult(result);
				repository.save(unit);
				break;
			case -1:
				break;
			}
			
		}
	}

 
	 
}

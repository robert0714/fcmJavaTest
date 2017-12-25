package develop.odata.fcm.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import develop.odata.fcm.domain.FcmRecord;
import develop.odata.fcm.endpoint.FcmRequest;
import develop.odata.fcm.endpoint.FcmResponse;
import develop.odata.fcm.endpoint.Notification;
import develop.odata.fcm.repository.IFcmRecordRepository;
import develop.odata.fcm.service.FCMService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FCMServiceTest {

	@Autowired
	private FCMService service;

	@Before
	public void setUp() throws Exception {
		IFcmRecordRepository repository =mock(IFcmRecordRepository.class);
		 
		List<FcmRecord> value =new ArrayList<FcmRecord>();
		FcmRequest request =  pseudo() ;;
		FcmRecord sample =new FcmRecord();
		sample.setSendData(request);
		value.add(sample);
		when(repository.findBydoneIsFalse()).thenReturn(value);
		
		ReflectionTestUtils.setField(service, "repository", repository);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Ignore
	public void testSendMsg() throws Exception {
		FcmRequest request =  pseudo() ;
		FcmResponse answer = service.sendMsg(request);
		System.out.println(answer);
	}

	@Test
	@Ignore
	public void testDailyJob() throws Exception {
		service.dailyJob();
	}
	@Test
	public void testDailyJobSendDateIsNull () throws Exception {
		IFcmRecordRepository repository =mock(IFcmRecordRepository.class);
		 
		List<FcmRecord> value =new ArrayList<FcmRecord>();
		FcmRequest request =  pseudo() ;
		request.setSendDate(null);
		;
		FcmRecord sample =new FcmRecord();
		sample.setSendData(request);
		value.add(sample);
		when(repository.findBydoneIsFalse()).thenReturn(value);
		
		ReflectionTestUtils.setField(service, "repository", repository);
		service.dailyJob();
	}

	protected FcmRequest pseudo() {
		FcmRequest request = new FcmRequest();
		request.setIds(
				"eYK5l9INky4:APA91bG_lUfjNcbdsUw-ajCNk0Fq4-pTU8Oe-ICKSegKsJgdu2RXK9NMBuV8THIA9CBsZE-Mr7_-Ga0NItejx74BjcfjFBSt6fhVGj9iJ8XbSvIpHOtocYY6gX1VctkbTi2EnlhKj2S5",
				"cF-y8mc5B2w:APA91bHDVGhvPwQoXA6Rwp82FBCUv2wHPszh0B3AB_Hv53cdOg-p2SobyJBvXePM-xXcxT5F547p4WwZ_XnWmN5NJMGF3Ak07NThcrkUJcH1TmYvv6crI3Ymj4yuhQV9wEhz9-AjVTZS");
		Notification notification = new Notification();
		
		notification.setBody("Im Robert");
		notification.setTitle("Maass Test");
		
		request.setNotification(notification);
		Date date = DateUtils.addDays(new Date(), -1);
		request.setSendDate(date);
		
		request.setAccountId("Lehninger");
		return request;
	}
}

package develop.odata.fcm.endpoint;
 
import java.util.concurrent.TimeUnit;
 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.CacheControl; 
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RestController;
   
import develop.odata.fcm.service.FCMService; 

@RestController
@RequestMapping(value = "/fcm")
public class FcmEndpoint {

	@Autowired
	private FCMService service; 

	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(FcmEndpoint.class);
 

	 
//	 
	@RequestMapping(produces = { MediaType.APPLICATION_JSON_VALUE } )
	public ResponseEntity<FcmRequest> listdata(FcmRequest request) {
		
		service.save(request);
		 
		/***
		 * client cache
		 */
		final	CacheControl cc = CacheControl.maxAge(15l,TimeUnit.MINUTES);
		
		final ResponseEntity<FcmRequest> result = ResponseEntity.ok().cacheControl(cc).body(request);
		return result;
	} 
}

package develop.odata.fcm.domain;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import develop.odata.fcm.endpoint.FcmRequest;
import develop.odata.fcm.endpoint.FcmResponse;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
@Relation(collectionRelation = "fcmRecord")
@Document
public class FcmRecord {

	@Id
	private FcmRequest sendData;

	private boolean done;

	private FcmResponse result;

	public FcmRequest getSendData() {
		return sendData;
	}

	public void setSendData(FcmRequest sendData) {
		this.sendData = sendData;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public FcmResponse getResult() {
		return result;
	}

	public void setResult(FcmResponse result) {
		this.result = result;
	}
	
	

}

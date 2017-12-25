package develop.odata.fcm.endpoint;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FcmRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5204258618230126147L;

	@JsonProperty("account_id")
	private String accountId;

	@JsonProperty("registration_ids")
	private String[] ids;
	
	@JsonProperty("notification")
	private Notification notification ;
	

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	@JsonProperty("send_date")
	private Date sendDate;

	public String[] getIds() {
		return ids;
	}

	public void setIds(String... ids) {
		this.ids = ids;
	}

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	} 
	
	
	
}

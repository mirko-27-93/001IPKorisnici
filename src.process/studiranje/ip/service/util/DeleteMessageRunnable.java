package studiranje.ip.service.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import studiranje.ip.bean.InformationBean;
import studiranje.ip.service.lang.ShortAjaxIdContstants;
import studiranje.ip.service.lang.ShortAjaxNamingException;

public class DeleteMessageRunnable extends ShortAjaxStringTransmissionRunnable{
	private InformationBean userInfoBean; 
	
	public DeleteMessageRunnable(InformationBean userInfoBean) {
		this.userInfoBean = userInfoBean; 
	}
	
	@Override
	public void run() {
		userInfoBean.reset();
	}

	@Override
	public void loadFromJSON(String dataJSON) throws ShortAjaxNamingException{
		JsonParser parser = new JsonParser();
		JsonElement root = parser.parse(dataJSON); 
		JsonObject object = root.getAsJsonObject();
		if(object.get("id")==null)  throw new ShortAjaxNamingException(); 
		if(!object.get("id").getAsString().contentEquals(getId())) throw new ShortAjaxNamingException();
	}

	@Override
	public String storeToJSON() {
		return "{\"id\":\""+getId()+"\"}"; 
	}

	@Override
	public String getId() {
		return ShortAjaxIdContstants.DELETE_MESSAGE;
	}
}

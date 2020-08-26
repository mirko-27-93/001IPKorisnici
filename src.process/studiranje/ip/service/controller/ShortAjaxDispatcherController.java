package studiranje.ip.service.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import studiranje.ip.service.lang.ShortAjaxNamingException;
import studiranje.ip.service.util.ShortAjaxStringTransmissionRunnable;

/**
 * Контрола и пула за активности које су за АЈАКС. 
 * @author mirko
 * @version 1.0
 */
public class ShortAjaxDispatcherController {
	private HashMap<String, ShortAjaxStringTransmissionRunnable> map = new HashMap<>();
	
	public synchronized ShortAjaxStringTransmissionRunnable get(String id) {
		return map.get(id); 
	}
	public synchronized List<String> list(){
		ArrayList<String> list = new ArrayList<>(map.keySet());
		Collections.sort(list);
		return list; 
	}
	public synchronized void set(String id, ShortAjaxStringTransmissionRunnable manervar) {
		map.put(id, manervar);
	}
	public synchronized void remove(String id) {
		map.remove(id); 
	}
	public synchronized String execute(String id, String jsonArgument) throws ShortAjaxNamingException{
		ShortAjaxStringTransmissionRunnable a = map.get(id);
		if(a==null) throw new ShortAjaxNamingException("Zero.");
		a.loadFromJSON(jsonArgument); a.run();
		return a.storeToJSON();
	}
}

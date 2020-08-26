package studiranje.ip.service.util;

import studiranje.ip.service.lang.ShortAjaxNamingException;

/**
 * Класа којом је могуче извршавати краткорочне, стринг параметризоване и напомењене
 * параметре и процесе. 
 * @author mirko
 * @version 1.0
 */
public abstract class ShortAjaxStringTransmissionRunnable implements Runnable{
	
	@Override 
	public abstract void run();
	public abstract void loadFromJSON(String dataJSON) throws ShortAjaxNamingException;
	public abstract String storeToJSON() throws ShortAjaxNamingException; 
	public abstract String getId() throws ShortAjaxNamingException; 
}

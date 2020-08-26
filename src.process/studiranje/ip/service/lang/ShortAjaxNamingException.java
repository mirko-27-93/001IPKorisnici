package studiranje.ip.service.lang;

/**
 * Изузетак услед мимолижањеа идентификације активности и параметера који су 
 * прослеђени АЈАКСОМ. 
 * @author mirko
 * @version 1.0
 */
public class ShortAjaxNamingException extends RuntimeException{
	private static final long serialVersionUID = 4630892922101263113L;
	private String jsonRequest = ""; 
	
	public ShortAjaxNamingException() {
		super();
	}

	public ShortAjaxNamingException(String message, Throwable cause) {
		super(message, cause);
	}

	public ShortAjaxNamingException(String message) {
		super(message);
	}

	public ShortAjaxNamingException(Throwable cause) {
		super(cause);
	}

	public String getJsonRequest() {
		return jsonRequest;
	}

	public void setJsonRequest(String jsonRequest) {
		this.jsonRequest = jsonRequest;
	}
}

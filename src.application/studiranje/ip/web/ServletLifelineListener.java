package studiranje.ip.web;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import studiranje.ip.controller.UserGeneralController;

/**
 * Ослушкивач који одјављује корисника, уколико је остао пријављен,
 * а у случају ишчезавања сесије, зато што је напустио неодјављен нпр. . 
 * @author mirko
 * @version 1.0
 */
@WebListener
public class ServletLifelineListener implements HttpSessionListener {
	public static final String ATTR_SESSION_LOGIN = "status.logged"; 
	private UserGeneralController controller = UserGeneralController.getInstance();
	
    public ServletLifelineListener() {}

    @Override
    public void sessionDestroyed(HttpSessionEvent se)  { 
       String username = (String) se.getSession().getAttribute(ATTR_SESSION_LOGIN);
       if(username!=null) controller.getSessions().logout(username, se.getSession());
    }
	
}

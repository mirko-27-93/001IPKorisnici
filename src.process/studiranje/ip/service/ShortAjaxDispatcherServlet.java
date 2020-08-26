package studiranje.ip.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import studiranje.ip.bean.InformationBean;
import studiranje.ip.lang.UserSessionConstantes;
import studiranje.ip.service.controller.ShortAjaxDispatcherController;
import studiranje.ip.service.lang.ShortAjaxIdContstants;
import studiranje.ip.service.lang.ShortAjaxNamingException;
import studiranje.ip.service.util.DeleteMessageRunnable;

/**
 * Намијењено за кратке АЈАКС сервисне и извршне активности. 
 * Параметри стандардним прослеђивањем преко веба. Одговори ЈСОН. 
 * @author mirko
 * @version 1.0
 */
@WebServlet("/ShortAjaxDispatcherServlet")
public class ShortAjaxDispatcherServlet extends HttpServlet{
	private static final long serialVersionUID = 1835138760879067104L;

	/**
	 * Generate or get. Генерише и поставља или преузима и врћа зрно за информације у ВА (веб апликацији).
	 * @param req захтијев. 
	 * @param resp одговор. 
	 * @return зрно за информације. 
	 */
	private InformationBean gengetUserInformationBean(HttpServletRequest req, HttpServletResponse resp) {
		InformationBean infoBean = (InformationBean) req.getSession().getAttribute(UserSessionConstantes.USER_INFO_BEAN); 
		if(infoBean == null) {
			infoBean = new InformationBean(); 
			req.getSession().setAttribute(UserSessionConstantes.USER_INFO_BEAN, infoBean);
		}
		return infoBean; 
	}
	
	private void registerActivity(ShortAjaxDispatcherController ajaxServerCtrl, HttpServletRequest req, HttpServletResponse resp) {
		InformationBean bean = gengetUserInformationBean(req, resp);
		DeleteMessageRunnable deleteMessageActivity = new DeleteMessageRunnable(bean);
		ajaxServerCtrl.set(deleteMessageActivity.getId(), deleteMessageActivity);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		ShortAjaxDispatcherController controller = new ShortAjaxDispatcherController();
		registerActivity(controller, req, resp);
		String activity = req.getParameter("activity");
		if(activity==null) throw new ShortAjaxNamingException("Activity.");
		switch(activity) {
			case ShortAjaxIdContstants.DELETE_MESSAGE:
				JsonObject object = new JsonObject();
				object.addProperty("id", activity);
				String result = controller.execute(ShortAjaxIdContstants.DELETE_MESSAGE, object.toString()); 
				resp.getWriter().println(result); 
				break;
			default: 
				throw new ShortAjaxNamingException("Activity, unknown.");
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}

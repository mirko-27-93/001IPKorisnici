package studiranje.ip.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import studiranje.ip.bean.GeneralUserBean;
import studiranje.ip.bean.InformationBean;
import studiranje.ip.controller.UserGeneralController;
import studiranje.ip.lang.UserMessagesSourcesConstants;
import studiranje.ip.lang.UserSessionConstantes;
import studiranje.ip.model.UserRequisit;

/**
 * Постављање кратког описа за сервлет (у виду датотеке обичног текстуалног фајла)
 * (базом података - до 500 карактера)
 * @author mirko
 * @version 1.0
 */
@WebServlet("/UserDescriptionSetServlet")
public class UserDescriptionSetServlet extends HttpServlet{
	private static final long serialVersionUID = -71520751615872120L;
	
	private transient UserGeneralController controller = UserGeneralController.getInstance(); 
	public static final String ATTR_SESSION_LOGIN = "status.logged"; 
	
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
	

	private GeneralUserBean gengetUserBasicDataUserBean(String username, HttpServletRequest req, HttpServletResponse resp) {
		GeneralUserBean basicDataBean = (GeneralUserBean) req.getSession().getAttribute(UserSessionConstantes.USER_GENERAL_BASIC_DATA_BEAN); 
		if(basicDataBean == null) {
			basicDataBean = new GeneralUserBean(username); 
			req.getSession().setAttribute(UserSessionConstantes.USER_INFO_BEAN, basicDataBean);
		}
		basicDataBean.setUsername(username);
		return basicDataBean; 
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		InformationBean infoBean = gengetUserInformationBean(req, resp);
		
		
		String username = (String) req.getSession().getAttribute(ATTR_SESSION_LOGIN); 
		if(username==null) {
			resp.sendError(404, "NO USER LOGGED.");
			return; 
		}
		
		String telephone   = req.getParameter("telephone");
		String cityAddress = req.getParameter("city_address"); 
		String userDescription = req.getParameter("user_description"); 
		
		if(telephone==null) telephone = ""; 
		if(cityAddress==null) cityAddress = ""; 
		if(userDescription==null) userDescription=""; 
		
		try {
			controller.getRegistrator().getUserDataLink().setDescription(username, userDescription);
			UserRequisit requisite = controller.getRegistrator().getUserDataLink().getRequisit(username); 
			requisite.setTelephone(telephone);
			requisite.setCity(cityAddress);
			controller.getRegistrator().getUserDataLink().updateRequisite(username, requisite);
			infoBean.reset();
			infoBean.setMessageSource(UserMessagesSourcesConstants.CLASSIC_SUCCESS_MSG_SRC);
			infoBean.setMessage("msg", "Постављање описа корисника успјешно.");
			infoBean.setAnnotation("OTHER_BASIC_USER_DATA");
		}catch(Exception ex) {
			infoBean.reset();
			infoBean.setException("msg",ex);
			infoBean.setMessageSource(UserMessagesSourcesConstants.CLASSIC_FAILURE_MSG_SRC);
			infoBean.setMessage("msg", "Постављање описа корисника неуспјешно.");
			infoBean.setAnnotation("OTHER_BASIC_USER_DATA");
		}
		GeneralUserBean basicDataBean = gengetUserBasicDataUserBean(username, req, resp); 
		basicDataBean.reload();
		resp.sendRedirect(req.getContextPath()+"/UserSwitchServlet");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}

package studiranje.ip.picture;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import studiranje.ip.bean.InformationBean;
import studiranje.ip.controller.UserGeneralController;
import studiranje.ip.lang.UserMessagesSourcesConstants;
import studiranje.ip.lang.UserSessionConstantes;
import studiranje.ip.model.UserRequisit;

/**
 * Сервлет који служи за брисање корисничке слике. 
 * @author mirko
 * @version 1.0
 */
@WebServlet("/UserPictureDeleteServlet")
public class UserPictureDeleteServlet extends HttpServlet{
	private static final long serialVersionUID = 2464768281248713963L;
	
	private UserGeneralController controller = UserGeneralController.getInstance(); 
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
    
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		String username = (String) req.getSession().getAttribute(ATTR_SESSION_LOGIN); 
		InformationBean info = (InformationBean) gengetUserInformationBean(req, resp);
		
		if(username==null) {
			info.reset();
			info.setException("msg", new RuntimeException("NOT LOGGED USER. DELETE PICTURE NOT POSIBLE."));
			info.setMessage("msg", "Брисање слике корисника неизводиво.");
			info.setMessageSource(UserMessagesSourcesConstants.CLASSIC_FAILURE_MSG_SRC);
			info.setAnnotation("USER_PICTURE_UPLOAD");
			resp.sendRedirect(req.getContextPath()+"/UserSwitchServlet");
			return; 
		}
		
		try {
			UserRequisit ureq = (UserRequisit) controller.getRegistrator().getUserDataLink().getRequisit(username); 
			if(ureq.getUserPicture() != null) controller.deleteUserImage(username);
			info.reset();
			info.setMessageSource(UserMessagesSourcesConstants.CLASSIC_SUCCESS_MSG_SRC);
			info.setMessage("msg", "Брисање слике корисника је изводено.");
			resp.sendRedirect(req.getContextPath()+"/UserSwitchServlet");
		}catch(Exception ex) {
			info.reset();
			info.setException("msg", ex);
			info.setMessage("msg", "Брисање слике корисника није изведено због грешке.");
			info.setAnnotation("USER_PICTURE_UPLOAD");
			info.setMessageSource(UserMessagesSourcesConstants.CLASSIC_FAILURE_MSG_SRC);
			resp.sendRedirect(req.getContextPath()+"/UserSwitchServlet");
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp); 
	}	
}

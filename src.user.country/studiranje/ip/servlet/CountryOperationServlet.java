package studiranje.ip.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import studiranje.ip.bean.InformationBean;
import studiranje.ip.controller.CountryDefinitionListController;
import studiranje.ip.controller.CountryIESeviceController;
import studiranje.ip.lang.UserMessagesSourcesConstants;

/**
 * Сервлет за операције попут синхронизације. 
 * @author mirko
 * @version 1.0
 */
@WebServlet("/CountryOperationServlet")
public class CountryOperationServlet extends HttpServlet{
	private static final long serialVersionUID = 1458257498401043780L;
	public static final CountryDefinitionListController cdlc = CountryServiceCenter.cdlc;
	public static final CountryIESeviceController ctrl = CountryServiceCenter.ctrl;
	private static boolean synchronizedBefore = false; 
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		InformationBean msg = (InformationBean)req.getSession().getAttribute("userInfoBean");
		CountryOperationSigns operation = CountryOperationSigns.valueOf(req.getParameter("operation"));
		
		if(operation==null) throw new UnsupportedOperationException();
		if(operation==CountryOperationSigns.SYNCHRONIZATION) {
			if(msg==null) {
				msg = new InformationBean();
				req.getSession().setAttribute("userInfoBean", msg);
			}
			if(synchronizedBefore) {
				msg.reset();
				msg.setMessage("msg", "Синхронизација је успјешно извршена, раније."); 
				msg.setMessageSource(UserMessagesSourcesConstants.CLASSIC_SUCCESS_MSG_SRC);
				msg.setAnnotation("COUNTRY_CHOOSER");
			}else {
				try {
					ctrl.synchronizeWithService();
				}catch(Exception ex) {
					ctrl.getCdlc().load();
				}
				msg.reset();
				msg.setMessage("msg", "Синхронизација је успјешно извршена."); 
				msg.setMessageSource(UserMessagesSourcesConstants.CLASSIC_SUCCESS_MSG_SRC);
				msg.setAnnotation("COUNTRY_CHOOSER");
				synchronizedBefore = true;
			}
		}
		resp.sendRedirect(req.getContextPath()+"/UserSwitchServlet");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}

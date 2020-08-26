package studiranje.ip.picture;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import studiranje.ip.bean.InformationBean;
import studiranje.ip.bean.PreviewTemporaryBean;
import studiranje.ip.lang.UserSessionConstantes;

/**
 * Сервлет који служи за једнократан приказ корисничке слике која се заодеслила у 
 * привременом зрну прегледа пријављеног корисника. 
 * @author mirko
 * @version 1.0
 */
@WebServlet("/UserPictureReviewServlet")
public class UserPictureReviewServlet extends HttpServlet{
	private static final long serialVersionUID = 625912254213559348L;
	
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
	
	private PreviewTemporaryBean gengetPreviewTemporary(HttpServletRequest req, HttpServletResponse resp) {
		PreviewTemporaryBean previewTempBean = (PreviewTemporaryBean) req.getSession().getAttribute(UserSessionConstantes.PREVIEW_TEMP_BEAN); 
		if(previewTempBean == null) {
			previewTempBean = new PreviewTemporaryBean(); 
			req.getSession().setAttribute(UserSessionConstantes.PREVIEW_TEMP_BEAN, previewTempBean);
		}
		return previewTempBean; 
	}
	
    public static final String ATTR_SESSION_LOGIN = "status.logged"; 
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("image");
		resp.addHeader("Content-Disposition", "inline; filename*=UTF-8''user.dat");
		
		String username = (String)req.getSession().getAttribute(ATTR_SESSION_LOGIN);
		if(username==null) {
			resp.sendError(404, "NO USER. DOWNLOAD PREVIEW USER STOP");
			return; 
		}
		
		InformationBean 	 info =    gengetUserInformationBean(req, resp);
		PreviewTemporaryBean preview = gengetPreviewTemporary(req, resp);
		
		try {
			resp.getOutputStream().write(preview.getUserPicture());
		}catch(Exception ex) {
			info.reset();
			resp.sendError(404, "DOWNLOAD PREVIEW USER ERROR");
			return; 
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}

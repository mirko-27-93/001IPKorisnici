package studiranje.ip.picture;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import studiranje.ip.controller.UserGeneralController;
import studiranje.ip.model.UserRequisit;

/**
 * Сервлет који се користи за преузимање корисичке слике. 
 * @author mirko
 * @version 1.0
 */
@WebServlet("/UserPictureDownloadServlet")
public class UserPictureDownloadServlet extends HttpServlet{
	private static final long serialVersionUID = -6614987930475298982L;
	
	private UserGeneralController controller = UserGeneralController.getInstance(); 
    public static final String ATTR_SESSION_LOGIN = "status.logged";
	
	public UserPictureDownloadServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("image");
		String username = (String) req.getSession().getAttribute(ATTR_SESSION_LOGIN); 
		if(username==null) {resp.sendError(404, "NO LOGGED USER. DOWNLOAD USER PICTURE FAILURE."); return; }
		try {
			UserRequisit requisit = controller.getRegistrator().getUserDataLink().getRequisit(username); 
			File userPicture = requisit.getUserPicture();  
			if(userPicture==null) {resp.sendError(404, "USER PICTURE NOT FOUND."); return;} 
			String filename = userPicture.getName().split("_",2)[1]; 
			resp.addHeader("Content-Disposition", "inline; filename*=UTF-8''"+filename);
			resp.getOutputStream().write(Files.readAllBytes(userPicture.toPath()));
		}catch(RuntimeException ex) {
			throw ex; 
		}
		catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}

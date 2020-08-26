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
 * Сервлет који се користи за преузимање профилне слике. 
 * @author mirko
 * @version 1.0
 */
@WebServlet("/ProfilePictureDownloadServlet")
public class ProfilePictureDownloadServlet extends HttpServlet{
	private static final long serialVersionUID = -3799981966157254874L;
	
	private UserGeneralController controller = UserGeneralController.getInstance(); 
    public static final String ATTR_SESSION_LOGIN = "status.logged"; 
	
	public ProfilePictureDownloadServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("image");
		String username = (String) req.getSession().getAttribute(ATTR_SESSION_LOGIN); 
		if(username==null) {resp.sendError(404, "NO LOGGED USER. DOWNLOAD PROFILE PICTURE FAILURE."); return;}
		try {
			UserRequisit requisit = controller.getRegistrator().getUserDataLink().getRequisit(username); 
			File profilePicture = requisit.getProfilePicture();  
			if(profilePicture==null) { resp.sendError(404,"PROFILE PICTURE NOT FOUND."); return; } 
			String filename = profilePicture.getName().split("_",2)[1];
			resp.addHeader("Content-Disposition", "inline; filename*=UTF-8''"+filename);
			resp.getOutputStream().write(Files.readAllBytes(profilePicture.toPath())); 
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

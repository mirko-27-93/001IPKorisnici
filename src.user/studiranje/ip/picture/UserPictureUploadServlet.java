package studiranje.ip.picture;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import studiranje.ip.bean.InformationBean;
import studiranje.ip.controller.UserGeneralController;
import studiranje.ip.exception.InvalidImageFormatException;
import studiranje.ip.lang.UserSessionConstantes;

/**
 *  Сервлет који се користи за постављање корисничке слике. 
 */
@WebServlet("/UserPictureUploadServlet")
public class UserPictureUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UserPictureUploadServlet() {
        super();
    }
    
    public static final String ATTR_SESSION_LOGIN = "status.logged"; 
    private UserGeneralController controller = UserGeneralController.getInstance(); 
    
    
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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int maxFileSize = 5000*1024;
		int maxMemSize =  5000*1024; 
	
		InformationBean infoBean =  gengetUserInformationBean(request, response);
		
		String contentType = request.getContentType(); 
		contentType = (contentType==null)? "": contentType; 
		
		if(contentType.indexOf("multipart/form-data")>=0) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(maxMemSize);
		
			factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
			
			ServletFileUpload upload = new ServletFileUpload(factory); 
			upload.setSizeMax(maxFileSize);
			
			
			try {
				List<?> fileItems = upload.parseRequest(request); 
				Iterator<?> i = fileItems.iterator(); 
				int count = 0; 
				while(i.hasNext()) {
					FileItem fi = (FileItem) i.next();
					if(!fi.isFormField()) {
						if(fi.getSize()==0L) continue; 
						if(fi.getName()==null) continue; 
						if(fi.getName().trim().length()==0) continue; 
						count++;
					}
				}
				i = fileItems.iterator();
				
				String username = (String) request.getSession().getAttribute(ATTR_SESSION_LOGIN);
				
				if(username==null) throw new RuntimeException("No logged user."); 
				
				if(count!=1) {
					controller.getMessages().setInfoBean(infoBean).setUploadUserPictureGeneralFailureForWeb(request, response);
					infoBean.setException("msg", new RuntimeException("No single file upload."));
					infoBean.setAnnotation("USER_PICTURE_UPLOAD");
					response.sendRedirect(request.getContextPath()+"/UserSwitchServlet");
					return; 
				}
				
				while(i.hasNext()) {
					FileItem fi = (FileItem) i.next();
					
					if(!fi.isFormField()) {
						if(fi.getSize()==0L) continue; 
						if(fi.getName()==null) continue; 
						if(fi.getName().trim().length()==0) continue; 
						
						String fileName = fi.getName(); 
						InputStream fileStream = fi.getInputStream();
						byte by[] = fileStream.readAllBytes(); 
						if(ImageIO.read(new ByteArrayInputStream(by))==null) throw new InvalidImageFormatException();
						ByteArrayInputStream bais = new ByteArrayInputStream(by);
						
						controller.archiveUserImage(username, fileName, bais);
					}
				}
				response.sendRedirect(request.getContextPath()+"/UserSwitchServlet");
			}catch(Exception ex) {
				controller.getMessages().setInfoBean(infoBean).setUploadUserPictureGeneralFailureForWeb(request, response);
				infoBean.setException("msg", ex);
				infoBean.setAnnotation("USER_PICTURE_UPLOAD");
				response.sendRedirect(request.getContextPath()+"/UserSwitchServlet");
				return; 
			}
		}else {
			controller.getMessages().setInfoBean(infoBean).setUploadUserPictureGeneralFailureForWeb(request, response);
			infoBean.setException("msg", new RuntimeException("No valid form of servlet."));
			infoBean.setAnnotation("USER_PICTURE_UPLOAD");
			response.sendRedirect(request.getContextPath()+"/UserSwitchServlet");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}

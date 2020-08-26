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
import studiranje.ip.bean.PreviewTemporaryBean;
import studiranje.ip.controller.UserGeneralController;
import studiranje.ip.exception.InvalidImageFormatException;
import studiranje.ip.lang.UserSessionConstantes;

/**
 * Сервлет који служи за постављање прегледа профилне слике. 
 * @author mirko
 * @version 1.0
 */

@WebServlet("/ProfilePicturePreviewServlet")
public class ProfilePicturePreviewServlet extends HttpServlet{
	private static final long serialVersionUID = -2673220214231712223L;
	
	private UserGeneralController controller = UserGeneralController.getInstance(); 
    public static final String ATTR_SESSION_LOGIN = "status.logged"; 
	
	public ProfilePicturePreviewServlet() {
		super();
	}

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
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int maxFileSize = 5000*1024;
		int maxMemSize =  5000*1024; 
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		String username = (String) req.getSession().getAttribute(ATTR_SESSION_LOGIN); 
		InformationBean infoBean =  gengetUserInformationBean(req, resp);
		
		if(username==null) {
			controller.getMessages().setInfoBean(infoBean).setUploadProfilePictureGeneralFailureForWeb(req, resp);
			infoBean.setAnnotation("PROFILE_PICTURE_UPLOAD");
			infoBean.setException("msg", new RuntimeException("NO LOGGED USER. PREVIEW PROFILE PICTURE FAILURE.")); 
			resp.sendRedirect(req.getContextPath()+"/UserSwitchServlet");
			return; 
		}
		
		
		String contentType = req.getContentType(); 
		contentType = (contentType==null)? "": contentType; 
		
		if(contentType.indexOf("multipart/form-data")>=0) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(maxMemSize);
		
			factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
			
			ServletFileUpload upload = new ServletFileUpload(factory); 
			upload.setSizeMax(maxFileSize);
			
			
			try {
				List<?> fileItems = upload.parseRequest(req); 
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
						
				if(count!=1) {
					controller.getMessages().setInfoBean(infoBean).setUploadProfilePictureGeneralFailureForWeb(req, resp);
					infoBean.setException("msg", new RuntimeException("No single file preview."));
					infoBean.setAnnotation("PROFILE_PICTURE_UPLOAD");
					resp.sendRedirect(req.getContextPath()+"/UserSwitchServlet");
					return;
				}
				
				while(i.hasNext()) {
					FileItem fi = (FileItem) i.next();
					
					if(!fi.isFormField()) {
						if(fi.getSize()==0L) continue; 
						if(fi.getName()==null) continue; 
						if(fi.getName().trim().length()==0) continue; 
				
						InputStream fileStream = fi.getInputStream();
						byte by[] = fileStream.readAllBytes(); 
						if(ImageIO.read(new ByteArrayInputStream(by))==null) throw new InvalidImageFormatException();
					 
						gengetPreviewTemporary(req, resp).setProfilePicture(by);
						
						infoBean.reset();
					}
				}
				resp.sendRedirect(req.getContextPath()+"/UserSwitchServlet");
			}catch(Exception ex) {
				controller.getMessages().setInfoBean(infoBean).setUploadProfilePictureGeneralFailureForWeb(req, resp);
				infoBean.setException("msg", ex);
				infoBean.setAnnotation("PROFILE_PICTURE_UPLOAD");
				resp.sendRedirect(req.getContextPath()+"/UserSwitchServlet");
				return; 
			}
		}else {
			controller.getMessages().setInfoBean(infoBean).setUploadProfilePictureGeneralFailureForWeb(req, resp);
			infoBean.setException("msg", new RuntimeException("No valid form of servlet."));
			infoBean.setAnnotation("PROFILE_PICTURE_UPLOAD");
			resp.sendRedirect(req.getContextPath()+"/UserSwitchServlet");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}

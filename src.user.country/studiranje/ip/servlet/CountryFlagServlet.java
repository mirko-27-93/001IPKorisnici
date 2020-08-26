package studiranje.ip.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import studiranje.ip.controller.CountryDefinitionListController;
import studiranje.ip.controller.CountryIESeviceController;
import studiranje.ip.controller.EUSiteDefinitionListController;
import studiranje.ip.model.Country;

/**
 * Сервлет за прослеђивање заставице за циљну државу. 
 * @author mirko
 * @version 1.0
 */
@WebServlet("/CountryFlagServlet")
public class CountryFlagServlet extends HttpServlet{
	private static final long serialVersionUID = 1773343983189675824L;
	public static final CountryDefinitionListController cdlc = CountryServiceCenter.cdlc;
	public static final CountryIESeviceController ctrl = CountryServiceCenter.ctrl;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("image/png");
		
		String a3c = req.getParameter("a3c"); 
		if(a3c==null) throw new RuntimeException("Not specified country.");
		if(a3c.trim().length()==0) throw new RuntimeException("Not specified country.");
		
		resp.setHeader("Content-Disposition", "inline; filename*=UTF-8''country_flag_"+a3c+".png"); 
		Country country = ctrl.getCountry(a3c); 
		if(country==null) { resp.sendError(404, "Country not found"); return; }
		
		if(!ctrl.getCidlc().existsPicture(a3c)) {
			if(cdlc instanceof EUSiteDefinitionListController) {
				EUSiteDefinitionListController edlc = (EUSiteDefinitionListController) cdlc; 
				String a2c = edlc.getLocalStorage().liveA2CMap().get(a3c); 
				if(a2c!=null) {
					new Thread(()->{
						try {
							edlc.loadAndLocalizePictureIfNotExists(a2c);
							edlc.getLocalStorage().setImageInfo(a2c, a2c+".png");
						}catch(Exception ex) {}
					}).start();
				}
			}
		}
		
		if(cdlc instanceof EUSiteDefinitionListController) {
			EUSiteDefinitionListController edlc = (EUSiteDefinitionListController) cdlc; 
			edlc.getLocalStorage().inspectPictures();
		}
		
		byte[] content = ctrl.getCidlc().getPictureContent(a3c); 
		if(content==null) {resp.sendError(404, "Country flag image not found"); return;}
		resp.getOutputStream().write(content); 
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}

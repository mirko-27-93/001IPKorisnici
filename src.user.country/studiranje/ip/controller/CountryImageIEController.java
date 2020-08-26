package studiranje.ip.controller;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

/**
 * Управљање сликама са заставицама од држава. 
 * @author mirko
 * @version 1.0
 */
public class CountryImageIEController {
	private CountryDefinitionListController cdlc;
	private CountryIESeviceController ctrl; 
	
	public CountryImageIEController(CountryDefinitionListController cdlc) {
		super();
		this.cdlc = cdlc;
		ctrl = new CountryIESeviceController(this); 
	}
	
	public CountryImageIEController(CountryIESeviceController ctrl) {
		super(); 
		this.ctrl = ctrl;
		cdlc = ctrl.getCdlc();
	}
	
	public CountryDefinitionListController getCdlc() {
		return cdlc;
	}

	public void setCdlc(CountryDefinitionListController cdlc) {
		this.cdlc = cdlc;
	} 
	
	public File getPicturePath(String id) {
		if(cdlc instanceof EUSiteDefinitionListController) {
			EUSiteDefinitionListController edlc = (EUSiteDefinitionListController) cdlc; 
			LocalDefinitionListController ldlc = edlc.getLocalStorage();
			return ldlc.getPicture(id);
		}
		return null; 
	}
	
	public boolean existsPicture(String id) {
		File pict = getPicturePath(id);
		if(pict == null) return false; 
		if(!pict.exists()) return false; 
		return true; 
	}
	
	public byte[] getPictureContent(String id) {
		if(!existsPicture(id)) return null; 
		try(FileInputStream fis = new FileInputStream(getPicturePath(id))){
			return fis.readAllBytes();
		}catch(Exception ex) {
			return null; 
		}
	}
	
	public Image getPictureObject(String id)  {
		byte[] content = getPictureContent(id); 
		if(content==null) return null; 
		try {return ImageIO.read(new ByteArrayInputStream(content));}
		catch(Exception ex) {return null;}
	}

	public CountryIESeviceController getCtrl() {
		return ctrl;
	}
}

package studiranje.ip.taglib.descript;

import java.io.ByteArrayInputStream;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import studiranje.ip.taglib.descript.tagcode.enviroment.FlipFlopDescribeEnviroment;

/**
 * Таг који се користи за лакше постављање садржаја описа за неки елемент. 
 * @author Masinacc
 * @version 1.0
 */
public class FlipFlopTag extends BodyTagSupport{
	private static final long serialVersionUID = 6844367664908175825L;
	private String elementId = ""; 
	private String descId = ""; 
	
	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		if(elementId==null) elementId=""; 
		this.elementId = elementId;
	}

	public String getDescId() {
		return descId;
	}

	public void setDescId(String descId) {
		if(descId==null) descId=""; 
		this.descId = descId;
	}
	
	public boolean testElementId() {
		if(elementId.trim().length()==0) return false; 
		for(char c: elementId.toCharArray()){
			if(Character.isAlphabetic(c)) continue; 
			if(Character.isDigit(c)) continue; 
			if(c=='_') continue; 
			return false;
		}
		return true;
	} 
	
	public boolean testDescId() {
		if(descId.trim().length()==0) return false; 
		for(char c: descId.toCharArray()){
			if(Character.isAlphabetic(c)) continue; 
			if(Character.isDigit(c)) continue; 
			if(c=='_') continue; 
			return false;
		}
		return true;
	}
	
	@Override 
	public int doStartTag() throws JspException{
		if(pageContext.getAttribute("yi.tag.root.node")!=null) {
			throw new JspException("DESC:Describe Tag - duplicate.");
		}
		pageContext.setAttribute("yi.tag.root.node", this);
		pageContext.setAttribute("yi.tag.element.node", null);
		pageContext.setAttribute("yi.tag.info.node", null);
		return EVAL_BODY_AGAIN;
	}
	
	@Override
	public int doEndTag() throws JspException {
		if(!testDescId() || !testElementId())
			throw new RuntimeException("DESC:DESCRIBE - ID names bad syntax. Valid is alphanumerics and downline."); 
		
		JspWriter out=pageContext.getOut(); 
		String xmlHtmlContent = "<desc:describe xmlns:desc=\"http://www.yatospace.org/describe\">";
		xmlHtmlContent+=bodyContent.getString(); 
		xmlHtmlContent+="</desc:describe>";  
	    try {
	    	FlipFlopDescribeEnviroment schemarValidator = new FlipFlopDescribeEnviroment(xmlHtmlContent); 
	    	schemarValidator.exceptioningValidate();
	    	Builder builder = new Builder();
			Document document = builder.build(new ByteArrayInputStream(xmlHtmlContent.getBytes("UTF-8")));
			Element root = document.getRootElement();
			Elements nodes = root.getChildElements();
			

			Element element = null; 
			Element info =  null;
			
			for(int i=0; i<nodes.size(); i++) {
				Element node = nodes.get(i);
				if(node.getQualifiedName().contentEquals("desc:element"))
					element = node; 
				if(node.getQualifiedName().contentEquals("desc:info"))
					info = node;
			}
			
			if(info==null) {
				System.out.println(element.toXML());
				out.print(element.toXML());
			}else {
				out.println("<div id='"+elementId+"' style='display:block' onclick='descript_flipflop_show(\""+elementId+"\", \""+descId+"\")'>"+element.toXML()+"</div>");
				out.println("<div id='"+descId+"' style='display:none'><blockquote><p>"+info.toXML()+"</p></blockquote></div>");
			}
			
			
	    }catch(Exception ex) {
	    	throw new JspException(ex);
	    }finally {
	    	pageContext.setAttribute("yi.tag.root.node", null);
			pageContext.setAttribute("yi.tag.element.node", null);
			pageContext.setAttribute("yi.tag.info.node", null);
	    }
	    return SKIP_BODY;  
	}  
}

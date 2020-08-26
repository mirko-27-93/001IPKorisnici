package studiranje.ip.taglib.descript;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Код тага који је необавезан, а ако се поајви његова унутрашњост спецификује, 
 * садржај описа који се појављује за неки елемент. 
 * @author Masinacc
 * @version 1.0
 */
public class FlipFlopDescriptionTag extends BodyTagSupport{
	private static final long serialVersionUID = -2479181308842197737L;
	
	@Override
	public int doStartTag() throws JspException {
		if(pageContext.getAttribute("yi.tag.root.node")==null) {
			throw new JspException("DESC:INFO Tag - used out of DESC:DESCRIBE.");
		}
		if(pageContext.getAttribute("yi.tag.info.node")!=null) {
			throw new JspException("DESC:INFO Tag - double node.");
		}
		if(pageContext.getAttribute("yi.tag.element.into")!=null) 
			throw new JspException("DESC:INFO Tag - into DESC:ELEMENT.");
		pageContext.setAttribute("yi.tag.info.into", new Object());
		return EVAL_BODY_AGAIN; 
	}
	
	@Override
	public int doEndTag() throws JspException {
		JspWriter out=pageContext.getOut(); 
		try {
			out.print("<desc:info>"+bodyContent.getString().trim()+"</desc:info>");  
	    }catch(Exception ex) {
	    	throw new JspException(ex);
	    }finally{
	    	pageContext.setAttribute("yi.tag.info.node", this);
	    	pageContext.setAttribute("yi.tag.info.into", null);
	    }
		return SKIP_BODY;
	}
}

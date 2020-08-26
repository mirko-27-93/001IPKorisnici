package studiranje.ip.taglib.descript;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Код тага који је необавезан, а ако �?е поајви његова унутрашњо�?т �?пецификује, 
 * �?адржај �?амог елемента. 
 * @author Masinacc
 * @version 1.0
 */
public class FlipFlopElementTag extends BodyTagSupport{
	private static final long serialVersionUID = -574452525838549590L;
	
	@Override
	public int doStartTag() throws JspException {
		if(pageContext.getAttribute("yi.tag.root.node")==null) {
			throw new JspException("DESC:ELEMENT Tag - used out of DESC:DESCRIBE.");
		}
		if(pageContext.getAttribute("yi.tag.element.node")!=null) {
			throw new JspException("DESC:ELEMENT Tag - double node.");
		}
		if(pageContext.getAttribute("yi.tag.info.into")!=null) 
			throw new JspException("DESC:ELEMENT Tag - into DESC:INFO.");
		pageContext.setAttribute("yi.tag.element.into", new Object());
		return EVAL_BODY_AGAIN; 
	}
	
	@Override
	public int doEndTag() throws JspException {
		JspWriter out=pageContext.getOut(); 
		try {
	    	out.print("<desc:element>"+bodyContent.getString().trim()+"</desc:element>");  
	    }catch(Exception ex) {
	    	throw new JspException(ex);
	    }finally{
	    	pageContext.setAttribute("yi.tag.element.node", this);
	    	pageContext.setAttribute("yi.tag.element.into", null);
	    }
		return SKIP_BODY;
	}
}

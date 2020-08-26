package studiranje.ip.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Сервлет контролер за свичеве. 
 * @author mirko
 * @version 1.0
 */

@WebServlet("/UserSwitchServlet")
public class UserSwitchServlet extends HttpServlet{
	private static final long serialVersionUID = 5583744931371703378L;
	public static final String ATTR_SESSION_LOGIN = "status.logged"; 
	
	public UserSwitchServlet() {}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		Object object = req.getSession().getAttribute(ATTR_SESSION_LOGIN);
		if(object==null) {
			RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/user/register.jsp"); 
			dispatcher.include(req, resp);
		}
		else {
			RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/user/main.jsp"); 
			dispatcher.include(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}

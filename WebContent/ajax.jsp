<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import ='com.google.gson.JsonObject' %>

<%@ page import='studiranje.ip.bean.InformationBean,  
			     studiranje.ip.lang.UserSessionConstantes,
			     studiranje.ip.service.controller.ShortAjaxDispatcherController,
			     studiranje.ip.service.lang.ShortAjaxIdContstants,
			     studiranje.ip.service.lang.ShortAjaxNamingException,
			     studiranje.ip.service.util.DeleteMessageRunnable,
			     studiranje.ip.service.util.DeleteMessageRunnable'%>

<%!
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
	
	private void registerActivity(ShortAjaxDispatcherController ajaxServerCtrl, HttpServletRequest req, HttpServletResponse resp) {
		InformationBean bean = gengetUserInformationBean(req, resp);
		DeleteMessageRunnable deleteMessageActivity = new DeleteMessageRunnable(bean);
		ajaxServerCtrl.set(deleteMessageActivity.getId(), deleteMessageActivity);
	}
%>

<%
	response.setContentType("application/json");
	ShortAjaxDispatcherController controller = new ShortAjaxDispatcherController();
	registerActivity(controller, request, response);
	String activity = request.getParameter("activity");
	if(activity==null) throw new ShortAjaxNamingException("Activity.");
	switch(activity) {
		case ShortAjaxIdContstants.DELETE_MESSAGE:
			JsonObject object = new JsonObject();
			object.addProperty("id", activity);
			String result = controller.execute(ShortAjaxIdContstants.DELETE_MESSAGE, object.toString()); 
			response.getWriter().println(result); 
			break;
		default: 
			throw new ShortAjaxNamingException("Activity, unknown.");
	}
%>
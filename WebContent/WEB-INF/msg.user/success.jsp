<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:useBean id='userInfoBean' class='studiranje.ip.bean.InformationBean' scope='session'></jsp:useBean>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "desc" uri = "http://www.yatospace.com/describe" %>

<div class='success pointerable' align='center' id='message_box'><br>
	<desc:describe elementId="messageSrc" descId="messageDesc">
		<desc:element>
			<div>
				<c:out value='${userInfoBean.getMessage("msg")}'></c:out><br/>
			</div>
		</desc:element>
		<desc:info>
			<div>
				<a style='padding-left : 10px' href="javascript:user_message_clear('${pageContext.request.contextPath}')">УКЛАЊАЊЕ</a>
			</div>
		</desc:info>
	</desc:describe><br>
</div>
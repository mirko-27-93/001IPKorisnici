<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:useBean id='userBean'     class='studiranje.ip.bean.UserBean' scope='session'></jsp:useBean>
<jsp:useBean id='userInfoBean' class='studiranje.ip.bean.InformationBean' scope='session'></jsp:useBean>

<%@ taglib prefix = "c"    uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "desc" uri = "http://www.yatospace.com/describe" %>

<c:if test='${sessionScope["status.logged"]==null}'>
	<!DOCTYPE html>
	<html>
		<head>
			<meta charset="UTF-8">
			<title>Корисници</title>
			<link rel="icon"   	   type="image/x-icon" href="${pageContext.request.contextPath}/WEB-ICON/tux.ico"></link>
			<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/WEB-CSS/messages.css'></link>
			<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/WEB-CSS/cursors.css'></link>
			<script type="text/javascript" src='${pageContext.request.contextPath}/FUNCTIONALS-JAVASCRIPT/descript.js'></script>
			<script type="text/javascript" src='${pageContext.request.contextPath}/WEB-JAVASCRIPT/ajax_user_message_clear.js'></script>
		</head>
		<body>
			<h3>Пријава корисника</h3>
			<form name='prijava_korisnika'>
				<table>
					<tr>
						<td>Корисничко име: </td>
						<td><input type='text' name='username' value='${userBean.username}'/></td>
					</tr>
					<tr>
						<td>Лозинка: </td>
						<td><input type='password' name='password'/></td>
					</tr>
					<tr>
						<td>
							<br><input type='submit' name='login'    value='Потврда'      formaction='../../UserResolveServlet/LOGIN' formmethod='POST' onclick='document.getElementById("operation").value="LOGIN" '/>
								<input type='submit' name='register' value='регистрација' formaction='../../UserSwitchServlet'        formmethod='POST' onclick='document.getElementById("operation").value="REGISTER"' formtarget='_blank'/>
					</tr>
				</table>
				<input type='hidden' name='operation' id='operation' value=''/>
			</form>
			<c:if test="${userInfoBean.messageSource ne ''}">
				<br><jsp:include page='${userInfoBean.messageSource}'></jsp:include>
			</c:if>
			<br>
			<jsp:include page="/WEB-INF/app/linx.jsp"></jsp:include>
		</body>
	</html>
</c:if>
<c:if test='${sessionScope["status.logged"]!=null}'>
	<c:redirect url = "../../UserSwitchServlet"/>
</c:if>
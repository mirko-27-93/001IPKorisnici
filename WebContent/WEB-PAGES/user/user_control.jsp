<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:useBean id='userBean'              class='studiranje.ip.bean.UserBean' scope='session'></jsp:useBean>
<jsp:useBean id='userInfoBean'          class='studiranje.ip.bean.InformationBean' scope='session'></jsp:useBean>
<jsp:useBean id='userConfigurationBean' class='studiranje.ip.bean.UserConfigurationBean' scope='session'></jsp:useBean>

<%@ taglib prefix = "c"    uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "desc" uri = "http://www.yatospace.com/describe" %>

<jsp:useBean id='sessionBean' class='studiranje.ip.bean.SessionBean' scope='session'></jsp:useBean>
<c:out value='${sessionBean.loadFromId(pageContext.session.id)}'></c:out>

<c:if test='${sessionScope["status.logged"]!=null}'>
	<c:out value='${userConfigurationBean.reload(sessionScope["status.logged"])}'></c:out>
	<!DOCTYPE html>
	<html>
		<head>
			<meta charset="UTF-8">
			<title>Корисници</title>
			<link rel="icon"   	   type="image/x-icon" href="${pageContext.request.contextPath}/WEB-ICON/tux.ico"></link>
			<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/WEB-CSS/messages.css'></link>
			<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/WEB-CSS/cursors.css'></link>
			<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/WEB-CSS/fonts.css'></link> 
			<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/WEB-CSS/tables.css'></link> 
			<script type="text/javascript" src='${pageContext.request.contextPath}/FUNCTIONALS-JAVASCRIPT/descript.js'></script>
			<script type="text/javascript" src='${pageContext.request.contextPath}/WEB-JAVASCRIPT/ajax_user_message_clear.js'></script>
			<script type="text/javascript" src='${pageContext.request.contextPath}/AJAX-JAVASCRIPT-JSON/sessions_works.js'></script>
			<style>
				input[type=button].exit{
					background:url(../../WEB-APP/MENU/exit.png) no-repeat center center; 
					background-size: 100% 100%;
					width: 30px;
					height: 30px;   
					border: solid 1px black; 
					cursor: pointer; 
				}
				input[type=button].exit:hover{
					border: solid 1px gray; 
				}
				input[type=button].exit:active{
					border: solid 1px blue; 
				}
				input[type=button].menu{
					background:url(../../WEB-APP/MENU/menu.png) no-repeat center center; 
					background-size: 100% 100%;
					width: 30px;
					height: 30px;  
					border: solid 1px black;
					cursor: pointer; 
				}
				input[type=button].menu:hover{
					border: solid 1px gray; 
				}
				input[type=button].menu:active{
					border: solid 1px blue; 
				}
			</style>
		</head>
		<body>
			<jsp:include page="/WEB-INF/images/profile-picture.jsp"></jsp:include>
			<h3 id='USER_CTRL'>Корисници - контролна плоча</h3>
			<form name='user_control_panel' method='POST' action='../../UserFlagsSetServlet'>
				<table>
					<tr>
						<td>Размјена обавештења преко система ове веб апликације: </td>
						<td><input type='checkbox' name='unws' ${userConfigurationBean.userConfigurations.webNotifications?"checked":""} /></td>
					</tr>
					<tr>
						<td>Размјена обавештења преко електронске поште: </td>
						<td><input type='checkbox' name='unes' ${userConfigurationBean.userConfigurations.emailNotifications?"checked":""} /></td>
					</tr>
					<tr>
						<td>Корисничко управљање сесијама: </td>
						<td><input type='checkbox' name='uscs' ${userConfigurationBean.userConfigurations.userSessionsControl?"checked":""}/></td>
					</tr>
				</table>
				<br>
				<input type='hidden' name='control' value='CTRL'/>
				<input type='submit' name='submit' value='Потврда'/>
			</form>
			<br>
			<c:if test='${userInfoBean.annotation eq ""}'>
				<c:if test="${userInfoBean.messageSource ne ''}">
					<br><jsp:include page='${userInfoBean.messageSource}'></jsp:include>
				</c:if>
			</c:if>
			<h3>Техничке информације о пријављеном кориснику </h3>
			<table>
				<tr>
					<td>Корисничко име : </td>
					<td><font face='YI Courier New'><c:out value="${userBean.username}"></c:out></font></td>
				</tr>
				<tr>
					<td>Запис лозинке: </td>
					<td><font face='YI Courier New'><c:out value="${userBean.getPasswordRecord()}"></c:out></font></td>
				</tr>
				<tr>
					<td>Веб сесија: </td>
					<td><font face='YI Courier New'><c:out value="${pageContext.session.id}"></c:out></font></td>
				</tr>
				<tr>
					<td>Адреса електронске поште :  </td>
					<td><font face='YI Courier New'><c:out value="${userBean.email}"></c:out></font></td>
				</tr>
			</table>
			<br>
			<c:if test='${userConfigurationBean.userConfigurations.userSessionsControl}'>
				<h3 id='CURRENT_SESSION'>Поставке тренутне сесије</h3>
				<form name='current_session' action='../../SessionSetInfoServlet'>
					<table>
						<tr>
							<td>Основна идентификација/име : </td>
							<td><input type='text' name='session.basic.id' value='<c:out value="${sessionBean.sessionData.basicId}"></c:out>' ></td>
						</tr>
						<tr>
							<td>Технички подаци о сесији : </td>
							<td><input type='text' value='<c:out value="${sessionBean.sessionData.getAppTypeLine()}"></c:out>' readonly></td>
						</tr>
					</table>
					<p>Кориснички опис сесије: </p>
					<textarea name='session.description' cols='80' rows='25'><c:out value="${sessionBean.sessionData.description}"></c:out></textarea>
					<br><br>
					<input type='hidden' name='session.data' value='${sessionBean.sessionData.otherData}'/>
					<input type='submit' value='Потврда'/> 
				</form>
				<c:if test='${userInfoBean.annotation eq "CURRENT_SESSION"}'>
					<c:if test="${userInfoBean.messageSource ne ''}">
						<br><jsp:include page='${userInfoBean.messageSource}'></jsp:include>
					</c:if>
				</c:if>
				<br>
				<jsp:include page="/WEB-INF/parts.session/sessions.jsp"></jsp:include>
				<c:if test='${userInfoBean.annotation eq "USER_SESSIONS"}'>
					<c:if test="${userInfoBean.messageSource ne ''}">
						<br><jsp:include page='${userInfoBean.messageSource}'></jsp:include>
					</c:if>
				</c:if>
				<br>
			</c:if>
			<jsp:include page="/WEB-INF/app/linx.jsp"></jsp:include>
		</body>
	</html>
	
</c:if>
<c:if test='${sessionScope["status.logged"]==null}'>
	<c:redirect url = "../../UserSwitchServlet"/>
</c:if>
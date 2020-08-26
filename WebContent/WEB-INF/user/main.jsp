<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:useBean id='userBean'     class='studiranje.ip.bean.UserBean' scope='session'></jsp:useBean>
<jsp:useBean id='userInfoBean' class='studiranje.ip.bean.InformationBean' scope='session'></jsp:useBean>

<%@ taglib prefix = "c"    uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "desc" uri = "http://www.yatospace.com/describe" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Корисници</title>
		<link rel="icon"   	   type="image/x-icon" href="${pageContext.request.contextPath}/WEB-ICON/tux.ico"></link>
		<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/WEB-CSS/messages.css'></link>
		<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/WEB-CSS/cursors.css'></link>
		<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/WEB-CSS/tables.css'></link>
		<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/WEB-CSS/fonts.css'></link> 
		<script type="text/javascript" src='${pageContext.request.contextPath}/FUNCTIONALS-JAVASCRIPT/descript.js'></script>
		<script type="text/javascript" src='${pageContext.request.contextPath}/WEB-JAVASCRIPT/user_operation_coonfirmation.js'></script>
		<script type="text/javascript" src='${pageContext.request.contextPath}/WEB-JAVASCRIPT/ajax_user_message_clear.js'></script>
		<script type="text/javascript" src='${pageContext.request.contextPath}/WEB-JAVASCRIPT/password_old_new_event.js'></script>
		<script type="text/javascript" src='${pageContext.request.contextPath}/WEB-JAVASCRIPT/functional_elements.js'></script>
		<script type="text/javascript" src='${pageContext.request.contextPath}/AJAX-JAVASCRIPT-JSON/country_works.js'></script>
		<style type="text/css">
			#dropContainer{
				overflow-x: scroll;  /* прокрутка по горизонтали */
				overflow-y: scroll; /* прокрутка по вертикали   */
			}
			#dropContainerUser{
				overflow-x: scroll;  /* прокрутка по горизонтали */
				overflow-y: scroll; /* прокрутка по вертикали   */
			}
		</style>
	</head>
	<body>
		<jsp:include page="/WEB-INF/images/profile-picture.jsp"></jsp:include>
		<h3>Измјена корисника (основни подаци)</h3>
		<form name='izmjena_podataka_korisnika' onsubmit='return check_and_confirm_erase_and_delete(event)'>
			<table>
				<tr>
					<td>Статус: </td>
					<td>старо</td>
					<td>ново</td>
				</tr>
				<tr>
					<td>Име: </td>
					<td><input type='text' name='old_firstname' value='${userBean.firstname}' readonly/></td>
					<td><input type='text' name='firstname' value='${userBean.firstname}'/></td>
				</tr>
				<tr>
					<td>Презиме: </td>
					<td><input type='text' name='old_secondname' value='${userBean.secondname}' readonly/></td>
					<td><input type='text' name='secondname' value='${userBean.secondname}'/></td>
				</tr>
				<tr>
					<td>Електронска пошта: </td>
					<td><input type='text' name='old_useremail' value='${userBean.email}' readonly/></td>
					<td><input type='text' name='useremail' value='${userBean.email}'/></td>
				</tr>
				<tr>
					<td>Корисничко име: </td>
					<td><input type='text' name='old_username' value='${userBean.username}' readonly/></td>
					<td><input type='text' name='username' value='${userBean.username}'/></td>
				</tr>
				<tr>
					<td>Лозинка: </td>
					<td><input type='password' name='old_password'  id='old_password1' onkeyup='copy_old_password_to_neo_fields()'/></td>
					<td><input type='password' name='password'  id='password1'/></td>
				</tr>
				<tr>
					<td>Поновљена лозинка: </td>
					<td></td>
					<td><input type='password' name='password2' id='password2'/></td>
				</tr>
				<tr>
					<td>
						<br><input type='submit' name='change'     value='Потврда' formaction='./UserResolveServlet/UPDATE' formmethod='POST' onclick='document.getElementById("operation").value="UPDATE"'/>
							<input type='submit' name='delete'     value='брисање' formaction='./UserResolveServlet/DELETE' formmethod='POST' onclick='document.getElementById("operation").value="DELETE"'/>
						    <input type='submit' name='logout'     value='одјава' formaction='./UserResolveServlet/LOGOUT'  formmethod='POST' onclick='document.getElementById("operation").value="LOGOUT"'/>
						    <input type='submit' name='logout_all' value='одјава са свих сесија' formaction='./UserResolveServlet/LOGOUT_ALL_SESSIONS_FOR_USER'  formmethod='POST' onclick='document.getElementById("operation").value="LOGOUT_ALL_SESSIONS_FOR_USER"'/>
					</td>
				</tr>
			</table>
			<input type='hidden' name='operation' id='operation' value=''/>
		</form>
		<c:if test='${userInfoBean.annotation eq ""}'>
			<c:if test="${userInfoBean.messageSource ne ''}">
				<br><jsp:include page='${userInfoBean.messageSource}'></jsp:include>
			</c:if>
		</c:if>
		<br>
		<jsp:include page="/WEB-INF/parts/profile_user_picture_upload.jsp"></jsp:include>
		<br>
		<c:if test='${userInfoBean.annotation eq "PROFILE_PICTURE_UPLOAD"}'>
			<c:if test="${userInfoBean.messageSource ne ''}">
				<br><jsp:include page='${userInfoBean.messageSource}'></jsp:include>
			</c:if>
		</c:if>
		<c:if test='${userInfoBean.annotation eq "USER_PICTURE_UPLOAD"}'>
			<c:if test="${userInfoBean.messageSource ne ''}">
				<br><jsp:include page='${userInfoBean.messageSource}'></jsp:include>
			</c:if>
		</c:if>
		<br>
		<jsp:include page="/WEB-INF/parts/country_chooser.jsp"></jsp:include>
		<c:if test='${userInfoBean.annotation eq "COUNTRY_CHOOSER"}'>
			<c:if test="${userInfoBean.messageSource ne ''}">
				<br><jsp:include page='${userInfoBean.messageSource}'></jsp:include>
			</c:if>
		</c:if>
		<br>
		<jsp:include page="/WEB-INF/parts/other_basic_user_data.jsp"></jsp:include>
		<c:if test='${userInfoBean.annotation eq "OTHER_BASIC_USER_DATA"}'>
			<c:if test="${userInfoBean.messageSource ne ''}">
				<br><jsp:include page='${userInfoBean.messageSource}'></jsp:include>
			</c:if>
		</c:if>
		<br>
		<jsp:include page="/WEB-INF/app/linx.jsp"></jsp:include>
	</body>
</html>
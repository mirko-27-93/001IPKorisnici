<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean id='userBean'     class='studiranje.ip.bean.UserBean' scope='session'></jsp:useBean>
<jsp:useBean id='userInfoBean' class='studiranje.ip.bean.InformationBean' scope='session'></jsp:useBean>
<jsp:useBean id='userGeneralBean' class='studiranje.ip.bean.GeneralUserBean' scope='session'>
	<jsp:setProperty name="userGeneralBean" property="username" value="${userBean.username}" />
</jsp:useBean>

<jsp:setProperty name="userGeneralBean" property="username" value="${userBean.username}" />

<%@ taglib prefix = "c"    uri = "http://java.sun.com/jsp/jstl/core" %>

<h3 id='OOPK_H'>Остали основни подаци корисника</h3>
<form name='OOPK' method='POST' action='${pageContext.request.contextPath}/UserDescriptionSetServlet#OOPK_H'>
	<table>
		<tr>
			<td valign='top'>Број телефона: </td>
			<td valign='top'><input type='text' name='telephone' value='${userGeneralBean.getTelephone()}'/></td>
		</tr>
	</table>
	<br>
	<table>
		<tr>
			<td valign='top'>Град/адреса: </td>
			<td valign='top'><textarea name='city_address' rows='15' cols='50'><c:out value='${userGeneralBean.getCityAndAddress()}'></c:out></textarea></td>
		</tr>
		<tr>
			<td valign='top'>Опис: </td>
			<td valign='top'><textarea name='user_description' rows='15' cols='50'><c:out value='${userGeneralBean.getDescription()}'></c:out></textarea></td>
		</tr>
	</table>
	<br>
	<input type='submit' value='Потврда'/>
</form>
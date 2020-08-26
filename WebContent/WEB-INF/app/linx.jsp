<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>

<table>
	<tr>
		<td valign='top'>
			<dl>
				<dt>Линкови</dt>
				<dd><a href='${pageContext.request.contextPath}'>Основна страница</a></dd>
				<c:if test='${sessionScope["status.logged"] == null}'>
					<dd><a href='${pageContext.request.contextPath}/WEB-PAGES/user/login.jsp'>Пријавна страница</a></dd>
				</c:if>
				<c:if test='${sessionScope["status.logged"] != null}'>
					<dd><a href='${pageContext.request.contextPath}/WEB-PAGES/user/user_control.jsp'>Контролна плоча</a></dd>
				</c:if>
				<dd><a href='${pageContext.request.contextPath}/users.jsp'>Преглед корисника</a></dd>
			</dl>
		</td>
		<td valign='top'>
			&nbsp;&nbsp;&nbsp;<jsp:include page="/WEB-INF/images/user-picture.jsp"></jsp:include>
		</td>
	</tr>
</table>
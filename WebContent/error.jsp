<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page isErrorPage="true" %>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Корисници</title>
		<link rel="icon"   	   type="image/x-icon" href="${pageContext.request.contextPath}/WEB-ICON/engine.ico"></link>
		<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/WEB-CSS/fonts.css'>
	</head>
	<body>
		<h3>Корисници - стараница за грешке.</h3>
		<table>
			<tr valign='top' align='left'>
				<td>Код &nbsp;&nbsp;&nbsp;</td>
				<td><div class='machine_font'>
					<b style='color:red'><c:out value="${pageContext.errorData.statusCode}"></c:out></b><br>
				</div></td>
			</tr>
			<%if(request.getAttribute("javax.servlet.error.message")!=null) {%>
				<tr valign='top' align='left'>
					<td>Порука &nbsp;&nbsp;&nbsp;</td>
					<td><div class='machine_font'>
						<c:forEach var='line' items='<%=request.getAttribute("javax.servlet.error.message").toString().split("\n") %>'>
							<b><c:out value='${line}'></c:out></b>
							<br>
						</c:forEach>
					</div></td>
				</tr>
			<%} %>
			<c:if test="${pageContext.exception != null}">
				<tr valign='top' align='left'>
					<td>Изузетак &nbsp;&nbsp;&nbsp;</td>
					<td>	
						<div class='machine_font'>
							<c:set var="ex" value='${pageContext.exception}' scope='request'></c:set>
							<% while(request.getAttribute("ex")!=null){%>
								<b style='color:red'><c:out value="${ex.getClass().getName()}"></c:out>:</b><br>
								<c:forEach var='line' items='${ex.getMessage().split("\\\n")}'>
									<b style='color:blue'><c:out value='${line}'></c:out></b>
									<br>
								</c:forEach>
								<c:forEach var='element' items='${ex.stackTrace}'>
									<c:forEach var='line' items='${element.toString().split("\\\n")}'>
										<c:out value="${line}"></c:out>
										<br>
									</c:forEach>
								</c:forEach>
								<br>
								<c:set var="ex" value='${ex.cause}' scope='request'></c:set>
							<% }%>
						</div>
					</td>
				</tr>
			</c:if>
		</table>
	</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:useBean id='userInfoBean' class='studiranje.ip.bean.InformationBean' scope='session'></jsp:useBean>

<%@ taglib prefix = "c"    uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "desc" uri = "http://www.yatospace.com/describe" %>


<div class='error pointerable' align='center' id='message_box'>
	<br>
	<desc:describe elementId="messageSrc" descId="messageDesc">
		<desc:element>
			<c:out value='${userInfoBean.getMessage("msg")}'></c:out>
		</desc:element>
		<desc:info>
			<div>
				<table>
					<c:if test='${userInfoBean.getException("msg")!=null}'>
						<tr>
							<td>Изузетак: </td>
							<td><c:out value='${userInfoBean.getException("msg").getClass().getName()}'></c:out></td>
						</tr>
						<c:if test='${userInfoBean.getException("msg").message!=null}'>
							<tr>
								<td>Порукa: </td>
								<td><c:out value='${userInfoBean.getException("msg").getMessage()}'></c:out></td>
							</tr>
						</c:if>
					</c:if>
				</table>
				<br/>
				<a href='${pageContext.request.contextPath}/UserExceptionPreviewServlet' target='_blank'>ПРЕГЛЕД</a>
				<a style='padding-left : 10px' href="javascript:user_message_clear('${pageContext.request.contextPath}')">УКЛАЊАЊЕ</a>
			</div>
		</desc:info>
	</desc:describe>
&nbsp;</div>


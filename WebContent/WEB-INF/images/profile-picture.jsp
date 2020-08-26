<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:useBean id='previewTempBean' class='studiranje.ip.bean.PreviewTemporaryBean' scope='session'></jsp:useBean>

<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>

<c:if test='${not previewTempBean.existsProfilePicture()}'>
	<img src='${pageContext.request.contextPath}/ProfilePictureDownloadServlet' alt='' width='100%' height='300px' onerror="this.style.display='none'"></img>
</c:if>
<c:if test='${previewTempBean.existsProfilePicture()}'>
	<img src='${pageContext.request.contextPath}/ProfilePictureReviewServlet' alt='' width='100%' height='300px' onerror="this.style.display='none'"></img>
</c:if>
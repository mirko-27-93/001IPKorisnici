<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:useBean id='previewTempBean' class='studiranje.ip.bean.PreviewTemporaryBean' scope='session'></jsp:useBean>

<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>

<c:if test='${not previewTempBean.existsUserPicture()}'>    
	<img id='user-img' src='${pageContext.request.contextPath}/UserPictureDownloadServlet' alt='' width='300 px' height='300 px' onerror="this.style.display='none'"></img>
</c:if>
<c:if test='${previewTempBean.existsUserPicture()}'>    
	<img id='user-img' src='${pageContext.request.contextPath}/UserPictureReviewServlet' alt='' width='300 px' height='300 px' onerror="this.style.display='none'"></img>
</c:if>
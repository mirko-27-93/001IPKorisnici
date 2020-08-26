<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test='${sessionScope["status.logged"]!=null}'>
	<div>
	<table>
		<tr>
			<td>&nbsp;</td>
			<td>
				<h3 id='UPP_H'>Профилне слике.</h3>
				<form id="profile_picture" name="profile_picture" method="POST" 
					accept-charset="UTF-8" enctype="multipart/form-data">
					<div>Постављање профилне слике&nbsp;</div>
					<br>
					<div style="display:none" id="noneContent">
						<br>
						<br>&nbsp; На ово мјесто треба превући жељену слику. &nbsp; 
					</div>
					<div id="dropContainer" style="border:1px solid black;height:100px; vertical-align: top; text-align: center" ></div>
					<input type="file" id="fileInput" name="fileInput" size="50" style="display:none"/>
					<br><div>
						<input type="submit" name="profile.picture.preview" value="Преглед" formaction='${pageContext.request.contextPath}/ProfilePicturePreviewServlet#UPP_H'/>
						<input type="submit" name="profile.picture.set" value="Постављање" formaction='${pageContext.request.contextPath}/ProfilePictureUploadServlet#UPP_H'/>
						<input type="submit" name="profile.picture.reset" value="Брисање" formaction='${pageContext.request.contextPath}/ProfilePictureDeleteServlet#UPP_H' formenctype='application/x-www-form-urlencoded' formmethod='GET'/>
					</div>
					<script>
						document.getElementById('dropContainer').innerHTML=document.getElementById("noneContent").innerHTML; 
						initDragAndDropFileChoose('dropContainer', 'fileInput');
					</script>
				</form>
				<br>
				<form id="user_picture" name="user_picture" method="POST" 
					accept-charset="UTF-8" enctype="multipart/form-data">
					<div>Постављање корисничке слике&nbsp;</div>
					<br>
					<div style="display:none" id="noneContent">
						<br>
						<br>&nbsp; На ово мјесто треба превући жељену слику. &nbsp; 
					</div>
					<div id="dropContainerUser" style="border:1px solid black;height:100px; vertical-align: top; text-align: center" ></div>
					<input type="file" id="fileInputUser" name="fileInputUser" size="50" style="display:none"/>
					<br><div>
						<input type="submit" name="user.picture.preview" value="Преглед" formaction='${pageContext.request.contextPath}/UserPicturePreviewServlet#UPP_H'/>
						<input type="submit" name="user.picture.set" value="Постављање" formaction='${pageContext.request.contextPath}/UserPictureUploadServlet#UPP_H'/>
						<input type="submit" name="user.picture.reset" value="Брисање" formaction='${pageContext.request.contextPath}/UserPictureDeleteServlet#UPP_H' formenctype='application/x-www-form-urlencoded' formmethod='GET'/>
					</div>
					<script>
						document.getElementById('dropContainerUser').innerHTML=document.getElementById("noneContent").innerHTML; 
						initDragAndDropFileChoose('dropContainerUser', 'fileInputUser');
					</script>
				</form>
			</td>
		</tr>
	</table>
	</div>
</c:if>
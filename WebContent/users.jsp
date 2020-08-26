<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean id='usersPageBean'  class='studiranje.ip.bean.PageBean' scope='session'></jsp:useBean>
<jsp:useBean id='databaseInfoBean'  class='studiranje.ip.bean.DatabaseInfoBean' scope='session'></jsp:useBean>

<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>

<%@page import = "studiranje.ip.bean.PageBean" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Корисници</title>
		<link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/WEB-ICON/tux.ico"></link>
		<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/WEB-CSS/fonts.css'></link> 
		<link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/WEB-CSS/tables.css'></link> 
	</head>
	<body>
		 <sql:setDataSource var = "connection" driver = "${databaseInfoBean.driver}"
         url = "${databaseInfoBean.jdbcURL}" user="${databaseInfoBean.username}"  password = "${databaseInfoBean.password}"/>
         
         <sql:query dataSource = "${connection}" var = "result">
            SELECT COUNT(username) AS number  FROM ip.userinfo WHERE username LIKE ?;
            <sql:param value='${usersPageBean.startFilter}%'></sql:param>
         </sql:query>
         
         <c:forEach var = "row" items = "${result.rows}">
         	<c:set var='total' value="${row.number}"></c:set>
         </c:forEach>
         
         <jsp:setProperty property="totalCount" name="usersPageBean" value="${total}"/>
         
         <c:if test='${param["operation"] eq "GO"}'>
          <% 
         	PageBean pageInfo = (PageBean) request.getSession().getAttribute("usersPageBean");
         	try{pageInfo.setPageNo(Integer.parseInt(request.getParameter("page_no")));}catch(Exception ex){}
         	try{pageInfo.setPageSize(Integer.parseInt(request.getParameter("page_size")));}catch(Exception ex){}
         	pageInfo.setStartFilter(request.getParameter("start_filter"));
         %>
         </c:if>
         <c:if test='${param["operation"] eq "PREVIOUS"}'>
          <% 
         	PageBean pageInfo = (PageBean) request.getSession().getAttribute("usersPageBean");
         	try{pageInfo.setPageNo(Integer.parseInt(request.getParameter("page_no"))-1);}catch(Exception ex){}
         	try{pageInfo.setPageSize(Integer.parseInt(request.getParameter("page_size")));}catch(Exception ex){}
         	pageInfo.setStartFilter(request.getParameter("start_filter"));
         %>
         </c:if>
         <c:if test='${param["operation"] eq "NEXT"}'>
          <% 
         	PageBean pageInfo = (PageBean) request.getSession().getAttribute("usersPageBean");
         	try{pageInfo.setPageNo(Integer.parseInt(request.getParameter("page_no"))+1);}catch(Exception ex){}
         	try{pageInfo.setPageSize(Integer.parseInt(request.getParameter("page_size")));}catch(Exception ex){}
         	pageInfo.setStartFilter(request.getParameter("start_filter"));
         %>
         </c:if>
        <jsp:include page="/WEB-INF/images/profile-picture.jsp"></jsp:include>
		<h3>Преглед корисника</h3>
		<form name='pregled_korisnika_manervar' method='POST'>
			<table>
				<tr>
					<td>Страница бр.  : </td>
					<td><input type='text' name='page_no' value='${usersPageBean.pageNo}'></td>
				</tr>
				<tr>
					<td>Величина стр. : </td>
					<td><input type='text' name='page_size' value='${usersPageBean.pageSize}'></td>
				</tr>
				<tr>
					<td>Бр. ставки    : </td>
					<td><input type='text' name='total_count' value='${usersPageBean.totalCount}' readonly></td>
				</tr>
				<tr>
					<td>Бр. страница  : </td>
					<td><input type='text' name='num_pages' value='${usersPageBean.pageCount()}' readonly></td>
				</tr>
				<tr>
					<td>Ставки на стр.: </td>
					<td><input type='text' name='page_count' value='${usersPageBean.itemsInPage()}' readonly></td>
				</tr>
				<tr>
					<td>Индекс почетка : </td>
					<td><input type='text' name='index_item' value='${usersPageBean.indexStartOfPage()}' readonly></td>
				</tr>
				<tr>
					<td>Филтер - корисничко име : </td>
					<td><input type='text' name='start_filter' value='<c:out value="${usersPageBean.startFilter}"></c:out>'/></td>
				</tr>
			</table>
			<br>
			<input type='hidden' name='operation' id='operation' value=''/>
			<input type='submit' value='Назад' onclick='document.getElementById("operation").value="PREVIOUS"'/>
			<input type='submit' value='иди на' onclick='document.getElementById("operation").value="GO"'/>
			<input type='submit' value='напријед' onclick='document.getElementById("operation").value="NEXT"'/>
		</form>
		<br>
		<c:if test="${usersPageBean.pageNo gt 0}">
			<sql:query dataSource = "${connection}" var = "result">
	            SELECT username, firstname, secondname, emailaddress, telephone, city, country FROM ip.userinfo 
	            WHERE username LIKE ? ORDER BY username ASC
	            LIMIT <c:out value='${usersPageBean.pageSize}'></c:out>
	            OFFSET <c:out value='${(usersPageBean.pageNo-1)*usersPageBean.pageSize}'></c:out>
	            <sql:param value='${usersPageBean.startFilter}%'></sql:param>
	        </sql:query>
	        
	        <table class='viewport_table simpletable'>
	        	<thead class='simpletable'>	
	        		<tr class='simpletable' align='left' >
	        			<th class='simpletable'>Корисничко име </th>
	        			<th class='simpletable'>име </th>
	        			<th class='simpletable'>презиме </th>
	        			<th class='simpletable'>електронска пошта </th>
	        			<th class='simpletable'>телефон </th>
	        			<th class='simpletable'>град </th>
	        			<th class='simpletable'>држава </th>
	        		</tr>
	        	</thead>
	        	<tbody class='simpletable'>
			        <c:forEach var = "row" items = "${result.rows}">
		         		<tr class='simpletable'>
		         			<td class='simpletable'><c:out value="${row.username}"></c:out></td>
		         			<td class='simpletable'><c:out value="${row.firstname}"></c:out></td>
		         			<td class='simpletable'><c:out value="${row.secondname}"></c:out></td>
		         			<td class='simpletable'><c:out value="${row.emailaddress}"></c:out></td>
		         			<td class='simpletable'><c:out value="${row.telephone}"></c:out></td>
		         			<td class='simpletable'><c:out value="${row.city}"></c:out></td>
		         			<td class='simpletable'><c:out value="${row.country}"></c:out></td>
		         		</tr>
		         	</c:forEach>
         		</tbody>
         	</table>
        </c:if>
        <br>
        <jsp:include page="/WEB-INF/app/linx.jsp"></jsp:include>
	</body>
</html>
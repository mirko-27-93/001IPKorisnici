<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<jsp:useBean id='userBean'     class='studiranje.ip.bean.UserBean' scope='session'></jsp:useBean>
<jsp:useBean id='userInfoBean' class='studiranje.ip.bean.InformationBean' scope='session'></jsp:useBean>

<jsp:useBean id='sessionPageBean' class='studiranje.ip.bean.SessionPageBean' scope='session'></jsp:useBean>
<c:out value='${sessionPageBean.reloadInfo(sessionScope["status.logged"], param["operation"], param["page_no"], param["page_size"], param["start_filter"])}'></c:out>

<h3 id='ACTIVE_SESSIONS'>Активне корисничке сесије</h3>
<form name='pregled_sesija_manervar' method='POST' action='#ACTIVE_SESSIONS'>
	<table>
		<tr>
			<td>Страница бр.  : </td>
			<td><input type='text' name='page_no' id='page_no' value='${sessionPageBean.pageNo}'></td>
		</tr>
		<tr>
			<td>Величина стр. : </td>
			<td><input type='text' name='page_size' id='page_size' value='${sessionPageBean.pageSize}'></td>
		</tr>
		<tr>
			<td>Бр. ставки    : </td>
			<td><input type='text' name='total_count' value='${sessionPageBean.totalCount}' readonly></td>
		</tr>
		<tr>
			<td>Бр. страница  : </td>
			<td><input type='text' name='num_pages' value='${sessionPageBean.pageCount()}' readonly></td>
		</tr>
		<tr>
			<td>Ставки на стр.: </td>
			<td><input type='text' name='page_count' value='${sessionPageBean.itemsInPage()}' readonly></td>
		</tr>
		<tr>
			<td>Индекс почетка : </td>
			<td><input type='text' name='index_item' value='${sessionPageBean.indexStartOfPage()}' readonly></td>
		</tr>
		<tr>
			<td>Филтер - корисничко име : </td>
			<td><input type='text' name='start_filter' value='${sessionPageBean.startFilter}'/></td>
		</tr>
	</table>
	<br>
	<input type='hidden' name='operation' id='operation' value=''/>
	<input type='submit' value='Назад' onclick='document.getElementById("operation").value="PREVIOUS"'/>
	<input type='submit' value='иди на' onclick='document.getElementById("operation").value="GO"'/>
	<input type='submit' value='напријед' onclick='document.getElementById("operation").value="NEXT"'/>
</form>

<br>

<form name='operacije_tabele_sesija' method='POST' action='${pageContext.request.contextPath}/SessionManervarServlet'>
	<input type='hidden' name='operation_mode' id='operation_mode' value=''/>
	<input type='hidden' name='logout_sid' id='logout_sid' value=''/>
	<input type='hidden' name='chossed_sid' id='chossed_sid' value=''/>
	<div id='sessions_table'></div><br>
	<script>ajax_session_list_generate('${pageContext.request.contextPath}','${sessionScope["status.logged"]}')</script>
	<div id='session_info_place'></div>
	<input type='submit' name='logout_all'    value='Одјава са свих сесија' onclick='logout_all_prepare()'/>
	<input type='submit' name='logout_chossed' value='одјава са изабраних сесија' onclick='logout_chossed_prepare()'/>
	<input type='submit' name='logout_selected' value='одјава са прегледане сесије' onclick='logout_selected_prepare()'/>
	<input type='submit' name='logout_current' value='одјава са текуће сесије' onclick='logout_current_prepare()'/>
	<input type='button' name='logout_chosed' value='поништење прегледа' onclick='clear_session_info()'/>
</form>

<br>
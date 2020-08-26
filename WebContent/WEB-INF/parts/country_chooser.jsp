<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean id='userInfoBean' class='studiranje.ip.bean.InformationBean' scope='session'></jsp:useBean>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var='userInfoBean.annotation' scope='session' value='COUNTRY_CHOOSER'></c:set>

<h3 id='CC_H'>Избор државе корисика.</h3>
<div> &nbsp;Доступне државе:</div>
<form name='moguce_drzave'>
	<div id='country_map_page'>
		<table>
				<tr>
					<td>Страница бр.  : </td>
					<td><input type='text' name='page_no' value='1' id='no'></td>
				</tr>
				<tr>
					<td>Величина стр. : </td>
					<td><input type='text' name='page_size' value='10' id='size'></td>
				</tr>
				<tr>
					<td>Бр. ставки    : </td>
					<td><input type='text' name='total_count' value='0' id='total' readonly></td>
				</tr>
				<tr>
					<td>Бр. страница  : </td>
					<td><input type='text' name='num_pages' value='0' id='count' readonly></td>
				</tr>
				<tr>
					<td>Ставки на стр.: </td>
					<td><input type='text' name='page_count' value='0' id='view' readonly></td>
				</tr>
				<tr>
					<td>Индекс почетка : </td>
					<td><input type='text' name='index_item' value='0' id='index' readonly></td>
				</tr>
				<tr>
					<td>Филтер - корисничко име : </td>
					<td><input type='text' name='start_filter' value='' id='filter'></td>
				</tr>
			</table>
			<br>
			<input type='hidden' name='operation' id='command' value=''/>
			<input type='button' value='Назад' onclick='document.getElementById("command").value="PREVIOUS"; ajax_load_country_previous_page_table("${pageContext.request.contextPath}")' />
			<input type='button' value='иди на' onclick='document.getElementById("command").value="CURRENT"; ajax_load_country_current_page_table("${pageContext.request.contextPath}")'/>
			<input type='button' value='напријед' onclick='document.getElementById("command").value="NEXT";  ajax_load_country_next_page_table("${pageContext.request.contextPath}")'/>
	</div>
	<div id='country_map'>
	</div>
	<script>ajax_load_country_current_page_table('${pageContext.request.contextPath}');</script>
</form>
<br>
<table>
	<tr>
		<td valign='top'>Постојеће : </td>
		<td valign='top'>
			<div id='current_country_data'></div>
			<div id='current_country_flag'></div>
		</td>
	</tr>
	<tr>
		<td valign='top'>Новоизабрано : </td>
		<td valign='top'>
			<div id='choosed_country_data'></div>
			<div id='choosed_country_flag'></div>
		</td>
	</tr>
</table>
<br>
<form name='izbor_drzave' method='POST'>
	<input type='hidden' name='a3c' value=''/>
	<input type='hidden' name='operation' value='SYNCHRONIZATION'/>
	<input type='submit' name='synchron' value='Тик синхронизације сервиса' formmethod='POST' formaction='${pageContext.request.contextPath}/CountryOperationServlet#CC_H'/>
	<input type='submit' name='ok'  value='потврда' formmethod='POST' formaction='${pageContext.request.contextPath}/CountryUserApplyServlet#CC_H'/>
	<input type='submit' name='del' value='поништавање' formmethod='POST' formaction='${pageContext.request.contextPath}/CountryUserApplyServlet#CC_H' onclick='clear_user_country()'/>
</form>
<br>
<script>ajax_view_user_country('${pageContext.request.contextPath}');</script>
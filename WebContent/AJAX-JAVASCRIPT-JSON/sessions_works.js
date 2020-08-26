"UTF-8"

/**
 * АЈАКС и операције и акције, те функционалности везане за JSF. 
 */

function htmlEscape(html){
	html = html.replace(/&/g, '&amp;');
	html = html.replace(/</g, '&gt;');
	html = html.replace(/</g, '&lt;');
	html = html.replace(/"/g, '&qute;');
	html = html.replace(/'/g, '&apos;');
	html = html.replace(/[ ]/g,'&nbsp;');
	return html; 
}

function logout_from(session_id){
	document.getElementById('logout_sid').value=session_id;
	document.getElementById('operation_mode').value="LOGOUT_SESSION";
	document.forms['operacije_tabele_sesija'].submit();
}

function logout_all_prepare(){
	document.getElementById('operation_mode').value="LOGOUT_ALL";
}

function logout_chossed_prepare(){
	document.getElementById('operation_mode').value="LOGOUT_SELECTED";
}

function logout_selected_prepare(){
	document.getElementById('operation_mode').value="LOGOUT_SESSION";
	document.getElementById('logout_sid').value=document.getElementById('chossed_sid').value; 
}

function logout_current_prepare(){
	document.getElementById('operation_mode').value="LOGOUT";
}

function clear_session_info(){
	document.getElementById('session_info_place').innerHTML="";
	document.getElementById('chossed_sid').value=""; 
	document.getElementById('logout_sid').value=""; 
}

function ajax_session_info_generate(app_path, sid){
	var xmlHttp = new XMLHttpRequest();
	xmlHttp.onreadystatechange = function(){
        if(xmlHttp.readyState === 4 && xmlHttp.status === 200){
			var result = JSON.parse(this.response); 
			var place = document.getElementById('session_info_place');
			var html = "<table>"
					 + "  <tr>"
					 + "    <td>Сесијски назив : </td>"
					 + "    <td><font face='YI Courier New'>"+htmlEscape(sid)+"</font></td>"
					 + "  </tr>"
					 + "  <tr>"
					 + "    <td>Основни назив : </td>"
					 + "    <td><font face='YI Courier New'>"+htmlEscape(result["basic_id"])+"</font></td>"
					 + "  </tr>"
					 + "  <tr>"
					 + "    <td>Тип апликације : </td>"
					 + "    <td><font face='YI Courier New'>"+htmlEscape(result["app_type"])+"</font></td>"
					 + "  </tr>"
				     + "</table><br><br>"
					 + "<table>"
					 + "  <tr>"
					 + "    <td align='center'>КОРИСНИЧКИ ОПИС СЕСИЈЕ</td>"
					 + "    <td align='center'>КОРИСНИКОВИ ПОДАЦИ О СЕСИЈИ</td>"
					 + "  </tr>"
					 + "  <tr>"
					 + "    <td><font face='YI Courier New'><textarea cols='50' rows='15' readonly>"+htmlEscape(result["description"])+"</textarea></font></td>"
					 + "    <td><font face='YI Courier New'><textarea cols='50' rows='15' readonly>"+htmlEscape(result["data"])+"</textarea></font></td>"
					 + "  </tr>"
					 + "</table><br><br>"
					 + "<table>" 
					 + "  <tr>"
					 + "    <td>Системски маркер : </td>"
					 + "    <td><font face='YI Courier New'>"+htmlEscape(result["system_id"])+"</font></td>"
					 + "  </tr>"
					 + "  <tr>"
					 + "    <td>Маркер платформе : </td>"
					 + "    <td><font face='YI Courier New'>"+htmlEscape(result["platform_id"])+"</font></td>"
					 + "  </tr>"
					 + "  <tr>"
					 + "    <td>Апликациони маркер : </td>"
					 + "    <td><font face='YI Courier New'>"+htmlEscape(result["application_id"])+"</font></td>"
					 + "  </tr>"
					 + "  <tr>"
					 + "    <td>Кориснички маркер : </td>"
					 + "    <td><font face='YI Courier New'>"+htmlEscape(result["user_id"])+"</font></td>"
					 + "  </tr>"
					 + "  <tr>"
					 + "    <td>Маркер одељења : </td>"
					 + "    <td><font face='YI Courier New'>"+htmlEscape(result["part_id"])+"</font></td>"
					 + "  </tr>"
				     + "</table><br><br>";
			place.innerHTML = html; 
			document.getElementById('chossed_sid').value=htmlEscape(sid); 
		}
	}
	
	xmlHttp.open("POST", app_path+"/SessionGetInfoServlet", true); 
	xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded"); 
	xmlHttp.send("session="+encodeURI(sid));
}

function ajax_session_list_generate(app_path, username){
	var xmlHttp = new XMLHttpRequest();
	xmlHttp.onreadystatechange = function(){
        if(xmlHttp.readyState === 4 && xmlHttp.status === 200){
			var result = JSON.parse(this.response); 
			var place = document.getElementById('sessions_table');
			var html  = "<table class='simpletable'><thead class='simpletable'>"
					  + "  <tr class='simpletable'>"
					  + "     <th class='simpletable'>Сесија</th>"
					  + "     <th class='simpletable'>описивање</th>"
					  + "     <th class='simpletable'>одјава</th>"
					  + "     <th class='simpletable'>избор</th>"
					  + "  </tr></thead><tbody class='simpletable'>"; 
			
			for(var i=0; i<result.sessions.length; i++){
				var ajax_call   = "ajax_session_info_generate('"+encodeURI(app_path)+"','"+htmlEscape(result.sessions[i])+"')";
				var func_logout = "logout_from('"+result.sessions[i]+"')";
				html  = html + "<tr class='simpletable'>"
					  + "     <td class='simpletable'>"+htmlEscape(result.sessions[i])+"</td>"
					  + "     <td align='center' valign='middle' class='simpletable'><input type='button'   name='btn_review_"+htmlEscape(result.sessions[i])+"' class='menu' onclick=\""+ajax_call+"\"/></td>"
					  + "     <td align='center' valign='middle' class='simpletable'><input type='button'   name='btn_logout_"+htmlEscape(result.sessions[i])+"' class='exit' onclick=\""+func_logout+"\"/></td>"
					  + "     <td align='center' valign='middle' class='simpletable'><input type='checkbox' name='chcek_choose_"+htmlEscape(result.sessions[i])+"'/></td>"
				      + "</tr>"; 
			}
			
			html += "</tbody></table>";
			place.innerHTML = html;  
		}
	}
	
	var page_no = document.getElementById('page_no').value;
	var page_size = document.getElementById('page_size').value; 
	
	if(page_no === null) return; 
	if(page_size === null) return; 
	
	xmlHttp.open("POST", app_path+"/SessionGetListServlet", true); 
	xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded"); 
	xmlHttp.send("page_no="+encodeURI(page_no)+"&page_size="+encodeURI(page_size));
}




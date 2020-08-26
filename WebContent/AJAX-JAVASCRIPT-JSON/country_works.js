"UTF-8"

/**
 * Функционалност за управљање државама, углавном AJAX. 
 */

function clear_user_country(){
	var paramEl = document.forms["izbor_drzave"]["a3c"];
	paramEl.value='';
}

function set_user_country(a3c){
	var paramEl = document.forms["izbor_drzave"]["a3c"];
	paramEl.value=encodeURI(a3c);
}

function htmlEscape(html){
	html = html.replace(/&/g, '&amp;');
	html = html.replace(/</g, '&gt;');
	html = html.replace(/</g, '&lt;');
	html = html.replace(/"/g, '&qute;');
	html = html.replace(/'/g, '&apos;');
	html = html.replace(/[ ]/g,'&nbsp;');
	return html; 
}

function ajax_choose_country(country_id, app_path){	
	var xmlHttp = new XMLHttpRequest();
	xmlHttp.onreadystatechange = function(){
        if(xmlHttp.readyState === 4 && xmlHttp.status === 200){
			var cDataEl = document.getElementById('choosed_country_data');
			var cFlagEl = document.getElementById('choosed_country_flag'); 
			var result = JSON.parse(xmlHttp.responseText); 
			var ccs = "";
			var tlds = "";
			
			for(var i=0; i<result.ccs.length; i++)
				ccs += htmlEscape(result.ccs[i])+" ";
			
			for(var i=0; i<result.tlds.length; i++)
				tlds += htmlEscape(result.tlds[i])+" ";
			
			cDataEl.innerHTML = ""
			+"<table>"
			+" </tr>"
			+"  <td valign='top'><font face='YI Courier New'>"
			+"     &nbsp;"
			+"  </font></td>"
			+" </tr>"
			+" <tr>"
			+"  <td valign='top'><font face='YI Courier New'>"
			+     htmlEscape(result.name)
			+"  </font></td>"
			+" </tr>"
			+" <tr>"
			+"  <td valign='top'><font face='YI Courier New'>"
			+     htmlEscape(result.a2c)+" "+htmlEscape(result.a3c)
			+"  </font></td>"
			+" </tr>"
			+" <tr>"
			+"  <td valign='top'><font face='YI Courier New'>"
			+     ccs
			+"  </font></td>"
			+" </tr>"
			+" <tr>"
			+"  <td valign='top'><font face='YI Courier New'>"
			+     tlds
			+"  </font></td>"
			+" </tr>"
			+"  <td valign='top'><font face='YI Courier New'>"
			+"     &nbsp;"
			+"  </font></td>"
			+" </tr>"
			+"</table>";
			
			cFlagEl.innerHTML='<img style="background-color: darkgray; border: 1px solid black;  padding-left:50px; padding-right:50px; padding-top: 10px; padding-bottom:10px" src="'+app_path+'/CountryFlagServlet?a3c='+encodeURI(country_id)+'" alt="" onerror="this.style.display:none"/>';
			set_user_country(result.a3c);
		}
	}
	xmlHttp.open("POST", app_path+"/CountryInfoServlet", true); 
	xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded"); 
	xmlHttp.send("a3c="+encodeURI(country_id));
}


function ajax_view_user_country(app_path){
	var xmlHttp = new XMLHttpRequest();
	xmlHttp.onreadystatechange = function(){
        if(xmlHttp.readyState === 4 && xmlHttp.status === 200){
			var cDataEl = document.getElementById('current_country_data');
			var cFlagEl = document.getElementById('current_country_flag'); 
			var result = JSON.parse(xmlHttp.responseText); 
			var ccs = "";
			var tlds = "";
			
			if(typeof result.personal.extended.country['country'] === "undefined") return; 
			
			for(var i=0; i<result.personal.extended.country['country_info']['ccs'].length; i++)
				ccs += htmlEscape(result.personal.extended.country['country_info']['ccs'][i])+" ";
			
			for(var i=0; i<result.personal.extended.country['country_info']['tlds'].length; i++)
				tlds += htmlEscape(result.personal.extended.country['country_info']['tlds'][i])+" ";
			
			cDataEl.innerHTML = ""
			+"<table>"
			+" </tr>"
			+"  <td valign='top'><font face='YI Courier New'>"
			+"     &nbsp;"
			+"  </font></td>"
			+" </tr>"
			+" <tr>"
			+"  <td valign='top'><font face='YI Courier New'>"
			+     htmlEscape(result.personal.extended.country['country'])
			+"  </font></td>"
			+" </tr>"
			+" <tr>"
			+"  <td valign='top'><font face='YI Courier New'>"
			+     htmlEscape(result.personal.extended.country['country_info']['a2c'])+" "+htmlEscape(result.personal.extended.country['country_info']['a3c'])
			+"  </font></td>"
			+" </tr>"
			+" <tr>"
			+"  <td valign='top'><font face='YI Courier New'>"
			+     ccs
			+"  </font></td>"
			+" </tr>"
			+" <tr>"
			+"  <td valign='top'><font face='YI Courier New'>"
			+     tlds
			+"  </font></td>"
			+" </tr>"
			+"  <td valign='top'><font face='YI Courier New'>"
			+"     &nbsp;"
			+"  </font></td>"
			+" </tr>"
			+"</table>";
			
			cFlagEl.innerHTML='<img style="background-color: darkgray; border: 1px solid black;  padding-left:50px; padding-right:50px; padding-top: 10px; padding-bottom:10px" src="'
				+app_path+'/CountryFlagServlet?a3c='+encodeURI(result.personal.extended.country['country_info']['a3c'])+'" alt="" onerror="this.style.display:none"/>';
		}
	}
	xmlHttp.open("POST", app_path+"/CountryUserInfoServlet", true); 
	xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded"); 
	xmlHttp.send('detail=full');
}

function ajax_load_country_current_page_table(app_path){
	var xmlHttp = new XMLHttpRequest();
	
	var elemNo = document.getElementById("no");
	var elemSize = document.getElementById("size"); 
	var elemFilter = document.getElementById("filter");
	
    xmlHttp.onreadystatechange = function(){
        if(xmlHttp.readyState === 4 && xmlHttp.status === 200){
			var result = JSON.parse(xmlHttp.responseText); 
			var element = document.getElementById("country_map");
			if(result.page.no===0) return;
			
			var html = ""
			+ "<table class='simpletable'>"
			+ "  <thead class='simpletable'>"
			+ "		<tr class='simpletable'>"
			+ "			<th class='simpletable'>Назив"
			+ "			</th>"
			+ "			<th class='simpletable'>Код А2"
			+ "			</th>"
			+ "			<th class='simpletable'>Код А3"
			+ "			</th>"
			+ "         <th class='simpletable'>Селекција"
			+ "			</th>"
			+ "		</tr>"
			+ "  </thead>"
			+ "  <tbody class='simpletable'>";
			
			for(var i=0; i<result.list.length; i++){
				html+= ""
				+"		<tr class='simpletable'>"
				+ "			<td class='simpletable'>"+htmlEscape(result.list[i].name)
				+ "			</td>"
				+ "			<td class='simpletable'>"+htmlEscape(result.list[i].a2c)
				+ "			</td>"
				+ "			<td class='simpletable'>"+htmlEscape(result.list[i].a3c)
				+ "			</td>"
				+ "			<td class='simpletable'><input type='button' value='"+htmlEscape(result.list[i].name)+"' style='width:100%' onclick='ajax_choose_country(\""+htmlEscape(result.list[i].a3c)+"\",\""+app_path+"\")'/>" 
				+ "			</td>"
				+ "		</tr>";
			}
			
			html+= ""
			+ "  </body>"
			+ "</table>";
			element.innerHTML = html;
			
			var elementNo = document.getElementById("no");
			var elementSize = document.getElementById("size"); 
			var elementFilter = document.getElementById("filter");
			
			elementNo.value     =result.page.no;
			elementSize.value   =result.page.size; 
			elementFilter.value =result.page.filter;
			
			var elementTotal = document.getElementById("total");
			var elementCount = document.getElementById("count"); 
			var elementView  = document.getElementById("view");
			
			elementTotal.value  =result.page.total;
			elementCount.value =result.page.count; 
			elementView.value =result.page.view;
        }
    }
	
	xmlHttp.open("POST", app_path+"/CountryDatapageServlet", true); 
	xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded"); 
	xmlHttp.send("command=CURRENT&no="+encodeURI(elemNo.value)+"&size="+encodeURI(elemSize.value)+"&filter="+encodeURI(elemFilter.value));
}

function ajax_load_country_previous_page_table(app_path){
	var xmlHttp = new XMLHttpRequest();
	
	var elemNo = document.getElementById("no");
	var elemSize = document.getElementById("size"); 
	var elemFilter = document.getElementById("filter");
	
    xmlHttp.onreadystatechange = function(){
        if(xmlHttp.readyState === 4 && xmlHttp.status === 200){
			var result = JSON.parse(xmlHttp.responseText); 
			var element = document.getElementById("country_map");
			if(result.page.no===0) return;
			var html = ""
			+ "<table class='simpletable'>"
			+ "  <thead class='simpletable'>"
			+ "		<tr class='simpletable'>"
			+ "			<th class='simpletable'>Назив"
			+ "			</th>"
			+ "			<th class='simpletable'>Код А2"
			+ "			</th>"
			+ "			<th class='simpletable'>Код А3"
			+ "			</th>"
			+ "         <th class='simpletable'>Селекција"
			+ "			</th>"
			+ "		</tr>"
			+ "  </thead>"
			+ "  <tbody class='simpletable'>";
			
			for(var i=0; i<result.list.length; i++){
				html+= ""
				+"		<tr class='simpletable'>"
				+ "			<td class='simpletable'>"+htmlEscape(result.list[i].name)
				+ "			</td>"
				+ "			<td class='simpletable'>"+htmlEscape(result.list[i].a2c)
				+ "			</td>"
				+ "			<td class='simpletable'>"+htmlEscape(result.list[i].a3c)
				+ "			</td>"
				+ "			<td class='simpletable'><input type='button' value='"+htmlEscape(result.list[i].name)+"' style='width:100%' onclick='ajax_choose_country(\""+htmlEscape(result.list[i].a3c)+"\",\""+app_path+"\")'/>"; 
				+ "			</td>"
				+ "		</tr>";
			}
			
			html+= ""
			+ "  </body>"
			+ "</table>";
			element.innerHTML = html;
			
			var elementNo = document.getElementById("no");
			var elementSize = document.getElementById("size"); 
			var elementFilter = document.getElementById("filter");
			
			elementNo.value     =result.page.no;
			elementSize.value   =result.page.size; 
			elementFilter.value =result.page.filter;
			
			var elementTotal = document.getElementById("total");
			var elementCount = document.getElementById("count"); 
			var elementView  = document.getElementById("view");
			
			elementTotal.value  =result.page.total;
			elementCount.value =result.page.count; 
			elementView.value =result.page.view;
        }
    }
	
	xmlHttp.open("POST", app_path+"/CountryDatapageServlet", true); 
	xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded"); 
	xmlHttp.send("command=PREVIOUS&no="+encodeURI(elemNo.value)+"&size="+encodeURI(elemSize.value)+"&filter="+encodeURI(elemFilter.value));
}

function ajax_load_country_next_page_table(app_path){
	var xmlHttp = new XMLHttpRequest();
	
	var elemNo = document.getElementById("no");
	var elemSize = document.getElementById("size"); 
	var elemFilter = document.getElementById("filter");
	
    xmlHttp.onreadystatechange = function(){
        if(xmlHttp.readyState === 4 && xmlHttp.status === 200){
			var result = JSON.parse(xmlHttp.responseText); 
			var element = document.getElementById("country_map");
			if(result.page.no===0) return;
			
			var html = ""
			+ "<table class='simpletable'>"
			+ "  <thead class='simpletable'>"
			+ "		<tr class='simpletable'>"
			+ "			<th class='simpletable'>Назив"
			+ "			</th>"
			+ "			<th class='simpletable'>Код А2"
			+ "			</th>"
			+ "			<th class='simpletable'>Код А3"
			+ "			</th>"
			+ "         <th class='simpletable'>Селекција"
			+ "			</th>"
			+ "		</tr>"
			+ "  </thead>"
			+ "  <tbody class='simpletable'>";

			for(var i=0; i<result.list.length; i++){
				html+= ""
				+"		<tr class='simpletable'>"
				+ "			<td class='simpletable'>"+htmlEscape(result.list[i].name)
				+ "			</td>"
				+ "			<td class='simpletable'>"+htmlEscape(result.list[i].a2c)
				+ "			</td>"
				+ "			<td class='simpletable'>"+htmlEscape(result.list[i].a3c)
				+ "			</td>"
				+ "			<td class='simpletable'><input type='button' value='"+htmlEscape(result.list[i].name)+"' style='width:100%' onclick='ajax_choose_country(\""+htmlEscape(result.list[i].a3c)+"\",\""+app_path+"\")'/>"; 
				+ "			</td>"
				+ "		</tr>";
			}
			
			html+= ""
			+ "  </body>"
			+ "</table>";
			element.innerHTML = html;
			
			var elementNo = document.getElementById("no");
			var elementSize = document.getElementById("size"); 
			var elementFilter = document.getElementById("filter");
			
			elementNo.value     =result.page.no;
			elementSize.value   =result.page.size; 
			elementFilter.value =result.page.filter;
			
			var elementTotal = document.getElementById("total");
			var elementCount = document.getElementById("count"); 
			var elementView  = document.getElementById("view");
			
			elementTotal.value  =result.page.total;
			elementCount.value =result.page.count; 
			elementView.value =result.page.view;
        }
    }
	
	xmlHttp.open("POST", app_path+"/CountryDatapageServlet", true); 
	xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded"); 
	xmlHttp.send("command=NEXT&no="+encodeURI(elemNo.value)+"&size="+encodeURI(elemSize.value)+"&filter="+encodeURI(elemFilter.value));
}
"UTF-8"
/**
 * Уклањање поруке обавјештења са странице. 
 */

function user_message_clear(app_path){
	var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function(){
        if(xmlHttp.readyState === 4 && xmlHttp.status === 200){
			var result = JSON.parse(xmlHttp.responseText); 
			if(result.id==="DELETE_MESSAGE"){
				document.getElementById('message_box').innerHTML="";	
			}
        }
    }
	
	xmlHttp.open("POST", app_path+"/ajax.jsp", true); 
	xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded"); 
	xmlHttp.send("activity=DELETE_MESSAGE"); 
}
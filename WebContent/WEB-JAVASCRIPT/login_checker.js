"UTF-8"
/**
 * Клијентске фронтенд, провјере потврде лозинке за даљу регистрацију, 
 * уз обавјештење ако је неправилна, тј. ако се лозинке не слажу. 
 */

function checkPasswords(pass1, pass2){
	if(pass1!==pass2) alert('Лозинке се не слажу.');
	return pass1===pass2
}

function defaultRegisterCheck(event){
	var command = document.forms['unos_korisnika']['operation'].value; 
	if(command!=="REGISTER") return true;
	var p1=document.getElementById('password1').value; 
	var p2=document.getElementById('password2').value;
	return checkPasswords(p1,p2);
}


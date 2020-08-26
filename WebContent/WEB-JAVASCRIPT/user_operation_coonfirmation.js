"UTF-8"
/**
 * Превентивне клијентске провјере за брисање и измјене података за кориснике. 
 */

function checkPasswords(pass1, pass2){
	if(pass1!==pass2) alert('Лозинке се не слажу.');
	return pass1===pass2
}

function defaultRegisterCheck(event){
	var command = document.forms['izmjena_podataka_korisnika']['operation'].value; 
	if(command!=="UPDATE") return true;
	var p1=document.getElementById('password1').value; 
	var p2=document.getElementById('password2').value;
	return checkPasswords(p1,p2);
}

function check_and_confirm_erase_and_delete(event){
	var command = document.forms['izmjena_podataka_korisnika']['operation'].value; 
	if(command=='DELETE') return confirm("Да ли заиста желите обрисати корисника ?");
	if(command=='UPDATE') return confirm("Да ли заиста желите измјенити податке корисника ?")
							  && defaultRegisterCheck(event);
	return true; 
}
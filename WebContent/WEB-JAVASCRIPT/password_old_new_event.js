"UTF-8"
/**
 * Аутоматско копирање при куцању старе шифре на нову шифру. За потребе измјена. 
 */

function copy_old_password_to_neo_fields(){
	var old_text_box_1 = document.getElementById("old_password1");
	var neo_text_box_1 = document.getElementById("password1");
	var neo_text_box_2 = document.getElementById("password2");
	
	neo_text_box_1.value = old_text_box_1.value; 
	neo_text_box_2.value = old_text_box_1.value; 
}
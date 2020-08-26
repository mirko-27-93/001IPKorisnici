"UTF-8"
/**
 * Функционалности за постављање описа у елементе. 
 * Опрез, постављње се односи на display : none, block својства, 
 * тако да ако се за ово својство има нека друга вриједност, 
 * не би требало да се користе ове функционалности. 
 */

function descript_flip_show(element_id, description_id){
	document.getElementById(element_id).style.display     = 'block'; 
	document.getElementById(description_id).style.display = 'block'; 
}

function descript_flip_hide(element_id, description_id){
	document.getElementById(element_id).style.display     = 'none'; 
	document.getElementById(description_id).style.display = 'block'; 
}

function descript_flop_show(element_id, description_id){
	document.getElementById(element_id).style.display     = 'block';
	document.getElementById(description_id).style.display = 'none'; 
}

function descript_flop_hide(element_id, description_id){
	document.getElementById(element_id).style.display     = 'none';
	document.getElementById(description_id).style.display = 'none'; 
}

function descript_flipflop_show(element_id, description_id){
	var state = document.getElementById(description_id).style.display; 
	if(state==='none') descript_flip_show(element_id, description_id);
	else			   descript_flop_show(element_id, description_id);
}
"UTF-8"

function initDragAndDropFileChoose(dropContainerId, fileInputId){
	var dropContainerObj = document.getElementById(dropContainerId); 
	var fileInputObj = document.getElementById(fileInputId);
	dropContainerObj.ondragover = dropContainerObj.ondragenter =
		function (evt) {
		  evt.preventDefault();
		};
	dropContainerObj.ondrop = 
		function (evt) {
		  fileInputObj.files = evt.dataTransfer.files;
		  evt.preventDefault();
		  var res = "<table class='simpletable'><thead>"; 
		  res+="<th class='simpletable'>Назив</th>";
		  res+="<th class='simpletable'>МИМЕ Тип</th>";
		  res+="<th class='simpletable'>Величина</th>";
		  dropContainerObj.innerHTML+="</thead>";
		  for (var i = 0; i < fileInputObj.files.length; i++) {
			  res+= "<tr class='simpletable'>";
			  res+="<td class='simpletable'>"+fileInputObj.files[i].name+"</td>";
			  res+="<td class='simpletable'>"+fileInputObj.files[i].type+"</td>";
			  res+="<td class='simpletable'>"+fileInputObj.files[i].size+"</td>";
			  res+= "</tr>";
		  }
		  res+= "</table>";
		  dropContainerObj.innerHTML=res;
		};
}



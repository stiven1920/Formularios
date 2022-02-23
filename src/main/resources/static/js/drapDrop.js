var vcaja2 = document.getElementById("caja2");
var vcaja3 = document.getElementById("caja3");
var vcaja4 = document.getElementById("caja4");
var iru = document.getElementById("ru");
var ird = document.getElementById("rd");
var irt = document.getElementById("rt");
var opciones = document.getElementsByClassName("opcion");

for(let i=0; i<opciones.length; i++){
	opciones[i].setAttribute("draggable","true");
	//opciones[i].setAttribute("id","opcion"+i);
	opciones[i].addEventListener("dragstart",(ev) => iniciarArrastre(ev));
}

function iniciarArrastre(ev){
	ev.dataTransfer.setData("idopcion", ev.target.id);
	console.log(ev.target.id);
}

vcaja2.addEventListener("dragover", (ev) => permitirSoltarc2(ev));
vcaja2.addEventListener("drop", (ev) => soltarc2(ev));
vcaja3.addEventListener("dragover", (ev) => permitirSoltarc3(ev));
vcaja3.addEventListener("drop", (ev) => soltarc3(ev));
vcaja4.addEventListener("dragover", (ev) => permitirSoltarc4(ev));
vcaja4.addEventListener("drop", (ev) => soltarc4(ev));

function permitirSoltarc2(ev){
	console.log("arrastrando 2");
	ev.preventDefault();
}

function soltarc2(ev){
	ev.preventDefault();
	var data = ev.dataTransfer.getData("idopcion");
	vcaja2.appendChild(document.getElementById(data));
	ru.value = ev.dataTransfer.getData("idopcion");
	console.log("i " +ev.dataTransfer.getData("idopcion"));
}

function permitirSoltarc3(ev){
	console.log("arrastrando 3");
	ev.preventDefault();
}

function soltarc3(ev){
	ev.preventDefault();
	var data = ev.dataTransfer.getData("idopcion");
	vcaja3.appendChild(document.getElementById(data));
	rd.value = ev.dataTransfer.getData("idopcion");
	console.log("i " +ev.dataTransfer.getData("idopcion"));
}

function permitirSoltarc4(ev){
	console.log("arrastrando ");
	ev.preventDefault();
}

function soltarc4(ev){
	ev.preventDefault();
	var data = ev.dataTransfer.getData("idopcion");
	vcaja4.appendChild(document.getElementById(data));
	rt.value = ev.dataTransfer.getData("idopcion");
	console.log("i " +ev.dataTransfer.getData("idopcion"));
}


var data = new FormData();

var imgu = document.getElementById('ru');
	imgu.onchange = function (e) {
	var file = $('#ru').prop('files');
	readImage(file, 'ru', 'imcargar');
}

var imgd = document.getElementById('rd');
	imgd.onchange = function (e) {
	var file = $('#rd').prop('files');
	readImage(file, 'rd', 'imcargar');
}

var imgt = document.getElementById('rt');
	imgt.onchange = function (e) {
	var file = $('#rt').prop('files');
	readImage(file, 'rt', 'imcargar');
}

function readImage(input, name, mostrar) {
	data.append(name, input[0]);
	var reader = new FileReader();
	reader.onload = function (e) {
		/*
	var img = document.getElementById(mostrar);
	img.src= e.target.result;
	img.style.width = "12rem";
	img.style.heigth= "auto";
	*/
	}
	reader.readAsDataURL(input[0]);
}
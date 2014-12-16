var canvasID = "libraries-stat",
	gitChart = null,
	lineColors = ["#C8001A", "#2F3871", "#FF5022", "#FFAF00", "#5083F1", "#002157", "#0076A3", "#BD8CBF", "#603913", "#C69C6D"]

function makeChart(repositories){
	'use strict'

	var data = [],
		maxIncrement = 0;
	repositories.forEach(function(el, idx){
		var line = {
			label: el['name'],
			data: el['incrs'],
			url: el['url'],
			strokeColor: lineColors[idx],
			pointColor: lineColors[idx],
			fillColor: "rgba(151,187,205,0.05)",
			pointStrokeColor: "#fff",
			pointHighlightFill: "#fff",
			pointHighlightStroke: "rgba(151,187,205,1)",
		};
		maxIncrement = Math.max(Math.max.apply(null, el['incrs']), maxIncrement);
		data.push(line);
	});

	var legendStr = "<ul class=\"line-legend\">";
	
	for (var i=0; i<data.length; i++){
		legendStr += "<li><span style=\"background-color:" + data[i].strokeColor + "\">&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;";
		legendStr += "<a href=\"" + data[i].url + "\">" + data[i].label + "</a>";
		legendStr += "<div class=\"weights\">" + Math.round(repositories[i].cplx) + "</div>";
		legendStr += "<div class=\"weights\">" + Math.round(repositories[i].diffw) + "</div>";
		legendStr += "<div class=\"weights\">" + Math.round(repositories[i].w) + "</div>";
		legendStr += "<div class=\"weights\">" + Math.round(repositories[i].sum) + "</div>";
		legendStr += "</li>";
	}
	legendStr += "</ul>";

	document.getElementById(canvasID).parentNode.innerHTML += legendStr;

	var steps = Math.ceil(maxIncrement/20),
		gitChart = new Chart(document.getElementById(canvasID).getContext("2d")).Line(
			// data
			{
				labels: ["29.11.14", "30.11.14", "1.12.14", "2.12.14", "3.12.14", "4.12.14", "5.12.14"],
				datasets: data,
			}, {
				animation: false,
				showTooltips: true,
				scaleOverride: true,
				scaleSteps: steps,
				scaleStepWidth: 20,
				scaleStartValue: null,
				multiTooltipTemplate: "<%= datasetLabel %>: <%= value %>"
			}
		);
}

// LOAD DATA

var xmlhttp = new XMLHttpRequest();
var url = "/api/";

xmlhttp.onreadystatechange = function() {
    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
        var myArr = JSON.parse(xmlhttp.responseText);
        makeChart(myArr);
        document.querySelector('img[src*="spinner"]').remove();
        document.getElementById(canvasID).hidden = false;
    }
}

xmlhttp.open("GET", url, true);
xmlhttp.send();
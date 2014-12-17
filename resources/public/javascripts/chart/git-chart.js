var canvasID = "libraries-stat",
	gitChart = null,
	lineColors = ["#C8001A", "#2F3871", "#FF5022", "#FFAF00", "#5083F1", "#002157", "#0076A3", "#BD8CBF", "#603913", "#C69C6D"],
	columnNames = ['&nbsp;&nbsp;', 'Имя репозитория', 'Активность', 'Коммитов за неделю'];

function makeChart(response){
	'use strict'

	var data = [],
		maxIncrement = 0;
	response.data.forEach(function(el, idx){
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

	var GitLegend = function(colNames, data){
		var self = {};
		self.colNames = colNames;
		self.data = data;

		self.build = function(){
			var out = document.createElement("table"),
				currentRow = null,
				addRow = function(){
					currentRow = document.createElement("tr");
					out.appendChild(currentRow);
				},
				wrap = function(name, node){
					var newNode = document.createElement(name);
					newNode.appendChild(node);
					return newNode;
				};

			out.classList.add("line-legend");

			addRow();

			self.colNames.forEach(function (val){
				var th = document.createElement('th');
				th.innerHTML = val;
				currentRow.appendChild(th);
			});

			self.data.forEach(function(val, idx){
				addRow();
				var color = document.createElement('div'),
					name = document.createElement('td'),
					weight = document.createElement('td'),
					increment = document.createElement('td');

				color.style = "background-color:" + val.strokeColor;
				name.innerHTML = '<a href="' + val.url + '">' + val.label + '</a>';
				weight.innerHTML = Math.round(response.data[idx].w);
				increment.innerHTML = Math.round(response.data[idx].sum);

				color.classList.add('lineColor');
				weight.classList.add('number');
				increment.classList.add('number');

				currentRow.appendChild(wrap('td', color));
				currentRow.appendChild(name);
				currentRow.appendChild(weight);
				currentRow.appendChild(increment);
			});

			return out;
		}

		self.buildHTML = function() { return self.build().outerHTML; }

		return self;
	}

	document.getElementById(canvasID).parentNode.appendChild(
		GitLegend(columnNames,data).build()
	);

	var steps = Math.ceil(maxIncrement/20),
		gitChart = new Chart(document.getElementById(canvasID).getContext("2d")).Line(
			// data
			{
				labels: response.dates,
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
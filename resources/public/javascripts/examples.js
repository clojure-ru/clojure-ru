var Examples = {
  names: ['hello_world', 'math'],

  random: function() {
    return Examples.names[Math.floor(Math.random() * Examples.names.length)];
  },

  choose: function() {
    var name = Examples.random();

    $("#code").load('/examples/' + name + '/', function(){

	    $('#code pre code').each(function(i, block) {
		  hljs.highlightBlock(block);
		});
	 
	});
  }
};

$(document).ready(Examples.choose);
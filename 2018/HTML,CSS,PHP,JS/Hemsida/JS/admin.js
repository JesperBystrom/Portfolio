var x = []; //date
var y = []; //amount sold
var max = 4;
var min = 0;
var sample = 5;
window.addEventListener('load',function(){
	var c=document.getElementById("myCanvas");
	var ctx=c.getContext("2d");
	
	var d = new Date();
	
	var dd = d.getDate();
	var mm = d.getMonth()+1;
	var yy = d.getFullYear();
	
	for(var i=0;i<100;i++){
		x[i] = yy + "-0" + mm + "-" + dd;//d.getFullYear() + "-0" + (d.getMonth()-1) + "-" + d.getDay();
		y[i] = Math.random() * 80;
	}
	
	c.width  = window.innerWidth * 0.8;
  	c.height = window.innerHeight;
	
	ctx.fillStyle = "#fff";
	ctx.fillRect(0,c.height-100,c.width,100);
	ctx.fillRect(0,0,100,c.height);
	
	ctx.fillStyle = "green";
	for(var i=sample;i>=0;i--){
		x[i] = yy + "-0" + mm + "-" + dd;
		dd--;
		var index = (sample-i);
		ctx.font = (20-sample)+"px Arial";
		ctx.fillText(x[i],80+(c.width/sample)*(index),c.height-64);
		ctx.fillText(i,90,((c.height/sample)*index)-96);
	}
	ctx.strokeStyle = "green";
	ctx.beginPath();
	ctx.moveTo(100,c.height-100);
	for(var i=0;i<x.length;i++){
		var xp = (110+((c.width-110)/x.length)*(i));
		var yp = (c.height-100)+(c.height/x.length)*(-y[i]);
		if(xp > c.width) {
			log("out of boundaries");
		}
		ctx.lineTo(xp,yp);
		ctx.moveTo(xp,yp);
		ctx.stroke();
	}
	/*ctx.beginPath();
	ctx.moveTo(0,0);
	ctx.lineTo(300,150);
	ctx.moveTo(300,150);
	ctx.lineTo(600,0);
	ctx.stroke();*/
});

window.log = function(str){
	console.log(str);	
}
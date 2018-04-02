window.addEventListener('load',function(){
	var searchIcon = document.getElementById("search");
	var searchHidden = document.getElementById("searchHidden");
	var canHide = false;
	
	document.addEventListener("click", function(){
		if(canHide){
			searchHidden.style.display = "none";
			canHide = false;
		}
	});
	searchIcon.addEventListener("click", function(){
		toggle();
		canHide = false;
		setTimeout(function(){
			canHide = true;
		}, 10);
	});

	function toggle(){
		if(searchHidden.style.display == "block"){
			searchHidden.style.display = "none";
		} else {
			searchHidden.style.display = "block";
		}
	}
});
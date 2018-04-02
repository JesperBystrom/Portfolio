window.addEventListener('load',function(){
	var pcSearchIcon = document.getElementById("pc_search");
	var mobileSearchIcon = document.getElementById("mobile_search");
	var pcSearchHidden = document.getElementById("pc_searchHidden");
	var mobileSearchHidden = document.getElementById("mobile_searchHidden");
	var pcLogin = document.getElementById("pcLogin");
	var canHide = false;
	var menuOpen = false;
	
	pcLogin.addEventListener("click", function(){
		toggle(document.getElementById("loginScreen"));
		//document.getElementById("loginScreen").style.display = "block";
	});
	
	document.addEventListener("click", function(){
		canHide = !canHide;
	});
	pcSearchIcon.addEventListener("click", function(){
		toggle(pcSearchHidden);
		canHide = !canHide;
	});
	mobileSearchIcon.addEventListener("click", function(){
		mobileSearchHidden.style.display = "block";
		//toggle(mobileSearchHidden);
		if(!isMobileMenuOpened()){
			mobileSearchHidden.style.display = "block";
			document.body.style.overflowY = "hidden";
			openMobileMenu();	
		} else {
			mobileSearchHidden.style.display = "hidden";
			document.body.style.overflowX = "hidden";
			document.body.style.overflowY = "visible";
			closeMobileMenu();
		}
		canHide = !canHide;

	});
	
	window.onresize = function(){
		document.body.style.overflowY = "visible";
		closeMobileMenu();
		//closeMobileMenu();
	}
	
	
	function openMobileMenu(){
		menuOpen = true;
		mobileSearchHidden.style.transform = "translateX(-110vw)";
		setTimeout(function(){
			mobileSearchHidden.style.transform = "translateX(0)";
		}, 10);
	}
	
	function closeMobileMenu(){
		menuOpen = false;
		mobileSearchHidden.style.transform = "translateX(-110vw)";
	}
	
	function isMobileMenuOpened(){
		return menuOpen;
	}
	
	function toggle(element){
		if(element.style.display == "block"){
			element.style.display = "none";
		} else {
			element.style.display = "block";
		}
	}
	
	function isVisible(element){
		if(element.style.display == "block"){
			return true;
		}
		return false;
	}
	
	function isMobileLayout(){
		if(window.innerWidth <= "880px"){
			return true;	
		}
		return false;
	}
});
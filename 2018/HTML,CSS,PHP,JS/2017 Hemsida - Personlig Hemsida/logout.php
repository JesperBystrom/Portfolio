<?php
	//Loggar ut en
	session_start();
	$_SESSION["logged_in"] = false;
	
	header("LOCATION: index.php");
?>
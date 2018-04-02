<?php
	session_start();
	//error_reporting(0);
	
?>
<?php
	//Avaktiverar alla sessions för att undvika errors
	$_SESSION['found'] = false;
	$_SESSION['takenUsername'] = false;
	$_SESSION['wrongInput'] = false;
	$_SESSION['file_exists'] = false;
?>
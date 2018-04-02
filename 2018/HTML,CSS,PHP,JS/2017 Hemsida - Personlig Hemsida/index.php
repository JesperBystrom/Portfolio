<?php
	require "sessionDisabler.php";
	if($_SESSION["logged_in"] != true){
		$_SESSION["logged_in"] = false;
	}
	require "connect.php";
	error_reporting(0);
?>
<!doctype html>
<html lang="sv">
	<head>
		<meta charset="utf-8">
		<title>Namnlöst dokument</title>
        <link rel="stylesheet" type="text/css" href="CSS/index.css">
    <?php require "header.php" ?>
       	
       	<div class="bgOverlay"></div>
	</body>
</html>
<?php
	session_start();
?>
<!doctype html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Namnlöst dokument</title>
        
        <style>
			
		</style>
	</head>
	<body>      
    
    	<?php
			//Testar bara sessions
			echo $_SESSION["username"]."<br>";
			echo $_SESSION["id"]."<br>";
			echo $_SESSION["logged_in"]."<br>";
		?>
	</body>
</html>
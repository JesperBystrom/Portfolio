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
        <?php
			require "connect.php";
				$gameIDQuery = mysqli_query($conn,"SELECT * FROM games WHERE id=14");
				while($row = mysqli_fetch_assoc($gameIDQuery))
				{	
					echo $row["AD_img"];
				}
		?>
	</head>
	<body>
	</body>
</html>
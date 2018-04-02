<?php
	session_start();
	if($_SESSION["logged_in"] != true){
		$_SESSION["logged_in"] = false;
	}
	require "connect.php";
?>
<!doctype html>
<html><head>
		<meta charset="utf-8">
		<title>Namnlöst dokument</title>
        <style>
			*
			{
				margin: 0;
				padding: 0;	
			}
			<?php
			$query = mysqli_query($conn, 'SELECT background FROM devlog INNER JOIN users ON devlog.username = users.username WHERE devlog.username = users.username');
			while($row = mysqli_fetch_assoc($query)){
				echo '
				body
				{
					background:url('.$row["background"].');
					background-size:cover;
				}
				';
			}
			?>
						
			.bgcover
			{
				width: 98.9vw;
				height: 117vh;
    			background: -webkit-linear-gradient(transparent, black 80%); 
				position:absolute;
				top: 0;
				left: 0;
			}
		
			.description
			{
				width: 50em;
				height: 100%;
				background:rgba(255,255,255,1);
				margin:auto;
				position:relative;
				top: 4.9em;
				overflow:hidden;
			}
			
			.description p
			{
				padding:2em;
				margin-top: 9em;	
			}
			<?php
			$query = mysqli_query($conn, 'SELECT potrait FROM devlog INNER JOIN users ON devlog.username = users.username WHERE devlog.username = users.username');
			while($row = mysqli_fetch_assoc($query)){
				echo '
				.writerImage
				{
					width: 8em;
					height: 8em;
					background:url('.$row["potrait"].');
					background-size:cover;	
					position:absolute;
					top:1em;
					left:1em;
					border:black solid 0.5em;
					
				}
				';
			}
			?>
			
			.writerImage h1
			{
				margin-left: 5em;
				text-align:bottom;
			}
			
		</style>
               	 <?php
			require "header.php";
		?>       
	</head> 
	<body>
    	<div class="bg"></div>
    	<div class="bgcover"></div>
    	<div class="description">
            <div class="writerImage">
            <?php
			/*$query = mysqli_query($conn, 'SELECT title FROM devlog INNER JOIN users ON devlog.username = users.username WHERE devlog.username = users.username');
			while($row = mysqli_fetch_assoc($query)){
				echo '
            	<h1>'.$row["title"].'</h1>
				';
			}*/
			?>
            </div>
        <p>
        <?php
			/*$query = mysqli_query($conn, 'SELECT description FROM devlog INNER JOIN users ON devlog.username = users.username WHERE devlog.username = users.username');
			while($row = mysqli_fetch_assoc($query))
			echo $row["description"];*/
		?>
        </p>
        </div>
	</body>
</html>
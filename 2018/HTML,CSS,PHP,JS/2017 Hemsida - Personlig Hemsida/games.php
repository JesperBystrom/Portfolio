<?php
	//Här "disablar" jag alla sessions för att undvika errors.
	require "sessionDisabler.php";
	if($_SESSION["logged_in"] != true){
		$_SESSION["logged_in"] = false;
	}
	require "connect.php";
	$queryGames = "SELECT * FROM games ORDER BY user";
?>

<!doctype html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Namnlöst dokument</title>
        <style>
			*
			{
				margin: 0;
				padding: 0;	
			}
			.bg
			{
				background:url(Bilder/M.png);
				width: 100vw;
				height: 100vh;
				position:absolute;
				left: 0;
				top: 0;
				opacity: 0.4;
				overflow:hidden;
			}
			
			.bgCover
			{
				width: 100vw;
				height: 100vh;
				position: absolute;
				top: 0;
				left: 0;
				background: rgba(22,22,22,0.8);
			}
			<?php
			//Sorterings system, beroende på vilken "knapp" man trycker på så ska det bli en annan sortering.
			switch($_GET["sort"]){
				case 0:
					$queryGames = "SELECT * FROM games ORDER BY user";
				break;
				
				case 1:
					//Denna sökning gör att bara dina spel syns.
					$queryGames = "SELECT * FROM games INNER JOIN users ORDER BY CASE WHEN users.username IN('Mojko') THEN -1 ELSE games.id END,games.id DESC";
					//SELECT * FROM games INNER JOIN users ON users.username = games.user ORDER BY CASE WHEN games.id IN(0) THEN -1 ELSE games.id END,games.id DESC
				break;
				
				default:
					$queryGames = "SELECT * FROM games";
				break;	
			}
			
			$spelBilder = mysqli_query($conn, $queryGames);
			while($row=mysqli_fetch_assoc($spelBilder))
			{
			echo '
			.spelWrapper ul li .id'.$row["id"].'
			{
				display:block;
				float:left;
				width: 15vw;
				height: 20vh;
				background-size:cover;
				margin-right: 1.8vw;
				margin-left: 1.8vw;	
				margin-bottom: 5vh;
				position:relative;
				top: 20vh;	
			}
			';
			}
			
			//$stjärnor = mysqli_query($conn, "SELECT * FROM games");
			?>
				.spelWrapper ul li img
				{
					width: 2.5vw;
					height: 5vh;
					display:block;
					float:left;
					margin-top: 50vh;
					position:relative;
					right: 55vw;
					
				}
			
			.spelWrapper ul
			{
				list-style-type: none;	
			}
			
			nav
			{
				width: 100vw;
				height: 10vh;
				background-color:rgba(0,0,0,0.67);
				position:absolute;
				top: 10vh;	
				left: 0;
			}
			
			.nav .sortByName
			{
				width: 5em;
				height: 5em;
				background:yellow;
				float:left;	
				margin-left: 2em;
			}
			.nav .sortByRating
			{
				width: 5em;
				height: 5em;
				background:red;
				float:left;
				margin-left: 2em;
			}
			
			<?php
			
			//Samma sökning som ovan
			switch($_GET["sort"]){
				case 0:
					$queryGames = "SELECT * FROM games ORDER BY user";
				break;
				
				case 1:
					$queryGames = "SELECT * FROM games INNER JOIN users ORDER BY CASE WHEN users.username IN('Mojko') THEN -1 ELSE games.id END,games.id DESC";
				break;
				
				case 2:
					$queryGames = "SELECT * FROM games ORDER BY rating DESC";
				break;
				
				default:
					$queryGames = "SELECT * FROM games";
				break;	
			}
			
			$spelbilder = mysqli_query($conn, $queryGames);
			
			while($row = mysqli_fetch_assoc($spelbilder))
				{
					echo '
					.id'.$row["id"].'
					{
						background:url('.$row["AD_img"].');
						background-size:cover;
						display:block;
						float:left;
						width: 15vw;
						height: 20vh;
						background-size:cover;
						margin-right: 1.8vw;
						margin-left: 1.8vw;	
						margin-bottom: 5vh;
						position:relative;
						top: 20vh;
						border: 10px rgba(42,42,42,1.00) solid;
						border-radius:5px;
					}
					
					.id'.$row["id"].':hover
					{
						box-shadow:inset 0px 200px 0px rgba('.$row["rating_color_red"].','.$row["rating_color_green"].',0,0.6);
						opacity: 0.85;
					}
					
					.gameDrop'.$row["id"].'
					{
						width: 5vw;
						height: 20vh;
						background:red;
						position:relative;
						left: -99999;
					}
					
					.id'.$row["id"].':hover .gameDrop'.$row["id"].'
					{	
						left: 0;	
					}
					';
					
				}
			?>
						
		</style>      
	<?php require "header.php"; ?>
        <div class="bg"></div>
        <div class="bgCover"></div>
    	<div class="spelWrapper">
			<?php
                $spelbilder = mysqli_query($conn, $queryGames);
                while($row = mysqli_fetch_assoc($spelbilder))
                {
					echo '<ul>';
						echo '<li>';
                    		echo '<a class="'.$row["id"].'" href="game.php?id='.$row["id"].'"><div class="id'.$row["id"].'"></div></a>';
						echo '</li>';
					echo '</ul>';
                    
                }

           ?>             
  		</div>
        <!--<div class="header"></div>---> 
        <nav>
       		<a href='games.php?sort=1'><div class="sortByName"></div></a>
            <a href='games.php?sort=2'><div class="sortByRating"></div></a>
   		</nav>
  
        
	</body>
</html>
<?php
	require "connect.php";
	//Här tar jag alla resultat ifrån games där id = id på toppen av hemsidan / game idt.
	$gameTitleQuery = mysqli_query($conn,"SELECT * FROM games WHERE id='" . $_GET['id']."'");
	session_start();
	error_reporting(0);
?>
<!doctype html>
<html lang="en">
	<head>
    	<meta charset="utf-8">
        
        <?php
			//While loop för att printa ut titeln beroende på vilket id man har.
        	while($row = mysqli_fetch_assoc($gameTitleQuery))
			{
				//echo '<br>'.$row["id"].'<br>';	
				echo '<title>' . $row["title"] . '</title>';
			}
		?>
        
        
        <style>
			*
			{
				margin: 0;
				padding: 0;	
			}
			.banner
			{
				width: 100%;
				height:40vh;
				<?php
					//En while loop som tar emot alla reklam bilder för att använda det som en "banner"
					$gameIDQuery = mysqli_query($conn,"SELECT * FROM games WHERE id=" . $_GET['id']."");
					while($row = mysqli_fetch_assoc($gameIDQuery))
					{
						echo 'background:url('.$row["AD_img"].');';
						echo 'background-size:cover';
					}
				?>
			}
			
			.nav 
			{
				background:rgba(0,0,0,0.4);
				width: 100%;
				height: 10vh;
				margin: 0;
				padding: 0;
			}
			
			.nav a p
			{
				position:relative;
				text-align:center;
				top: 3.5vh;	
				margin:auto;
			}
			
			<?php
			//Här ändras description storleken beroende på hur lång stringen är som man får från $row['description']
			//Man kunde gjort 100% men det såg inte lika bra ut.
			$h = 50;
			$desc = mysqli_query($conn, "SELECT * FROM games");
			while($row = mysqli_fetch_assoc($desc))
			{
				$descLen = strlen($row['description']);
				explode($descLen, 15);
				if($descLen < 1000){
					$height = $h;	
				} else {
					 $height = $descLen / 15;
				}
			
			echo'
			.desc
			{
				width: 60%;
				height: '.$height.'vh;
				background:rgba(63,167,253,0.85);
				margin:0;
				float:left;		
			}
			';
			}
		?>
		
			.profileImage img
			{
				width: 4em;
				height: 4em;	
				border-radius: 2em;
				background:red;
				position:relative;
			}
			
			.desc .profileImage p
			{
				position:relative;
				bottom: 2em;
			}
			
			.desc p
			{
				margin-left: 5em;	
			}
			<?php
			//Samma sak med descriptionen här.
			$desc = mysqli_query($conn, "SELECT * FROM games");
			while($row = mysqli_fetch_assoc($desc))
			{
				$descLen = strlen($row['description']);
				if($descLen < 1000){
					$height = $h;	
				} else {
					$height = $descLen/15;
				}
			echo '
			.descSides
			{
				width: 20%;
				height: '.$height.'vh;
				float:left;
				margin: 0;
				background:black;
				background-size:cover;
			}
			';
			}
			?>

			.screenshotBorders
			{
				width: 8em;
				height: 8em;
				background:red;	
				margin: 2em;
				float:left;
			}
			.screenshotBorders img
			{
				width: 100%;
				height: 100%;
				display:block;
				object-fit: cover;	
			}
			
			
		</style>
		<?php require "header.php"; ?>
        <?php 
			
			//Gjort detta för att få en download länk!
            $gameDescQuery = mysqli_query($conn,"SELECT * FROM games WHERE id=".$_GET["id"].""); 
            $GAME = mysqli_fetch_assoc($gameDescQuery);
            $GAME_2 = $GAME["game"];
            $gameDescQuery = mysqli_query($conn,"SELECT * FROM games"); 
            //$GAME = mysqli_query($conn, "SELECT game FROM games WHERE id=".$row["id"]."");
        ?>
        <div class="banner"></div>
        <div class="nav"><?php echo'<a href="uploads/games/'.$row["url"].''.$GAME_2.'"><p>Download</p></a></div>'; ?>
        <div class="descSides"></div>
        <div class="desc">
        
            <div class="profileImage">
				<?php
					//Visar bildet & namnet på vem som har skapat spelet.
					$query = mysqli_query($conn, "SELECT * FROM users WHERE id = '".$_SESSION["id"]."'");
					while($row = mysqli_fetch_assoc($query)){
						echo '<img src="'.$row["profileImage"].'" alt="profileImage"></img><p>Creator: '.$row["username"].'</p>';
					}
                ?>
            </div>
			<?php
				//tar emot descriptionen.
				$query = mysqli_query($conn,"SELECT description FROM games WHERE id='".$_GET["id"]."'"); 
				while($row = mysqli_fetch_assoc($query)){
					echo '<p>' . $row["description"] . '</p>'; 
				}
            ?>
            <?php
				$gameIDQuery = mysqli_query($conn,"SELECT * FROM games WHERE id=" . $_GET['id']."");
				while($row = mysqli_fetch_assoc($gameIDQuery))
				{
					echo ' <div class="screenshotBorders"> <img src='.$row["screenshot_1"].' alt="screenshot1"></div>
						   <div class="screenshotBorders"> <img src='.$row["screenshot_2"].' alt="screenshot2"></div>
					';
				}
			?>
        </div>
        
        <div class="descSides"></div>
	</body>
</html>
<?php
	mysqli_close($conn);
?>
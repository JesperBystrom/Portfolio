<?php
	require "sessionDisabler.php";
	
	require "connect.php";
	
	//Här inner joinar jag användarens tabell för att checka om spelets ägares namn är lika med användarensnamn.
	$query = mysqli_query($conn, "SELECT * FROM games INNER JOIN users ON games.user=users.username WHERE games.user = '".$_SESSION["username"]."'");
	//Här gör jag gamecount beroende på hur många resultat som hittas utav query'n.
	$rows = mysqli_num_rows($query);
	$gameCount = $rows;
	//Här uppdateras "gameCount" för att kolla hur många spel användaren har skapat.
	mysqli_query($conn, "UPDATE users SET gameCount = ".$gameCount." WHERE id=".$_SESSION['id']."");
?>
<!doctype html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Namnlöst dokument</title>
        <link rel="stylesheet" type="text/css" href="CSS/profile.css">
        <style>
			*
			{
				margin: 0;
				padding: 0;	
				box-sizing:border-box;
			}
			
			<?php
			//Sätter bakgrunden beroende på ens id
			$query = mysqli_query($conn, 'SELECT * FROM users WHERE id=' . $_SESSION['id']);
			while($row = mysqli_fetch_assoc($query)){
				echo '
				body
				{
					background:url('.$row["profileBackground"].');
					background-size:cover;
				}
				';
			}
			?>

			<?php
			//Sätter bakgrunden på profilbilden beroende på vilket id man har.
			$query = mysqli_query($conn, "SELECT * FROM users WHERE id=" . $_GET['id']);
			while($row = mysqli_fetch_assoc($query)){
				echo '
				.writerImage
				{
					width: 8em;
					height: 8em;
					background:url('.$row["profileImage"].');
					background-size:cover;	
					position:absolute;
					top:4em;
					left:1em;
					border:white solid 0.5em;
					transition: ease-in 0.1s;
					
				}
				';
			}
			?>
		</style>     
	<?php require "header.php"; ?>
    	<div class="bg"></div>
    	<div class="bgcover"></div>
        <div class="slideshow">
        	<div class="divHeader"></div>
			<?php
				//tar emot header_img beroende på vem som skapat spelet. Detta är till för slidern.
				$query = mysqli_query($conn, 'SELECT * FROM games WHERE user="'.$_SESSION["username"].'"');
				
				while($row = mysqli_fetch_assoc($query))
				{
					echo '
						<img class="mySlides" src="'.$row["AD_img"].'" alt="mySlides">
					';
				}
			?>
        </div>
        
   	<div class="description">
        <div class="divHeader"></div>
		<?php
			//Sätter titeln på profilsidan
            $query = mysqli_query($conn, "SELECT * FROM users WHERE id=" . $_GET['id']);
            while($row = mysqli_fetch_assoc($query)){
                echo '
                    <h2>'.$row["profileTitle"].'</h2>
                ';
            }
        ?>
        <div class="writerImage"></div>
        <p>
        <?php
			//Här är profilens description.
			$query = mysqli_query($conn, "SELECT * FROM users WHERE id=" . $_GET['id']);
			while($row = mysqli_fetch_assoc($query)){
				echo $row["profileDesc"];
			}
		?>
        </p>
        </div>
        <div class="games">
        	<div class="divHeader"><h3>Top 3 Games</h3></div>
            <a href="#"><div class="add_game"></div></a>
        	<?php
				//Gör en while loop två gånger för att först checka gameCounten och sedan lägga ut så många spel
				//Beroende på det.
				$query = mysqli_query($conn, "SELECT gameCount FROM users WHERE id='" . $_SESSION['id']."'");
				$query_2 = mysqli_query($conn, "SELECT * FROM games WHERE user='".$_SESSION['username']."'");
				$i = 0;
				while($row = mysqli_fetch_assoc($query)){
					while($row_2 = mysqli_fetch_assoc($query_2)){
						if($row_2["user"] == $_SESSION["username"])
						{
							echo '
								<div class="slideShowButton" onClick=nextPicture('.$i.')>'.$row_2["title"].'</div>
							';
							$i++;	
						}
					}
				}
			?>
        </div>
        
        <script>
		
			/*
				När man klickar på <button> så ska bilden ändra ökas eller sänkas beroende på vilken av knapparna man klickar på.
				
				Script skapat av w3schools
			*/
			var slideIndex = 1;
			
			function nextPicture(n){
				div(slideIndex = n);	
			}
			
			function div(n){
				var i;
				var img = document.getElementsByClassName("mySlides");
				for(i=0;i<img.length;i++){
					img[i].style.display = "none";
				}
				img[slideIndex].style.display = "block";
				console.log(slideIndex);
			}
			div(slideIndex);
		</script>
	</body>
</html>
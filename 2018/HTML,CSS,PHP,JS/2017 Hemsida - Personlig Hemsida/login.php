<?php require "sessionDisabler.php"; ?>

<!doctype html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Namnlöst dokument</title>
        <div class="bg"></div>
        <div class="bgOverlay"></div>
        <div class="loginBG">

        </div>
        <?php
			require "connect.php";
			//Tar emot användarnamnet om det inte finns några dåliga karaktärer med i stringen från formuläret. Samma sak med password
			$username = mysqli_real_escape_string($conn,$_POST['username']);
			$password = mysqli_real_escape_string($conn,$_POST['password']);
			
			//Här "hashar" jag lösenordet med md5.
			$password = md5($password);
			
			//Här loopar jag igenom användarens information för att sedan checka om postens "username" och postens "password"
			//Är lika med databasens information. Sedan gör jag "found" variabeln true och sätter alla sessions så att dom
			//kan användas var som helst på hemsidan.
			$query = mysqli_query($conn, "SELECT * FROM users");
			$_SESSION['found'] = false;
			while($row = mysqli_fetch_assoc($query)){
				if($username === $row['username'] && $password === $row['password']){
					$_SESSION['found'] = true;
					$_SESSION['logged_in'] = true;
					$_SESSION['username'] = $username;
					$_SESSION['id'] = $row['id'];	
				}
				//Om användaren hittas, skicka den till locationen
				if($_SESSION['found']){
					header("Location: index.php");
					break;
				} else {
					//Om ens information inte hittas i databasen, avaktivera alla sesssions och skicka en
					//tilbaka till register.php med varningsmedelanden
					$_SESSION['logged_in'] = false;
					$_SESSION['found'] = false;
					$_SESSION['wrongInput'] = true;
					echo $password;
					echo "<br>";
					echo $row['password'];
					echo "<br>";
					echo $username;
					echo "<br>";
					echo $row['username'];
					
					header("Location: register.php");	
					break;
				}
			}
		?>
	</head>
</html>
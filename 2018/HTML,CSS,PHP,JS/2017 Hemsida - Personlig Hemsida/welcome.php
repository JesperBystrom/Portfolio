<?php
	require "sessionDisabler.php";
?>
<!doctype html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Namnlöst dokument</title>
        
	</head>
	<body>
        <div class="bg"></div>
        <div class="bgOverlay"></div>
        <div class="loginBG">
        	<p> hej </p>
        </div>
        
        <?php
		echo "<br>";
		//Skapa en koppling till databasen
		require "connect.php";
		
		//Tar emot information ifrån formuläret.
		$username = mysqli_real_escape_string($conn,$_POST['username']);
		$password = mysqli_real_escape_string($conn,$_POST['password']);
		$email = mysqli_real_escape_string($conn,$_POST['email']);
		
		//hashar lösenordet
		$password = md5($password);
		
		
		//Skickar default information till ens profilsida. 
		$profileBackground = "uploads/profiles/".$username."/profileBackground.png";
		$profileImage = "uploads/profiles/".$username."/profileImage.png";
		$profileTitle = "Bios Title";
		$profileDesc = "Bios description";
		$_SESSION['takenUsername'] = false;
		echo "Username: " . $username . "<br>";
		echo "Password: " . $password . "<br>";
		echo "Email: " . $email . "<br>";
		
		$sql = mysqli_query($conn,"SELECT username FROM users WHERE username='".$username."'");
		$sql_2 = mysqli_query($conn, "SELECT * FROM users");
		$num_rows = mysqli_num_rows($sql);
		$_SESSION['found'] = false;
		
		//Om användarnamnet inte finns, forstätt.
		if($num_rows == 0){
			//Skicka in alla default värden OCH användarnamnet och lösenrodet som man angett till databasen.
			//Skapa sedan directorys och skicka användaren till deras nyskapta profil 
			mysqli_query($conn,'INSERT INTO users(username,password,email,profileBackground,profileImage,profileTitle,profileDesc) VALUES("'.$username.'","'.$password.'","'.$email.'","'.$profileBackground.'","'.$profileImage.'","'.$profileTitle.'","'.$profileDesc.'")');
			echo 'INSERT INTO users(username,password,email) VALUES("'.$username.'","'.$password.'","'.$email.'","'.$profileBackground.'","'.$profileImage.'","'.$profileTitle.'","'.$profileDesc.'")';
			echo "Sucessfully registred";
			$_SESSION['logged_in'] = true;
			$_SESSION['username'] = $username;
			$_SESSION['found'] = true;
			
			mkdir("uploads/profiles/".$username."", 0777, true);
			copy('uploads/Default/defaultProfileImage.png', 'uploads/profiles/'.$username.'/profileImage.png');
			copy('uploads/Default/defaultProfileBackground.png', 'uploads/profiles/'.$username.'/profileBackground.png');
			
			$sql = mysqli_query($conn, "SELECT * FROM users");
			while($row = mysqli_fetch_assoc($sql)){
				$_SESSION['id'] = $row['id'];
			}
			header('LOCATION: profile.php?id='.$_SESSION["id"].'');
		}
		//Om användarnamnet redan finns, skicka tillbaka användaren till registreringssidan
		if(!$_SESSION['found']){
			$_SESSION['takenUsername'] = true;
			header('LOCATION: register.php');	
		}
		
		
		mysqli_close($conn);
	?>
    
	</body>
</html>
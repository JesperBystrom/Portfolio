<?php
	session_start();
	require "connect.php";
	
	//Tar emot all information från formuläret.
	$gameName = mysqli_real_escape_string($conn,$_POST["gameName"]);
	$gameDescription = mysqli_real_escape_string($conn,$_POST["gameDescription"]);
	$url = $gameName;
	for($i=0;$i<strlen($url);$i++){
		if($url[$i] == ' '){
			$url[$i] = '_';	
		}
	}
	$dir = "uploads/games/". $url ."";
	//Skapar en directory beroende på ens användarnamnet, och sedan skicka bilder dit som "default" på profil sidan!
	if(!file_exists("uploads/games/". $url ."")){
		mkdir("uploads/games/".$url."", 0777, true);	
		move_uploaded_file($_FILES["fileToUpload"]["tmp_name"], "uploads/games/". $url ."/Game.exe");
		move_uploaded_file($_FILES["AD_img"]["tmp_name"], "uploads/games/". $url ."/AD.png");
		move_uploaded_file($_FILES["screenshot_1"]["tmp_name"], "uploads/games/". $url ."/screenshot_1.png");
		move_uploaded_file($_FILES["screenshot_2"]["tmp_name"], "uploads/games/". $url ."/screenshot_2.png");
		
		
		//Skickar in all information till databasen.
		/*mysqli_query($conn, "UPDATE games SET username='".$username."' WHERE id='".$_SESSION['id']."'");
		mysqli_query($conn, "UPDATE users SET password='".$password."' WHERE id='".$_SESSION['id']."'");
		mysqli_query($conn, "UPDATE users SET profileTitle='".$biosTitle."' WHERE id='".$_SESSION['id']."'");
		mysqli_query($conn, "UPDATE users SET profileDesc='".$biosDescription."' WHERE id='".$_SESSION['id']."'");
		mysqli_query($conn, "UPDATE users SET profileImage='".$fileDirectory."' WHERE id='".$_SESSION['id']."'");
		mysqli_query($conn, "UPDATE users SET profileBackground='".$background."' WHERE id='".$_SESSION['id']."'");*/
		mysqli_query($conn, "INSERT INTO games(user, title, url, description, AD_img, screenshot_1, screenshot_2, file) VALUES('".$_SESSION['username']."', '".$gameName."', '".$url."', '".$gameDescription."', '".$dir."/AD.png', '".$dir."/screenshot_1.png', '".$dir."/screenshot_2.png', '".$dir."/Game.exe')");
		$_SESSION["file_exists"] = false;
		header("LOCATION: profile.php?id=".$_SESSION["id"]."");
	} else {
		$_SESSION["file_exists"] = true;
		header("LOCATION: add_game.php");
	}
	mysqli_close($conn);
	
		//copy('uploads/profiles/'.$_SESSION["username"].'/profileImage.png', 'uploads/profiles/'.$username.'/profileImage.png');
		//copy('uploads/profiles/'.$_SESSION["username"].'/profileBackground.png', 'uploads/profiles/'.$username.'/profileBackground.png');
		
		//rmdir("uploads/profiles/". $_SESSION["username"] ."");
		
		/*if(!file_exists("uploads/profiles/". $username ."/profileImage.png")){
			copy('uploads/Default/defaultProfileImage.png', 'uploads/profiles/'.$username.'/profileImage.png');
		}
		if(!file_exists("uploads/profiles/". $username ."/profileBackground.png")){
			copy('uploads/Default/defaultProfileBackground.png', 'uploads/profiles/'.$username.'/profileBackground.png');	
		}*/
			
	//}
	//$fileDirectory = $dir . $_SESSION["username"] . '.png';
?>
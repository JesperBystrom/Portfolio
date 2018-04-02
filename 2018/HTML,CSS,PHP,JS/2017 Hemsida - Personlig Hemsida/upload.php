<?php
	session_start();
	require "connect.php";
	
	//Tar emot all information från formuläret.
	$username = mysqli_real_escape_string($conn,$_POST["username"]);
	$password = mysqli_real_escape_string($conn,$_POST["password"]);
	$biosTitle = mysqli_real_escape_string($conn,$_POST["biosTitle"]);
	$biosDescription = mysqli_real_escape_string($conn,$_POST["biosDesc"]);
	$background = "uploads/profiles/". $username ."/profileBackground.png";
	
	//Skapar en directory beroende på ens användarnamnet, och sedan skicka bilder dit som "default" på profil sidan!
	$dir = "uploads/profiles/". $username ."/";
	if(!file_exists("uploads/profiles/". $username ."")){
		rename("uploads/profiles/". $_SESSION["username"] ."","uploads/profiles/".$username."");		
		$fileDirectory = $dir . "profileImage.png";
		$file = $_FILES["fileToUpload"]["tmp_name"];
		move_uploaded_file($file, $fileDirectory);
		
		//hashar lösenordet.
		$password = md5($password);
		
		//Skickar in all information till databasen.
		mysqli_query($conn, "UPDATE users SET username='".$username."' WHERE id='".$_SESSION['id']."'");
		mysqli_query($conn, "UPDATE users SET password='".$password."' WHERE id='".$_SESSION['id']."'");
		mysqli_query($conn, "UPDATE users SET profileTitle='".$biosTitle."' WHERE id='".$_SESSION['id']."'");
		mysqli_query($conn, "UPDATE users SET profileDesc='".$biosDescription."' WHERE id='".$_SESSION['id']."'");
		mysqli_query($conn, "UPDATE users SET profileImage='".$fileDirectory."' WHERE id='".$_SESSION['id']."'");
		mysqli_query($conn, "UPDATE users SET profileBackground='".$background."' WHERE id='".$_SESSION['id']."'");
		
		$_SESSION["username"] = $username;
		$_SESSION["file_exists"] = false;
		header("LOCATION: profile.php?id=".$_SESSION["id"]."");
	} else {
		$_SESSION["file_exists"] = true;
		header("LOCATION: profileEdit.php");
	}
	echo $username;
	echo $password;
	echo $biosTitle;
	echo $biosDescription;
	echo $file;
	mysqli_close($conn);
?>
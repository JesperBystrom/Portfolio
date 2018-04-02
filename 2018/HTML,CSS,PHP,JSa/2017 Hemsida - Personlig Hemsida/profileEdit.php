<?php
	session_start();
	require "connect.php";
	error_reporting(0);
?>
<!DOCTYPE HTML>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Namnlöst dokument</title>
        <link rel="stylesheet" type="text/css" href="CSS/profileEdit.css">
        
	<?php require "header.php"; ?>
    	<div class="bg"></div>
    	<div class="bgcover"></div>
		<?php
			//Om ens användarnamn redan existerar i databasen, visa "file_exists" diven.
            if($_SESSION["file_exists"]){
                echo '<div class="file_exists"><p>Username taken! Please choose another one</p></div>';	
            }
        ?>
        <div class="middle">
        	<div class="divHeader"><p>Profile modification</p></div> 
            <div class="inputBox">
            <!--<div class="question_mark"></div> -->
            <!-- En massa divs för error medelanden-->
                <form action="upload.php" method="post" enctype="multipart/form-data">
                	<div class="question_mark"><div class="infoBox"><p> Upload a new profile image </p></div></div>
                    
                    <label for="fileToUpload">.</label>
                    <input type="file" name="fileToUpload" id="fileToUpload">.
                    </label>
                    
                    <div class="question_mark"><div class="infoBox"><p> Username, insert your NEW username, not your old! Remember to choose a unique username and not an existing one</p></div></div>
                    
                    <label for="username">.</label>
                    <input type="text" name="username" placeholder="Username" id="username">.
                    </label>
                    
                    <div class="question_mark"><div class="infoBox"><p> Password, insert your NEW password, not your old! </p></div></div>
                    
                    <label for="password">.</label>
                    <input type="password" name="password" placeholder="Password" id="password">.
                    
                    <div class="question_mark"><div class="infoBox"><p> Bios title, write a short title! Maybe your name? </p></div></div>
                    
                    <label for="biosTitle">.</label>
                    <input type="text" name="biosTitle" placeholder="Bios Title" id="biosTitle">.
                    
                    <label for="description">.</label>
                    <textarea name="biosDesc" id="description" cols="40" rows="5" placeholder="Bios description"></textarea>
                    
                    <label for="submit">.</label>
                    <input id="submit" type="submit" value="Update account">
                    </label>
                </form>
            </div>
        </div>
	</body>
</html>
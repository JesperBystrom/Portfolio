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
        
        <style>
			*
			{
				margin: 0;
				padding: 0;	
				box-sizing:border-box;
			}
			
			body, html 
			{ 
				overflow-x: hidden; 
				overflow-y: hidden;
			}
			
			.bgcover
			{
				width: 100em;
				height: 49.6em;
    			background:rgba(22,22,22,0.8);
				position:absolute;
				top: 0;
				left: 0;
				overflow:hidden;
				z-index: -998;
			}
			
			.bg
			{
				width: 100em;
				height: 49.6em;
				background:url(Bilder/M.png);
				opacity: 0.4;
				position:absolute;
				top: 0;
				left: 0;
				overflow:hidden;
				z-index: -999;
			}
			
			.middle
			{
				width: 30em;
				height: 38em;
				background:rgba(33,33,33,1);
				margin-top: 10.5em;
				margin-left: 35em;
				position:absolute;
			}
			.divHeader
			{
				width: 100%;
				height: 3em;
				position:relative;
				background:rgba(63,167,253,0.7);
			}
			.inputBox form input
			{
				background:rgba(83,83,83,0.4);
				background-size:2em 2em;
				background-position:5% 50%;
				border-color:rgba(43,43,43,0.5);
				width: 20vw;
				height: 3.5em;
				margin-top: 2vh;	
				opacity: 0.6;
				padding-left: 3.5vw;
				color:white;
				transition:ease 0.2s;
				margin-left: 1.5em;
				position:relative;
			}
			/*
			.inputBox form input:nth-child(1){
				background:rgba(83,83,83,0.4) url(Bilder/username_2.png)  no-repeat;
				background-position:0.5em 1.1em;				
			}
			
			.inputBox form input:nth-child(3){
				background:rgba(83,83,83,0.4) url(Bilder/username_2.png)  no-repeat;
				background-position:0.5em 1.1em;				
			}
			.inputBox form input:nth-child(5){
				background:rgba(83,83,83,0.4) url(Bilder/password.png)  no-repeat;
				background-position:0.5em 1.1em;				
			}
			
			.inputBox form input:nth-child(7){
				background:rgba(83,83,83,0.4) url(Bilder/username_2.png)  no-repeat;
				background-position:0.5em 1.1em;				
			}
			*/
			
			.inputBox form textarea
			{
				background:rgba(83,83,83,0.4);
				border-color:rgba(43,43,43,0.5);
				width: 20vw;
				height: 3.5em;
				margin-top: 2vh;	
				opacity: 0.6;
				padding-left: 3.5vw;
				color:white;
				transition:ease 0.2s;
				margin-left: 5.5em;
			}
			
			.inputBox form input:focus
			{
				opacity: 1;
				box-shadow: 3px 3px 5px #8C8C8C;
			}	
			
			.inputBox p
			{
				color:white;
				font-family:Cambria, "Hoefler Text", "Liberation Serif", Times, "Times New Roman", serif	
			}
			
			.inputBox #description
			{
				height: 10em;
				padding: 0;	
			}
			
			@keyframes slideInFromRight {
			  0% {
				left: 50%;
			  }
			  100% {
				left: 25%;
			  }
			}
			
			.file_exists
			{
				width: 10em;
				height: 5em;
				background:red;
				position:absolute;
				left: 25%;
				top: 50%;
				animation: 0.5s cubic-bezier(0.130, 0.130, 0.015, 0.995) 0s 1 slideInFromRight;
				-webkit-animation-fill-mode: forwards;
			}
			.question_mark
			{
				width:2em;
				height:2em;
				background:url(Bilder/question_mark.png);
				background-size:cover;	
				margin:0;
				padding:0;
				margin-top: 1.5em;
				margin-left: 1.5em;
				float:left;
			}
			
			
			#username
			{
				background:rgba(83,83,83,0.4) url(Bilder/username_2.png);
				background-repeat:no-repeat;
				background-position: 0 1em;
			}
			
			#password
			{
				background:rgba(83,83,83,0.4) url(Bilder/username_2.png);
				background-repeat:no-repeat;
				background-position: 0 1em;
			}
			
			#biosTitle
			{
				background:rgba(83,83,83,0.4) url(Bilder/username_2.png);
				background-repeat:no-repeat;
				background-position: 0 1em;
			}
			
			.infoBox
			{
				width: 10em;
				height: 12em;
				background:rgba(33,33,33,1);
				position:absolute;
				left: -0em;
				top: 4em;
				z-index: -1;
			}
			.infoBox p
			{
				padding: 0.5em;
				color:gray;
				z-index: -1;
			}
			
			.question_mark:hover .infoBox
			{
				left: -10em;
			}
			
			#submit
			{
				margin-left: 5em;
				text-align:center;	
			}
			
			.divHeader p
			{
				color:lightgray;
				padding: 1em;	
			}
			.image
			{
				background:rgba(83,83,83,0.4) url(Bilder/berg.png) !important;
				background-repeat:no-repeat !important;
				background-position: 0.3em 1em !important;	
			}
			
			.fileToUpload
			{
				background:rgba(83,83,83,0.4) url(Bilder/pacman.png) !important;
				background-repeat:no-repeat !important;
				background-position: 0.3em 0.5em !important;	
			}
			
		
		</style>
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
        	<div class="divHeader"><p>Upload game</p></div> 
            <div class="inputBox">
            <!--<div class="question_mark"></div> -->
            <!-- En massa divs för hjälp medelanden-->
                <form action="uploadGame.php" method="post" enctype="multipart/form-data">
                
                
                	<div class="question_mark"><div class="infoBox"><p> Upload a new game, only exe files allowed </p></div></div>
                    
                    <label for="fileToUpload">.</label>
                    <input type="file" name="fileToUpload" id="fileToUpload"><br>
                    
                	<div class="question_mark"><div class="infoBox"><p> AD_img banner+ad </p></div></div>
                    
                    <label for="image_0">.</label>
                    <input type="file" name="AD_img" id="image_0" class="image"><br>
                    
                 	<div class="question_mark"><div class="infoBox"><p> Screenshot_1 </p></div></div>
                    
                    <label for="image_1">.</label>
                    <input type="file" name="screenshot_1" id="image_1" class="image"><br>
                    
                	<div class="question_mark"><div class="infoBox"><p> Screenshot_2 </p></div></div>
                    
                    <label for="image_2">.</label>
                    <input type="file" name="screenshot_2" id="image_2" class="image"><br>
                    
                    <div class="question_mark"><div class="infoBox"><p> Game name, this will be ur title of the game. </p></div></div>
                    
                    <label for="username">.</label>
                    <input type="text" name="gameName" placeholder="Game name" id="username"><br>
                    
                    <label for="description">.</label>
                    <textarea name="gameDescription" id="description" cols="40" rows="5" placeholder="Game description"></textarea>
                    
                    <br>
                    <label for="submit">.</label>
                    <input id="submit" type="submit" value="Upload game">
                </form>
            </div>
        </div>
	</body>
</html>
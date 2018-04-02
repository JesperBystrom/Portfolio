<?php
	require "sessionDisabler.php";
	$_SESSION['logged_in'] = false;

?>
<!doctype html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Namnlöst dokument</title>
        <link rel="stylesheet" type="text/css" href="CSS/register.css">
        <style>
			.errorMessageBox
			{
				width: 12em;
				height: 5em;
				background:red;
				position:absolute;
				margin-left: 58em;
				animation: 0.5s cubic-bezier(0.130, 0.130, 0.015, 0.995) 0s 1 slideInFromRight;
				-webkit-animation-fill-mode: forwards;
				/*transition: ease-in margin-left 2.0s;*/
				<?php
					if($_SESSION['wrongInput']){
						echo ' margin-top: 25em; ';	
					}
				?>
			}
			
		</style>
	</head>
	<body>      
        <div class="bg"></div>
        <div class="mojogames"></div>
        <div class="bgOverlay"></div> 
        <div class="bild">
        	<div class="divHeader"></div>
        </div>
        
        
        <div class="registerBG  inputBox">
            <p> Havent registred yet? Register here </p>
            <div class="divHeader"></div>
            <form method="post" action="welcome.php">
                <label for="1">.
                <div class="username"><input type="text" name="username" placeholder="Username" id="1"></div>
                </label>
                
                <label for="2">.
                <div class="password"><input type="password" name="password" placeholder="Password" id="2"></div>
                </label>
                
                 <label for="3">.
                <div class="mail"><input type="text" name="email" placeholder="Email" id="3"></div>
                </label>
                
                <label for="4">.
                <input class="submit" type="submit" value="Submit">
                </label>
       		 </form>
            </div>
            
            <div class="loginBG inputBox">
                <p> Login </p>
                <div class="divHeader"></div>
                <form method="post" action="login.php">
                
                <label for="5" >.
                <div class="username"><input type="text" name="username" placeholder="Username" id="4"></div>
                </label>
                
                <label for="6">.
                <div class="password"> <input type="password" name="password" placeholder="Password" id="5" text="."> </div>
                </label>
                
                <label for="7">.
                <input class="submit" type="submit" value="Submit">
                </label>
                </form>
         	</div>
            <?php
				//Visa upp error medelanden
				if($_SESSION['takenUsername']){
					$reason = 'Username already taken';	
					$_SESSION['wrongInput'] = false;
				} 
				if($_SESSION['wrongInput']){
					$reason = 'Username or password does not match';
					$_SESSION['takenUsername'] = false;
				}
				if($_SESSION['wrongInput'] || $_SESSION['takenUsername']){
					echo '
						<div class="errorMessageBox"><p>'.$reason.'</p></div>
					';
				}
			?>
             
	</body>
</html>
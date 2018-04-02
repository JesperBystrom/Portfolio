<link rel="stylesheet" type="text/css" href="CSS/header.css">
</head>
<body>
<header>
	<ul>
    	<!--- 1 --->
    	<li id="home">
        	<a href="index.php"><div class="headerOverlay"><p>Home</p></div></a>
        </li>
        <!--- 2 --->
        <li id="prof"> 
        	<?php 
			//Knapp som leder till "games" sidan
				echo '<a href="games.php?sort=0"><div class="headerOverlay"><p>Games</p></div></a>';
			?>
			<?php
				
            ?>
        	
        </li>
        <!--- 3 --->
        <li id="devlogs">
			<?php
				//knapp som inte funkar ännu!
				if($_SESSION["logged_in"]){
					echo '<a href="devlogs.php?id='.$_SESSION["id"].'"><div class="headerOverlay"><p>Placeholder</p></div></a>';
				}
            ?>
        </li>
        <!--- 4 --->
        <li id="login">
			<?php
			//Knapp som leder till registreringssidan OM man inte är inloggad, om man är inloggad så ska ens namn & profilbild synas
            if(!$_SESSION["logged_in"]){
                echo '
                    <a href="register.php"><div class="headerOverlay"><p> Login </p></div></a>
                ';
            } else {
                $query = mysqli_query($conn, "SELECT * FROM users WHERE id = '".$_SESSION["id"]."'");
                while($row = mysqli_fetch_assoc($query)){
                    echo '
                        <div class="headerOverlay"><p> '.$_SESSION["username"].' </p></div>
                        <img src="'.$row["profileImage"].'" alt="profileImage">
                    ';
                }
            }
            ?>
            <?php
				//Detta är en dropdown meny som syns om man hovrar över ens namn & profilbild.
				//Denna dropdown meny leder en till sin:
				//Profil sida, Edit profil sida, (Devlogs, ej-klar), Logout som gör att man loggas ut. 
				if($_SESSION["logged_in"]){
					echo '
						<div class="loginHover"> 
							<ul>
								<li><a href="profile.php?id='.$_SESSION["id"].'"> View Profile </a></li>
								<li><a href="profileEdit.php"> Edit Profile </a></li>
								<li><a href="add_game.php"> Upload game </a></li>
								<li><a href="logout.php"> Logout </a></li>
							</ul>
							
						</div>
					';
				}
            ?>
      
        </li>
    </ul>
</header>
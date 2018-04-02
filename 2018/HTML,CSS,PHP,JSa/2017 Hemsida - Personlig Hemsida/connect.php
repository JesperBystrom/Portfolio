<?php
    
//variablar till funktionen mysqli_connect
$host = "localhost";	
$user = "root";
$pass = "";
$db = "mojogames";

//Här kopplar jag till databasen
$conn = mysqli_connect($host,$user,$pass,$db);

//Här checkar jag ifall en koppling har blivit gjord.
if(!$conn)
{
    echo "Error cant connect to database";	
}
?>
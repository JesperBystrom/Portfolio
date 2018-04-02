<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Namnlöst dokument</title>
</head>

	<?php
	
	$namn = array('Joel', 'Jesper', 'Mathias', 'William', 'Robin', 'David', 'Malte', 'korv', 'sverige', '99', 'a', 'x6');
	
	for($i=0;$i<12;$i++)
	{
		echo $namn[$i];	
		echo "<br>";
		 
	}
	
	$namn[] = 'Ludvig'; 
	echo $namn[12];
	
	//Stoppar in allt från $namn till $kamrater
	//Samma sak som en for loop.
	foreach($namn as $kamrater)
	{
		echo $kamrater . "<br>";
	}
	
	$elev = array('Namn: ' => 'Jesper', 'Klass: ' => 'TE15' );
	foreach($elev as $key => $value)
	{
		echo "<br>";
		echo $key . "<br>" . $value . "<br>";
	}
 	
	?>
    
<body>
</body>
</html>
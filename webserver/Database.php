<html>
<body>
<?php
//Grab name from database.html
$Name = $_POST["name"];//comment this line out if not using database.html;
$con = mysql_connect(':/homes/wind-wiki/mysql/tmp/mysql.sock',"root","wind");
if (!$con)
{
    die('Could not connect: ' . mysql_error());
}
//If connected create database using the given name; 
if (mysql_query("CREATE DATABASE $Name",$con))//change $Name to the name you want if not using Database.html
{
    echo "Database created";
}
else
{
    echo "Error creating database: " . mysql_error();
}

mysql_close($con);

?>
</body>
</html>

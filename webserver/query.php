<html>
<title> Query the Database</title>
<body>
<?php

$Database = "GroupMeet";

$con = mysql_connect(':/homes/byrdc/mysqld/mysqld.sock', "root", "groupmeet");
if(!$con)
{
	die('Could not connect: ' . mysql_error());
}
mysql_select_db("$Database", $con);

$sql = $_POST[query];
echo "Your SQL statement is <br>";
print($sql);
echo "<br>";

$result = mysql_query($sql,$con);
if(!$result)
{
	die('Error: ' . mysql_error($con));
}
echo"<br>It was a Success <br>";

	while(($row = mysql_fetch_array($result))!= false) {
    print_r($row);
    echo '<br />';
	}
mysql_close($con);
?>
</body>
</html>


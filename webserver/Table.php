<html>
<body>
<?php
$Database = "GroupMeet";

$Name = $_POST['name'];

$con = mysql_connect(':/homes/byrdc/mysqld/mysqld.sock', "root", "groupmeet");
if (!$con)
{
    die('Could not connect: ' . mysql_error());
}

mysql_select_db("$Database", $con);
$sql = "CREATE TABLE $Name " . $_POST['query'];

if(mysql_query($sql,$con))
{
echo "$Name succesfully created.";
} else {
	echo "Error Creating table: " . mysql_error();
}
mysql_close($con);
?>

</body>
</html>

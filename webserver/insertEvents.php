<html>
<title> Insert Events </title>
<body>

<?php
/* This script takes Post arguments "owner" and "event"
 * and creates an event with "owner" as the owner if the owner exists */


//set given Parameters
$Owner = $_POST[owner];
$Event = $_POST[event];
$Response = array();

if( !filter_var($Owner, FILTER_VALIDATE_EMAIL)) {
	//if Owner isn't a valid email

	$Response["PASSED"] = 0;
	$Response["message"] = "Error: $Owner is not a valid email";

	//send error
	echo json_encode($Response);
	exit();
}



//Default Database
$Database = "GroupMeet";
//create Connection
$con = mysql_connect(':/homes/byrdc/mysqld/mysqld.sock', "root", "groupmeet");

if(!$con)
{
	$Response["PASSED"] = 0;
	$Response["message"] = 'Error: ' . mysql_error($con);

	//send error
	echo json_encode($Response);
	exit();
}

//set Default Database
mysql_select_db("$Database", $con);
//Insert Event and Owner in to database;


//Check if Owner exists
 $query = mysql_query("SELECT * FROM Users WHERE UserName = '$Owner'",$con);

if(mysql_num_rows($query) <= 0)
{

		   $Response["PASSED"] = 0;
		   $Response["message"] = "$Owner doesn't exist in DataBase";
		   echo json_encode($Response);
		   exit();
}

$sql = "INSERT INTO Events (EventName, Username) VALUES ('$Event','$Owner')";

if(!mysql_query($sql,$con))
{
	$Response["PASSED"] = 0;
	$Response["message"] = 'Error: ' . mysql_error($con);

	//send error
	echo json_encode($Response);
	exit();

}

//send success response

$Response["PASSED"]= 1;
$Response["message"] = "$Event was successfully created by $Owner";
echo json_encode($Response);

mysql_close($con);

?>
</body>
</html>


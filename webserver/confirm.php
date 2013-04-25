<?php

/*
Sets EventDate of the Event to the owner selected common date/time
 */

$response = array();
//Name of Database
$Database = "GroupMeet";
//Make Connection
$con = mysql_connect(':/homes/byrdc/mysqld/mysqld.sock', "root", "groupmeet");
if(!$con)
{
	$response["PASSED"] = 0;
	$response["message"] = 'Error: ' . mysql_error($con);
	echo json_encode($response);
	exit();
}

//POST arguments go here
$Event = $_POST[event];
$User = $_POST[user];
$Date = $_POST[date];

//make the Default Database GroupMeet
mysql_select_db("$Database", $con);

/*
//check if necessary Posts exists exists
$query = mysql_query("<existance query>",$con);
if(mysql_num_rows($query) <= 0)
{
	$response["PASSED"] = 0;
	$response["message"] = "<argument doesn't exist> doesn't exist";
	echo json_encode($response);
	exit();
}
 */

//actual query for script;
$result = mysql_query("UPDATE Events set EventDate='$Date' WHERE EventName='$Event' AND UserName='$User'",$con);
//if failed
if(!$result)
{
	$response["PASSED"] = 0;
	$response["message"] = 'Error: ' . mysql_error($con);
	echo json_encode($response);
	exit();
}

//check for empty result
if(mysql_affected_rows() == 1) {

$response["PASSED"] = 1;
$response["message"] = "$Event was confirmed for $Date by $User";
//send JSON response

echo json_encode($response);

}
else
{ //failed; send failure message;
	$response["PASSED"] = 0;
	$response["message"] = "Update Query failed";
	echo json_encode($response);
}

mysql_close($con);
?>


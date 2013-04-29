<?php

/*
 Find if an Event has been confirmed or not.
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
$Event = mysql_real_escape_string($Event);
//actual query for script;
$result = mysql_query("Select * from Events where EventDate IS NOT NULL AND UserName='$User' AND EventName='$Event';",$con);
//if failed
if(!$result){

	$response["PASSED"] = 0;
	$response["message"] = 'Error: ' . mysql_error($con);
	echo json_encode($response);
	exit();

}

//check for empty result
if(mysql_num_rows($result) > 0) {


$row = mysql_fetch_array($result);

$response["confirm"] = $row['EventDate'];
$response["message"] = "$Event was confirmed for " . $row['EventDate'];
$response["PASSED"] = 1;
//send JSON response

echo json_encode($response);

} else {
//failed; send failure message;
	$response["PASSED"] = 0;
	$response["message"] = "$Event not confirmed";
	echo json_encode($response);

}

mysql_close($con);
?>


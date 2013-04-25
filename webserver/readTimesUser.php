<?php

/*
This script returns all timess that A given event has;
if the event does not exist it returns a json object with a "PASSED" index
and a "message" indicating the error.

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

//Name of User to search with
$Event = $_POST[event];
$User = $_POST[user];

//make the Default Database GroupMeet

mysql_select_db("$Database", $con);

//check if Event exists

$query = mysql_query("SELECT * FROM Events WHERE EventName = '$Event'",$con);
if(mysql_num_rows($query) <= 0)
{

	$response["PASSED"] = 0;
	$response["message"] = "$Event doesn't exist";
	echo json_encode($response);
	exit();

}

//get all Times for events;
$result = mysql_query("SELECT * FROM Times WHERE EventName='$Event' AND UserName='$User'",$con);
//if failed
if(!$result){

	$response["PASSED"] = 0;
	$response["message"] = 'Error: ' . mysql_error($con);
	echo json_encode($response);
	exit();

}

//check for empty result
if(mysql_num_rows($result) > 0) {

$response["times"] = array();

while($row = mysql_fetch_array($result))
{
	//temp array
	$product = array();
	$product["times"] = $row['DateTime'];
	//push single product into final response array
	array_push($response["times"], $product);

}

$response["PASSED"] = 1;
//send JSON response

echo json_encode($response);

} else {
//failed; send failure message;
	$response["PASSED"] = 0;
	$response["message"] = "No Times Found";
	echo json_encode($response);

}

mysql_close($con);
?>


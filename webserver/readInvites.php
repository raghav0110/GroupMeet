<?php

/*
This script returns all events that A given user is invited too;
if the user does not exist it returns a json object with a "PASSED" index
and a "meesage" indicating the error.

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
$User = $_POST[user];

//make the Default Database GroupMeet

mysql_select_db("$Database", $con);

//check if user exists

$query = mysql_query("SELECT * FROM Users WHERE UserName = '$User'",$con);
if(mysql_num_rows($query) <= 0)
{

	$response["PASSED"] = 0;
	$response["message"] = "Invitee: $User doesn't exist";
	echo json_encode($response);
	exit();

}

//get all Events that User was invited too
$result = mysql_query("SELECT * FROM Invites WHERE UserName='$User'",$con);
//if failed
if(!$result){

	$response["PASSED"] = 0;
	$response["message"] = 'Error: ' . mysql_error($con);
	echo json_encode($response);
	exit();

}

//check for empty result
if(mysql_num_rows($result) > 0) {

$response["events"] = array();

while($row = mysql_fetch_array($result))
{
	//temp array
	$product = array();
	$product["EventName"] = $row['EventName'];
	//push single product into final response array
	array_push($response["events"], $product);

}

$response["PASSED"] = 1;
//send JSON response

echo json_encode($response);

} else {
//failed; send failure message;
	$response["PASSED"] = 0;
	$response["message"] = "No Invites Found";
	echo json_encode($response);

}

mysql_close($con);
?>


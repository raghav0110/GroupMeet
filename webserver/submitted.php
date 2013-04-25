<?php

/*
Checks the # of users who submitted



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
$Event = mysql_real_escape_string($_POST[event]);
//make the Default Database GroupMeet

mysql_select_db("$Database", $con);

//check if Event exists

$query = mysql_query("SELECT * from Events Where EventName='$Event'",$con);
if(mysql_num_rows($query) <= 0)
{

	$response["PASSED"] = 0;
	$response["message"] = "$Event doesn't exist in Database";
	echo json_encode($response);
	exit();

}

$Owner;
$query = mysql_query("SELECT UserName as owner FROM Events Where EventName='$Event'",$con);
if(!$query){
	//if failed
	$response["PASSED"] = 0;
	$response["message"] = 'Error: ' . mysql_error($con);
	echo json_encode($response);
	exit();
}

$row = mysql_fetch_assoc($query);
$Owner = $row['owner'];

//get the number of Invites
$numInvites;
$query = mysql_query("SELECT Count(*) AS num from Invites Where EventName='$Event'",$con);
if(!$query){
//if failed
	$response["PASSED"] = 0;
	$response["message"] = 'Error: ' . mysql_error($con);
	echo json_encode($response);
	exit();

}

$row = mysql_fetch_assoc($query);
$numInvites = $row['num'];

//get the number of submissions
$numSubmitted;
$query = mysql_query("SELECT Count(distinct UserName) AS num from Times Where EventName='$Event'",$con);
if(!$query){
//if failed
	$response["PASSED"] = 0;
	$response["message"] = 'Error: ' . mysql_error($con);
	echo json_encode($response);
	exit();

}
$row = mysql_fetch_assoc($query);
$numSubmitted = $row['num'];


$response["PASSED"] = 1;
$response["message"] = "$numSubmitted out of $numInvites have submitted times";
$response["submitted"] = $numSubmitted;
$response["numInvited"] = $numInvites;
//send JSON response

echo json_encode($response);

mysql_close($con);
?>



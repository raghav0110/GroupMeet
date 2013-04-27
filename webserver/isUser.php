<?php

/*
 Script to check if the User and Password is correct
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
$Password = md5($_POST[password]);
$User = mysql_real_escape_string($_POST[user]);

//make the Default Database GroupMeet

mysql_select_db("$Database", $con);

//check if necessary Posts exists exists

$query = mysql_query("SELECT * from Users Where UserName = '$User' AND Password = '$Password'",$con);
//if failed
if(!$query){

	$response["PASSED"] = 0;
	$response["message"] = 'Error: ' . mysql_error($con);
	echo json_encode($response);
	exit();

}

//check for empty result
if(mysql_num_rows($query) > 0) {


$response["PASSED"] = 1;
//send JSON response
$response["message"] = "$User was found";
echo json_encode($response);

} else {
//failed; send failure message;
	$response["PASSED"] = 0;
	$response["message"] = "incorrect $User or incorrect password";
	echo json_encode($response);

}

mysql_close($con);
?>


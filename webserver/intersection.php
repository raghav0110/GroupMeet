<?php

/*
Checks the # of users who submitted
if that equals the number of invites then it will send a JSON response with a final message
	otherwise it will compute the current intersection of times and send back the best times
	aka times with highest count
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
$User = mysql_real_escape_string($_POST[user]);
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

//actual query for script;
$result = mysql_query("SELECT DateTime AS num FROM Times WHERE EventName='$Event' group by DateTime HAVING count(*) ='$numSubmitted'",$con);
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
	$product["intersections"] = $row['num'];
	//push single product into final response array
	array_push($response["times"], $product);

}

$response["PASSED"] = 1;
if($Owner == $User)
	$response["Final"]= 0;
if($numSubmitted === $numInvites)
{
	if($Owner === $User) {
		$response["Final"] = 1;
	}
}
//send JSON response

echo json_encode($response);

} else {
//failed; send failure message;
	$response["PASSED"] = 0;
	$response["message"] = "No Intersections Found";
	echo json_encode($response);

}

mysql_close($con);
?>


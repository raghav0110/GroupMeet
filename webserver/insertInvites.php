<html>
<title> Insert Invitees </title>
<body>

<?php
/* This script takes a post argument invitee that is the user to invite and
 * the event to invite them to */

$invitee = $_POST[invitee];
$event = $_POST[event];
$response = array();

//check if invitee is valid email
if( !filter_var($invitee, FILTER_VALIDATE_EMAIL)) {

		$response["PASSED"] = 0;
		$response["message"] = "$invitee is not valid email";
		echo json_encode($response);
		exit();

}
// Default Database
$Database = "GroupMeet";
// make connection
$con = mysql_connect(':/homes/byrdc/mysqld/mysqld.sock', "root", "groupmeet");

if(!$con)
{
	$response["PASSED"] = 0;
	$response["message"] = 'Error: ' . mysql_error($con);
	echo json_encode($response);
	exit();

}

mysql_select_db("$Database", $con);
$query = mysql_query("SELECT * FROM Users WHERE UserName = '$invitee'", $con);
//if failed
if(mysql_num_rows($query) <= 0)
{
		$response["PASSED"] = 0;
		$response["message"] = "$invitee does not exist in Database";
		echo json_encode($response);
		exit();
}
$event = mysql_real_escape_string($event);
$query = mysql_query("SELECT * FROM Events WHERE EventName = '$event'", $con);
//if failed
if(mysql_num_rows($query) <= 0)
{
		$response["PASSED"] = 0;
		$response["message"] = "$event does not exist in Database";
		echo json_encode($response);
		exit();
}



$sql = "INSERT INTO Invites (EventName, UserName) VALUES ('$event','$invitee')";

if(!mysql_query($sql,$con))
{

	$response["PASSED"] = 0;
	$response["message"] = 'Error: ' . mysql_error($con);
	echo json_encode($response);
	exit();
}

$response["PASSED"] = 1;
$response["message"] = "$invitee was successfully invited to $event";
echo json_encode($response);

mysql_close($con);
?>
</body>
</html>


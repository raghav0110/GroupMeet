<?php
$User = ($_POST[user]);
$Password = md5($_POST[password]);
$response = array();
//check if given User is a valid email address

if( !filter_var($User, FILTER_VALIDATE_EMAIL)) {
	//create error response
	$response["PASSED"] = 0;
	$response["message"] = "Error $User was not a valid email format";

	// echo JSON response
	echo json_encode($response);
	exit();

}

//default database
$Database = "GroupMeet";

//connect to Database
$con = mysql_connect(':/homes/byrdc/mysqld/mysqld.sock', "root", "groupmeet");

if(!$con)
{

	$response["PASSED"] = 0;
    $response["message"] = 'Error: ' . mysql_error($con);

	// echo JSON response
	echo json_encode($response);
	exit();

}

//set Default database to $Database

mysql_select_db("$Database", $con);

//insert User and Password into User Table
$sql = "SELECT * FROM Users WHERE UserName='$User'";
$result = mysql_query($sql,$con);
if(!result)
{
	//if query fails send error
	$response["PASSED"] = 0;
    $response["message"] = 'Error: ' . mysql_error($con);

	// echo JSON response
	echo json_encode($response);
	exit();
}
if(mysql_num_rows($result) <= 0) {

	$response["PASSED"] = 0;
	$response["message"] = mysql_real_escape_string("UserName/Password was Incorrect");

	echo json_encode($response);
	mysql_close($con);
	exit();

}
	while($row = mysql_fetch_array($result)){
	if($row['Password'] != $Password)
	{
	$response["PASSED"] = 0;
	$response["message"] = mysql_real_escape_string("UserName/Password  was Incorrect");

	echo json_encode($response);
	mysql_close($con);
	exit();
	}

}
//send success response
header( 'Location: /interface.html' );
//$response["PASSED"] = 1;
//$response["message"] = "$User was successfully Logged In";

echo json_encode($response);

mysql_close($con);
?>


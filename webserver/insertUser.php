<html>
<title> Insert Users </title>
<body>
<?php
$User = mysql_real_escape_string($_POST[email]);
$Password = md5(mysql_real_escape_string($_POST[password]));
$response = array();
//check if given User is a valid email address

if( !filter_var($User, FILTER_VALIDATE_EMAIL)) {
	//create error response
	$response["PASSED"] = 0;
	$response["message"] = "Error username was not a valid email format";

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
$sql = "INSERT INTO Users VALUES ('$User', '$Password')";

if(!mysql_query($sql,$con))
{
	//if query fails send error
	$response["PASSED"] = 0;
    $response["message"] = 'Error: ' . mysql_error($con);

	// echo JSON response
	echo json_encode($response);
	exit();
}
//send success response
$response["PASSED"] = 1;
$response["message"] = "$User was successfully created";

echo json_encode($response);

mysql_close($con);
?>
</body>
</html>


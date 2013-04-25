<html>
<title> Read Events of a User </title>
<body>
<?php
/* This script takes Post arguments "user" and returns a json response
 * with all events that user has or returns an error json response */

$User = $_POST[user];
$response = array();

if( !filter_var($User, FILTER_VALIDATE_EMAIL)) {
	//if Owner isn't a valid email

	$response["PASSED"] = 0;
	$response["message"] = "Error: $User is not a valid email";

	//send error
	echo json_encode($response);
	exit();
}
//Default Database;
$Database = "GroupMeet";

//create connection
$con = mysql_connect(':/homes/byrdc/mysqld/mysqld.sock', "root", "groupmeet");

if(!$con)
{

		$response["PASSED"] = 0;
		$response["message"] = 'Error: ' . mysql_error($con);

		echo json_encode($response);
		exit();
}

mysql_select_db("$Database", $con);




$query = mysql_query("SELECT * FROM Users WHERE UserName = '$User'",$con);
  if(mysql_num_rows($query) <= 0)
	   {

		         $response["PASSED"] = 0;
		         $response["message"] = "$User doesn't exist in Database";
		         echo json_encode($response);
			      exit();

	   }

$result = mysql_query("SELECT * FROM Events WHERE UserName='$User'",$con);
if(!$result){

	$response["PASSED"] = 0;
	$response["message"] ='Error: ' . mysql_error($con);
	echo json_encode($response);
	exit();
}
if(mysql_num_rows($result) > 0) {
$response["events"] = array();
while($row = mysql_fetch_array($result))
  {
	  $products = array();
	  $products['EventName'] = $row['EventName'];
  		//push single product into final response array
	  array_push($response["events"], $products);

  }
$response["PASSED"] = 1;
//send JSON response
echo json_encode($response);
} else {
	$response["PASSED"] = 0;
	$response["message"] = "No events found for $User";
	echo json_encode($response);
}
mysql_close($con);
?>
</body>
</html>

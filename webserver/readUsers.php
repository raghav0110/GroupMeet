<?php

$Database = "GroupMeet";

$con = mysql_connect(':/homes/byrdc/mysqld/mysqld.sock', "root", "groupmeet");
if(!$con)
{
	die('Could not connect: ' . mysql_error());
}
mysql_select_db("$Database", $con);

$result = mysql_query("SELECT * FROM Users",$con);
if(!$result){

	die( 'Error: ' . mysql_error($con));

}

echo "<table border='1'>
<tr>
<th>UserName</th>
<th>Password(un-encrypted)</th>
</tr>";

while($row = mysql_fetch_array($result))
  {
  echo "<tr>";
  echo "<td>" . $row['UserName'] . "</td>";
  echo "<td>" . $row['Password'] . "</td>";
  echo "</tr>";
  }
echo "</table>";

mysql_close($con);
?>


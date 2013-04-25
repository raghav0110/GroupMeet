<?php
$con = mysql_connect(":/homes/wind-wiki/mysql/tmp/mysql.sock","root","wind");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

 mysql_select_db("my_wind", $con);

mysql_query("DELETE FROM Voltages WHERE windID > 1769");

mysql_close($con);
?>

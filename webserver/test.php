<html>
<head>
  <title>PHP Test</title>
 </head>
 <body>
<?php
mysql_connect(':/homes/byrdc/mysqld/mysqld.sock', "root", "groupmeet") or die(mysql_error());
echo "Connected to MySQL<br />";
?>
</body>
</html>

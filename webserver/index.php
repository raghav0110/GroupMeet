<!DOCTYPE html>
<html>
    <title>BeagleBone Data</title>
  <head>
	<meta http-equiv="refresh" content="20" />
    <!--LOAD the AJAX API-->
   <script type="text/javascript" src="https://www.google.com/jsapi"></script>
     <script type="text/javascript"> 

         function Home() {
         var msg1 = "Loading Homepage"
         var newMsg = msg1
         alert(newMsg)
         location = "http://wind.cs.purdue.edu"
         }
         function read() {
         var msg1 = "Displaying Data in plain text"
         var newMsg = msg1
         alert(newMsg)
         location = "read.php"
         }
         function insert() {
         var msg1 = "inserting data in to Database"
         var newMsg = msg1
         alert(newMsg)
         location = "insert.php"
         }

		 function timedRefresh(timeoutPeriod) {
		 setTimeout("location.reload(true);",timeoutPeriod);
		 }
      // Load the Visualization API Library and the line chart
  google.load('visualization', '1', {'packages':['annotatedtimeline']});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
        var data = new google.visualization.DataTable();
        data.addColumn('datetime', 'Date');
        data.addColumn('number', 'AIN0');
        data.addColumn('string', 'title1');
        data.addColumn('string', 'text1');
        data.addColumn('number', 'AIN1');
        data.addColumn('string', 'title2');
        data.addColumn('string', 'text2');
        data.addColumn('number', 'AIN2');
        data.addColumn('string', 'title3');
        data.addColumn('string', 'text3');
        data.addColumn('number', 'AIN3');
        data.addColumn('string', 'title4');
        data.addColumn('string', 'text4');
        data.addColumn('number', 'AIN4');
        data.addColumn('string', 'title5');
        data.addColumn('string', 'text5');
        data.addColumn('number', 'AIN5');
        data.addColumn('string', 'title6');
        data.addColumn('string', 'text6');
        data.addColumn('number', 'AIN6');
        data.addColumn('string', 'title7');
        data.addColumn('string', 'text7');
        data.addRows([
               <?php $con = mysql_connect(":/homes/wind-wiki/mysql/tmp/mysql.sock","root","wind");
    if(!$con)
    {
         die('Could not connect: ' . mysql_error());
    }
//select the database to read from
    mysql_select_db("my_wind", $con);

    $count = mysql_query("SELECT * FROM Voltages");
          //Count the number of rows in the Table
          $i=0;
          $x=0;
          while($counter = mysql_fetch_array($count))
          {
            $x++;
          }
    $s = $x-2;
    //fetch all data from the Voltage table
    $result = mysql_query("SELECT * FROM Voltages");
//While there is data display i
$t=10;
 while($row = mysql_fetch_array($result))
 { $epoch = (int)$row['Time']+$t;
$dt = new DateTime("@$epoch");
 $str= $dt->format('Y, m, d, i,h');
    echo "[";
    echo "new Date($str), " . $row['AIN0'] . ", undefined, undefined, " . $row['AIN1'] . ", undefined, undefined, " . $row['AIN2'] . ", undefined, undefined, " . $row['AIN3'] . ", undefined, undefined, " . $row['AIN4'] . ", undefined, undefined, " .  $row['AIN5'] . ", undefined, undefined, " .  $row['AIN6'] . ", undefined, undefined";
    echo "], \n";
        if($i === $s)
        {

            echo "[";
    echo "new Date($str), " . $row['AIN0'] . ", undefined, undefined, " . $row['AIN1'] . ", undefined, undefined, " . $row['AIN2'] . ", undefined, undefined, " . $row['AIN3'] . ", undefined, undefined, " . $row['AIN4'] . ", undefined, undefined, " .  $row['AIN5'] . ", undefined, undefined, " .  $row['AIN6'] . ", undefined, undefined";
            echo "]";
            break;
        }
$t+=10;   
 $i++;
    }
//close the connection to mysql
    mysql_close($con);

 ?> 
                ]);

        var chart = new google.visualization.AnnotatedTimeLine(document.getElementById('chart_div'));
        chart.draw(data, {displayAnnotations: true});
      }
     </script> 
  </head>
  <body>
 <div id='chart_div' style='width: 1270px; height: 500px;'></div>
<br><FORM> <CENTER>
  <INPUT Type="Button" Name="Homebutton" Value="Home" onclick="Home()">
  <INPUT Type="Button" Name="Readbutton"   Value="Plain Text Form" onclick="read()">
</FORM>
  </body>
</html>

<?php
/**
 * Created by PhpStorm.
 * User: Peter
 * Date: 26-11-2016
 * Time: 10:40
 */
include_once 'includes/mysql.inc.php';



if(isset($_POST['IP'])&&isset($_POST['sensor_id'])&&isset($_POST['temperature'])){
    echo "Variables set";

    $sql = "INSERT INTO ajouTemperatureRecords VALUES ($_POST[sensor_id],$_POST[temperature],NOW());";
    $result = mysqli_query($dbc, $sql) ;
    if($result){
        echo 'Success';

    }else{
        echo $dbc->error;
        echo mysqli_error($dbc);
    }
}

?>

<form action="" method="post">
    <input type="text" name="IP" value="localhost">
    <input type="text" name="sensor_id" value="1">
    <input type="text" name="temperature" value="20">
    <input type="submit">
</form>

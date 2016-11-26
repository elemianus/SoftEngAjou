<?php
/**
 * Created by PhpStorm.
 * User: Peter
 * Date: 25-11-2016
 * Time: 21:00
 */
include_once 'includes/mysql.inc.php';

$rootArray = array();
if (isset($_GET['sensor_id'])) {

    $sql = "SELECT * FROM `ajouTemperatureRecords` WHERE `ajouSensors.sensor_id` = $_GET[sensor_id] ORDER BY `timestamp` DESC LIMIT 1";
    $result = mysqli_query($dbc, $sql) ;

    if ($result) {
        while ($row = mysqli_fetch_assoc($result)) {

            $item = array(
                "temperature" => $row['temperature'],
                "timestamp" => $row['timestamp']
            );
            $rootArray[] = $item;
        }
    }else{
        echo $dbc->error;
        echo mysqli_error($dbc);
    }
}
$jsonArray = json_encode($rootArray);
echo $jsonArray;
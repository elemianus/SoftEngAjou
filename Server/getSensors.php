<?php
/**
 * Created by PhpStorm.
 * User: Peter
 * Date: 25-11-2016
 * Time: 21:00
 */
include_once 'includes/mysql.inc.php';

$rootArray = array();


$sql = "SELECT * FROM `ajouSensors`";
$result = mysqli_query($dbc, $sql);

if ($result) {
    while ($row = mysqli_fetch_assoc($result)) {

        $item = array(
            "sensor_id" => $row['sensor_id'],
            "sensor_name" => $row['sensor_name']
        );
        $rootArray[] = $item;
    }
} else {
    echo $dbc->error;
    echo mysqli_error($dbc);
}

$jsonArray = json_encode($rootArray);
echo $jsonArray;
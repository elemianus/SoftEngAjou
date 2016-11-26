<?php
/**
 * Created by PhpStorm.
 * User: Peter
 * Date: 26-11-2016
 * Time: 11:18
 */
if(isset($_POST['sensor_id'])&&isset($_POST['temperature'])){
    var_dump($_POST);
}else{
    echo 'variabes not set';
    var_dump($_POST);
}
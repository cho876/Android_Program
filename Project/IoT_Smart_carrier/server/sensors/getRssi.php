<?php
        $db_host = "14.49.36.196";
        $db_user = "root";
        $db_passwd = "qwerty1";
        $db_name = "carrier";

        $conn = mysqli_connect($db_host, $db_user, $db_passwd, $db_name);

        if(!$conn){
                echo "DB Connect Fail";
        }
        else{
                if(isset($_POST["u_rssi_left"]) && isset($_POST["u_rssi_right"])){
                        $rssiLeft = $_POST["u_rssi_left"];
                        $rssiRight = $_POST["u_rssi_right"];
                        $sql = "UPDATE SENSOR SET rssiLeft = '$rssiLeft', rssiRight = '$rssiRight'";
                        $result = mysqli_query($conn, $sql);

                        if(!$result){
                                echo "SQL FAIL";
                        }
                        else{
                                echo "SQL Success";
                        }
                }
                else{
                        echo "Data ERROR";
                }
        }
        mysqli_close($conn);
?>

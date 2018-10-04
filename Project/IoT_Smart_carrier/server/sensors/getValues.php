<?php
        $db_host = "14.49.36.196";
        $db_user = "root";
        $db_passwd = "qwerty1";
        $db_name = "carrier";

        $conn = mysqli_connect($db_host, $db_user, $db_passwd, $db_name);

        if(!$conn)
                echo "DB Connect Fail";

        else{
                $mode = $_POST['method'];

                mysqli_query($conn, "SET NAMES UTF8");
                $sql = "SELECT * FROM SENSOR";
                $result = mysqli_query($conn, $sql);

                if(!$result){
                        $response = array(
                                'retured_weight' => '',
                                'returned_distance' => '',
                                'returned_delivery' => '',
                                'returned_rssi' => '',
                                'response_code' => "-1");
                        echo json_encode($response);
                }
                else{
                        while($row = mysqli_fetch_array($result)){
                                $reponse = array(
                                        'returned_weight' => $row['weight'],
                                        'returned_distance' => $row['distance'],
                                        'returned_delivery' => $row['delivery_flag'],
                                        'returned_rssi' => $row['rssi'],
                                        'response_code' => "1");
                                echo json_encode($reponse);
                        }
                }
        }
        mysqli_close($conn);
?>

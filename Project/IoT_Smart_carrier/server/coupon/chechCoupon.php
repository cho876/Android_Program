<?php
        $db_host = "14.49.36.196";
        $db_user = "root";
        $db_passwd = "qwerty1";
        $db_name = "carrier";

        $conn = mysqli_connect($db_host, $db_user, $db_passwd, $db_name);

        if(!$conn)
                echo "DB Connect Fail";

        else{
                if(isset($_POST['userId']) && isset($_POST['method'])){
                        $coupon_table = COUPON_.$_POST['userId'];
                        $mode = $_POST['method'];

                        mysqli_query($conn, "SET NAMES UTF8");
                        $sql = "SELECT COUNT(*) FROM $coupon_table";
                        $result = mysqli_query($conn, $sql);

                        if($result){
                                 $row = mysqli_fetch_array($result);
                                  $response = array(
                                          'returned_kind' => $row[0],
                                          'returned_rate' => "EMPTY",
                                          'response_code' => "1"
                                  );
                        }
                        else{
                                $response = array(
                                       'returned_kind' => "EMPTY",
                                       'returned_rate' => "EMPTY",
                                       'response_code' => "-1"
                                );
                        }
                        echo json_encode($response);
                }
        }
        mysqli_close($conn);
?>

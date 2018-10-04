<?php
        $db_host = "14.49.36.196";
        $db_user = "root";
        $db_passwd = "qwerty1";
        $db_name = "carrier";

        $conn = mysqli_connect($db_host, $db_user, $db_passwd, $db_name);

        if(!$conn)
                echo "DB Connect Fail";

        else{
                if(isset($_POST['userId']) && isset($_POST['index'])){
                        $userId = $_POST['userId'];
                        $coupon_table = COUPON_.$_POST['userId'];
                        $index = $_POST['index'];

                        mysqli_query($conn, "SET NAMES UTF8");

                        $sql = "DELETE FROM $coupon_table WHERE id = $index";
                                $result = mysqli_query($conn, $sql);

                                $sql = "ALTER TABLE $coupon_table AUTO_INCREMENT=1";
                                $result = mysqli_query($conn, $sql);

                                $sql = "SET @CNT = 0";
                                $result = mysqli_query($conn, $sql);

                                $sql = "UPDATE $coupon_table SET $coupon_table.id=@CNT:=@CNT+1";
                                $result = mysqli_query($conn, $sql);

                                $response = array(
                                        'returned_kind' => $user_Id,
                                        'returned_rate' => "EMPTY",
                                        'response_code' => "1"
                                        );
                                echo json_encode($response);
                }
        }
        mysqli_close($conn);
?>

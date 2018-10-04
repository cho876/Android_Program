<?php
        $db_host = "14.49.36.196";
        $db_user = "root";
        $db_passwd = "qwerty1";
        $db_name = "carrier";

        $conn = mysqli_connect($db_host, $db_user, $db_passwd, $db_name);

        if(!$conn)
                echo "DB Connect Fail";

        else{
                mysqli_query($conn, "SET NAMES UTF8");

                $user_id = $_POST['userId'];

                $sql = "UPDATE DELIVERY SET status = 1 WHERE userID = '$user_id'";
                $result = mysqli_query($conn, $sql);

                if($result){
                        $response = array(
                                'returned_name' => $user_id,
                                'returned_address' => "EMPTY",
                                'retuened_flag' => "1",
                                'response_code' => "1"
                        );
                        echo json_encode($response);
                }
        }
        mysqli_close($conn);
?>

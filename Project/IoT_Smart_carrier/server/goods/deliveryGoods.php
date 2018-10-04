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

                $user_mode = $_POST['method'];
                $user_id = $_POST['userId'];

                $sql = "UPDATE SENSOR SET delivery_flag = 1";
                $result = mysqli_query($conn, $sql);

                $sql = "SELECT userName, userAddress FROM MEMBER WHERE userID = '$user_id'";
                $result = mysqli_query($conn, $sql);
                $row = mysqli_fetch_row($result);
                $user_name = $row[0];
                $user_address = $row[1];

                $sql = "INSERT INTO DELIVERY VALUES ('$user_id', '$user_name', '$user_address', 0)";
                $result = mysqli_query($conn, $sql);

                if($result){
                        $response = array(
                                'returned_name' => $user_name,
                                'returned_address' => $user_address,
                                'returned_flag' => "0",
                                'response_code' => "1"
                        );
                        echo json_encode($response);
                }
        }
        mysqli_close($conn);
?>

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

                $sql = "SELECT userID, userName, userAddress, status FROM DELIVERY";
                $result = mysqli_query($conn, $sql);

                if($result){
                        while($row = mysqli_fetch_array($result)){
                                $response[] = array(
                                        'returned_name' => $row['userName'],
                                        'returned_address' => $row['userAddress'],
                                        'returned_flag' => $row['status'],
                                        'response_code' => "1"
                                );
                                $item = $response;
                        }
                        echo json_encode($item);
                }
        }
        mysqli_close($conn);
?>

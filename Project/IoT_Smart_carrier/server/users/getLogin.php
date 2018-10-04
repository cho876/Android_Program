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
                $user_mode = $_POST['method'];

                if($user_mode == "login"){
                        $user_pw = $_POST['password'];
                        $sql = "SELECT * FROM MEMBER WHERE userID = '$user_id' AND userPW = '$user_pw'";
                }
                else if($user_mode == "logout")
                        $sql = "SELECT * FROM MEMBER WHERE userID = '$user_id'";

                $result = mysqli_query($conn, $sql);

                $row = mysqli_fetch_array($result);

                if(is_null($row)){
                        $response = array('returned_username' => "EMPTY",
                                        'response_code' => "-1");
                        echo json_encode($response);
                }
                else{
                        if($user_mode == "logout"){
                                $sql2 = "UPDATE MEMBER SET status = 0 WHERE userID = '$user_id'";
                                $result = mysqli_query($conn, $sql2);
                                $response = array('returned_username' => $user_id,
                                                'response_code' => "1");
                        }
                        else if($user_mode == "login"){
                                $sql2 = "UPDATE MEMBER SET status = 1 WHERE userID = '$user_id'";
                                $result = mysqli_query($conn, $sql2);
                                $response = array('returned_username' => $user_id,
                                                'response_code' => "1");
                        }
                        echo json_encode($response);
                }
        }
        mysqli_close($conn);
?>

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
                        $user_id = GOODS_.$_POST['userId'];
                        $index = $_POST['index'];

                        mysqli_query($conn, "SET NAMES UTF8");
                        $sql = "DELETE FROM $user_id WHERE id = $index";
                        $result = mysqli_query($conn, $sql);

                        if(!$result){
                                        $response = array(
                                        'returned_name' => $user_id,
                                        'returned_kind' => "",
                                        'returned_price' => "",
                                        'response_code' => "-1"
                                        );
                                echo json_encode($response);

                        }

                        else{
                                $sql = "ALTER TABLE $user_id AUTO_INCREMENT=1";
                                $result = mysqli_query($conn, $sql);
                                $sql = "SET @CNT = 0";
                                $result = mysqli_query($conn, $sql);
                                $sql = "UPDATE $user_id SET $user_id.id=@CNT:=@CNT+1";
                                $result = mysqli_query($conn, $sql);

                                $response = array(
                                        'returned_name' => $user_id,
                                        'returned_kind' => "",
                                        'returned_price' => "",
                                        'response_code' => "1"
                                        );
                                echo json_encode($response);
                        }
                }
        }
        mysqli_close($conn);
?>

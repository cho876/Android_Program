<?php
        $db_host = "14.49.36.196";
        $db_user = "root";
        $db_passwd = "qwerty1";
        $db_name = "carrier";

        $conn = mysqli_connect($db_host, $db_user, $db_passwd, $db_name);

        if(!$conn)
                echo "DB Connect Fail";

        else{
                $user_table = GOODS_.$_POST['userId'];

                $sql = "SELECT userSex, COUNT(*) FROM MEMBER GROUP BY userSex";
                $result = mysqli_query($conn, $sql);
                $result2 = mysqli_query($conn, $sql);
                if($result){
                        $row = mysqli_fetch_array($result);
                        if(!$row){
                                $response[] = array(
                                        'returned_category' => "EMPTY",
                                        'returned_count' => "EMPTY",
                                        'response_code' => "-1"
                                );
                                $item = response;
                        }
                        else{
                                while($row = mysqli_fetch_array($result2)){
                                        $response[] = array(
                                                'returned_category' => $row['userSex'],
                                                'returned_count' => $row['COUNT(*)'],
                                                'response_code' => "1"
                                        );
                                        $item = $response;
                                }
                        }
                        echo json_encode($item);
                }
        }
        mysqli_close($conn);
?>

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
                        $sql = "SELECT category, rate FROM $coupon_table";
                        $result = mysqli_query($conn, $sql);

                        if($result){
                                $stack = array();
                                while($row = mysqli_fetch_array($result))
                                        array_push($stack, $row);

                                foreach($stack as $items){
                                        $response[] = array(
                                                 'returned_kind' => $items[0],
                                                 'returned_rate' => $items[1],
                                                 'response_code' => "1"
                                        );
                                        $result = $response;
                                }
                                echo json_encode($result);
                        }

                        else{
                                $response[] = array(
                                       'returned_kind' => "EMPTY",
                                       'returned_rate' => "EMPTY",
                                       'response_code' => "-1"
                                );
                                $item = $response;
                                echo json_encode($item);
                        }
                }
        }
        mysqli_close($conn);
?>

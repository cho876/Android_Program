<?php
        $db_host = "14.49.36.196";
        $db_user = "root";
        $db_passwd = "qwerty1";
        $db_name = "carrier";

        $conn = mysqli_connect($db_host, $db_user, $db_passwd, $db_name);

        if(!$conn)
                echo "DB Connect Fail";

        else{
                $user_id = GOODS_.$_POST['userId'];
                mysqli_query($conn, "SET NAMES UTF8");
                $sql = "SELECT goodsName, goodsCategory, goodsPrice FROM $user_id";
                $result = mysqli_query($conn, $sql);

                if($result){
                        while($row = mysqli_fetch_array($result)){
                                $resultArray[] = array(
                                        'returned_name' => $row['goodsName'],
                                        'returned_kind' => $row['goodsCategory'],
                                        'returned_price' => $row['goodsPrice'],
                                        'response_code' => "1"
                                );
                                $item = $resultArray;
                        }
                        echo json_encode($item);
                }
        }
        mysqli_close($conn);
?>

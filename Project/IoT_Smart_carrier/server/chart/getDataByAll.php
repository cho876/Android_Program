<?php
        $db_host = "14.49.36.196";
        $db_user = "root";
        $db_passwd = "qwerty1";
        $db_name = "carrier";

        $conn = mysqli_connect($db_host, $db_user, $db_passwd, $db_name);

        if(!$conn)
                echo "DB Connect Fail";

        else{
                $method = $_POST['method'];
                $flag = -1;

                if($method == "category"){
                        $sql = "SELECT goodsCategory, goodsDetail, count FROM SELL_HISTORY GROUP BY goodsCategory";
                        $flag = 1;
                }
                else if($method == "detail"){
                        $category = $_POST['category'];
                        $sql = "SELECT goodsCategory, goodsDetail, count FROM SELL_HISTORY WHERE goodsCategory = '$category' GROUP BY goodsDetail";
                        $flag = 2;
                }

                $result = mysqli_query($conn, $sql);
                if($result){
                        $row = mysqli_fetch_array($result);

                        if(!$row){
                                $response[] = array(
                                        'returned_category' => "EMPTY",
                                        'returned_count' => "EMPTY",
                                        'returned_detail' => "EMPTY",
                                        'response_code' => $flag
                                );
                                $item = response;
                        }
                        else{
                                while($row = mysqli_fetch_array($result)){
                                        $response[] = array(
                                                'returned_category' => $row[0],
                                                'returned_detail' => $row[1],
                                                'returned_count' => $row[2],
                                                'response_code' => $flag
                                        );
                                        $item = $response;
                        }
                        else{
                                while($row = mysqli_fetch_array($result)){
                                        $response[] = array(
                                                'returned_category' => $row[0],
                                                'returned_detail' => $row[1],
                                                'returned_count' => $row[2],
                                                'response_code' => $flag
                                        );
                                        $item = $response;
                                }
                        }
                        echo json_encode($item);
                }
        }
        mysqli_close($conn);
?>

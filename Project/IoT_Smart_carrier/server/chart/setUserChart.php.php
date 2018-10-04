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
                $user_table = GOODS_.$_POST['userId'];
                $flag = -1;

                if($method == "category"){
                        $sql = "SELECT goodsCategory, goodsDetail, COUNT(*) FROM $user_table GROUP BY goodsCategory";
                        $flag = 1;
                }
                else if($method == "detail"){
                        $category = $_POST['category'];
                        $sql = "SELECT goodsCategory, goodsDetail, COUNT(*) FROM $user_table WHERE goodsCategory = '$category' GROUP BY goodsDetail";
                        $flag = 2;
                }

                $result = mysqli_query($conn, $sql);
                if($result){
                           while($row = mysqli_fetch_array($result)){
                                        $response[] = array(
                                                'returned_category' => $row['goodsCategory'],
                                                'returned_detail' => $row['goodsDetail'],
                                                'returned_count' => $row['COUNT(*)'],
                                                'response_code' => $flag
                                        );
                                        $item = $response;
                        }
                        echo json_encode($item);
                }
        }
        mysqli_close($conn);
?>

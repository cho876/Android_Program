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

                $mode = $_POST['method'];
                $user_id = $_POST['userId'];
                $user_pw = $_POST['password'];
                $user_name = $_POST['name'];
                $user_address = $_POST['address'];
                $user_email = $_POST['email'];
                $user_sex = $_POST['sex'];
                $user_age = $_POST['age'];
                $goods_table = GOODS_.$user_id;
                $coupon_table = COUPON_.$user_id;

                $sql = "INSERT INTO MEMBER VALUES ('$user_id', '$user_name', '$user_email', '$user_pw', 0, '$user_age', '$user_sex', '$user_address', 0)";
                $result = mysqli_query($conn, $sql);

                if(!$result){
                        $response = array('returned_username' => "ERROR",
                                        'returned_password' => "ERROR",
                                        'response_code' => "-1");
                        echo json_encode($response);
                }
                else{
                        $sql2 = "CREATE TABLE $goods_table(
                                        id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                        goodsId int NOT NULL,
                                        goodsName char(20) NOT NULL,
                                        goodsCategory char(10) NOT NULL,
                                        goodsDetail char(10) NOT NULL,
                                        goodsPrice int NOT NULL) default charset=utf8 collate utf8_general_ci";
                        $result2 = mysqli_query($conn, $sql2);

                        $sql2 = "CREATE TABLE $coupon_table(
                                        id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                        category char(10) NOT NULL,
                                        rate int NOT NULL) default charset=utf8 collate utf8_general_ci";
                        $result2 = mysqli_query($conn, $sql2);

                        if(!$result){
                                $response = array('returned_username' => "ERROR",
                                           'returned_password' => "ERROR",
                                           'response_code' => "0");
                        }
                        else{
                                $response = array('returned_username' => '$user_id',
                                           'returned_password' => '$user_pw',
                                           'response_code' => "1");
                        }
                        echo json_encode($response);
                }
        }
        mysqli_close($conn);
?>


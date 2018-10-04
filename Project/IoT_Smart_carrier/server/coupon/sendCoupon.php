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
                $category = $_POST['category'];
                $rate = $_POST['rate'];

                mysqli_query($conn, "SET NAMES UTF8");

                /*if($method == "age")
                        $sql = "SELECT userID FROM MEMBER WHERE userAge = '$value'";
                else if($method == "sex")
                        $sql = "SELECT userID FROM MEMBER WHERE userSex = '$value'";*/
                $sql = "SELECT userID FROM MEMBER";
                $result = mysqli_query($conn, $sql);

                $stack = array();
                while ($row = mysqli_fetch_array($result)){
                        array_push($stack, $row[0]);
                }

                foreach($stack as $userId){
                        $coupon_table = COUPON_.$userId;
                        $sql = "INSERT INTO $coupon_table(category, rate) VALUES ('$category','$rate')";
                        $result = mysqli_query($conn, $sql);
                }

                if($result){
                        $response = array(
                                'returned_kind' => "$row",
                                'returned_rate' => "SUCCESS",
                                'returned_code' => "1");
                }
                else{
                        $response = array(
                                'returned_kind' => "EMPTY",
                                'returned_rate' => "FAIL",
                                'returned_code' => "-1");
                }
                echo json_encode($response);
        }
        mysqli_close($conn);
?>

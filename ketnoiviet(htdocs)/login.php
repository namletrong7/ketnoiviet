<?php 
	include "connect.php";
	$phonenumber = $_POST['phonenumber'];
	$password = $_POST['password'];
     // thực hiện truy vấn 
	$query = "SELECT * FROM users WHERE phonenumber = '$phonenumber' AND password = '$password'";

	$data = mysqli_query($conn, $query);
	$count = mysqli_num_rows($data);
	// nếu có dữ liệu thì 
	if($count>0){
		echo "1";
	} 
	else{
		echo "0";
	}
?>
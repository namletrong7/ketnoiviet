<?php 
  // check tài khoản
	include "connect.php";

	// nhận dữ liệu được gửi về
	$phonenumber = $_POST['phonenumber'];
	$manguserinfo = array();
     // tạo câu lệnh truy vấn kiểm tra xem số điện thoại nhập vào đã tồn tại chưa 
	$query = "SELECT * FROM users WHERE phonenumber = $phonenumber";
	$data = mysqli_query($conn, $query);
	$count = mysqli_num_rows($data);
	if($count>0){   
		echo "Tontai";
	} 
	else{
		echo "Chuatontai";
	}
?>
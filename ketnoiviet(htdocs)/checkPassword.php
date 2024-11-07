<?php 

	include "connect.php"; 
	$idusers = $_POST['idusers'];
	$oldpassword = md5($_POST['oldpassword']);
	
     // thực hiện truy vấn 
	$query = "SELECT * FROM users WHERE idusers = '$idusers' AND password = '$oldpassword'";

	$data = mysqli_query($conn, $query);
	$count = mysqli_num_rows($data);
	// nếu có dữ liệu thì 
	if($count>0){
	  echo "true";
	} 
	else{
		echo "false";
	}

 ?>
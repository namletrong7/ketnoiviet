<?php 
     
	include "connect.php";
	// nhận dữ liệu được gửi
	$users = $_POST['users'];
	$userfollow	= $_POST['userfollow'];

	$chon = "SELECT * FROM followers WHERE followers.users = $users AND followers.userfollow = $userfollow";

	$datachon = mysqli_query($conn, $chon);

	if(mysqli_num_rows($datachon) == 0){
		echo "false";
	}
	if(mysqli_num_rows($datachon) == 1){
		echo "true";
	}
?>
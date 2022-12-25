<?php 

	include "connect.php"; 
    $phoneUser = $_POST['phoneUser'];
	$matKhauMoi = $_POST['matKhauMoi'];
	// $phoneUser ="0337356550";
	// $matKhauMoi = "123";
	// $phoneUser ="0337356550";

	$query = "UPDATE users SET password = '$matKhauMoi' WHERE phonenumber='$phoneUser'";

	$data = mysqli_query($conn, $query);
	if($data){
		echo "Done";
	} 
	else{
		echo "Error";
	}

 ?>
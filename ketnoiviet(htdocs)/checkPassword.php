<?php 

	include "connect.php"; 
	$phoneUser = $_POST['phoneUser'];
	// $phoneUser ="0337356550";
	$query ="select password from users where phonenumber='$phoneUser'";
	$data = mysqli_query($conn, $query);
   	while ($row = $data->fetch_assoc()) {
   		echo $row['password'];

  }

 ?>
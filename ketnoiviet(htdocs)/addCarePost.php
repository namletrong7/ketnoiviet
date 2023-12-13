<?php 
	include "connect.php";
	$idPost = $_POST['idPost'];  // idPost
	$phoneUser = $_POST['phoneUser'];   //người thực hiện thông báo
  

	$chon = "SELECT * FROM carepost WHERE phoneUser = $phoneUser  and idPost = $idPost";
     // thuc hien cau lenh truy van
	$datachon = mysqli_query($conn, $chon);
    if(mysqli_num_rows($datachon) == 0){
        $query = "INSERT INTO carepost VALUES (null,'$idPost','$phoneUser')";
		$data = mysqli_query($conn, $query);

	}
    
 ;


	
	
	
?>
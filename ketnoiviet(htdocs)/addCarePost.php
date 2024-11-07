<?php 
	include "connect.php";
	$idPost = $_POST['idPost'];  // idPost
	$idusers = $_POST['idusers'];   //người thực hiện thông báo
  

	$chon = "SELECT * FROM carepost WHERE idusers = '$idusers'  and idPost = '$idPost' ";
     // thuc hien cau lenh truy van
	$datachon = mysqli_query($conn, $chon);
    if(mysqli_num_rows($datachon) == 0){
        $query = "INSERT INTO carepost VALUES (null,'$idPost','$idusers')";
		$data = mysqli_query($conn, $query);

	}
    
 ;


	
	
	
?>
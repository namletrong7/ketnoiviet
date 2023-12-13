<?php 
	include "connect.php";
	// lay du users nguoi thuc hien theo doi
	$idReadNotifi = $_POST['idReadNotifi'];
     // tao cau lenh truy lee
	$chon = "UPDATE readnotifi SET hasRead = 'true'  WHERE idReadPost = $idReadNotifi";
     // thuc hien cau lenh truy van
	$datachon = mysqli_query($conn, $chon);
	
	
	
	
?>
<?php 
	include "connect.php";
	$idpost = $_POST['idpost'];
	$idusers	= $_POST['idusers'];
    $status = "1"; 

	$chon = "SELECT * FROM react WHERE react.idpost = $idpost AND react.idusers = $idusers";

	$datachon = mysqli_query($conn, $chon);

	if(mysqli_num_rows($datachon) == 0){  // nếu chưa thích bài viết n
		echo "false";
	}
	if(mysqli_num_rows($datachon) == 1){  // nếu đã thích bài viết
		echo "true";
	}
?>
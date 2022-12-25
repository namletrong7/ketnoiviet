<?php 
	include "connect.php";
	// lấy tất cả địa danh tại bảng post
	$query = "SELECT post.nameplace FROM post";
	$data = mysqli_query($conn, $query);
	// tạo ra mảng tỉnh tại chứa tỉnh
	$mangprovince = array();
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($mangprovince, new Nameplace(
			$row['nameplace']));
	}
	echo json_encode($mangprovince);
	class Nameplace{
		 function __construct ($nameplace){
			$this->nameplace 				= $nameplace;
		}
	}
?>
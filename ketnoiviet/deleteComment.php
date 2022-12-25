<?php 
	include "connect.php";
	// nhan id cua bình luận
	$id = $_POST['id'];
    //$id = '1';
	

	$query = "DELETE FROM comment WHERE id = '$id'";
	$data = mysqli_query($conn, $query);
	if($data){
		echo "Done";
	} 
	else{
		echo "Error";
	}
?>
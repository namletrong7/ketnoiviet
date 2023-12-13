<?php 
	include "connect.php";
	$idpost = $_POST['idpost'];
	$phoneuser	= $_POST['phoneuser'];
    $status = "1";   // trạng thái là 1 thì ban đầu chưa thichd bài viết đó 


	$chon = "SELECT * FROM react WHERE react.idpost = $idpost AND react.phoneuser = $phoneuser";

	$datachon = mysqli_query($conn, $chon);
     // nếu người đó chưa like bài viết này thì thực hiện thích bài viết dó
	if(mysqli_num_rows($datachon) == 0){
		$them = "INSERT INTO react(id, idpost, phoneuser, status) VALUES (null,$idpost,'$phoneuser',$status)";
		$data = mysqli_query($conn, $them);
		if($data){
			echo "0";
		} 
		else{
			echo "loi khi them";
		}
	}
	// nếu đã thích bài viết đó thì truy vấn sẽ có 1 hàng
	if(mysqli_num_rows($datachon) == 1){
		$xoa = "DELETE FROM react WHERE idpost = $idpost and phoneuser = $phoneuser";
		$data1 = mysqli_query($conn, $xoa);
		if($data1){
			echo "Hủy thích bài viết !";
		} 
		else{
			echo "loi khi xoa";
		}
	}

	
	
	
?>
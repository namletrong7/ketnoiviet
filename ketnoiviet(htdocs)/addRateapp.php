<?php 
     // phần đánh giá ứng dụng
	include "connect.php";
	// lấy dữ liệu được gửi
	$phoneuser	= $_POST['phoneuser'];
	$star	= $_POST['star'];
	$content	= $_POST['content'];

   // thực hiện truy vấn kiểm tra xem số thuê bao đó đã thực hiện đánh giá chưa
	$chon = "SELECT * FROM rateapp WHERE rateapp.phoneuser = $phoneuser";

	$datachon = mysqli_query($conn, $chon);
    // nếu thục hiện truy vấn mà ko có hàng nào thì thực hiện thêm đánh giá app
	if(mysqli_num_rows($datachon) == 0){
		$them = "INSERT INTO rateapp(id, phoneuser, star, content) VALUES (null,'$phoneuser','$star','$content')";
		$data = mysqli_query($conn, $them);
		if($data){
			echo "1";
		} 
		else{
			echo "loi khi up";
		}
	}
	// nếu đã đánh giá app thì thực hiện update lại đánh giá app
	if(mysqli_num_rows($datachon) == 1){
		$updateLaiDanhGia = "UPDATE rateapp SET rateapp.star = '$star', rateapp.content = '$content' WHERE phoneuser = '$phoneuser' ";
		$data1 = mysqli_query($conn, $updateLaiDanhGia);
		if($data1){
			echo "0";
		} 
		else{
			echo "loi khi cap nhat";
		}
	}

	
	
	
?>
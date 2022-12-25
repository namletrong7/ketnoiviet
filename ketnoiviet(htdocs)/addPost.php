<?php 
	include "connect.php";
	// lấy các biến được gửi 
	$nameplace = $_POST['nameplace'];  // lấy địa điểm
	$province	= $_POST['province'];  // lấy tỉnh
	$district	= $_POST['district'];   // lấy quận huyện 
	$ward	= $_POST['ward'];         // lấy xã
	$address	= $_POST['address'];     // địa chỉ cụ thể  
	$description	= $_POST['description'];  // mô tả
	$content	= $_POST['content'];            // nội dung
     // láy các đường dẫn hình ảnh được gửi
    $target_dir = "images/";
	$image1	= $_POST['image1'];
    $image2	= $_POST['image2'];
    $image3	= $_POST['image3'];
    $image4	= $_POST['image4'];
    // lấy thông tin được gửi
    $phoneuser	= $_POST['phoneuser'];
	$datepost	= $_POST['datepost'];
	$status	= $_POST['status'];
   
     // tạo tên ảnh để nhập vào đâu đát 
    $imageStore1 = rand()."_".time().".jpeg";
    $target_dir1 = $target_dir."/".$imageStore1;
    file_put_contents($target_dir1, base64_decode($image1));

    $imageStore2 = rand()."_".time().".jpeg";
    $target_dir2 = $target_dir."/".$imageStore2;
    file_put_contents($target_dir2, base64_decode($image2));

    $imageStore3 = rand()."_".time().".jpeg";
    $target_dir3 = $target_dir."/".$imageStore3;
    file_put_contents($target_dir3, base64_decode($image3));

    $imageStore4 = rand()."_".time().".jpeg";
    $target_dir4 = $target_dir."/".$imageStore4;
    file_put_contents($target_dir4, base64_decode($image4));
	// 
    $result = array();
	$query = "INSERT INTO post(id, nameplace, province, district, ward, address, description, content, image1, image2, image3, image4, phoneuser, datepost, status) VALUES (null,'$nameplace','$province','$district','$ward','$address','$description', '$content', '$imageStore1', '$imageStore2', '$imageStore3', '$imageStore4','$phoneuser','$datepost','$status')";
	
	$data = mysqli_query($conn, $query);
	if($data){
		echo "Done";
	} 
	else{
		echo "Error";
	}
?>
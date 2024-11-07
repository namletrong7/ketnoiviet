<?php 
	include "connect.php";
	$idPost = $_POST['idPost'];  // idPost
	$idusers = $_POST['idusers'];   //người thực hiện thông báo
    $timeNotifi = $_POST['timeNotifi'];
    $typeNotifi = $_POST['typeNotifi'];
    



   // câu lệnh truy vấn lấy ra ảnh đại diện của gười thực hiện thông báo này 
	$chon = "SELECT * FROM users WHERE idusers = '$idusers' ";
	$datachon = mysqli_query($conn, $chon);

     $length = 10; // Độ dài của chuỗi ngẫu nhiên
     $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'; // Ký tự có thể xuất hiện trong chuỗi
     $idNotifi = '';
     for ($i = 0; $i < $length; $i++) {
    $idNotifi .= $characters[rand(0, strlen($characters) - 1)];
          }

   
	 // lấy ảnh đại diện 
     if (mysqli_num_rows($datachon) > 0) {
        while($row = mysqli_fetch_assoc($datachon)) {
            $avatar= $row['imageuser'];  // láy ra ảnh đại diên
            /// tạo thông báo
            $query = "INSERT INTO notifi VALUES ('$idNotifi','$idPost','$idusers','$avatar','$typeNotifi',null, '$timeNotifi')";
            $data = mysqli_query($conn, $query);

            // nó sẽ lấy ra tất cả những người quan tâm post này để tạo readPost 
            $humanCarePost = "SELECT idusers FROM carepost WHERE idPost = $idPost";
	        $dataHumanCarePost = mysqli_query($conn, $humanCarePost);
              // thực hiện tạo readPost cho tất cả mọi người 
              while($row = mysqli_fetch_assoc($dataHumanCarePost)) {
               $idusers= $row['idusers'];  // láy phone những người uqan tâm post đo 
               $query = "INSERT INTO readnotifi VALUES (null,'$idNotifi','$idusers','false')";
               $data = mysqli_query($conn, $query);
              }


        
        }
     }
	
	
	
?>
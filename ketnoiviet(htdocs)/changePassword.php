<?php 

	include "connect.php"; 
	include "testAES.php";
    $idusers = $_POST['idusers'];
	$matKhauMoi = md5($_POST['matKhauMoi']);
	// $idusers = "66";
	// $matKhauMoi = md5("Lethitrang1@");
	 
	// láy ra các thông tin đã bị mã hóa và giải mã ngược lại vs mk cũ 
    $query = "SELECT * FROM users  WHERE idusers='$idusers'";

	$data = mysqli_query($conn, $query);
    if ($data) {
		
		while ($row = mysqli_fetch_assoc($data)) {
			$passwordOld = $row['password'];
			$emailOld = giaiMa($passwordOld,$row['email']);  
			$phonenumberOld = giaiMa($passwordOld,$row['phonenumber']);  
		}
	
		// Giải phóng bộ nhớ sau khi sử dụng
		mysqli_free_result($data);
	}

	//mã hóa lại thông tin theo mk mới 
	$emailMoi = maHoa($matKhauMoi,$emailOld);  
	$phonenumberMoi = maHoa($matKhauMoi,$phonenumberOld);  

	

	$query1 = "UPDATE users SET password = '$matKhauMoi' , email = '$emailMoi' , phonenumber = '$phonenumberMoi'  WHERE idusers='$idusers'";

	$data1 = mysqli_query($conn, $query1);
	if($data1){
		echo "Done";
	} 
	else{
		echo "Error";
	}
  // Đóng kết nối sau khi sử dụng
   mysqli_close($conn);
 ?>
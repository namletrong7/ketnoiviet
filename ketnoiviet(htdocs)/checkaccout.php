<?php 
// Done
  // check tài khoản
	include "connect.php";
	include "testAES.php";
	// nhận dữ liệu được gửi về đang còn nguyên chưa bị mã hóa
	$phonenumber = $_POST['phonenumber'];

      
	// check xem đã có sdt nào tồn tại hay chưa bawngfd cách duyệt vòng lặp
	$query = "SELECT * FROM users ";
	$data = mysqli_query($conn, $query);
	while ($row = mysqli_fetch_assoc($data)) {
		$phoneNumberDaGiaiMa = giaiMa($row['password'],$row['phonenumber']);
		if($phoneNumberDaGiaiMa==$phonenumber){
			echo "Tontai";
			break;
		}
	}
?>
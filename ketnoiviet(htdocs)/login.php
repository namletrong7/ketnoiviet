<?php 
// Done
	include "connect.php";
	include "testAES.php";
	$password = md5($_POST['password']);;// nhận và mã hóa ngay mật khẩu

	$phonenumber = maHoa($password,$_POST['phonenumber']);// mã hóa  sdt luôn 
     // thực hiện truy vấn 
	$query = "SELECT * FROM users WHERE phonenumber = '$phonenumber' AND password = '$password'";

	$data = mysqli_query($conn, $query);
	$count = mysqli_num_rows($data);
	// nếu có dữ liệu thì 
	if($count>0){
		$row = mysqli_fetch_assoc($data);
        echo $row['idusers'];
	} 
	else{
		echo "loginThatBai";
	}
?>
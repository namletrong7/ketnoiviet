<?php 
// Done
	include "connect.php";
	include "testAES.php";
	// mã hóa md5 và sử dụng passowrd làm key 
	$password	= md5($_POST['password']);
	// mã hóa số điện thoại 
    $phonenumber = maHoa($password, $_POST['phonenumber']);
	// các thông tin này khong cần mã hóa
	$nameuser	=   $_POST['nameuser'];
	$datecreate =   $_POST['datecreate'];


	$query = "INSERT INTO users (idusers,nameuser, birthday, gender, imageuser, cover, email, phonenumber, status, hometown, money, datecreate, password, isHomeTown, isGender, isBirthday, isEmail) VALUES (null,'$nameuser','','','831407957_1636091120.jpeg','banner.png','','$phonenumber','','',0,'$datecreate','$password','true','true','true','true')";
	$data = mysqli_query($conn, $query);
	if($data){
		echo "Done";
	} 
	else{
		echo "Error";
	}
?>
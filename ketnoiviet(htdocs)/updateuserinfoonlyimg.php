<?php 
	include "connect.php";include "testAES.php";
	$target_dir = "users/";
	$password = md5($_POST['password']);
	$idusers = $_POST['idusers'];
	$name_user = $_POST['nameuser'];
	$birth_day = $_POST['birthday'];
	$gen_der = $_POST['gender'];

	$image_user = $_POST['imageuser'];

	$imageStore1 = rand()."_".time().".jpeg";
    $target_dir1 = $target_dir."/".$imageStore1;
    file_put_contents($target_dir1, base64_decode($image_user));

	// mã hóa thông tin email của người dùng
    $e_mail = maHoa($password,$_POST['email']);
	$sta_tus = $_POST['status'];
	$home_town =$_POST['hometown'];

	$manguser = array();
	$query = "UPDATE users 
				SET nameuser = '$name_user', 
				birthday = '$birth_day',  
				gender = '$gen_der', 
				imageuser = '$imageStore1',
				email = '$e_mail', 
				status = '$sta_tus', 
				hometown = '$home_town'
				WHERE idusers = '$idusers' ";
	$data 			= mysqli_query($conn, $query);
	$data = mysqli_query($conn, $query);
	if($data){
		echo "Done";
	} 
	else{
		echo "Error";
	}
?>

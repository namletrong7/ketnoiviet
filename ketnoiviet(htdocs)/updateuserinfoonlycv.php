<?php 
	include "connect.php";
	include "testAES.php";
	$target_dir = "users/";
	$password = md5($_POST['password']);
	$idusers = $_POST['idusers'];
	$name_user = $_POST['nameuser'];
	$birth_day = $_POST['birthday'];
	$gen_der = $_POST['gender'];

	$co_ver = $_POST['cover'];

    $imageStore2 = rand()."_".time().".jpeg";
    $target_dir2 = $target_dir."/".$imageStore2;
    file_put_contents($target_dir2, base64_decode($co_ver));

// mã hóa thông tin email của người dùng
    $e_mail = maHoa($password,$_POST['email']);
	$sta_tus = $_POST['status'];
	$home_town =$_POST['hometown'];

	$manguser = array();
	$query = "UPDATE users 
				SET nameuser = '$name_user', 
				birthday = '$birth_day',  
				gender = '$gen_der', 
				cover = '$imageStore2',
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

<?php 
	include "connect.php";
	// nhan id cua bai post
	$idpost = $_POST['idpost'];
	// nhaan sdt cua user o bai post
	$idusers	= $_POST['idusers'];
	// noi dung bai viet
    $content = $_POST['content']; 
 //    $idpost = '3';
	// $phoneuser	= '0376701749';
 //    $content = 'Tuyệt vời luôn'; 

	$query = "INSERT INTO comment (id,idpost,idusers,content) VALUES (null,'$idpost','$idusers','$content')";
	$data = mysqli_query($conn, $query);
	if($data){
		echo "Done";
	} 
	else{
		echo "Error";
	}
?>
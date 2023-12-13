<?php 
	include "connect.php";
    $phonenumber = $_POST['phonenumber'];
    $mangfollow = array();
	$query = "SELECT * FROM followers WHERE users='$phonenumber'";
	$data = mysqli_query($conn, $query);
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($mangfollow, new followers(
			$row['id'],
			$row['users'],
			$row['userfollow']
			
		));
	}
	echo json_encode($mangfollow);
	class followers{
		function __construct ($id,$users,$userfollow){
			$this->id 			=$id;
			$this->users 			=$users;
			$this->userfollow 		=$userfollow;
			
		}
	}
?>

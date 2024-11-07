<?php 
// danh sách người like bài viết 
	include "connect.php";
	$idpost = $_POST['idpost'];
	$query = "SELECT react.idpost,react.idusers,users.nameuser FROM react,users WHERE react.idpost = '$idpost' and react.idusers = users.idusers";
	
	$data = mysqli_query($conn, $query);
	$mangLike = array();
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($mangLike, new like(
			$row['idpost'],
            $row['idusers'],
            $row['nameuser']
			));
	}
	echo json_encode($mangLike);
	
	class like{
		 function __construct ($idpost, $idusers, $nameuser){
			$this->idpost 				= $idpost;
			$this->idusers 		= $idusers;
			$this->nameuser 		= $nameuser;
		}
	}
?>
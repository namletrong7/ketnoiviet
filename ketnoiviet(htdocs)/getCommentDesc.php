<?php 
	include "connect.php";
	$idpost = $_POST['idpost'];
	
	$mangcomment = array();
	$query = "SELECT users.nameuser, users.idusers, users.imageuser, comment.content FROM comment,users WHERE comment.idusers = users.idusers and comment.idpost = '$idpost' ORDER by comment.id Desc";
	$data 			= mysqli_query($conn, $query);
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($mangcomment, new Comment(
			$row['nameuser'],
			$row['idusers'],
			$row['imageuser'],
			$row['content']
		));
	}
	echo json_encode($mangcomment);
	class Comment{
		function __construct ($nameuser, $idusers, $imageuser, $content){
			$this->nameuser 			=$nameuser;
			$this->idusers 		=$idusers;
			$this->imageuser 		=$imageuser;
			$this->content	=$content;
		}
	}
?>
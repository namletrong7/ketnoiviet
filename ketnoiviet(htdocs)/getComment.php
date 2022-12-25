<?php 
	include "connect.php";
	// láy thông tin được gưie
	$idpost = $_POST['idpost'];
	// $idpost = '3';
	// tạo 1 mảng chứa các bình luận
	$mangcomment = array();
	// thực hiện truy vấn lấy tên người dùng, số dt người dùng, ảnh đại diện, và nội dung bình luận
	   // từ 2 bảng comment và users tại
	   // tức là lấy bình luận của 1 bài viết rồi sắp xếp các hàng nhận dduocj theo thứ tự tăng dần của id bình luận
	$query = "SELECT users.nameuser, users.phonenumber, users.imageuser, comment.content FROM comment,users WHERE comment.phoneuser = users.phonenumber and comment.idpost = '$idpost' ORDER by comment.id ASC";
	// thực hiện câu lệnh truy vấn 
	$data = mysqli_query($conn, $query);
	// mysqli_fetch_assoc: Hàm sẽ trả về mảng kết hợp chứa thông tin của hàng kết quả.
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($mangcomment, new Comment(
			$row['nameuser'],
			$row['phonenumber'],
			$row['imageuser'],
			$row['content']
		));
	}
	// 
	echo json_encode($mangcomment);
	class Comment{
		function __construct ($nameuser, $phonenumber, $imageuser, $content){
			$this->nameuser 			=$nameuser;
			$this->phonenumber 		=$phonenumber;
			$this->imageuser 		=$imageuser;
			$this->content	=$content;
		}
	}
?>
<?php 
// các bài đăng đã được phê duyệt
	include "connect.php";

	$query = "SELECT post.id,post.nameplace,post.province,post.district,post.ward,post.address,post.description,post.content,post.image1,post.image2,post.image3,post.image4,post.idusers,post.datepost,post.status, users.nameuser,users.imageuser FROM post,users WHERE post.idusers = users.idusers && post.status = 1 ORDER By ID DESC";
	
	$data = mysqli_query($conn, $query);
	// lấy các bài đăng đưa vào 1 mảng
	$mangPost = array();
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($mangPost, new post(
			$row['id'],
            $row['nameplace'],
            $row['province'],
            $row['district'],
            $row['ward'],
            $row['address'],
            $row['description'],
            $row['content'],
            $row['image1'],
            $row['image2'],
            $row['image3'],
            $row['image4'],
            $row['idusers'],
            $row['datepost'],
            $row['status'],
            $row['nameuser'],
            $row['imageuser']
			));
	}
	echo json_encode($mangPost);
	
	class post{
		 function __construct ($id, $nameplace, $province, $district, $ward, 
                                $address, $description, $content, $image1, $image2, 
                                $image3, $image4, $idusers, $datepost, $status, $nameuser, $imageuser){
			$this->id 				= $id;
			$this->nameplace 		= $nameplace;
			$this->province 		= $province;
			$this->district 		= $district;
			$this->ward 		= $ward;
			$this->address 		= $address;
			$this->description 		= $description;
			$this->content 		= $content;
			$this->image1 		= $image1;
			$this->image2 		= $image2;
			$this->image3 		= $image3;
			$this->image4 		= $image4;
			$this->idusers 		= $idusers;
			$this->datepost 		= $datepost;
            $this->status 		= $status;
            $this->nameuser 		= $nameuser;
            $this->imageuser 		= $imageuser;
		}
	}
?>
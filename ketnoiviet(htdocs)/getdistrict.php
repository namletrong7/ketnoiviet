<?php 
	include "connect.php";
	// lấy id của tỉnh nhận dc khi nhấn chọn tỉnh 
	$idtinh = $_POST['idtinh'];
	// tạo 1 mảng huyện chứa tất cả các huyện của 1 tỉnh
	$manghuyen = array();

	// tạo câu lệnh truy vấn lấy huyện của 1 tỉnh qua id tỉnh 
	$query = "SELECT * FROM district WHERE provinceid = $idtinh ";
	$data = mysqli_query($conn, $query);
	// truyền dữ liệu nhận được vào mảng huện 
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($manghuyen, new District(
			$row['id'],
			$row['name'],
			$row['prefix'],
			$row['provinceid']
		));
	}
	
	echo json_encode($manghuyen);
	class District{
		function __construct ($id, $name, $prefix, $provinceid){
			$this->id 			=$id;
			$this->name 		=$name;
			$this->prefix 		=$prefix;
			$this->provinceid	=$provinceid;
		}
	}
?>
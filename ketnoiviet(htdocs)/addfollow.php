<?php 
	include "connect.php";
	// lay du users nguoi thuc hien theo doi
	$users = $_POST['users'];
	// lay user cua nguoi dc theo dõi 
	$userfollow	= $_POST['userfollow'];
     // tao cau lenh truy lee
	$chon = "SELECT * FROM followers WHERE followers.users = $users AND followers.userfollow = $userfollow";
     // thuc hien cau lenh truy van
	$datachon = mysqli_query($conn, $chon);
     // kiểm tra xem userFloww đã theo dõi user hay chưa
	if(mysqli_num_rows($datachon) == 0){   // nếu mà truy vấn ko có hàng nào thì userfolow chưa theo dõi users
		// thực hiện truy vấn thêm người theo dõi vào user
		$them = "INSERT INTO followers(id, users, userfollow) VALUES (null,'$users',$userfollow)";
		$data = mysqli_query($conn, $them);   // thực biện truy vấn
		if($data){  // 
			echo "1";
		} 
		else{
			echo "loi khi them";
		}
	}
	//nếu truy vấn có dữ liệu thì tức là đã theo dõi thì nó sẽ thực hiện xóa hàng đó tức là bỏ theo dõi
	if(mysqli_num_rows($datachon) == 1){
		$xoa = "DELETE FROM followers WHERE followers.users = $users AND followers.userfollow = $userfollow";
		$data1 = mysqli_query($conn, $xoa);
		if($data1){  // nếu mà thực hiện truy vấn thành công
			echo "0";
		} 
		else{
			echo "loi khi xoa";
		}
	}

	
	
	
?>
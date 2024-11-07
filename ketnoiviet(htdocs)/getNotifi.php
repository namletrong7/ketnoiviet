
<?php 
// api lấy dữ liệu thông báo của người dùng 
	include "connect.php";
	
	$idusers = $_POST['idusers'];   //người nhận dc  thông báo
	
	$mangNotifi = array();

	$query = "SELECT notifi.idNotifi, notifi.idPost, notifi.type, users.nameuser, notifi.avatarUser, readnotifi.hasRead,readnotifi.idReadPost, notifi.timeNotifi FROM notifi,carepost,users,readnotifi WHERE notifi.idPost = carepost.idPost 
    and carepost.idusers = '$idusers' and users.idusers = notifi.idusers 
    and readnotifi.idusers = $idusers and readnotifi.idNotifi =  notifi.idNotifi ORDER BY  notifi.idSetUp DESC";


	// thực hiện câu lệnh truy vấn 
	$data = mysqli_query($conn, $query);
	// mysqli_fetch_assoc: Hàm sẽ trả về mảng kết hợp chứa thông tin của hàng kết quả.
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($mangNotifi, new notifi(
			$row['idNotifi'],
			$row['idPost'],
			$row['nameuser'],
			$row['avatarUser']	,
			$row['hasRead'],
			$row['type'],
            $row['idReadPost'],
			$row['timeNotifi']
		));
	}
	// 
	echo json_encode($mangNotifi);
	class notifi{
		function __construct ($idNotifi, $idPost, $nameuser, $avatarUser,$hasRead,$type,$idReadPost,$timeNotifi){
			$this->idNotifi 			=$idNotifi;
			$this->idPost 		=$idPost;
			$this->nameuser 		=$nameuser;
			$this->avatarUser 			=$avatarUser;
			$this->hasRead 		=$hasRead;
			$this->type 		=$type;
            $this->idReadPost 		=$idReadPost;
			$this->timeNotifi 		=$timeNotifi;
		}
	}
?>
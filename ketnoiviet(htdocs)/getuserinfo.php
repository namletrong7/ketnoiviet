<?php 
  //Done
	include "connect.php";
	include "testAES.php";
	$idusers = $_POST['idusers'];
	
	$manguser = array();
	$query = "SELECT * FROM users WHERE idusers = $idusers ";
	$data 			= mysqli_query($conn, $query);
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($manguser, new User(
			$row['idusers'],
			$row['nameuser'],
			$row['birthday'],
			$row['gender'],
			$row['imageuser'],
			$row['cover'],
			giaiMa($row['password'],$row['email']),
			giaiMa($row['password'],$row['phonenumber']),
			$row['status'],
			$row['hometown'],
			$row['money'],
			$row['datecreate'],
			$row['password'],
			$row['isHomeTown'],
			$row['isGender'],
			$row['isBirthday'],
			$row['isEmail']
		));
	}
	echo json_encode($manguser);
	class User{
		function __construct ($idusers, $nameuser, $birthday, $gender, $imageuser, $cover, $email, $phonenumber, $status, $hometown, $money, $datecreate, $password,$isHomeTown,$isGender,$isBirthday,$isEmail){
			$this->idusers 			=$idusers;
			$this->nameuser 		=$nameuser;
			$this->birthday 		=$birthday;
			$this->gender			=$gender;
			$this->imageuser		=$imageuser;
			$this->cover			=$cover;
			$this->email			=$email;
			$this->phonenumber		=$phonenumber;
			$this->status			=$status;
			$this->hometown			=$hometown;
			$this->money			=$money;
			$this->datecreate		=$datecreate;
			$this->password			=$password;
			$this->isHomeTown			=$isHomeTown;
			$this->isGender			=$isGender;
			$this->isBirthday			=$isBirthday;
			$this->isEmail			=$isEmail;
		}
	}
?>

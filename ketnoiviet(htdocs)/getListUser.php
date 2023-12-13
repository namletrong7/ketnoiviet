<?php 
	include "connect.php";
	$manguser = array();
	$query = "SELECT * FROM users";
	$data 			= mysqli_query($conn, $query);
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($manguser, new User(
			$row['idusers'],
			$row['nameuser'],
			$row['birthday'],
			$row['gender'],
			$row['imageuser'],
			$row['cover'],
			$row['email'],
			$row['phonenumber'],
			$row['status'],
			$row['hometown'],
			$row['money'],
			$row['datecreate'],
			$row['password']
		));
	}
	echo json_encode($manguser);
	class User{
		function __construct ($idusers, $nameuser, $birthday, $gender, $imageuser, $cover, $email, $phonenumber, $status, $hometown, $money, $datecreate, $password){
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
		}
	}
?>

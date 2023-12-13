<?php
$host	= "localhost";
$username = "root";
$password = "";
$datebase = "ketnoiviet";
$connection = mysqli_connect($host, $username, $password, $datebase);
mysqli_set_charset($connection, 'UTF8');
ob_start();
date_default_timezone_set('Asia/Ho_Chi_Minh');
function makedatanl($connection, $module, $datain)
{
	$dataout = array();
	$query = "SELECT * FROM $module ";
	$query_run = mysqli_query($connection, $query);
	$field = mysqli_fetch_fields($query_run);

	foreach ($field as $value) {
		foreach ($datain as $key => $data) {
			if ($value->name == $key) {
				$dataout[$key] = $datain[$key];
				break;
			} else if ($value->name != "id") {
				$dataout[$value->name] = "";
			}
		}
	}
	return $dataout;
}

function makedatanlupdate($connection, $module, $datain)
{
	$dataout = array();
	$query = "SELECT * FROM $module ";
	$query_run = mysqli_query($connection, $query);
	$field = mysqli_fetch_fields($query_run);

	foreach ($field as $value) {
		foreach ($datain as $key => $data) {
			if ($value->name == $key) {
				$dataout[$key] = $datain[$key];
				break;
			}
		}
	}
	return $dataout;
}
function insertdb1($connection, $dbname, $data)
{
	$sql = 'INSERT INTO ' . $dbname . '(';
	$query = "SELECT * FROM $dbname ";
	$query_run = mysqli_query($connection, $query);
	$field = mysqli_fetch_fields($query_run);
	foreach ($field as $value) {
		if ($value->name != "id") {
			$sql .= $value->name . ',';
		}
	}
	$sql = substr($sql, 0, -1) . ')' . ' ' . 'VALUES' . '' . '(';
	foreach ($field as $value) {
		foreach ($data as $key => $val) {
			if ($value->name == $key) {
				$sql .= "'" . $val . "',";
				break;
			}
		}
	}
	$sql = substr($sql, 0, -1) . ')';
	$query_run = mysqli_query($connection, $sql);
	$newid = mysqli_query($connection, "SELECT MAX(id) as id from $dbname");
	foreach ($newid as $row) {
		return $row['id'];
	}
}
function deletedb($connection, $dbname, $where)
{
	$sql = "DELETE FROM `" . $dbname . "` where " . $where;
	mysqli_query($connection, $sql);
	return 1;
}
function insertdb($connection, $dbname, $data)
{
	$sql = 'INSERT INTO ' . $dbname . '(';
	$query = "SELECT * FROM $dbname ";
	$query_run = mysqli_query($connection, $query);
	$field = mysqli_fetch_fields($query_run);
	foreach ($field as $value) {
		if ($value->name != "id") {
			$sql .= $value->name . ',';
		}
	}
	$sql = substr($sql, 0, -1) . ')' . ' ' . 'VALUES' . '' . '(';
	foreach ($field as $value) {
		foreach ($data as $key => $val) {
			if ($value->name == $key) {
				$sql .= "'" . $val . "',";
				break;
			} else if ($value->name != 'id') {
				$sql .= "' ',";
			}
		}
	}
	$sql = substr($sql, 0, -1) . ')';
	$query_run = mysqli_query($connection, $sql);
	// return $sql;
	$newid = mysqli_query($connection, "SELECT MAX(id) as id from $dbname");
	foreach ($newid as $row) {
		return $row['id'];
	}
}
function checklogin($connection, $dbname, $data)
{
	$username = $data['username'];
	$password = $data['password'];
	$result = mysqli_query($connection, "select * from $dbname where username='$username' and password='$password' and quyen='admin'");
	if (mysqli_num_rows($result) > 0) {
		return 1;
	} else {
		return 2;
	}
}
function check_username($connection, $dbname, $username)
{
	$result = mysqli_query($connection, "select * from $dbname where username='$username'");
	if (mysqli_num_rows($result) > 0) {
		return 1;
	} else {
		return 2;
	}
}
function select_info($connection, $sql)
{
	$info = mysqli_fetch_assoc(mysqli_query($connection, $sql));
	return $info;
}
function select_list1($connection, $sql)
{
	$result = mysqli_query($connection, $sql);
	if ($result) {
		$info = mysqli_fetch_all($result, MYSQLI_ASSOC);
		$data_with_field_names_as_keys = array();
		foreach ($info as $key => $value) {
			$data_with_field_names_as_keys[$key] = $value;
		}

		return $data_with_field_names_as_keys;
	} else {
		return null;
	}
}
function select_list($connection, $sql)
{
	$info = mysqli_fetch_all(mysqli_query($connection, $sql));
	return $info;
}
function updatedb($connection, $dbname, $data)
{
	$sql = 'UPDATE ' . $dbname . ' SET ';
	$query = "SELECT * FROM $dbname ";
	$query_run = mysqli_query($connection, $query);
	$field = mysqli_fetch_fields($query_run);
	foreach ($field as $value) {
		foreach ($data as $key => $val) {
			if ($value->name == $key && $value->name != "id" && $key != "img") {
				$sql .= $key . " = '" . $val . "' , ";
				break;
			}
			if ($key == "img" && (!empty($val) || $val != '')) {
				$sql .= $key . " = '" . $val . "' , ";
				break;
			}
		}
	}
	$sql = $sql . "WHERE id='{$data['id']}'";
	$sql = str_replace(", WHERE", "WHERE", $sql);
	$query_run = mysqli_query($connection, $sql);
	return true;
}
function print_array($arr)
{
	echo "<pre>";
	print_r($arr);
	echo "</pre>";
}
function uploadfile($name, $tmp_name)
{
	$targetDirectory = __DIR__ . '/img/'; 
	$targetFile = $targetDirectory . basename($name); 
	if (move_uploaded_file($tmp_name, $targetFile)) {
		return 1;
	} else {
		return 2;
	}
}
function inhoachudau($string)
{
	$normalizedString = preg_replace('/\s+/', ' ', $string);
	$titleCaseString = ucwords($normalizedString);
	return $titleCaseString;
}

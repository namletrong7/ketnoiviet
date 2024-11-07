<?php
// Kiểm tra xem có file được gửi đến không
// if (isset($_FILES['file'])) {
//     $uploadedFile = $_FILES['file'];

//     // Thư mục đích để lưu trữ file
//     $targetDirectory = "testfile/";

//     // Tạo đường dẫn đầy đủ đến file đích
//     $targetPath = $targetDirectory . basename($uploadedFile['name']);

//     // Di chuyển file từ thư mục tạm sang thư mục đích
//     if (move_uploaded_file($uploadedFile['tmp_name'], $targetPath)) {
//         echo json_encode(['status' => 'success', 'message' => 'File uploaded successfully.']);
//     } else {
//         echo json_encode(['status' => 'error', 'message' => 'Failed to upload file.']);
//     }
// } else {
//     echo json_encode(['status' => 'error', 'message' => 'No file uploaded.']);
// }

$status = 200; // Mặc định là thành công
chmod("testfile/", 0755); // Thay thế $yourUploadDirectory bằng đường dẫn thực tế của thư mục

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Đường dẫn đến thư mục lưu trữ file
    $uploadDirectory = 'testfile/';

    // Lặp qua từng file trong $_FILES
    foreach ($_FILES as $file) {
        //láy dinh dạng cho file 
        $pathInfo = pathinfo(basename($file['name']));
        $extension = $pathInfo['extension'];
        // tạo ra tên link file cũng laft ên file lưu trong server  để sau này đùng 
        $linkFile= rand()."_".time() . "." . $extension;
        // tên của file để đưa vào database 
        $nameFile=basename($file['name']);
        $targetFilePath = $uploadDirectory.$linkFile;
        // Di chuyển file từ thư mục tạm sang thư mục đích
        if (!move_uploaded_file($file['tmp_name'], $targetFilePath)) {
            echo json_encode($nameFile);
            $status = 400; // Nếu có bất kỳ lỗi nào, đặt status là 400
            break; // Dừng vòng lặp ngay lập tức khi có lỗi
        }
    }
    // Cuối cùng, xuất thông báo JSON sau khi vòng lặp đã kết thúc
echo json_encode(['status' => $status, 'message' => $status == 200 ? 'All files uploaded successfully.' : 'Failed to upload one or more files.']);
} else {
    echo json_encode(['status' => 'error', 'message' => 'Invalid request method.']);
}


?>

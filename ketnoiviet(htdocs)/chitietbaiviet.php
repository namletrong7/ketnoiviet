<?php
session_start();
if(!empty($_SESSION['username'])){
require_once "connectForWeb.php";
if (isset($_GET['getluotlike'])) {
    $data=select_list1($connection,"select * from react where idpost='{$_POST['id']}'");
?>
    <div class="modal-body">
    <table class="table" id="listlike">
        <thead>
            <tr>
                <th scope="col">STT</th>
                <th>Phoneuser</th>

            </tr>
        </thead>
        <tbody>
            <?php
            $t = 1;
            foreach ($data as $value) {
            ?>
                <tr>
                    <th scope="row"><?= $t ?></th>
                    <td><?= $value['phoneuser'] ?></td>
                </tr>
            <?php
                $t++;
            }
            ?>

        </tbody>
    </table>
    <script>
        new DataTable('#listlike', {
            "lengthChange": false,
            ordering: false,
            searching:false,
            pageLength: 10 
        });
    </script>
    </div>
<?php
    exit;
}
if(isset($_GET['xoacomment'])){
    deletedb($connection, 'comment', 'id=' . $_POST['id']);
    echo 1;
    exit;
}
if (isset($_GET['getbinhluan'])) {
    $data=select_list1($connection,"select * from comment where idpost='{$_POST['id']}'");
?>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <div class="modal-body">
    <table class="table" id="listcomment">
        <thead>
            <tr>
                <th scope="col">STT</th>
                <th>Chức năng</th>
                <th>Phoneuser</th>
                <th>Nội dung</th>
            </tr>
        </thead>
        <tbody>
            <?php
            $t = 1;
            foreach ($data as $value) {
            ?>
                <tr>
                    <th scope="row"><?= $t ?></th>
                    <th style="text-align: center;" onclick="xoacomment(<?= $value['id'] ?>)"><button type="button" style="border:0px !important"><i class="fa fa-trash-o" aria-hidden="true"></i></button></th>
                    <td><?= $value['phoneuser'] ?></td>
                    <td><?= $value['content'] ?></td>
                </tr>
            <?php
                $t++;
            }
            ?>

        </tbody>
    </table>
    <script>
        new DataTable('#listcomment', {
            "lengthChange": false,
            ordering: false,
            searching:false,
            pageLength: 10 
        });
   
    </script>
    </div>
<?php
    exit;
}
if (isset($_GET['getdata'])) {
    $chitiet = select_info($connection, "select * from post where id='{$_POST['id']}'");
    $sltym = select_list1($connection, "select * from react where idpost='{$_POST['id']}'");
    $binhluan = select_list1($connection, "select * from comment where idpost='{$_POST['id']}'");
?>

    <style>
        #accordionSidebar{
            display: none;
        }
        .bg-warning {
            --bs-bg-opacity: 1;
            background-color: rgba(var(--bs-warning-rgb), var(--bs-bg-opacity)) !important;
        }
    </style>
    <h1 style="text-align:center">Chi tiết bài viết</h1>
    <div class="row">
        <div class="col-md-6">
            <div class="card card-bd">
                <div class="bg-primary card-border"></div>
                <div class="card-body box-style">
                    <div class="media align-items-center">
                        <div class="media-body me-3" style="    text-align: center">
                            <h2 class="count num-text text-black font-w700" style="    text-align: center"><?= count($sltym)  ?></h2>
                            <span class="fs-14" style="    text-align: center">Lượt like</span>
                        </div>
                        <button onclick="getluotlike()" style="border: 0px;">
                            <svg  class="primary-icon" width="36" height="36" viewBox="0 0 36 36" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <path d="M11.9999 1.5H5.99994C3.51466 1.5 1.49994 3.51472 1.49994 6V29.8125C1.49994 32.2977 3.51466 34.3125 5.99994 34.3125H11.9999C14.4852 34.3125 16.4999 32.2977 16.4999 29.8125V6C16.4999 3.51472 14.4852 1.5 11.9999 1.5Z" fill="#20F174"></path>
                            <path d="M30 1.5H24C21.5147 1.5 19.5 3.51472 19.5 6V12C19.5 14.4853 21.5147 16.5 24 16.5H30C32.4853 16.5 34.5 14.4853 34.5 12V6C34.5 3.51472 32.4853 1.5 30 1.5Z" fill="#20F174"></path>
                            <path d="M30 19.5H24C21.5147 19.5 19.5 21.5147 19.5 24V30C19.5 32.4853 21.5147 34.5 24 34.5H30C32.4853 34.5 34.5 32.4853 34.5 30V24C34.5 21.5147 32.4853 19.5 30 19.5Z" fill="#20F174"></path>
                        </svg>
                        </button>
                        
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="card card-bd">
                <div class="bg-warning card-border"></div>
                <div class="card-body box-style">
                    <div class="media align-items-center" style="text-align: center">
                        <div class="media-body me-3" style="text-align: center">
                            <h2 class="count num-text text-black font-w700" style="text-align: center">
                                <?= count($binhluan) ?></h2>
                            <span class="fs-14" style="text-align: center">Lượt comment</span>
                        </div>
                        <button onclick="getbinhluan()" style="border: 0px;">
                        <svg width="46" height="46" viewBox="0 0 46 46" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <path d="M34.4999 1.91663H11.4999C8.95917 1.91967 6.52338 2.93032 4.72682 4.72688C2.93026 6.52345 1.91961 8.95924 1.91656 11.5V26.8333C1.91935 29.0417 2.6834 31.1816 4.07994 32.8924C5.47648 34.6031 7.42011 35.7801 9.58323 36.225V42.1666C9.58318 42.5136 9.67733 42.8541 9.85564 43.1518C10.0339 43.4495 10.2897 43.6932 10.5957 43.8569C10.9016 44.0206 11.2463 44.0982 11.5929 44.0813C11.9395 44.0645 12.275 43.9539 12.5636 43.7613L23.5749 36.4166H34.4999C37.0406 36.4136 39.4764 35.4029 41.273 33.6064C43.0695 31.8098 44.0802 29.374 44.0832 26.8333V11.5C44.0802 8.95924 43.0695 6.52345 41.273 4.72688C39.4764 2.93032 37.0406 1.91967 34.4999 1.91663ZM30.6666 24.9166H15.3332C14.8249 24.9166 14.3374 24.7147 13.9779 24.3552C13.6185 23.9958 13.4166 23.5083 13.4166 23C13.4166 22.4916 13.6185 22.0041 13.9779 21.6447C14.3374 21.2852 14.8249 21.0833 15.3332 21.0833H30.6666C31.1749 21.0833 31.6624 21.2852 32.0219 21.6447C32.3813 22.0041 32.5832 22.4916 32.5832 23C32.5832 23.5083 32.3813 23.9958 32.0219 24.3552C31.6624 24.7147 31.1749 24.9166 30.6666 24.9166ZM34.4999 17.25H11.4999C10.9916 17.25 10.5041 17.048 10.1446 16.6886C9.78517 16.3291 9.58323 15.8416 9.58323 15.3333C9.58323 14.825 9.78517 14.3374 10.1446 13.978C10.5041 13.6186 10.9916 13.4166 11.4999 13.4166H34.4999C35.0082 13.4166 35.4957 13.6186 35.8552 13.978C36.2146 14.3374 36.4166 14.825 36.4166 15.3333C36.4166 15.8416 36.2146 16.3291 35.8552 16.6886C35.4957 17.048 35.0082 17.25 34.4999 17.25Z" fill="#3ECDFF"></path>
                        </svg>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row" style="margin-bottom:10px">
        <div class="col-md-6">
            <label>Địa điểm:<?= $chitiet['nameplace'] ?></label>
        </div>
        <div class="col-md-6">
            <label>Tỉnh/Thành phố:<?= $chitiet['province'] ?></label>
        </div>
        <div class="col-md-6">
            <label>Huyện/Thị trấn:<?= $chitiet['district'] ?></label>
        </div>
        <div class="col-md-6">
            <label>Xã/Phường:<?= $chitiet['ward'] ?></label>
        </div>
        <div class="col-md-6">
            <label>Địa chỉ:<?= $chitiet['address'] ?></label>
        </div>
        <div class="col-md-6">
            <label>Số điện thoại:<?= $chitiet['phoneuser'] ?></label>
        </div>
        <div class="col-md-6">
            <label>Ngày đăng:<?= $chitiet['datepost'] ?></label>
        </div>
        <div class="col-md-6">
            <label>Trạng thái:<?= $chitiet['status'] ?></label>
        </div>
        <div class="col-md-12">
            <label>Mô tả ngắn:</label>
            <textarea name="mota" class="form-control" id="mota" cols="10" rows="5"><?= $chitiet['description'] ?></textarea>
        </div>
        <div class="col-md-12">
            <label>Chi tiết bài viết:</label>
            <textarea name="ct" class="form-control" id="ct" cols="10" rows="10"><?= $chitiet['content'] ?></textarea>
        </div>
    </div>
    <div class="row">
        <div class="col-md-3">
            <img style="width:100%;height:300px" src="./images/<?= $chitiet['image1'] ?>" alt="">
        </div>
        <div class="col-md-3">
            <img style="width:100%;height:300px" src="./images/<?= $chitiet['image2'] ?>" alt="">
        </div>
        <div class="col-md-3">
            <img style="width:100%;height:300px" src="./images/<?= $chitiet['image3'] ?>" alt="">
        </div>
        <div class="col-md-3">
            <img style="width:100%;height:300px" src="./images/<?= $chitiet['image4'] ?>" alt="">
        </div>
    </div>

<?php
    exit;
}
if (isset($_GET['id'])) {
    global $idpost;
    $idpost = $_GET['id'];

    include 'header.php';
    include 'navbar.php';
?>
    <script>
        getdata();

        function getdata() {
            $.ajax({
                type: "POST",
                url: '?getdata',
                data: {
                    id: <?= $idpost ?>
                },
                dateType: 'text',
                success: function(data) {
                    $("#content1").html(data);
                }
            })
        }

        function getluotlike() {
            $.ajax({
                type: "POST",
                url: '?getluotlike',
                data: {
                    id: <?= $idpost ?>
                },
                dateType: 'text',
                success: function(data) {
                    $("#modal-content").html(data);
                    $("#exampleModal").css('height','80%');
                $("#exampleModal").modal('show');
                }
            })
        }
              function xoacomment(id){
                Swal.fire({
                title: 'Bạn chắc chắn muốn xóa?',
                icon: 'warning',
                showCancelButton: true,
                cancelButtonText: 'Hủy',
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Xóa'
            }).then((result) => {
                if (result.isConfirmed) {
                    $.ajax({
                        type: "POST",
                        url: '?xoacomment',
                        data: {
                            id
                        },
                        dateType: 'text',
                        success: function(data) {
                            Swal.fire(
                                'Xóa!',
                                'Đã xóa thành công',
                                'success'
                            ).then((result) => {
                                if (result.isConfirmed) {
                                   location.reload();
                                }
                            })
                        }
                    })

                }
            })
        }
        function getbinhluan() {
            $.ajax({
                type: "POST",
                url: '?getbinhluan',
                data: {
                    id: <?= $idpost ?>
                },
                dateType: 'text',
                success: function(data) {
                    $("#modal-content").html(data);
                    $("#exampleModal").css('height','80%');
                $("#exampleModal").modal('show');
                }
            })
        }
    </script>
    
<?php
}
}else{
    header("Location: ./index.php");
}
?>
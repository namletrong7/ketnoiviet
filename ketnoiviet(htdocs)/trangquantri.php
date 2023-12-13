<?php
session_start();
if (!empty($_SESSION['username'])) {
    require_once "connectForWeb.php";
    if (isset($_GET['getdata'])) {
        $namhientai = date('Y');
        $arr_theonam = array();
        $arr_diadanh = array();
        $baiviettheothang = select_list1($connection, "select * from post");
        foreach ($baiviettheothang as $value) {
            $dateString = $value['datepost'];
            $province = $value['province'];
            $dateParts = explode("/", $dateString);
            if(!empty($dateParts[2]) && !empty($dateParts[1])){
                $year = $dateParts[2];
                $month = $dateParts[1]; 
                if ($namhientai == $year) {
                if (!isset($arr_diadanh[$province])) {
                    $arr_diadanh[$province] = 1;
                } else {
                    $arr_diadanh[$province]++;
                }
                if (!isset($arr_theonam[$year][$month])) {
                    $arr_theonam[$year][$month] = 1;
                } else {
                    $arr_theonam[$year][$month]++;
                }
            }  
            }
        }
        arsort($arr_diadanh);
        $top2Values = array_slice($arr_diadanh, 0, 10);
        $keys = array_keys($top2Values);
        $json_keys = json_encode($keys);
        $values = array_values($top2Values);
        $json_values = json_encode($values);
?>
        <div class="row" style="margin-bottom: 140px;">
            <div class="col-xl-12 col-xxl-12 col-sm-12">
                <div class="card" style="height:900px">
                    <div class="card-header border-0 pb-0">
                        <h4 class="fs-20 mb-0 text-black">Thống kê bài viết theo tháng trong năm <?= $namhientai ?></h4>
                        <div class="dropdown">

                        </div>
                    </div>
                    <div class="card-body text-center pb-0 px-2 pt-2" style="position: relative;">
                        <div id="widgetChart1" class="widgetChart1 dashboard-chart" style="min-height: 285px;">
                            <canvas id="myChart" style="width:100%;max-width:100%"></canvas>
                        </div>
                        <div class="resize-triggers">
                            <div class="expand-trigger">
                                <div style="width: 248px; height: 294px;"></div>
                            </div>
                            <div class="contract-trigger"></div>
                        </div>
                    </div>
                </div>
                <script>
                    const xValues = [0,1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];

                    new Chart("myChart", {
                        type: "line",
                        data: {
                            labels: xValues,
                            datasets: [{
                                data: [0,<?= isset($arr_theonam[$year]['01']) ? $arr_theonam[$year]['01'] : 0 ?>, <?= isset($arr_theonam[$year]['02']) ? $arr_theonam[$year]['02'] : 0 ?>, <?= isset($arr_theonam[$year]['03']) ? $arr_theonam[$year]['03'] : 0 ?>, <?= isset($arr_theonam[$year]['04']) ? $arr_theonam[$year]['04'] : 0 ?>, <?= isset($arr_theonam[$year]['05']) ? $arr_theonam[$year]['05'] : 0 ?>, <?= isset($arr_theonam[$year]['06']) ? $arr_theonam[$year]['06'] : 0 ?>, <?= isset($arr_theonam[$year]['07']) ? $arr_theonam[$year]['07'] : 0 ?>, <?= isset($arr_theonam[$year]['08']) ? $arr_theonam[$year]['08'] : 0 ?>, <?= isset($arr_theonam[$year]['09']) ? $arr_theonam[$year]['09'] : 0 ?>, <?= isset($arr_theonam[$year]['10']) ? $arr_theonam[$year]['10'] : 0 ?>, <?= isset($arr_theonam[$year]['11']) ? $arr_theonam[$year]['11'] : 0 ?>, <?= isset($arr_theonam[$year]['12']) ? $arr_theonam[$year]['12'] : 0 ?>],
                                borderColor: "red",
                                fill: false
                            }]
                        },
                        options: {
                            legend: {
                                display: false
                            }
                        }
                    });
                </script>
            </div>
           
        </div>
        <div class="row">
        <div class="col-xl-12 col-xxl-12 col-sm-12">
                <div class="card">
                    <div class="card-header border-0 pb-0">
                        <h4 class="fs-20 mb-0 text-black">Top 10 tỉnh có bài viết nhiều nhất năm</h4>
                    </div>
                    <div class="card-body text-center pt-0" style="position: relative;">
                        <div id="radialChart" class="monthly-project-chart" style="min-height: 208.7px;">
                            <canvas id="myChart1"></canvas>
                        </div>
                        <div class="resize-triggers">
                            <div class="expand-trigger">
                                <div style="width: 248px; height: 294px;"></div>
                            </div>
                            <div class="contract-trigger"></div>
                        </div>
                    </div>
                </div>
                <script>
                    const labels = <?= $json_keys ?>;
                    const data = <?= $json_values ?>;
                    const ctx = document.getElementById('myChart1').getContext('2d');
                    const myChart = new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: labels,
                            datasets: [{
                                label: 'Số bài viết',
                                data: data,
                                backgroundColor: 'rgba(54, 162, 235, 0.6)',
                                borderColor: 'rgba(54, 162, 235, 1)',
                                borderWidth: 1
                            }]
                        },
                        options: {
                            scales: {
                                y: {
                                    beginAtZero: true
                                }
                            }
                        }
                    });
                </script>
            </div>
        </div>
    <?php
        exit;
    }
    if (isset($_GET['listuser'])) {
        $data = select_list($connection, "select * from users where Role is null ");
        // print_array($data);
    ?>
        <style>
            tr th {
                text-align: center !important;
            }

            td {
                text-align: center;
            }
        </style>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <table class="table" id="listuser">
            <thead>
                <tr>
                    <th scope="col">STT</th>
                    <th scope="col">Chức năng</th>
                    <th style="width:50px">Ảnh đại diện</th>
                    <th scope="col">Username</th>
                    <th scope="col">Ngày sinh</th>
                    <th scope="col">Giới tính</th>
                    <th>Email</th>
                    <th>Số điện thoại</th>
                    <th>Quê quán</th>

                </tr>
            </thead>
            <tbody>
                <?php
                $t = 1;
                foreach ($data as $value) {
                ?>
                    <tr>
                        <th scope="row"><?= $t ?></th>
                        <td><button type="button" onclick="deleteuser(<?= $value[0] ?>)" title="Xóa"><i class="fa fa-trash-o" aria-hidden="true"></i></button></td>
                        <td style="width:100px"><img style="width:100px" src="./users/<?= $value[5] ?>" alt=""> </td>
                        <td><?= $value[1] ?></td>
                        <td><?= $value[2] ?></td>
                        <td><?= $value[3] ?></td>
                        <td><?= $value[6] ?></td>
                        <td><?= $value[7] ?></td>
                        <td><?= $value[9] ?></td>
                    </tr>
                <?php
                    $t++;
                }
                ?>

            </tbody>
        </table>
        <script>
            new DataTable('#listuser', {
                ordering: false,
                scrollCollapse: true,
            });
        </script>
    <?php
        exit();
    }
    if (isset($_GET['gethuyen'])) {
        $huyen = select_list1($connection, "select id,name as text from district where provinceid='{$_POST['tinh']}'");
        echo json_encode($huyen);
        exit;
    }
    if (isset($_GET['luulaixa'])) {
        $check = array_column(select_list1($connection, "select * from ward where 
    provinceid='{$_POST['provinceid']}' and districtid='{$_POST['districtid']}'"), 'id', 'name');
        if (!empty($check[inhoachudau($_POST['name'])])) {
            echo 2;
            exit;
        }
        $data = makedatanl($connection, 'ward', $_POST);
        $data['name'] = inhoachudau($_POST['name']);
        insertdb1($connection, 'ward', $data);
        echo 1;
        exit;
    }
    if (isset($_GET['luulaitinh'])) {
        $tenqc = inhoachudau($_POST['name']);
        $check = select_info($connection, "select * from province where name='{$tenqc}'");
        if (!empty($check)) {
            echo 2;
            exit;
        }
        $data = makedatanl($connection, 'province', $_POST);
        $data['name'] = inhoachudau($_POST['name']);
        $data['area'] = $_POST['area'] . 'Km²';
        insertdb1($connection, 'province', $data);
        echo 1;
        exit;
    }
    if (isset($_GET['luulaihuyen'])) {
        $check = array_column(select_list1($connection, "select * from district where 
    provinceid='{$_POST['provinceid']}'"), 'id', 'name');
        if (!empty($check[inhoachudau($_POST['name'])])) {
            echo 2;
            exit;
        }
        $data = makedatanl($connection, 'district', $_POST);
        $data['name'] = inhoachudau($_POST['name']);
        insertdb1($connection, 'district', $data);
        echo 1;
        exit;
    }
    if (isset($_GET['duyetbaiviet'])) {
        $data['id'] = $_POST['id'];
        $data['status'] = 1;
        updatedb($connection, 'post', $data);
        echo 1;
        exit;
    }
    if (isset($_GET['updatexa'])) {
        $data = makedatanl($connection, 'ward', $_POST);
        $data['name'] = inhoachudau($_POST['name']);
        updatedb($connection, 'ward', $data);
        echo 1;
        exit;
    }
    if (isset($_GET['updatehuyen'])) {
        $data = makedatanl($connection, 'district', $_POST);
        $data['name'] = inhoachudau($_POST['name']);
        updatedb($connection, 'district', $data);
        echo 1;
        exit;
    }
    if (isset($_GET['updatetinh'])) {
        $data = makedatanl($connection, 'province', $_POST);
        $data['name'] = inhoachudau($_POST['name']);
        updatedb($connection, 'province', $data);
        echo 1;
        exit;
    }
    if (isset($_GET['themmoixa'])) {
        $data = select_list1($connection, 'select * from province')
    ?>
        <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">THÊM MỚI XÃ</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="modal-body">
            <form id="form_addxa">
                <div class="col-md-12">
                    <label for="provinceid">Tỉnh</label>
                    <select name="provinceid" id="provinceid" onchange="gethuyen()" class="form-control" style="width:100%">
                        <option value="">---</option>
                        <?php foreach ($data as $value) { ?>
                            <option value="<?= $value['id'] ?>"><?= $value['name'] ?></option>
                        <?php } ?>
                    </select>
                </div>
                <div class="col-md-12">
                    <label for="districtid">Huyện</label>
                    <select name="districtid" id="districtid" class="form-control" style="width:100%">
                        <option value="">---</option>
                    </select>
                </div>
                <div class="col-md-12">
                    <label for="xa">Xã</label>
                    <input type="text" name="name" class="form-control">
                </div>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
            <button type="button" onclick="luulaixa()" class="btn btn-primary">Lưu lại</button>
        </div>
        </form>
        <script>
            function luulaixa() {
                var formData = new FormData($("#form_addxa")[0]);
                $.ajax({
                    url: '?luulaixa', // Replace with your server endpoint
                    type: 'POST',
                    data: formData,
                    contentType: false,
                    processData: false,
                    success: function(data) {
                        if (data == 1) {
                            Swal.fire(
                                'Thêm mới!',
                                'Thêm mới thành công',
                                'success'
                            ).then((result) => {
                                if (result.isConfirmed) {
                                    $("#exampleModal").modal('hide');
                                    diadanh();
                                }
                            })
                        } else {
                            Swal.fire(
                                'Thêm mới!',
                                'Vui lòng kiểm tra lại thông tin',
                                'fail'
                            )
                        }

                    },
                    error: function(jqXHR, textStatus, errorMessage) {
                        console.error('Error:', errorMessage);
                    }
                });
            }
        </script>
    <?php
        exit;
    }
    if (isset($_GET['themmoitinh'])) {
        $data = select_list1($connection, 'select * from province')
    ?>
        <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">THÊM MỚI TỈNH</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="modal-body">
            <form id="form_addxa" class="row">
                <div class="col-md-6">
                    <label for="name">Tên Tỉnh/Thành phố</label>
                    <input type="text" name="name" id="name" class="form-control">
                </div>
                <div class="col-md-6">
                    <label for="code">Tên viết tắt</label>
                    <input type="code" name="code" id="name" class="form-control">
                </div>
                <div class="col-md-12">
                    <label for="area">Diện tích</label>
                    <input type="number" name="area" id="area" class="form-control">
                </div>
                <div class="col-md-12">
                    <label for="location">Vị trí</label>
                    <textarea name="location" id="location" cols="2" rows="2" class="form-control"></textarea>
                </div>

                <div class="col-md-12">
                    <label for="danhlam">Địa điểm nổi tiếng</label>
                    <textarea name="danhlam" id="danhlam" cols="2" rows="2" class="form-control"></textarea>
                </div>
                <div class="col-md-12">
                    <label for="dacsan">Đặc sản</label>
                    <textarea name="dacsan" id="dacsan" cols="2" rows="2" class="form-control"></textarea>
                </div>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
            <button type="button" onclick="luulaitinh()" class="btn btn-primary">Lưu lại</button>
        </div>
        </form>
        <script>
            function luulaitinh() {
                var formData = new FormData($("#form_addxa")[0]);
                $.ajax({
                    url: '?luulaitinh',
                    type: 'POST',
                    data: formData,
                    contentType: false,
                    processData: false,
                    success: function(data) {
                        if (data == 1) {
                            Swal.fire(
                                'Thêm mới!',
                                'Thêm mới thành công',
                                'success'
                            ).then((result) => {
                                if (result.isConfirmed) {
                                    $("#exampleModal").modal('hide');
                                    danhsachtinh();
                                }
                            })
                        } else {
                            Swal.fire(
                                'Thêm mới!',
                                'Vui lòng kiểm tra lại thông tin',
                                'fail'
                            )
                        }

                    },
                    error: function(jqXHR, textStatus, errorMessage) {
                        console.error('Error:', errorMessage);
                    }
                });
            }
        </script>
    <?php
        exit;
    }
    if (isset($_GET['themmoihuyen'])) {
        $data = select_list1($connection, 'select * from province')
    ?>
        <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">THÊM MỚI HUYỆN/THỊ TRẤN</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="modal-body">
            <form id="form_addxa">
                <div class="col-md-12">
                    <label for="provinceid">Tỉnh</label>
                    <select name="provinceid" id="provinceid" class="form-control" style="width:100%">
                        <option value="">---</option>
                        <?php foreach ($data as $value) { ?>
                            <option value="<?= $value['id'] ?>"><?= $value['name'] ?></option>
                        <?php } ?>
                    </select>
                </div>
                <div class="col-md-12">
                    <label for="name">Huyện</label>
                    <input type="text" name="name" id="name" class="form-control">
                </div>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
            <button type="button" onclick="luulaihuyen()" class="btn btn-primary">Lưu lại</button>
        </div>
        </form>
        <script>
            function luulaihuyen() {
                var formData = new FormData($("#form_addxa")[0]);
                $.ajax({
                    url: '?luulaihuyen', // Replace with your server endpoint
                    type: 'POST',
                    data: formData,
                    contentType: false,
                    processData: false,
                    success: function(data) {
                        if (data == 1) {
                            Swal.fire(
                                'Thêm mới!',
                                'Thêm mới thành công',
                                'success'
                            ).then((result) => {
                                if (result.isConfirmed) {
                                    $("#exampleModal").modal('hide');
                                    danhsachhuyen();
                                }
                            })
                        } else {
                            Swal.fire(
                                'Thêm mới!',
                                'Vui lòng kiểm tra lại thông tin',
                                'fail'
                            )
                        }

                    },
                    error: function(jqXHR, textStatus, errorMessage) {
                        console.error('Error:', errorMessage);
                    }
                });
            }
        </script>
    <?php
        exit;
    }
    if (isset($_GET['edithuyen'])) {
        $data = select_list1($connection, 'select * from province ');
        $info = select_info($connection, "select * from district where id='{$_POST['id']}'");

    ?>
        <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">CHỈNH SỬA HUYỆN/THỊ TRẤN</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="modal-body">
            <form id="form_addxa">
                <div class="col-md-12 form-group">
                    <label for="provinceid">Tỉnh</label>
                    <select name="provinceid" id="provinceid" onchange="gethuyen()" class="form-control" style="width:100%">
                        <option value="">---</option>
                        <?php foreach ($data as $value) { ?>
                            <option value="<?= $value['id'] ?>" <?= $info['provinceid'] == $value['id'] ? 'selected' : '' ?>><?= $value['name'] ?></option>
                        <?php } ?>
                    </select>
                </div>
                <div class="col-md-12 form-group">
                    <label for="name">Huyện</label>
                    <input type="text" name="name" id="name" value="<?= $info['name'] ?>" class="form-control">
                </div>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
            <button type="button" onclick="updatehuyen()" class="btn btn-primary">Lưu lại</button>
        </div>
        </form>
        <script>
            function updatehuyen() {
                var formData = new FormData($("#form_addxa")[0]);
                formData.append('id', '<?= $_POST['id'] ?>');
                $.ajax({
                    url: '?updatehuyen', // Replace with your server endpoint
                    type: 'POST',
                    data: formData,
                    contentType: false,
                    processData: false,
                    success: function(data) {
                        if (data == 1) {
                            $("#modal-content").modal('hide');
                            Swal.fire(
                                'Updated!',
                                'Updated thành công',
                                'success'
                            ).then((result) => {
                                if (result.isConfirmed) {
                                    $("#exampleModal").modal('hide');
                                    danhsachhuyen();
                                }
                            })

                        } else {
                            Swal.fire(
                                'Thêm mới!',
                                'Vui lòng kiểm tra lại thông tin',
                                'fail'
                            )
                        }

                    },
                    error: function(jqXHR, textStatus, errorMessage) {
                        console.error('Error:', errorMessage);
                    }
                });
            }
        </script>
    <?php
        exit;
    }
    if (isset($_GET['edittinh'])) {
        $data = select_info($connection, "select * from province where id='{$_POST['id']}'");
    ?>
        <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">CHỈNH SỬA TỈNH</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="modal-body">
            <form id="form_addxa" class="row">
                <div class="col-md-6">
                    <label for="name">Tên Tỉnh/Thành phố</label>
                    <input type="text" name="name" id="name" value="<?= $data['id'] ?>" class="form-control">
                </div>
                <div class="col-md-6">
                    <label for="code">Tên viết tắt</label>
                    <input type="code" name="code" id="name" value="<?= $data['name'] ?>" class="form-control">
                </div>
                <div class="col-md-12">
                    <label for="area">Diện tích</label>
                    <input type="text" name="area" id="area" value="<?= $data['area'] ?>" class="form-control">
                </div>
                <div class="col-md-12">
                    <label for="location">Vị trí</label>
                    <textarea name="location" id="location" cols="2" rows="2" class="form-control"><?= $data['location'] ?></textarea>
                </div>

                <div class="col-md-12">
                    <label for="danhlam">Địa điểm nổi tiếng</label>
                    <textarea name="danhlam" id="danhlam" cols="2" rows="2" class="form-control"><?= $data['danhlam'] ?></textarea>
                </div>
                <div class="col-md-12">
                    <label for="dacsan">Đặc sản</label>
                    <textarea name="dacsan" id="dacsan" cols="2" rows="2" class="form-control"><?= $data['dacsan'] ?></textarea>
                </div>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
            <button type="button" onclick="updatetinh()" class="btn btn-primary">Lưu lại</button>
        </div>
        </form>
        <script>
            function updatetinh() {
                var formData = new FormData($("#form_addxa")[0]);
                formData.append('id', '<?= $_POST['id'] ?>');
                $.ajax({
                    url: '?updatetinh', // Replace with your server endpoint
                    type: 'POST',
                    data: formData,
                    contentType: false,
                    processData: false,
                    success: function(data) {
                        if (data == 1) {
                            $("#modal-content").modal('hide');
                            Swal.fire(
                                'Updated!',
                                'Updated thành công',
                                'success'
                            ).then((result) => {
                                if (result.isConfirmed) {
                                    $("#exampleModal").modal('hide');
                                    danhsachtinh();
                                }
                            })

                        } else {
                            Swal.fire(
                                'Thêm mới!',
                                'Vui lòng kiểm tra lại thông tin',
                                'fail'
                            )
                        }

                    },
                    error: function(jqXHR, textStatus, errorMessage) {
                        console.error('Error:', errorMessage);
                    }
                });
            }
        </script>
    <?php
        exit;
    }
    if (isset($_GET['editdiadiem'])) {
        $data = select_list1($connection, 'select * from province ');
        $info = select_info($connection, "select * from ward where id='{$_POST['id']}'");
        $huyen = select_list1($connection, "select * from district where provinceid='{$info['provinceid']}'");
    ?>
        <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">CHỈNH SỬA XÃ</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="modal-body">
            <form id="form_addxa">
                <div class="col-md-12 form-group">
                    <label for="provinceid">Tỉnh</label>
                    <select name="provinceid" id="provinceid" onchange="gethuyen()" class="form-control" style="width:100%">
                        <option value="">---</option>
                        <?php foreach ($data as $value) { ?>
                            <option value="<?= $value['id'] ?>" <?= $info['provinceid'] == $value['id'] ? 'selected' : '' ?>><?= $value['name'] ?></option>
                        <?php } ?>
                    </select>
                </div>
                <div class="col-md-12 form-group">
                    <label for="districtid">Huyện</label>
                    <select name="districtid" id="districtid" class="form-control" style="width:100%">
                        <option value="">---</option>
                        <?php foreach ($huyen as $value) { ?>
                            <option value="<?= $value['id'] ?>" <?= $info['districtid'] == $value['id'] ? 'selected' : '' ?>><?= $value['name'] ?></option>
                        <?php } ?>
                    </select>
                </div>
                <div class="col-md-12 form-group">
                    <label for="xa">Xã</label>
                    <input type="text" name="name" value="<?= $info['name'] ?>" class="form-control">
                </div>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
            <button type="button" onclick="updatexa()" class="btn btn-primary">Lưu lại</button>
        </div>
        </form>
        <script>
            function updatexa() {
                var formData = new FormData($("#form_addxa")[0]);
                formData.append('id', '<?= $_POST['id'] ?>');
                $.ajax({
                    url: '?updatexa', // Replace with your server endpoint
                    type: 'POST',
                    data: formData,
                    contentType: false,
                    processData: false,
                    success: function(data) {
                        if (data == 1) {
                            $("#modal-content").modal('hide');
                            Swal.fire(
                                'Updated!',
                                'Updated thành công',
                                'success'
                            ).then((result) => {
                                if (result.isConfirmed) {
                                    $("#exampleModal").modal('hide');
                                    diadanh();
                                }
                            })

                        } else {
                            Swal.fire(
                                'Thêm mới!',
                                'Vui lòng kiểm tra lại thông tin',
                                'fail'
                            )
                        }

                    },
                    error: function(jqXHR, textStatus, errorMessage) {
                        console.error('Error:', errorMessage);
                    }
                });
            }
        </script>
    <?php
        exit;
    }
    if (isset($_GET['diadanh'])) {
        $data = select_list1($connection, "select ward.id as id,ward.name as xa,province.name as tinh,district.name as huyen from ward 
    left join province on ward.provinceid=province.id 
    left join district on district.id=ward.districtid ");
    ?>
        <style>
            tr th {
                text-align: center !important;
            }

            td {
                text-align: center;
            }
        </style>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <div class="col-md-12" style="text-align: right;">
            <button class="btn btn-primary left" style="margin-bottom:5px" onclick="themmoixa()">Thêm mới</button>
        </div>
        <table class="table" id="listad">
            <thead>
                <tr>
                    <th scope="col">STT</th>
                    <th scope="col">Chức năng</th>
                    <th scope="col">Xã</th>
                    <th scope="col">Huyện</th>
                    <th scope="col">Tỉnh</th>
                </tr>
            </thead>
            <tbody>
                <?php
                $t = 1;
                foreach ($data as $value) {
                ?>
                    <tr>
                        <th scope="row"><?= $t ?></th>
                        <td><button type="button" onclick="editdiadiem(<?= $value['id'] ?>)" title="Chỉnh sửa"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></button></td>
                        <td><?= $value['xa'] ?></td>
                        <td><?= $value['huyen'] ?></td>
                        <td><?= $value['tinh'] ?></td>
                    </tr>
                <?php
                    $t++;
                }
                ?>

            </tbody>
        </table>
        <script>
            new DataTable('#listad', {
                ordering: false,
                scrollCollapse: true,
            });

            function editdiadiem(id) {
                $.ajax({
                    type: "POST",
                    url: '?editdiadiem',
                    data: {
                        id
                    },
                    dateType: 'text',
                    success: function(data) {
                        $("#modal-content").html(data);
                        $("#exampleModal").modal('show');
                    }
                })
            }
        </script>
    <?php
        exit();
    }
    if (isset($_GET['danhsachtinh'])) {
        $data = select_list1($connection, "select * from province order by id desc ");
    ?>
        <style>
            tr th {
                text-align: center !important;
            }

            td {
                text-align: center;
            }
        </style>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <div class="col-md-12" style="text-align: right;">
            <button class="btn btn-primary left" style="margin-bottom:5px" onclick="themmoitinh()">Thêm mới</button>
        </div>
        <table class="table" id="listpro">
            <thead>
                <tr>
                    <th scope="col">STT</th>
                    <th scope="col">Chức năng</th>
                    <th scope="col">Tỉnh/Thành phố</th>
                    <th scope="col">Mã code</th>
                    <th scope="col">Vị trí</th>
                    <th scope="col">Diện tích</th>
                    <th scope="col">Địa điểm nổi tiếng</th>
                    <th scope="col">Đặc sản</th>
                </tr>
            </thead>
            <tbody>
                <?php
                $t = 1;
                foreach ($data as $value) {
                ?>
                    <tr>
                        <th scope="row"><?= $t ?></th>
                        <td><button type="button" onclick="edittinh(<?= $value['id'] ?>)" title="Chỉnh sửa"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></button></td>
                        <td><?= $value['name'] ?></td>
                        <td><?= $value['code'] ?></td>
                        <td><?= $value['location'] ?></td>
                        <td><?= $value['area'] ?></td>
                        <td><?= $value['danhlam'] ?></td>
                        <td><?= $value['dacsan'] ?></td>
                    </tr>
                <?php
                    $t++;
                }
                ?>

            </tbody>
        </table>
        <script>
            new DataTable('#listpro', {
                ordering: false,
                scrollCollapse: true,
                scrollX: true
            });

            function edittinh(id) {
                $.ajax({
                    type: "POST",
                    url: '?edittinh',
                    data: {
                        id
                    },
                    dateType: 'text',
                    success: function(data) {
                        $("#modal-content").html(data);
                        $("#exampleModal").modal('show');
                    }
                })
            }
        </script>
    <?php
        exit();
    }
    if (isset($_GET['danhsachbaiviet'])) {
        if (empty($_POST['tt'])) {
            $data = select_list1($connection, "select * from post  order by id desc ");
        } else if ($_POST['tt'] == 2) {
            $data = select_list1($connection, "select * from post where status= 0 order by id  desc ");
        } else if ($_POST['tt'] == 1) {
            $data = select_list1($connection, "select * from post where status= 1 order by id  desc ");
        }

    ?>
        <style>
            tr th {
                text-align: center !important;
            }

            td {
                text-align: center;
            }
        </style>
        <div class="div" style="margin-bottom:10px">
            <button type="button" class="btn btn-primary" onclick="danhsachbaiviet(1)">Danh sách bài viết đã duyệt </button>
            <button type="button" class="btn btn-primary" onclick="danhsachbaiviet(2)">Danh sách bài viết chưa duyệt</button>
        </div>

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <table class="table" id="listpost">
            <thead>
                <tr>
                    <th scope="col">STT</th>
                    <th scope="col">Chức năng</th>
                    <th scope="col">Tên địa điểm</th>
                    <th scope="col">Tỉnh/Thành phố</th>
                    <th scope="col">Huyện/Thị trấn</th>
                    <th scope="col">Xã</th>
                    <th scope="col">Địa chỉ</th>
                    <th scope="col">Mô tả ngắn</th>
                    <th scope="col">Bài viết</th>
                    <th scope="col">Số điện thoại</th>
                    <th scope="col">Ngày tạo</th>
                    <th scope="col">Trạng thái</th>
                </tr>
            </thead>
            <tbody>
                <?php
                $t = 1;
                foreach ($data as $value) {
                ?>
                    <tr>
                        <th scope="row"><?= $t ?></th>
                        <td><a target="_blank" href="./chitietbaiviet.php?id=<?= $value['id'] ?>"><i class="fa fa-info" aria-hidden="true"></i></a>
                        <?php if(isset($_POST['tt']) && $_POST['tt'] == 2) echo' <a onclick="duyetbaiviet('.$value['id'] .')" style="margin-left:10px;cursor:pointer"> <i class="fa fa-check" aria-hidden="true"></i></a>'?>   
                        </td>
                        <td><?= $value['nameplace'] ?></td>
                        <td><?= $value['province'] ?></td>
                        <td><?= $value['district'] ?></td>
                        <td><?= $value['ward'] ?></td>
                        <td><?= $value['address'] ?></td>
                        <td><?= $value['description'] ?></td>
                        <td><?= $value['content'] ?></td>
                        <td><?= $value['phoneuser'] ?></td>
                        <td><?= $value['datepost'] ?></td>
                        <td><?= $value['status'] ?></td>
                    </tr>
                <?php
                    $t++;
                }
                ?>

            </tbody>
        </table>
        <script>
            new DataTable('#listpost', {
                ordering: false,
                scrollCollapse: true,
                scrollX: true
            });
        </script>
    <?php
        exit();
    }
    if (isset($_GET['danhsachhuyen'])) {
        $data = select_list1($connection, "select district.id as id,district.name as huyen,province.name as tinh from district left join province on district.provinceid=province.id 
    order by district.id desc ");
    ?>
        <style>
            tr th {
                text-align: center !important;
            }

            td {
                text-align: center;
            }
        </style>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <div class="col-md-12" style="text-align: right;">
            <button class="btn btn-primary left" style="margin-bottom:5px" onclick="themmoihuyen()">Thêm mới</button>
        </div>
        <table class="table" id="listhuyen">
            <thead>
                <tr>
                    <th scope="col">STT</th>
                    <th scope="col">Chức năng</th>
                    <th scope="col">Tỉnh/Thành phố</th>
                    <th scope="col">Huyện/Thị trấn</th>
                </tr>
            </thead>
            <tbody>
                <?php
                $t = 1;
                foreach ($data as $value) {
                ?>
                    <tr>
                        <th scope="row"><?= $t ?></th>
                        <td><button type="button" onclick="edithuyen(<?= $value['id'] ?>)" title="Chỉnh sửa"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></button></td>
                        <td><?= $value['tinh'] ?></td>
                        <td><?= $value['huyen'] ?></td>
                    </tr>
                <?php
                    $t++;
                }
                ?>

            </tbody>
        </table>
        <script>
            new DataTable('#listhuyen', {
                ordering: false,

            });

            function edithuyen(id) {
                $.ajax({
                    type: "POST",
                    url: '?edithuyen',
                    data: {
                        id
                    },
                    dateType: 'text',
                    success: function(data) {
                        $("#modal-content").html(data);
                        $("#exampleModal").modal('show');
                    }
                })
            }
        </script>
    <?php
        exit();
    }
    if (isset($_GET['deleteuser'])) {
        $user = select_info($connection, "select * from users where idusers='{$_POST['id']}'");
        $file_path = './images/' . $user[4];
        $file_path1 = './images/' . $user[5];
        if (file_exists($file_path)) {
            unlink($file_path);
        }
        if (file_exists($file_path1)) {
            unlink($file_path1);
        }
        deletedb($connection, 'users', 'idusers=' . $_POST['id']);
        echo 1;
        exit;
    }
    ?>

    <style>
        .lds-dual-ring {
            display: inline-block;
            width: 80px;
            height: 80px;
        }

        .lds-dual-ring:after {
            content: " ";
            display: block;
            width: 64px;
            height: 64px;
            margin: 8px;
            border-radius: 50%;
            border: 6px solid #fff;
            border-color: #6b9311 transparent #6b9311 transparent;
            animation: lds-dual-ring 1.2s linear infinite;
        }

        @keyframes lds-dual-ring {
            0% {
                transform: rotate(0deg);
            }

            100% {
                transform: rotate(360deg);
            }
        }

        body {
            transition: opacity 0.3s ease-in-out;
        }

        body.loading {
            opacity: 0.5;
        }

        #load {
            display: none;
        }

        #exampleModal {
            height: 100% !important;
            width: 100% !important;
        }
    </style>
    <?php include 'header.php'
    ?>
    <?php include 'navbar.php'
    ?>

    <script>
        $(document).ajaxStart(function() {
            $("#load").css('display', 'block');
            $("body").addClass("loading");
        });
        $(document).ajaxStop(function() {
            $("#load").css('display', 'none');
            $("body").removeClass("loading");
        });

        function loader() {
            setTimeout(function() {
                $("#load").css('display', 'block');
                $("body").addClass("loading");
                setTimeout(function() {
                    $("#load").css('display', 'none');
                    $("body").removeClass("loading");
                }, 3000);
            }, 3000);
        }
        getdata();

        function getdata() {
            $.ajax({
                type: "POST",
                url: '?getdata',
                data: {},
                dateType: 'text',
                success: function(data) {
                    $("#content1").html(data);
                }
            })
        }

        function listuser() {
            $.ajax({
                type: "POST",
                url: '?listuser',
                data: {},
                dateType: 'text',
                success: function(data) {
                    $("#content1").html(data);
                }
            })
        }

        function diadanh() {
            $.ajax({
                type: "POST",
                url: '?diadanh',
                data: {},
                dateType: 'text',
                success: function(data) {
                    $("#content1").html(data);
                }
            })
        }

        function danhsachtinh() {
            $.ajax({
                type: "POST",
                url: '?danhsachtinh',
                data: {},
                dateType: 'text',
                success: function(data) {
                    $("#content1").html(data);
                }
            })
        }

        function danhsachhuyen() {
            $.ajax({
                type: "POST",
                url: '?danhsachhuyen',
                data: {},
                dateType: 'text',
                success: function(data) {
                    $("#content1").html(data);
                }
            })
        }

        function deleteuser(id) {
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
                        url: '?deleteuser',
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
                                    listuser();
                                }
                            })
                        }
                    })

                }
            })

        }
        function duyetbaiviet(id) {
            Swal.fire({
                title: 'Bạn chắc chắn muốn duyệt?',
                icon: 'warning',
                showCancelButton: true,
                cancelButtonText: 'Không duyệt',
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Duyệt'
            }).then((result) => {
                if (result.isConfirmed) {
                    $.ajax({
                        type: "POST",
                        url: '?duyetbaiviet',
                        data: {
                            id
                        },
                        dateType: 'text',
                        success: function(data) {
                            Swal.fire(
                                'Duyệt!',
                                'Đã duyệt thành công',
                                'success'
                            ).then((result) => {
                                if (result.isConfirmed) {
                                    danhsachbaiviet(1);
                                }
                            })
                        }
                    })

                }
            })

        }

        function danhsachbaiviet(tt) {
            $.ajax({
                type: "POST",
                url: '?danhsachbaiviet',
                data: {
                    tt
                },
                dateType: 'text',
                success: function(data) {
                    $("#content1").html(data);
                }
            })
        }

        function themmoixa() {
            $.ajax({
                type: "POST",
                url: '?themmoixa',
                data: {},
                dateType: 'text',
                success: function(data) {
                    $("#modal-content").html(data);
                    $("#exampleModal").modal('show');
                }
            })
        }

        function themmoitinh() {
            $.ajax({
                type: "POST",
                url: '?themmoitinh',
                data: {},
                dateType: 'text',
                success: function(data) {
                    $("#modal-content").html(data);
                    $("#exampleModal").modal('show');
                }
            })
        }

        function themmoihuyen() {
            $.ajax({
                type: "POST",
                url: '?themmoihuyen',
                data: {},
                dateType: 'text',
                success: function(data) {
                    $("#modal-content").html(data);
                    $("#exampleModal").modal('show');
                }
            })
        }

        function gethuyen() {
            $.ajax({
                type: "POST",
                url: '?gethuyen',
                data: {
                    tinh: $("#provinceid").val()
                },
                dateType: 'json',
                success: function(data) {
                    $("#districtid").empty().trigger('chosen:updated');
                    $("#districtid").append(`<option value="">---</option>`);
                    $.each(JSON.parse(data), function(index, value) {
                        $("#districtid").append(`<option value="${value.id}">${value.text}</option>`)
                    });
                    $('#districtid').trigger('chosen:updated');
                }

            })
        }
    </script>

    </html>
<?php } else {
    header("Location: ./index.php");
}
?>
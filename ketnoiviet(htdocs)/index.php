<?php
session_start();

require_once "connectForWeb.php";
if (isset($_REQUEST['checklogin'])) {
  $data = select_info($connection, "select * from users where phonenumber='{$_POST['email']}' and password='{$_POST['password']}' and Role = 'admin'");
  if (!empty($data)) {
    $_SESSION['username'] = $_POST['email'];
    echo 1;
  } else {
    echo 100;
  }
  exit();
}
session_destroy();
?>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Đăng nhập</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>

<body>
  <section class="vh-100" style="background-color: #9A616D;">
    <div class="container py-5 h-100">
      <div class="row d-flex justify-content-center align-items-center h-100">
        <div class="col col-xl-10">
          <div class="card" style="border-radius: 1rem;">
            <div class="row g-0">
              <div class="col-md-6 col-lg-5 d-none d-md-block">
                <img src="./images/nen2.jpg" alt="login form" class="img-fluid" style="border-radius: 1rem 0 0 1rem;" />
              </div>
              <div class="col-md-6 col-lg-7 d-flex align-items-center">
                <div class="card-body p-4 p-lg-5 text-black">
                  <form id="login">
                    <h5 class="mb-3 pb-3" style="text-align:center;font-weight:bold;font-size:20px">ĐĂNG NHẬP</h5>
                    <div class="form-outline mb-4">
                      <label class="form-label" for="email">Số điện thoại</label>
                      <input type="email" id="email" class="form-control form-control-lg" />
                    </div>
                    <div class="form-outline mb-4">
                      <label class="form-label" for="form2Example27">Password</label>
                      <input type="password" id="password" class="form-control form-control-lg" />
                    </div>
                    <div class="form-outline mb-4">
                      <code id="mess" style="text-align:center;display:none">Vui lòng kiểm tra lại tên đăng nhập hoặc mật khẩu!!!</code>
                    </div>
                    <div class="pt-1 mb-4" style="text-align:center">
                      <button class="btn btn-dark btn-lg btn-block" type="button" onclick="getsubmit()">Login</button>
                    </div>
                    <div class="text-center">
                      <p>or sign up with:</p>
                      <button type="button" class="btn btn-link btn-floating mx-1">
                        <i class="fab fa-facebook-f"></i>
                      </button>

                      <button type="button" class="btn btn-link btn-floating mx-1">
                        <i class="fab fa-google"></i>
                      </button>

                      <button type="button" class="btn btn-link btn-floating mx-1">
                        <i class="fab fa-twitter"></i>
                      </button>

                      <button type="button" class="btn btn-link btn-floating mx-1">
                        <i class="fab fa-github"></i>
                      </button>
                    </div>
                  </form>

                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
  <script>
    function getsubmit() {
      $.ajax({
        type: 'post',
        url: '?checklogin',
        data: {
          email: $("#email").val(),
          password: $("#password").val(),
        },
        dateType: 'json',
        success: function(data) {
          console.log(data)
          if (data == 1) {
            window.location.href='./trangquantri.php';
          } else {
            $("#mess").css('display', 'block');
          }
        }

      })
    }
  </script>
</body>

</html>
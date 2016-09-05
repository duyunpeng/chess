<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="zh_cn">
<head>
[@block name="Meta"]
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta charset="utf-8">
    <meta name="description" content="">
    <meta name="author" content="yjh">
    <link rel="shortcut icon" href="[@spring.url '/resources/images/favicon.ico' /]" type="image/x-icon"/>
[/@block]
    <title>信用榜 - 下载</title>

    <!-- Bootstrap core CSS -->
    <link href="[@spring.url '/resources/bootstrap/css/bootstrap.min.css'/]" rel="stylesheet">

    <link href="[@spring.url '/resources/app/download/download.css'/]" rel="stylesheet"/>

    <script src="[@spring.url '/resources/js/jquery.min.js'/]"></script>
</head>

<body class="back-img">
<div class="sing-cont1">
    <img width="100%" id="QRCode" data="${id}" src="[@spring.url '/download/qr_code/${id}'/]" alt="">
</div>
<div class="sing-cont2">
    <a href="[@spring.url '/download/${id}'/]"><img width="100%" src="[@spring.url '/resources/images/ios-btn.png'/]" alt=""></a>
</div>
<div class="sing-cont2">
    <a href="[@spring.url '/download/${id}'/]"><img width="100%" src="[@spring.url '/resources/images/android-btn.png'/]" alt=""></a>
</div>
</body>
</html>
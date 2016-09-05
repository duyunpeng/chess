[@override name="title"]首页控制台[/@override]

[@override name="topResources"]
    [@super /]
<!-- 文件上传 -->
<link href="[@spring.url '/resources/js/upload/webuploader.css'/]" rel="stylesheet">
<link href="[@spring.url '/resources/css/upload.css'/]" rel="stylesheet">
[/@override]

[@override name="breadcrumb"]
<div class="row">
    <div class="col-sm-12">
        <div class="page-title">
            控制面板
        </div>
        <div class="page-sub-header">
            欢迎回来, XXXXXXX , <i class="fa fa-map-marker text-danger"></i> XXXXXXX
        </div>
    </div>
</div>
[/@override]
[@override name="subContent"]
<div class="row">
    <h1 class="text-center">我是首页</h1>
</div>
[/@override]

[@override name="bottomResources"]
    [@super /]
[/@override]
[@extends name="/decorator.ftl"/]

[@override name="title"]版本管理 - 版本查看[/@override]
[@override name="topResources"]
    [@super /]

[/@override]

[@override name="breadcrumb"]
<ul class="breadcrumb">
    <li><a href="/">首页</a></li>
    <li><a href="/app_Version/pagination">版本管理</a></li>
    <li>版本查看</li>
</ul>
[/@override]

[@override name="headerText"]
版本 查看
[/@override]

[@override name="subContent"]
    [@mc.showAlert /]
<div class="row">
    <div class="col-lg-12">
        <ul class="contract-show">
            <li>
                <span class="col-md-3">app版本号</span>
                <div class="col-md-8 contract-box">${appVersion.appVersion!}</div>
            </li>
            <li>
                <span class="col-md-3">更新时间</span>
                <div class="col-md-8 contract-box">${appVersion.updateDate!}</div>
            </li>
            <li>
                <span class="col-md-3">更新内容</span>
                <div class="col-md-8 contract-box">${appVersion.updateContent!}</div>
            </li>
            <li>
                <span class="col-md-3">状态</span>
                <div class="col-md-8 contract-box">${(appVersion.status.getName())!}</div>
            </li>
            <div>
                <div class="col-sm-offset-6 col-sm-12">
                    <a href="[@spring.url '/app_Version/create' /]" class="btn btn-success">再创建一个</a>
                    <a href="[@spring.url '/app_Version/pagination' /]" class="btn btn-default">返回列表</a>
                </div>
            </div>
        </ul>
    </div>
</div>
[/@override]

[@override name="bottomResources"]
    [@super /]
<script type="text/javascript">
    var data_list = $(".data-list");
    data_list.slimScroll({
        height: '600px'
    });
</script>
[/@override]
[@extends name="/decorator.ftl"/]
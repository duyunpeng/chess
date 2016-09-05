[@override name="title"]意见管理 - 意见查看[/@override]
[@override name="topResources"]
    [@super /]

[/@override]

[@override name="breadcrumb"]
<ul class="breadcrumb">
    <li><a href="/">首页</a></li>
    <li><a href="/feed_back/pagination">意见管理</a></li>
    <li>意见查看</li>
</ul>
[/@override]

[@override name="headerText"]
意见 查看
[/@override]

[@override name="subContent"]
    [@mc.showAlert /]
<div class="row">
    <div class="col-lg-12">
        <ul class="contract-show">
            <li>
                <span class="col-md-3">联系邮箱</span>
                <div class="col-md-8 contract-box">${feedBack.email!}</div>
            </li>
            <li>
                <span class="col-md-3">联系电话</span>
                <div class="col-md-8 contract-box">${feedBack.phone!}</div>
            </li>
            <li>
                <span class="col-md-3">联系QQ</span>
                <div class="col-md-8 contract-box">${feedBack.qq!}</div>
            </li>
            <li>
                <span class="col-md-3">意见内容</span>
                <div class="col-md-8 contract-box">${feedBack.content!}</div>
            </li>
            <li>
                <span class="col-md-3">处理状态</span>
                <div class="col-md-8 contract-box">${feedBack.status.getName()!}</div>
            </li>
            <div>
                <div class="col-sm-offset-6 col-sm-12">
                    <a href="[@spring.url '/feed_back/pagination' /]" class="btn btn-default">返回列表</a>
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
[@override name="title"]权限管理 - 权限查看[/@override]
[@override name="topResources"]
    [@super /]

[/@override]

[@override name="breadcrumb"]
<ul class="breadcrumb">
    <li><a href="/">首页</a></li>
    <li><a href="/permission/pagination">权限管理</a></li>
    <li>权限查看</li>
</ul>
[/@override]

[@override name="headerText"]
权限 查看
[/@override]

[@override name="subContent"]
    [@mc.showAlert /]
<div class="row">
    <div class="col-lg-12">
        <ul class="contract-show">
            <li>
                <span class="col-md-3">权限名称</span>
                <div class="col-md-8 contract-box">${permission.name!}</div>
            </li>
            <li>
                <span class="col-md-3">权限值</span>
                <div class="col-md-8 contract-box">${permission.value!}</div>
            </li>
            <li>
                <span class="col-md-3">权限描述</span>
                <div class="col-md-8 contract-box">${permission.description!}</div>
            </li>
            <div>
                <div class="col-sm-offset-6 col-sm-12">
                    <a href="[@spring.url '/permission/create' /]" class="btn btn-success">再创建一个</a>
                    <a href="[@spring.url '/permission/pagination' /]" class="btn btn-default">返回列表</a>
                </div>
            </div>
        </ul>
    </div>
</div>
[/@override]

[@override name="bottomResources"]
    [@super /]

[/@override]
[@extends name="/decorator.ftl"/]
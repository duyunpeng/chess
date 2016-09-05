[@override name="title"]举报管理 - 举报查看[/@override]
[@override name="topResources"]
    [@super /]

[/@override]

[@override name="breadcrumb"]
<ul class="breadcrumb">
    <li><a href="/">首页</a></li>
    <li><a href="/report/pagination">举报管理</a></li>
</ul>
[/@override]

[@override name="headerText"]
举报 查看
[/@override]

[@override name="subContent"]
    [@mc.showAlert /]
<div class="row">
    <div class="col-lg-12">
        <ul class="contract-show">
            <li>
                <span class="col-md-3">举报人</span>
                <div class="col-md-8 contract-box">${report.reportUser.userName!}</div>
            </li>
            <li>
                <span class="col-md-3">被举报人</span>
                <div class="col-md-8 contract-box">${report.quiltReportUser.userName!}</div>
            </li>
            <li>
                <span class="col-md-3">标题</span>
                <div class="col-md-8 contract-box">${report.title!}</div>
            </li>
            <li>
                <span class="col-md-3">举报内容</span>
                <div class="col-md-8 contract-box">${report.content!}</div>
            </li>
            <li>
                <span class="col-md-3">举报图片</span>
                <div class="col-md-8 contract-box">
                    [#if report.pictures??]
                        [#list report.pictures as pic]
                            <img src="${pic.picPath!}"/>
                        [/#list]
                    [/#if]
                </div>
            </li>
            <li>
                <span class="col-md-3">举报状态</span>
                <div class="col-md-8 contract-box">${(report.handleStatus.getName())!}</div>
            </li>
            <li>
                <span class="col-md-3">处理结果说明</span>
                <div class="col-md-8 contract-box">${report.handleResult!}</div>
            </li>
            <li>
                <span class="col-md-3">处理时间</span>
                <div class="col-md-8 contract-box">${report.handleDate!}</div>
            </li>
            </div>
            <div>
                <div class="col-sm-offset-6 col-sm-12">
                    <a href="[@spring.url '/report/pagination' /]" class="btn btn-default">返回列表</a>
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
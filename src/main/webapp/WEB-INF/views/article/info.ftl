[@override name="title"]文章管理 - 文章查看[/@override]
[@override name="topResources"]
    [@super /]
[/@override]

[@override name="breadcrumb"]
<ul class="breadcrumb">
    <li><a href="/">首页</a></li>
    <li><a href="/article/pagination">文章管理</a></li>
    <li>文章查看</li>
</ul>
[/@override]

[@override name="headerText"]
文章 查看
[/@override]

[@override name="subContent"]
    [@mc.showAlert /]
<div class="row">
    <div class="col-lg-12">
        <ul class="contract-show">
            <li>
                <span class="col-md-3">文章标题</span>
                <div class="col-md-8 contract-box">${article.title!}</div>
            </li>
            <li>
                <span class="col-md-3">文章创建时间</span>
                <div class="col-md-8 contract-box">[@mc.dateShow article.createDate/]</div>
            </li>
            <li>
                <span class="col-md-3">文章发布人</span>
                <div class="col-md-8 contract-box">${article.account.userName!}</div>
            </li>
            <li>
                <span class="col-md-3">文章状态</span>
                <div class="col-md-8 contract-box">${(article.status.getName())!}</div>
            </li>
            <li>
                <span class="col-md-3">文章内容</span>
                <div class="col-md-8 contract-box">
                ${article.content!}
                </div>
            </li>
            <div>
                <div class="col-sm-offset-6 col-sm-12">
                    <a href="[@spring.url '/article/create' /]" class="btn btn-success">再创建一个</a>
                    <a href="[@spring.url '/article/pagination' /]" class="btn btn-default">返回列表</a>
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
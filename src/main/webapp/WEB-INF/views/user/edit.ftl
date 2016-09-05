[@override name="title"]用户管理 - 用户修改[/@override]
[@override name="topResources"]
    [@super /]
[/@override]

[@override name="breadcrumb"]
<ul class="breadcrumb">
    <li><a href="/">首页</a></li>
    <li><a href="/user/pagination">用户管理</a></li>
    <li>用户修改</li>
</ul>
[/@override]

[@override name="headerText"]
用户 修改
[/@override]

[@override name="subContent"]
    [@mc.showAlert /]
<div class="row">
    <div class="col-lg-8">
        <form class="form-horizontal" action="/user/edit" method="post" data-parsley-validate>

            <input type="hidden" name="id" value="${user.id!command.id}"/>
            <input type="hidden" name="version" value="${user.version!command.version}"/>

            <div class="form-group">
                <label for="name" class="col-md-3 control-label">用户名*</label>
                <div class="col-md-9">
                    <input type="text" class="form-control" value="${user.userName!}" disabled/>
                </div>
            </div>

            [@spring.bind "command.email"/]
            <div class="form-group">
                <label for="email" class="col-md-3 control-label">邮箱</label>
                <div class="col-md-9">
                    <input type="text" class="form-control" id="email" name="email" value="${user.email!command.email}"
                           placeholder="输入邮箱" data-parsley-email/>
                    [@spring.showErrors "email" "parsley-required"/]
                </div>
            </div>

            [@spring.bind "command.name"/]
            <div class="form-group">
                <label for="name" class="col-md-3 control-label">昵称</label>
                <div class="col-md-9">
                    <input type="text" class="form-control" id="name" name="name" value="${user.name!command.name}"
                           placeholder="输入昵称" data-parsley-email/>
                    [@spring.showErrors "name" "parsley-required"/]
                </div>
            </div>

            <div class="text-center m-top-md">
                <button type="reset" class="btn btn-default">重置</button>
                <button type="submit" class="btn btn-success">修改</button>
            </div>
        </form>
    </div>
    <div class="col-lg-3">
        <ul class="blog-sidebar-list font-18">创建注意事项
            <li>*位必填项</li>
        </ul>
    </div>
</div>

[/@override]

[@override name="bottomResources"]
    [@super /]
[/@override]
[@extends name="/decorator.ftl"/]
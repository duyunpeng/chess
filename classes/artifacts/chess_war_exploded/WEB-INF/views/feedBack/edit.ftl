[@override name="title"]意见管理 - 意见修改[/@override]
[@override name="topResources"]
    [@super /]

[/@override]

[@override name="breadcrumb"]
<ul class="breadcrumb">
    <li><a href="/">首页</a></li>
    <li><a href="/feed_back/pagination">意见管理</a></li>
    <li>意见修改</li>
</ul>
[/@override]

[@override name="headerText"]
区域 修改
[/@override]

[@override name="subContent"]
    [@mc.showAlert /]
<div class="row">
    <div class="col-lg-8">
        <form class="form-horizontal" action="/feed_back/edit" method="post" data-parsley-validate>

            <input type="hidden" name="id" value="${feedBack.id!command.id}" />
            <input type="hidden" name="version" value="${feedBack.version!command.version}" />

            [@spring.bind "command.email"/]
            <div class="form-group">
                <label for="email" class="col-md-3 control-label">联系邮箱*</label>
                <div class="col-md-9">
                    <input class="form-control" id="email" name="email"
                           value="${feedBack.email!command.email}" placeholder="输入联系邮箱"
                           data-parsley-required="true" data-parsley-required-messages="联系邮箱不能为空"
                           data-parsley-trigger="change"/>
                    [@spring.showErrors "email" "parsley-required"/]
                </div>
            </div>

            [@spring.bind "command.phone"/]
            <div class="form-group">
                <label for="phone" class="col-md-3 control-label">联系电话*</label>
                <div class="col-md-9">
                    <input class="form-control" id="phone" name="phone"
                           value="${feedBack.phone!command.phone}" placeholder="输入联系电话"
                           data-parsley-required="true" data-parsley-required-messages="联系电话不能为空"
                           data-parsley-trigger="change"/>
                    [@spring.showErrors "phone" "parsley-required"/]
                </div>
            </div>

            [@spring.bind "command.qq"/]
            <div class="form-group">
                <label for="qq" class="col-md-3 control-label">联系QQ*</label>
                <div class="col-md-9">
                    <input class="form-control" id="qq" name="qq"
                           value="${feedBack.qq!command.qq}" placeholder="输入联系QQ"
                           data-parsley-required="true" data-parsley-required-messages="联系QQ不能为空"
                           data-parsley-trigger="change"/>
                    [@spring.showErrors "qq" "parsley-required"/]
                </div>
            </div>

            [@spring.bind "command.content"/]
            <div class="form-group">
                <label for="content" class="col-md-3 control-label">意见内容*</label>
                <div class="col-md-9">
                    <input class="form-control" id="content" name="content"
                           value="${feedBack.content!command.content}" placeholder="输入意见内容"
                           data-parsley-required="true" data-parsley-required-messages="意见内容不能为空"
                           data-parsley-trigger="change"/>
                    [@spring.showErrors "content" "parsley-required"/]
                </div>
            </div>

            [@spring.bind "command.status"/]
            <div class="form-group">
                <label for="status" class="col-md-3 control-label">处理状态*</label>
                <div class="col-md-9">
                    <input class="form-control" id="status" name="status"
                           value="${feedBack.status!command.status}" placeholder="输入处理状态"
                           data-parsley-required="true" data-parsley-required-messages="处理状态不能为空"
                           data-parsley-trigger="change"/>
                    [@spring.showErrors "status" "parsley-required"/]
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
<script src="[@spring.url '/resources/js/area.js'/]"></script>
<script>
    $(".area-cascade").areaCascade("parent");
</script>
[/@override]
[@extends name="/decorator.ftl"/]
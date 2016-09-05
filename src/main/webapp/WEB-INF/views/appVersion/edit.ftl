[@override name="title"]版本管理 - 版本修改[/@override]
[@override name="topResources"]
    [@super /]

[/@override]

[@override name="breadcrumb"]
<ul class="breadcrumb">
    <li><a href="/">首页</a></li>
    <li><a href="/app_Version/pagination">版本管理</a></li>
    <li>版本修改</li>
</ul>
[/@override]

[@override name="headerText"]
区域 修改
[/@override]

[@override name="subContent"]
    [@mc.showAlert /]
<div class="row">
    <div class="col-lg-8">
        <form class="form-horizontal" action="/app_Version/edit" method="post" data-parsley-validate>

            <input type="hidden" name="id" value="${appVersion.id!command.id}" />
            <input type="hidden" name="version" value="${appVersion.version!command.version}" />

            [@spring.bind "command.appVersion"/]
            <div class="form-group">
                <label for="appVersion" class="col-md-3 control-label">app版本号*</label>
                <div class="col-md-9">
                    <input class="form-control" id="appVersion" name="appVersion"
                           value="${appVersion.appVersion!command.appVersion}" placeholder="输入app版本号"
                           data-parsley-required="true" data-parsley-required-messages="app版本号不能为空"
                           data-parsley-trigger="change"/>
                    [@spring.showErrors "appVersion" "parsley-required"/]
                </div>
            </div>

            [@spring.bind "command.updateContent"/]
            <div class="form-group">
                <label for="updateContent" class="col-md-3 control-label">更新内容*</label>
                <div class="col-md-9">
                    <input class="form-control" id="updateContent" name="updateContent"
                           value="${appVersion.updateContent!command.updateContent}" placeholder="输入更新内容"
                           data-parsley-required="true" data-parsley-required-messages="更新内容不能为空"
                           data-parsley-trigger="change"/>
                    [@spring.showErrors "updateContent" "parsley-required"/]
                </div>
            </div>

            [@spring.bind "command.status"/]
            <div class="form-group">
                <label for="status" class="col-md-3 control-label">状态*</label>
                <div class="col-md-9">
                    <select class="form-control" name="status" id="status"
                            data-parsley-required="true" data-parsley-required-messages="请选择版本状态"
                            data-parsley-trigger="change">
                        [#assign status = (appVersion.status!command.status)?default("") /]
                        <option value="">请选择</option>
                        <option value="ENABLE" [@mc.selected status "ENABLE"/]>启用</option>
                        <option value="DISABLE" [@mc.selected status "DISABLE"/]>禁用</option>
                    </select>
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
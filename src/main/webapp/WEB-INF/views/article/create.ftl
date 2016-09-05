[@override name="title"]文章管理 - 文章创建[/@override]
[@override name="topResources"]
    [@super /]
<script type="text/javascript" charset="utf-8" src="[@spring.url '/resources/ueditor/ueditor.config.js'/]"></script>
<script type="text/javascript" charset="utf-8" src="[@spring.url '/resources/ueditor/ueditor.all.min.js'/]"></script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="[@spring.url '/resources/ueditor/lang/zh-cn/zh-cn.js'/]"></script>
[/@override]

[@override name="breadcrumb"]
<ul class="breadcrumb">
    <li><a href="/">首页</a></li>
    <li><a href="/article/pagination">文章管理</a></li>
    <li>文章创建</li>
</ul>
[/@override]

[@override name="headerText"]
文章 创建
[/@override]

[@override name="subContent"]
    [@mc.showAlert /]
<div class="row">
    <div class="col-lg-8">
        <form class="form-horizontal" id="article-from" action="/article/create" method="post" data-parsley-validate>

            [@spring.bind "command.title"/]
            <div class="form-group">
                <label for="title" class="col-md-3 control-label">文章标题*</label>
                <div class="col-md-9">
                    <input class="form-control" id="name" name="title"
                           value="${command.title!}" placeholder="输入文章标题"
                           data-parsley-required="true" data-parsley-required-messages="文章标题不能为空"
                           data-parsley-trigger="change"/>
                    [@spring.showErrors "title" "parsley-required"/]
                </div>
            </div>

            [@spring.bind "command.status"/]
            <div class="form-group">
                <label for="status" class="col-md-3 control-label">文章状态*</label>
                <div class="col-md-9">
                    <select class="form-control" name="status" id="status"
                            data-parsley-required="true" data-parsley-required-messages="请选择角色状态"
                            data-parsley-trigger="change">
                        [#assign status = (command.status!)?default("") /]
                        <option value="">请选择</option>
                        <option value="ENABLE" [@mc.selected status "ENABLE"/]>启用</option>
                        <option value="DISABLE" [@mc.selected status "DISABLE"/]>禁用</option>
                    </select>
                    [@spring.showErrors "status" "parsley-required"/]
                </div>
            </div>

            [@spring.bind "command.content"/]
            <div class="form-group">
                <label for="content" class="col-md-3 control-label">文章内容*</label>
                <div class="col-md-9">
                    <script id="editor" type="text/plain" name="content" style="width:100%;height:300px;">
                        ${command.content!}

                    </script>
                    [@spring.showErrors "content" "parsley-required"/]
                </div>
            </div>

            <div class="text-center m-top-md">
                <button type="reset" class="btn btn-default">重置</button>
                <button type="submit" class="btn btn-success">创建</button>
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
<script src="[@spring.url '/resources/js/ajax.js'/]"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var ue = UE.getEditor('editor');
        $("#article-from").submit(function () {
            if (UE.getEditor('editor').hasContents() == false) {
                alert("请输入文章内容!");
                return false;
            }

        })
    })
</script>
[/@override]
[@extends name="/decorator.ftl"/]
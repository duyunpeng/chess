[@override name="title"]文章管理 - 文章修改[/@override]
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
    <li>文章修改</li>
</ul>
[/@override]

[@override name="headerText"]
文章 修改
[/@override]

[@override name="subContent"]
    [@mc.showAlert /]
<div class="row">
    <div class="col-lg-8">
        <form class="form-horizontal" action="/article/edit" method="post" data-parsley-validate>

            <input type="hidden" name="id" value="${article.id!command.id}"/>
            <input type="hidden" name="version" value="${article.version!command.version}"/>

            [@spring.bind "command.title"/]
            <div class="form-group">
                <label for="title" class="col-md-3 control-label">文章标题*</label>
                <div class="col-md-9">
                    <input class="form-control" id="name" name="title"
                           value="${article.title!command.title}" placeholder="输入文章标题"
                           data-parsley-required="true" data-parsley-required-messages="文章标题不能为空"
                           data-parsley-trigger="change"/>
                    [@spring.showErrors "title" "parsley-required"/]
                </div>
            </div>

            [@spring.bind "command.content"/]
            <div class="form-group">
                <label for="content" class="col-md-3 control-label">文章内容*</label>
                <div class="col-md-9">
                    <script id="editor" type="text/plain" name="content" style="width:100%;height:300px;">
                        ${article.content!command.content!}

                    </script>
                    [@spring.showErrors "content" "parsley-required"/]
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
<script src="[@spring.url '/resources/js/ajax.js'/]"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //加载appKey数据
        $("#type").selectAjaxData({url: "/article_type/list"});

        var ue = UE.getEditor('editor');
    })
</script>
[/@override]
[@extends name="/decorator.ftl"/]
[@override name="title"]意见管理[/@override]
[@override name="topResources"]
    [@super /]

[/@override]

[@override name="breadcrumb"]
<ul class="breadcrumb">
    <li><a href="/">首页</a></li>
    <li>意见管理</li>
</ul>
[/@override]

[@override name="headerText"]
意见 管理
[/@override]

[@override name="subContent"]
    [@mc.showAlert /]
<div class="smart-widget widget-dark-blue">
    <div class="smart-widget-header">
        <span class="smart-widget-option">
            [#--<span class="refresh-icon-animated" style="display: none;"><i--]
            [#--class="fa fa-circle-o-notch fa-spin"></i></span>--]
                <a href="#" class="widget-toggle-hidden-option"><i class="fa fa-cog"></i></a>
            <a href="#" class="widget-collapse-option" data-toggle="collapse"><i class="fa fa-chevron-up"></i></a>
            [#--<a href="#" class="widget-refresh-option"><i class="fa fa-refresh"></i></a>--]
            <a href="#" class="widget-remove-option"><i class="fa fa-times"></i></a>
        </span>
        <form class="form-inline no-margin" role="form">
            <div class="form-group">
                <label for="email" class="control-label">联系邮箱</label>
                <input type="text" class="form-control" id="email" name="email" value="${command.email!}"
                       placeholder="联系邮箱"/>
            </div>
            <div class="form-group">
                <label for="phone" class="control-label">联系电话</label>
                <input type="text" class="form-control" id="phone" name="phone" value="${command.phone!}"
                       placeholder="联系电话"/>
            </div>
            <div class="form-group">
                <label for="qq" class="control-label">联系QQ</label>
                <input type="text" class="form-control" id="qq" name="qq" value="${command.qq!}"
                       placeholder="联系QQ"/>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-md btn-success">查询</button>
            </div>
        </form>
    </div>
    <div class="smart-widget-inner">
        <div class="smart-widget-hidden-section" style="display: none;">
            <ul class="widget-color-list clearfix">
                <li style="background-color:#20232b;" data-color="widget-dark"></li>
                <li style="background-color:#4c5f70;" data-color="widget-dark-blue"></li>
                <li style="background-color:#23b7e5;" data-color="widget-blue"></li>
                <li style="background-color:#2baab1;" data-color="widget-green"></li>
                <li style="background-color:#edbc6c;" data-color="widget-yellow"></li>
                <li style="background-color:#fbc852;" data-color="widget-orange"></li>
                <li style="background-color:#e36159;" data-color="widget-red"></li>
                <li style="background-color:#7266ba;" data-color="widget-purple"></li>
                <li style="background-color:#f5f5f5;" data-color="widget-light-grey"></li>
                <li style="background-color:#fff;" data-color="reset"></li>
            </ul>
        </div>
        <div class="smart-widget-body no-padding">
            <div class="padding-md">
                <section class="overflow-auto nice-scrollbar">
                    <table class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>联系邮箱</th>
                            <th>联系电话</th>
                            <th>联系QQ</th>
                            <th>意见内容</th>
                            <th>处理状态</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                            [#if pagination.data??]
                                [#list pagination.data as feedBack ]
                                <tr>
                                    <td>${feedBack.email!}</td>
                                    <td>${feedBack.phone!}</td>
                                    <td>${feedBack.qq!}</td>
                                    <td>${feedBack.content!}</td>
                                    <td>${(feedBack.status.getName())!}</td>
                                    <td>
                                        <a href="[@spring.url '/feed_back/info/${feedBack.id!}'/]"
                                           data-toggle="tooltip" data-placement="top" title="点击查看详情">
                                            <span class="label label-info">详情</span>
                                        </a>
                                    [#if  feedBack.status != "All" ]
                                        [#if feedBack.status == "WAIT_HANDLE"]
                                            <a href="[@spring.url '/feed_back/update_status?id=${feedBack.id!}&version=${feedBack.version!}'/]"
                                               data-toggle="tooltip" data-placement="top" title="点击处理此数据">
                                                <span class="label label-danger">处理</span>
                                            </a>
                                        [#elseif feedBack.status =="IN_HANDLE"]
                                            <a href="[@spring.url '/feed_back/update_status?id=${feedBack.id!}&version=${feedBack.version!}'/]"
                                               data-toggle="tooltip" data-placement="top" title="点击完成处理此数据">
                                                <span class="label label-danger">完成处理</span>
                                            </a>
                                        [/#if]
                                    [/#if]
                                    </td>
                                </tr>

                                [/#list]

                            [/#if]
                        </tbody>
                    </table>
                </section>
            </div>
            <div class="bg-grey">
                [#if pagination!]
            [@mc.showPagination '/feed_back/pagination?email=${command.email!}&phone=${command.phone!}&qq=${command.qq!}' /]
        [/#if]
            </div>
        </div>

    </div>
</div>
    [#include 'shared/confirmation.ftl'/]
[/@override]

[@override name="bottomResources"]
    [@super /]
[/@override]
[@extends name="/decorator.ftl"/]
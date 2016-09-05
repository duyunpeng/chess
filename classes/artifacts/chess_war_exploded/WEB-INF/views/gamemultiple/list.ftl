[@override name="title"]游戏倍数管理[/@override]
[@override name="topResources"]
    [@super /]

[/@override]

[@override name="breadcrumb"]
<ul class="breadcrumb">
    <li><a href="/">首页</a></li>
    <li>游戏倍数管理</li>
</ul>
[/@override]

[@override name="headerText"]
游戏倍数 管理
[/@override]

[@override name="subContent"]
    [@mc.showAlert /]
<div class="row margin-md">
    <a href="/game_multiple/create" class="btn btn-md btn-success">新增游戏倍数</a>
</div>
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
                <label for="gameType" class="control-label">游戏*</label>
                <select class="form-control" name="gameType" id="gameType"
                        data-parsley-required="true" data-parsley-required-messages="请选择游戏"
                        data-parsley-trigger="change">
                    [#assign gameType = (command.gameType!)?default("") /]
                    <option value="">请选择</option>
                    <option value="LANDLORDS" [@mc.selected gameType "LANDLORDS" /]>斗地主
                    </option>
                    <option value="THREE_CARD" [@mc.selected gameType "THREE_CARD" /]>扎金花</option>
                    <option value="BULLFIGHT" [@mc.selected gameType "BULLFIGHT" /]>斗牛</option>
                </select>
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
                            <th>游戏</th>
                            <th>游戏倍数</th>
                            <th>创建时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                            [#if pagination.data??]
                                [#list pagination.data as multiple ]
                                <tr>
                                    <td>${multiple.gameType.getName()!}</td>
                                    <td>${multiple.multiple!}</td>
                                    <td>${multiple.createDate!}</td>
                                    <td>
                                        <a href="[@spring.url '/game_multiple/edit/${multiple.id!}'/]"
                                           data-toggle="tooltip" data-placement="top" title="点击修改信息">
                                            <span class="label label-success">修改</span>
                                        </a>
                                        <a href="[@spring.url '/game_multiple/delete/${multiple.id!}'/]"
                                           data-toggle="tooltip" data-placement="top" title="点击删除信息">
                                            <span class="label label-primary">删除</span>
                                        </a>
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
            [@mc.showPagination '/multiple/pagination?gameType=${command.gameType!}' /]
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
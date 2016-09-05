[@override name="title"]提现记录[/@override]
[@override name="topResources"]
    [@super /]
<link href="[@spring.url '/resources/js/datetimepicker/jquery.datetimepicker.css' /]" rel="stylesheet" type="text/css"/>
[/@override]

[@override name="breadcrumb"]
<ul class="breadcrumb">
    <li><a href="/">首页</a></li>
    <li>提现记录</li>
</ul>
[/@override]

[@override name="headerText"]
提现记录 管理
[/@override]

[@override name="subContent"]
    [@mc.showAlert /]
<div class="smart-widget widget-dark-blue">
    <div class="smart-widget-header">
        <span class="smart-widget-option">
                <a href="#" class="widget-toggle-hidden-option"><i class="fa fa-cog"></i></a>
            <a href="#" class="widget-collapse-option" data-toggle="collapse"><i class="fa fa-chevron-up"></i></a>
            <a href="#" class="widget-remove-option"><i class="fa fa-times"></i></a>
        </span>
        <form class="form-inline no-margin" role="form">
            <div class="form-group">
                <label for="userName" class="control-label">用户名</label>
                <input type="text" class="form-control" id="userName" name="userName" value="${command.userName!}"
                       placeholder="用户名"/>
            </div>
            <div class="form-group">
                <label for="status" class="control-label">是否成功</label>
                <select name="status" id="status" class="form-control">
                    [#assign status = (command.status!)?default("") /]
                    <option value="" [@mc.selected status "" /]>全部</option>
                    <option value="PENDING" [@mc.selected status "PENDING" /]>处理中</option>
                    <option value="FINISH" [@mc.selected status "FINISH" /]>处理完成</option>
                </select>
            </div>
            <div class="form-group">
                <label for="startTime" class="control-label">时间</label>
                <input type="text" class="form-control" value="${command.startTime!}" id="startDate"
                       name="startTime"/>
                到
                <input type="text" class="form-control" value="${command.endTime!}" id="endDate"
                       name="endTime"/>
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
                            <th>申请人</th>
                            <th>申请时间</th>
                            <th>金额</th>
                            <th>状态</th>
                            <th>完成时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                            [#if pagination.data??]
                                [#list pagination.data as withdraw ]
                                <tr>
                                    <td>${withdraw.username!}</td>
                                    <td>${withdraw.createTime!}</td>
                                    <td>${withdraw.money!}</td>
                                    <td>${withdraw.status.getName()!}</td>
                                    <td>${withdraw.finishTime!}</td>
                                    <td>
                                        <div class="btn-group">
                                            [#if withdraw.status.getValue() == 0]
                                                <button data-toggle="dropdown"
                                                        class="btn btn-primary dropdown-toggle btn-sm">
                                                    操作
                                                    <i class="icon-angle-down icon-on-right"></i>
                                                </button>

                                                <ul class="dropdown-menu">
                                                    <li>
                                                        <a class="blue"
                                                           href="[@spring.url '/withdraw/finish?id=${withdraw.id!}&version=${withdraw.version}'/]">处理完成</a>
                                                    </li>
                                                </ul>
                                            [/#if]
                                        </div>
                                    </td>
                                </tr>
                                [/#list]
                            [/#if]
                        </tbody>
                    </table>
                </section>
            </div>
            <div class="bg-grey">
                [#if pagination??]
                    [@mc.showPagination '/withdraw/list?startTime=${command.startTime!}&endTime=${command.endTime!}&baseUser=${command.baseUser!}&status=${command.status!}' /]
                [/#if]
            </div>
        </div>

    </div>
</div>
    [#include 'shared/confirmation.ftl'/]
[/@override]

[@override name="bottomResources"]
    [@super /]
<script src="[@spring.url '/resources/js/datetimepicker/jquery.datetimepicker.full.js'/]"></script>
<script type="text/javascript">


    $.datetimepicker.setLocale('en');
    $('#startDate').datetimepicker({
        dayOfWeekStart: 1,
        lang: 'en',
    });
    $('#endDate').datetimepicker({
        dayOfWeekStart: 1,
        lang: 'en',
    });


</script>
[/@override]
[@extends name="/decorator.ftl"/]
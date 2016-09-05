[@override name="title"]用户管理[/@override]
[@override name="topResources"]
    [@super /]

[/@override]

[@override name="breadcrumb"]
<ul class="breadcrumb">
    <li><a href="/">首页</a></li>
    <li>用户管理</li>
</ul>
[/@override]

[@override name="headerText"]
用户 管理
[/@override]

[@override name="subContent"]
    [@mc.showAlert /]
<div class="row margin-md">
    <a href="/user/create" class="btn btn-md btn-success">新增用户</a>
</div>
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
                <label for="status" class="control-label">用户状态</label>
                <select name="status" id class="form-control">
                    [#assign status = (command.status!)?default("") /]
                    <option value="ALL" [@mc.selected status "ALL" /]>全部</option>
                    <option value="ENABLE" [@mc.selected status "ENABLE" /]>启用</option>
                    <option value="DISABLE" [@mc.selected status "DISABLE" /]>禁用</option>
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
                            <th>用户名</th>
                            <th>创建时间</th>
                            <th>昵称</th>
                            <th>余额</th>
                            <th>状态</th>
                            <th>vip</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                            [#if pagination.data??]
                                [#list pagination.data as user ]
                                <tr>
                                    <td>${user.userName!}</td>
                                    <td>[@mc.dateShow user.createDate/]</td>
                                    <td>${user.name!}</td>
                                    <td>${user.money!}</td>
                                    <td>${(user.status.getName())!}</td>
                                    <td>[#if user.getVip()]是[#else]否[/#if]</td>
                                    <td>
                                        <a href="javascript:void(0)" data-toggle="tooltip" data-placement="top"
                                           title="点击增加余额" class="add-money" data-id="${user.id!}"
                                           data-userName="${user.userName!}">
                                            <span class="label label-purple">加余额</span>
                                        </a>
                                        <a href="javascript:void(0)" data-toggle="tooltip" data-placement="top"
                                           title="点击减少余额" class="subtract-money" data-id="${user.id!}"
                                           data-userName="${user.userName!}">
                                            <span class="label label-danger">减余额</span>
                                        </a>
                                        <a href="[@spring.url '/user/info/${user.id!}'/]"
                                           data-toggle="tooltip" data-placement="top" title="点击查看详情">
                                            <span class="label label-info">查看</span>
                                        </a>
                                        <a href="[@spring.url '/user/edit/${user.id}'/]"
                                           data-toggle="tooltip" data-placement="top" title="点击修改">
                                            <span class="label label-success">修改</span>
                                        </a>
                                        [#if account.isVip]
                                            <a href="[@spring.url '/user/update_vip?id=${user.id!}&version=${user.version!}'/]"
                                               data-toggle="tooltip" data-placement="top" title="取消vip">
                                                <span class="label label-primary">取消vip</span>
                                            </a>
                                        [#else]
                                            <a href="[@spring.url '/user/update_vip?id=${user.id!}&version=${user.version!}'/]"
                                               data-toggle="tooltip" data-placement="top" title="设置vip">
                                                <span class="label label-primary">设置vip</span>
                                            </a>
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
            [@mc.showPagination '/user/pagination?userName=${command.userName!}&status=${command.status!}' /]
        [/#if]
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="add-money">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">增加余额</h4>
            </div>
            <div class="modal-body">
                <form action="/user/add_money" method="post" class="form-horizontal" data-parsley-validate>
                    <input type="hidden" value="" name="id" id="id"/>

                    <div class="form-group">
                        <label for="" class="col-md-3 control-label">用户名*</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" id="userName" value="" readonly/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="money" class="col-md-3 control-label">金额*</label>
                        <div class="col-md-9">
                            <input type="number" class="form-control" value="" id="money" name="money"
                                   placeholder="输入金额"
                                   data-parsley-required="true" data-parsley-required-messages="金额不能为空"
                                   data-parsley-trigger="change"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="describe" class="col-md-3 control-label">描述*</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" value="" id="describe" name="describe"
                                   placeholder="输入描述"
                                   data-parsley-required="true" data-parsley-required-messages="描述不能为空"
                                   data-parsley-trigger="change"/>
                        </div>
                    </div>

                    <div class="text-center">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="submit" class="btn btn-primary">提交</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="subtract-money">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">减少余额</h4>
            </div>
            <div class="modal-body">
                <form action="/user/subtract_money" method="post" class="form-horizontal" data-parsley-validate>
                    <input type="hidden" value="" name="id" id="id"/>

                    <div class="form-group">
                        <label for="" class="col-md-3 control-label">用户名*</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" id="userName" value="" readonly/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="money" class="col-md-3 control-label">金额*</label>
                        <div class="col-md-9">
                            <input type="number" class="form-control" value="" id="money" name="money"
                                   placeholder="输入金额"
                                   data-parsley-required="true" data-parsley-required-messages="金额不能为空"
                                   data-parsley-trigger="change"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="describe" class="col-md-3 control-label">描述*</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" value="" id="describe" name="describe"
                                   placeholder="输入描述"
                                   data-parsley-required="true" data-parsley-required-messages="描述不能为空"
                                   data-parsley-trigger="change"/>
                        </div>
                    </div>

                    <div class="text-center">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="submit" class="btn btn-primary">提交</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


    [#include 'shared/confirmation.ftl'/]
[/@override]

[@override name="bottomResources"]
    [@super /]
<script type="text/javascript">
    $(".add-money").on("click", function () {
        var _modal = $("#add-money");
        _modal.find("form").find("#id").val($(this).attr("data-id"));
        _modal.find("form").find("#userName").val($(this).attr("data-userName"));
        _modal.find("form").find("#money").val(null);
        _modal.find("form").find("#describe").val(null);
        _modal.modal();
    })

    $(".subtract-money").on("click", function () {
        var _modal = $("#subtract-money");
        _modal.find("form").find("#id").val($(this).attr("data-id"));
        _modal.find("form").find("#userName").val($(this).attr("data-userName"));
        _modal.find("form").find("#money").val(null);
        _modal.find("form").find("#describe").val(null);
        _modal.modal();
    })
</script>
[/@override]
[@extends name="/decorator.ftl"/]
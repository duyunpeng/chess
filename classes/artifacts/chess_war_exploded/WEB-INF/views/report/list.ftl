[@override name="title"]举报管理[/@override]
[@override name="topResources"]
    [@super /]

[/@override]

[@override name="breadcrumb"]
<ul class="breadcrumb">
    <li><a href="/">首页</a></li>
    <li>举报管理</li>
</ul>
[/@override]

[@override name="headerText"]
举报 管理
[/@override]

[@override name="subContent"]
    [@mc.showAlert /]
[#--<div class="row margin-md">--]
    [#--<a href="/report/create" class="btn btn-md btn-success">新增版本</a>--]
[#--</div>--]
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
                <label for="reportUser" class="control-label">举报人</label>
                <input type="text" class="form-control" id="reportUser" name="reportUser" value="${command.reportUser!}"
                       placeholder="举报人"/>
            </div>
            <form class="form-inline no-margin" role="form">
                <div class="form-group">
                    <label for="quiltReportUser" class="control-label">被举报人</label>
                    <input type="text" class="form-control" id="quiltReportUser" name="quiltReportUser"
                           value="${command.quiltReportUser!}"
                           placeholder="被举报人"/>
                </div>
                <div class="form-group">
                    <label for="handleStatus" class="control-label">处理状态</label>
                    <select name="handleStatus" id class="form-control">
                        [#assign status = (command.handleStatus!)?default("") /]
                        <option value="ALL" [@mc.selected handleStatus "ALL" /]>全部</option>
                        <option value="WAIT_HANDLE" [@mc.selected handleStatus "WAIT_HANDLE" /]>待处理</option>
                        <option value="IN_HANDLE" [@mc.selected handleStatus "IN_HANDLE" /]>处理中</option>
                        <option value="OK_HANDLE" [@mc.selected handleStatus "OK_HANDLE" /]>处理完成</option>
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
                            <th>举报人</th>
                            <th>被举报人</th>
                            <th>举报标题</th>
                            <th>举报内容</th>
                            <th>处理状态</th>
                            <th>处理结果说明</th>
                            <th>处理时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                            [#if pagination.data??]
                                [#list pagination.data as report ]
                                <tr>
                                    <td>${report.reportUser.userName!}</td>
                                    <td>${report.quiltReportUser.userName!}</td>
                                    <td>${report.title!}</td>
                                    <td>${report.content!}</td>
                                    <td>${report.handleStatus.getName()!}</td>
                                    <td>${report.handleResult!}</td>
                                    <td>${report.handleDate!}</td>
                                    <td>
                                        <a href="[@spring.url '/report/info/${report.id!}'/]"
                                           data-toggle="tooltip" data-placement="top" title="点击查看详情">
                                            <span class="label label-info">查看</span>
                                        </a>
                                        [#if report.handleStatus !="ALL"]
                                            [#if report.handleStatus =="WAIT_HANDLE"]
                                                <a href="[@spring.url '/report/deal_report?id=${report.id!}&version=${report.version!}'/]"
                                                   data-toggle="tooltip" data-placement="top" title="点击禁用此数据">
                                                    <span class="label label-danger">处理</span>
                                                </a>
                                            [#elseif report.handleStatus =="IN_HANDLE"]
                                            <button  class="label label-danger" onclick="EV_modeAlert('dialog', '${report.id!}', '${report.version}')"">完成处理</button>

                                                [#--<a href="[@spring.url '/report/finish_report?id=${report.id!}&version=${report.version!}'/]"--]
                                                   [#--data-toggle="tooltip" data-placement="top" title="点击禁用此数据">--]
                                                    [#--<span class="label label-danger">完成处理</span>--]
                                                [#--</a>--]
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
            [@mc.showPagination '/report/pagination?reportUser=${command.reportUser!}&handleStatus=${command.handleStatus!}&quiltReportUser=${command.quiltReportUser!}' /]
        [/#if]
            </div>
        </div>

    </div>
</div>
</div>
    [#include 'shared/confirmation.ftl'/]

[/@override]

[@override name="bottomResources"]
    [@super /]
<script language="JavaScript" type="text/javascript">
    //用来记录要显示的DIV的ID值
    var EV_MsgBox_ID = ""; //重要

    //弹出对话窗口(msgID-要显示的div的id)
    function EV_modeAlert(msgID, id, version) {

        var append = "<input type=\"hidden\" name=\"id\" value=\"" + id + "\"/>"
                + "<input type=\"hidden\" name=\"version\" value=\"" + version + "\"/>";

        $("#form_hidden").append(append);

        //创建大大的背景框
        var bgObj = document.createElement("div");
        bgObj.setAttribute('id', 'EV_bgModeAlertDiv');
        document.body.appendChild(bgObj);
        //背景框满窗口显示
        EV_Show_bgDiv();
        //把要显示的div居中显示
        EV_MsgBox_ID = msgID;
        EV_Show_msgDiv();
    }

    //关闭对话窗口
    function EV_closeAlert() {
        var msgObj = document.getElementById(EV_MsgBox_ID);
        var bgObj = document.getElementById("EV_bgModeAlertDiv");
        msgObj.style.display = "none";
        document.body.removeChild(bgObj);
        EV_MsgBox_ID = "";
    }

    //窗口大小改变时更正显示大小和位置
    window.onresize = function () {
        if (EV_MsgBox_ID.length > 0) {
            EV_Show_bgDiv();
            EV_Show_msgDiv();
        }
    };

    //窗口滚动条拖动时更正显示大小和位置
    window.onscroll = function () {
        if (EV_MsgBox_ID.length > 0) {
            EV_Show_bgDiv();
            EV_Show_msgDiv();
        }
    };

    //把要显示的div居中显示
    function EV_Show_msgDiv() {
        var msgObj = document.getElementById(EV_MsgBox_ID);
        msgObj.style.display = "block";
        var msgWidth = msgObj.scrollWidth;
        var msgHeight = msgObj.scrollHeight;
        var bgTop = EV_myScrollTop();
        var bgLeft = EV_myScrollLeft();
        var bgWidth = EV_myClientWidth();
        var bgHeight = EV_myClientHeight();
        var msgTop = bgTop + Math.round((bgHeight - msgHeight) / 2);
        var msgLeft = bgLeft + Math.round((bgWidth - msgWidth) / 2);
        msgObj.style.position = "absolute";
        msgObj.style.top = msgTop + "px";
        msgObj.style.left = msgLeft + "px";
        msgObj.style.zIndex = "10001";
        $("#upload_imgs").empty();
    }
    //背景框满窗口显示
    function EV_Show_bgDiv() {
        var bgObj = document.getElementById("EV_bgModeAlertDiv");
        var bgWidth = EV_myClientWidth();
        var bgHeight = EV_myClientHeight();
        var bgTop = EV_myScrollTop();
        var bgLeft = EV_myScrollLeft();
        bgObj.style.position = "absolute";
        bgObj.style.top = bgTop + "px";
        bgObj.style.left = bgLeft + "px";
        bgObj.style.width = bgWidth + "px";
        bgObj.style.height = bgHeight + "px";
        bgObj.style.zIndex = "10000";
        bgObj.style.background = "#777";
        bgObj.style.filter = "progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=60,finishOpacity=60);";
        bgObj.style.opacity = "0.6";
    }
    //网页被卷去的上高度
    function EV_myScrollTop() {
        var n = window.pageYOffset
                || document.documentElement.scrollTop
                || document.body.scrollTop || 0;
        return n;
    }
    //网页被卷去的左宽度
    function EV_myScrollLeft() {
        var n = window.pageXOffset
                || document.documentElement.scrollLeft
                || document.body.scrollLeft || 0;
        return n;
    }
    //网页可见区域宽
    function EV_myClientWidth() {
        var n = document.documentElement.clientWidth
                || document.body.clientWidth || 0;
        return n;
    }
    //网页可见区域高
    function EV_myClientHeight() {
        var n = document.documentElement.clientHeight
                || document.body.clientHeight || 0;
        return n;
    }
</script>
[/@override]
[@extends name="/decorator.ftl"/]
<div id="dialog" style="width:40%; background: oldlace ;   padding: 10px 10px;   color: #333; left: 10%;   border: 1px solid #eee;    min-height: 200px;    display: none;">
    <div class="clearfix">
        <div style="padding-bottom: 20px;">
            <span>举报说明</span>
            <button class="pull-right btn" onclick="EV_closeAlert()">关闭</button>
        </div>
        <div class="col-md-12">
            <form class="form-horizontal" action="/report/finish_report" method="POST">
                <div class="form-group">
                    <div id="form_hidden"></div>
                    <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 举报说明* </label>

                    <div class="col-sm-12">
                        <textarea type="text" style="height: 150px;" id="form-field-1" name="handleResult" class="col-xs-10 col-sm-9" required>${report.handleResult!command.handleResult}</textarea>
                    </div>
                </div>
                <div class="col-xs-12 center">
                    <button class="btn btn-info" type="submit">
                        <i class="icon-ok bigger-110"></i>
                        完成
                    </button>
                    <button class="btn" type="reset">
                        <i class="icon-undo bigger-110"></i>
                        重置
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
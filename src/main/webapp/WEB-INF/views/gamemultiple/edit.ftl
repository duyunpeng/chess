[@override name="title"]游戏倍数管理 - 游戏倍数修改[/@override]
[@override name="topResources"]
    [@super /]

[/@override]

[@override name="breadcrumb"]
<ul class="breadcrumb">
    <li><a href="/">首页</a></li>
    <li><a href="/game_multiple/pagination">游戏倍数管理</a></li>
    <li>游戏倍数修改</li>
</ul>
[/@override]

[@override name="headerText"]
游戏倍数 修改
[/@override]

[@override name="subContent"]
    [@mc.showAlert /]
<div class="row">
    <div class="col-lg-8">
        <form class="form-horizontal" action="/game_multiple/edit" method="post" data-parsley-validate>

            <input type="hidden" name="id" value="${multiple.id!command.id}"/>
            <input type="hidden" name="version" value="${multiple.version!command.version}"/>

            [@spring.bind "command.gameType"/]
            <div class="form-group">
                <label for="level" class="col-md-3 control-label">游戏*</label>
                <div class="col-md-9">
                    <select class="form-control" name="gameType" id="gameType"
                            data-parsley-required="true" data-parsley-required-messages="请选择游戏"
                            data-parsley-trigger="change">
                        [#assign gameType = (multiple.gameType!command.gameType!)?default("") /]
                        <option value="">请选择</option>
                        <option value="LANDLORDS" [@mc.selected gameType "LANDLORDS" /]>斗地主</option>
                        <option value="THREE_CARD" [@mc.selected gameType "THREE_CARD" /]>扎金花</option>
                        <option value="BULLFIGHT" [@mc.selected gameType "BULLFIGHT" /]>斗牛</option>
                    </select>
                    [@spring.showErrors "gameType" "parsley-required"/]
                </div>
            </div>

            [@spring.bind "command.multiple"/]
            <div class="form-group">
                <label for="multiple" class="col-md-3 control-label">倍数*</label>
                <div class="col-md-9">
                    <input class="form-control" id="multiple" name="multiple"
                           value="${multiple.multiple!command.multiple!}" placeholder="输入游戏倍数"
                           data-parsley-required="true" data-parsley-required-messages="游戏倍数不能为空"
                           data-parsley-trigger="change" type="text"/>
                    [@spring.showErrors "multiple" "parsley-required"/]
                </div>
            </div>

            [@spring.bind "command.minMoney"/]
            <div class="form-group">
                <label for="minMoney" class="col-md-3 control-label">最小进入金额*</label>
                <div class="col-md-9">
                    <input class="form-control" id="minMoney" name="minMoney"
                           value="${multiple.minMoney!command.minMoney}" placeholder="最小进入金额"
                           data-parsley-required="true" data-parsley-required-messages="最小进入金额不能为空"
                           data-parsley-trigger="change" type="number"/>
                    [@spring.showErrors "minMoney" "parsley-required"/]
                </div>
            </div>

            <div class="text-center m-top-md">
                <button type="reset" class="btn btn-default">重置</button>
                <button type="submit" class="btn btn-success">修改</button>
            </div>
        </form>
    </div>
    <div class="col-lg-3">
        <ul class="blog-sidebar-list font-18">修改注意事项
            <li>*位必填项</li>
        </ul>
    </div>
</div>

[/@override]
[@extends name="/decorator.ftl"/]
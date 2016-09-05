[@override name="title"]机器人管理 - 机器人创建[/@override]
[@override name="topResources"]
    [@super /]

[/@override]

[@override name="breadcrumb"]
<ul class="breadcrumb">
    <li><a href="/">首页</a></li>
    <li>机器人创建</li>
</ul>
[/@override]

[@override name="headerText"]
机器人 创建
[/@override]

[@override name="subContent"]
    [@mc.showAlert /]
<div class="row">
    <div class="col-lg-8">
        <form class="form-horizontal" action="/robot/create" method="post" data-parsley-validate>

            [@spring.bind "command.gameType"/]
            <div class="form-group">
                <label for="level" class="col-md-3 control-label">游戏*</label>
                <div class="col-md-9">
                    <select class="form-control" name="gameType" id="gameType"
                            data-parsley-required="true" data-parsley-required-messages="请选择游戏"
                            data-parsley-trigger="change">
                        [#assign gameType = (command.gameType!)/]
                        <option value="">请选择</option>
                        <option value="LANDLORDS" [@mc.selected gameType "LANDLORDS" /]>斗地主</option>
                        <option value="THREE_CARD" [@mc.selected gameType "THREE_CARD" /]>扎金花</option>
                        <option value="BULLFIGHT" [@mc.selected gameType "BULLFIGHT" /]>斗牛</option>
                    </select>
                    [@spring.showErrors "gameType" "parsley-required"/]
                </div>
            </div>

            [@spring.bind "command.score"/]
            <div class="form-group">
                <label for="score" class="col-md-3 control-label">分数*</label>
                <div class="col-md-9">
                    <input class="form-control" id="score" name="score"
                           value="${command.score!}" placeholder="输入分数"
                           data-parsley-required="true" data-parsley-required-messages="分数不能为空"
                           data-parsley-trigger="change" type="text"/>
                    [@spring.showErrors "score" "parsley-required"/]
                </div>
            </div>

            [@spring.bind "command.count"/]
            <div class="form-group">
                <label for="count" class="col-md-3 control-label">机器人数量*</label>
                <div class="col-md-9">
                    <input class="form-control" id="count" name="count"
                           value="${command.count!}" placeholder="机器人数量"
                           data-parsley-required="true" data-parsley-required-messages="机器人数量不能为空"
                           data-parsley-trigger="change" type="number"/>
                    [@spring.showErrors "count" "parsley-required"/]
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
[@extends name="/decorator.ftl"/]
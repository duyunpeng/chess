<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>信用榜API</title>
    <link href="[@spring.url '/resources/app/document/api.css' /]" rel="stylesheet"/>
    <script type="text/javascript" src="[@spring.url '/resources/js/jquery.min.js' /]"></script>
</head>
[#assign tables=doc.apifile.tables.table apis=doc.apifile.apis.api /]
<body>
<div class="big_div">
    <div class="fl left_div">
        <div class="l_title" id="top">接口说明</div>
        <div>
            <h2>接口参数说明:</h2>
            <h3>key=XXX&secret=XXX&json=XXX</h3>
            <h4>key和secret为必传参数</h4>
            <h4>json为数据，格式为json格式（{name:'XXXX',age:12}）</h4>
            <h4>例:http://127.0.0.1:8899/app/api/login?key=xin-yong-bang-admin&secret=xin-yong-bang-admin&json={userName:'15823634844',password:'123123'}</h4>
        </div>
        <div class="biaoti">
            <ol>
                <li class="big_title">接口</li>
                <ol class="ul_tt">
                [#list apis as api]
                    <li><a href="#${api.id!}">${api.name!}</a></li>
                [/#list]
                </ol>
            </ol>
        </div>
    [#list apis as api]
        <div class="l_div_i" id="${api.id!}">
            <div class="mingcheng">
                <div class="sjk_name" onclick="changethistxt(this)"><span>${api.name!}</span><input
                        type="text" placeholder="输入表接口名称" value="${api.name!}" name="nametxt"
                        style="display:none;" onchange="updateapi('${api.id!}',this)"/>
                </div>
                <div class="mc">
                    <span>提交地址：</span>
                    <p onclick="changethistxt(this)"><span>${api.address!}</span><input
                            type="text" placeholder="输入请求地址" value="${api.address!}"
                            style="display:none;" name="addresstxt" onchange="updateapi('${api.id!}',this)"/></p>
                </div>
                <div class="mc">
                    <span>提交参数说明：</span><span><a onclick="changethistxt(this)"><span>${api.shuoming!}</span><input
                        type="text" placeholder="输入请求的参数说明" value="${api.shuoming!}"
                        name="shuomingtxt" style="display:none;" onchange="updateapi('${api.id!}',this)"/></a></span>
                </div>
                <div class="mc">
                    <span>注意：</span><span><a onclick="changethistxt(this)"><span>${api.zhuyi!}</span><input
                        type="text" placeholder="输入注意事项" value="${api.zhuyi!}" style="display:none;"
                        name="zhuyitxt" onchange="updateapi('${api.id!}',this)"/></a></span>
                </div>
                <div class="clear"></div>
            </div>
            <div class="mingcheng">
                <div class="mc">
                    <span>返回信息：</span>
                    <p onclick="changethistxt(this)"><span>${api.fanhuixinxi!}</span>
                    [#--<input type="text" placeholder="输入表返回的信息" value='${api.fanhuixinxi!}'--]
                    [#--style="display:none;" name="fanhuixinxitxt" onchange="updateapi('${api.id!}',this)"/>--]
                        <textarea placeholder="输入表返回的信息" name="fanhuixinxitxt"
                                  onchange="updateapi('${api.id!}',this)">${api.fanhuixinxi!}</textarea>
                    </p>
                </div>
                <div class="clear"></div>
            </div>
            <div class="clear"></div>
        </div>
    [/#list]
        <div style="width: 50%; text-align: center; line-height: 24px; font-size: 24px; background-color: #67FF5D; color: white; border-radius: 6px; padding: 10px 0; cursor: pointer; margin: 0 auto 10px auto;"
             onclick="addapi()">
            添加API
        </div>
    </div>
    <div class="fr right_div">
        <div class="l_title">数据库</div>
        <div class="biaoti">
            <ol>
                <li class="big_title">表</li>
                <ol class="ul_tt">
                [#list tables as table]
                    <li><a href="#${table.value!}">${table.value!}${table.name!}</a><a
                            style="color: red; font-size: 12px; margin-right: 20px; float: right;"
                            href="javascrpt:void(0)" onclick="deltable('${table.id!}')">删除</a>
                    </li>
                [/#list]
                </ol>
            </ol>
        </div>
        <div id="tables">
        [#list tables as table]
            <div class="l_div_i" id="${table.value!}">
                <div class="sjk_name">
                    <input type="text" class="sjk_name" name="tableinputname" attr-id="${table.id!}"
                           placeholder="输入表名字“用户表”" onchange="updatetable('${table.id!}',this)"
                           value="${table.name!}">
                    <input type="text" name="tableinputvalue" class="sjk_name" attr-id="${table.id!}"
                           placeholder="输入表“userinfo”" onchange="updatetable('${table.id!}',this)"
                           value="${table.value!}">
                </div>
                <table class="table2" data-id="${table.id!}">
                    <tbody>
                    <tr class="tit">
                        <td class="two1">字段</td>
                        <td class="two1">类型</td>
                        <td>说明</td>
                        <td style="width: 30px;">操作</td>
                    </tr>
                        [#assign rows=table.rows.row/]
                        [#list rows as row]
                        <tr>
                            <td onclick="xiugaiziduan(this)"><span>${row.value!}</span><input
                                    type="text"
                                    name="xiugaivalue"
                                    style="display: none;"
                                    value="${row.value!}" onchange="updaterow('${row.id!}',this)"/>
                            </td>
                            <td onclick="xiugaiziduan(this)"><span>${row.name!}</span>
                                <input type="text"
                                       name="xiugainame"
                                       style="display: none;"
                                       value="${row.name!}"
                                       onchange="updaterow('${row.id!}',this)"/>
                            </td>
                            <td onclick="xiugaiziduan(this)"><span>${row.content!}</span><input
                                    type="text"
                                    name="xiugaicontent"
                                    style="display: none;"
                                    value="${row.content!}" onchange="updaterow('${row.id!}',this)"/>
                            </td>
                            <td><a href="javascript:void(0)" style="text-align: center; color: red;"
                                   onclick="delrow('${row.id!}',this)">删除</a></td>
                        </tr>
                        [/#list]
                    </tbody>
                </table>
                <div style="width: 50%; text-align: center; line-height: 24px; font-size: 24px; background-color: #67FF5D; color: white; border-radius: 6px; padding: 10px 0; cursor: pointer; margin: 0 auto 10px auto;"
                     onclick="addrow('${table.id!}',this)">
                    添加字段
                </div>
                <div class="clear"></div>
            </div>
        [/#list]
        </div>
        <div style="width: 100%; text-align: center; line-height: 30px; font-size: 24px; background-color: #dbdbdb; border-radius: 6px; padding: 10px 0; cursor: pointer;"
             onclick="addtable()">
            添加表
        </div>
    </div>
    <div class="clear"></div>
    <div class="go_top"><a href="#top">^</a></div>
</div>
<script type="text/javascript">
    function deltable(result) {
        if (!confirm("是否删除表")) return;
        $.ajax({
            type: "post",
            url: "/document/apicommand/deltable",
            data: "tableid=" + result,
            success: function (data) {
                location.href = location.href;
            }
        })
    }
    function addtable() {
        $.ajax({
            type: "post",
            url: "/document/apicommand/addtable",
            success: function (data) {
                location.href = location.href;
//                data = eval("(" + data + ")");
//                if (data.state) {
//                    $("#tables").append("<div class=\"l_div_i\" id=\"logininfo\">" +
//                            "<div class=\"sjk_name\"><input type=\"text\" class=\"sjk_name\" name=\"tableinputname\" attr-id=\"" + data.message + "\" placeholder=\"输入表名字“用户表”\" onchange=\"updatetable('" + data.message + "',this)\"/> <input type=\"text\" name=\"tableinputvalue\" class=\"sjk_name\" attr-id=\"" + data.message + "\" placeholder=\"输入表“userinfo”\" onchange=\"updatetable('" + data.message + "',this)\"/></div>" +
//                            " <table class=\"table2\" data-id=\"" + data.message + "\"><tbody>" +
//                            " <tr class=\"tit\">" +
//                            "    <td class=\"two1\">字段</td>" +
//                            "    <td class=\"two1\">类型</td>" +
//                            "     <td>说明</td>" +
//                            "      <td style=\"width: 30px;\">操作</td>" +
//                            "    </tr>" +
//                            "</tbody></table>" +
//                            "  <div style=\"width: 50%; text-align: center; line-height: 24px; font-size: 24px; background-color: #67FF5D; color: white; border-radius: 6px; padding: 10px 0; cursor: pointer; margin: 0 auto 10px auto;\" onclick=\"addrow('" + data.message + "',this)\">添加字段</div>" +
//                            "<div class=\"clear\"></div>" +
//                            "</div>");
//                }
            }
        })
    }
    function updatetable(result, current) {
        var name = $(current).parent().find("input[name=tableinputname]").val();
        var value = $(current).parent().find("input[name=tableinputvalue]").val();
        $.ajax({
            type: "post",
            url: "/document/apicommand/updatetablename",
            data: "tableid=" + result + "&newname=" + name + "&newvalue=" + value,
            success: function (data) {
            }
        })
    }
    function addrow(tableid, result) {
        $.ajax({
            type: "post",
            url: "/document/apicommand/addrow",
            data: "tableid=" + tableid,
            success: function (data) {
                location.href = location.href;
//                data = eval("(" + data + ")");
//                if (data.state) {
//                    $(result).parent().find(".table2 tbody").append(" <tr>" +
//                            "<td onclick=\"xiugaiziduan(this)\"><span></span><input type=\"text\" name=\"xiugaivalue\" style=\"display: none;\" value=\"\" onchange=\"updaterow('" + data.message + "',this)\"/></td>" +
//                            "<td onclick=\"xiugaiziduan(this)\"><span></span><input type=\"text\" name=\"xiugainame\" style=\"display: none;\" value=\"\"  onchange=\"updaterow('" + data.message + "',this)\"/></td>" +
//                            "<td onclick=\"xiugaiziduan(this)\"><span></span><input type=\"text\" name=\"xiugaicontent\" style=\"display: none;\" value=\"\" onchange=\"updaterow('" + data.message + "',this)\" /></td>" +
//                            " <td><a href=\"javascript:void(0)\" style=\"text-align: center; color: red;\" onclick=\"delrow('" + data.message + "',this)\">删除</a></td>" +
//                            "</tr>");
//                }
            }
        })
    }
    function xiugaiziduan(result) {
        $(result).find("span").hide();
        $(result).find("input").show().focus();
    }
    function delrow(result, current) {
        if (!confirm("是否删除")) return;
        var tableid = $(current).parent().parent().parent().parent().attr("data-id");
        $.ajax({
            type: "post",
            url: "/document/apicommand/delrow",
            data: "tableid=" + tableid + "&rowid=" + result,
            success: function (data) {
                location.href = location.href;
//                data = eval("(" + data + ")");
//                if (data.state) location.href = location.href;
            }
        })
    }
    function updaterow(result, current) {
        var tableid = $(current).parent().parent().parent().parent().attr("data-id");
        var value = $(current).parent().parent().find("input[name=xiugaivalue]").val();
        var name = $(current).parent().parent().find("input[name=xiugainame]").val();
        var content = $(current).parent().parent().find("input[name=xiugaicontent]").val();
        $.ajax({
            type: "post",
            url: "/document/apicommand/updaterow",
            data: "tableid=" + tableid + "&rowid=" + result + "&value=" + value + "&name=" + name + "&content=" + content,
            success: function (data) {
//                data = eval("(" + data + ")");

            }
        })
    }
    function updateapi(result, cu) {
        var currentdiv = $("#" + result);
        var id = result;
        var name = $(currentdiv).find("input[name=nametxt]").val();
        var addresstxt = $(currentdiv).find("input[name=addresstxt]").val();
        var shuomingtxt = $(currentdiv).find("input[name=shuomingtxt]").val();
        var zhuyitxt = $(currentdiv).find("input[name=zhuyitxt]").val();
        var fanhuixinxitxt = $(currentdiv).find("textarea[name=fanhuixinxitxt]").val();
        $.ajax({
            type: "post",
            url: "/document/apicommand/updateapi",
            data: "apiid=" + id + "&name=" + encodeURIComponent(name) + "&addresstxt=" + encodeURIComponent(addresstxt) + "&shuomingtxt=" + encodeURIComponent(shuomingtxt) + "&zhuyitxt=" + encodeURIComponent(zhuyitxt) + "&fanhuixinxitxt=" + encodeURIComponent(fanhuixinxitxt),
            success: function (data) {
//                data = eval("(" + data + ")");
//                //if (data.state) location.href = location.href;
//                $(cu).parent().find("span").show().html($(cu).val());
//                $(cu).parent().find("input").hide();
            }
        })
    }
    function changethistxt(result) {
        $(result).find("span").hide();
        $(result).find("input").show().focus();
        $(result).find("textarea").show().focus();
    }
    function addapi() {
        $.ajax({
            type: "post",
            url: "/document/apicommand/addapi",
            success: function (e) {
                location.href = location.href;
//                e = eval("(" + e + ")");
//                if (e.state) location.href = location.href;
            }
        })
    }
</script>
</body>
</html>

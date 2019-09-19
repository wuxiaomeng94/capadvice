<#--Created by IntelliJ IDEA.
User: zxm
Date: 2017/12/20
Time: 10:00
To change this template use File | Settings | File Templates.-->

<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <title>添加意见</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
  <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
  <link rel="stylesheet" href="${re.contextPath}/plugin/ztree/css/metroStyle/metroStyle.css">
  <link rel="stylesheet" href="${re.contextPath}/plugin/bootstrap/bootstrap-3.3.0/dist/css/bootstrap.css">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet" />
  <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>
  <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js" charset="utf-8"></script>
  <script type="text/javascript" src="${re.contextPath}/plugin/ztree/js/jquery.ztree.core.js"></script>
  <script type="text/javascript" src="${re.contextPath}/plugin/ztree/js/jquery.ztree.excheck.js" charset="utf-8"></script>
  <script type="text/javascript" src="${re.contextPath}/plugin/tools/tool.js"></script>
    <script  type="text/javascript">
        $(document).ready(function () {
            $("#sendCaseType").select2();
        });
        /*var setting = {
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        };*/
        /*$(document).ready(function(){
            $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        });*/
    </script>
</head>

<body>
<#--<div class="x-body">-->

<div class="form-group">
    <h2 class="text-center">案件信息</h2>
</div>

  <div class="container" >
    <#--<div style="width:100%;height:400px;overflow: auto;">-->
    <#--<div class="layui-form-item">
      <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
        <legend style="font-size:16px;">案件信息</legend>
      </fieldset>
    </div>-->
      <#--<input type="hidden" name="id" id="id" value="${capAcceptOffice.id}"/>-->
        <input type="hidden" name="yesswCaseAnalyzeId" id="yesswCaseAnalyzeId" value="${capYesswCaseAnalyze.id}"/>

        <div class="row">

            <label class="control-label col-xs-1">案件编号:</label>
            <div class="col-xs-3">
                <#--<input type="text" readonly="readonly" name="yesswNumber" id="yesswNumber" value="${capYesswCaseAnalyze.yesswNumber}"/>-->
                ${capYesswCaseAnalyze.yesswNumber}
            </div>

            <label class="control-label col-xs-1">问题点位:</label>
            <div class="col-xs-3">
                <#--<input type="text" readonly="readonly" name="yesswProblemPlace" id="yesswProblemPlace" value="${capYesswCaseAnalyze.yesswProblemPlace}"/>-->
                ${capYesswCaseAnalyze.yesswProblemPlace}
            </div>

            <label class="control-label col-xs-1">来电人:</label>
            <div class="col-xs-3">
               <#-- <input type="text" readonly="readonly" name="yesswCallperson" id="yesswCallperson" value="${capYesswCaseAnalyze.yesswCallperson}"/>-->
               ${capYesswCaseAnalyze.yesswCallperson}
            </div>
        </div>

        <div class="row">

            <label class="control-label col-xs-1">联系方式:</label>
            <div class="col-xs-3">
                ${capYesswCaseAnalyze.yesswCallpersonPhone}
            </div>

            <label class="control-label col-xs-1">被反映区:</label>
            <div class="col-xs-3">
            ${capYesswCaseAnalyze.yesswAskedArea}
            </div>

            <label class="control-label col-xs-1">被反映街道:</label>
            <div class="col-xs-3">
            ${capYesswCaseAnalyze.yesswAskedStreet}
            </div>

        </div>

        <div class="row">

            <label class="control-label col-xs-1">问题标题:</label>
            <div class="col-xs-3">
            ${capYesswCaseAnalyze.yesswTitle}
            </div>

            <label class="control-label col-xs-1">创建时间:</label>
            <div class="col-xs-3">
            ${capYesswCaseAnalyze.yesswCreateTime}
            </div>

            <label class="control-label col-xs-1">派单时间:</label>
            <div class="col-xs-3">
            ${capYesswCaseAnalyze.yesswSendTime}
            </div>

        </div>

        <div class="row">

            <label class="control-label col-xs-1">问题分类:</label>
            <div class="col-xs-3">
            ${capYesswCaseAnalyze.yesswProblemType}
            </div>

            <label class="control-label col-xs-1">5级分类:</label>
            <div class="col-xs-7">
            ${capYesswCaseAnalyze.problemTypeLevel1}=>${capYesswCaseAnalyze.problemTypeLevel2}=>${capYesswCaseAnalyze.problemTypeLevel3}=>${capYesswCaseAnalyze.problemTypeLevel4}=>${capYesswCaseAnalyze.problemTypeLevel5}
            </div>

        </div>

        <div class="row">

            <label class="control-label col-xs-1">工单状态:</label>
            <div class="col-xs-3">
            ${capYesswCaseAnalyze.yesswStatus}
            </div>

            <label class="control-label col-xs-1">工单类型:</label>
            <div class="col-xs-3">
            ${capYesswCaseAnalyze.yesswType}
            </div>

            <label class="control-label col-xs-1">办结时间:</label>
            <div class="col-xs-3">
            ${capYesswCaseAnalyze.yesswEndTime}
            </div>

        </div>

        <div class="row">

            <label class="control-label col-xs-1">处理结果:</label>
            <div class="col-xs-11">
            ${capYesswCaseAnalyze.yesswResult}
            </div>
        </div>

        <div class="row">

            <label class="control-label col-xs-1">主要内容:</label>
            <div class="col-xs-11">
            ${capYesswCaseAnalyze.yesswContent}
            </div>
        </div>




        <#if !detail>
        <div class="layui-col-md12" style="height:40px;margin-top:3px;">
            <button class="layui-btn layui-btn-warm" data-type="addProcess">添加承办单位情况</button>
        </div>
        </#if>
        <div style="width:100%;height:150px;overflow:auto">
            <table id="adviceDealList" class="layui-hide" lay-filter="user"></table>
            <script type="text/html" id="toolBar">
                <#--<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>-->
                <#--{{#if(d.status == '1'){ }}-->
                <#--{{#if(${!detail}){  }}-->
                <a class="layui-btn layui-btn-xs  layui-btn-normal" lay-event="edit">编辑</a>
                <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
                <#--{{# } }}-->
            </script>
        </div>

        <div class="row">

            <label class="control-label col-xs-1">到期时间:</label>
            <div class="col-xs-3">
                <input type="text" name="finishTime" id="finishTime" lay-verify="datetime"
                       autocomplete="off" class="layui-input" value="${capYesswCaseAnalyze.finishTimeStr}">
            </div>

            <label class="control-label col-xs-1">派单类型:</label>
            <div class="col-xs-3">
                <select name="sendCaseType" id="sendCaseType" lay-verify="sendCaseType" style="width:100%">
                    <option value="">请选择</option>
                    <#list sendCaseTypeDict as item>
                        <option value="${item.key}" <#if item.key == capYesswCaseAnalyze.sendCaseType>selected</#if>>${item.value}</option>
                    </#list>
                    <#--<option value="1" <#if '1'==capYesswCaseAnalyze.sendCaseType>selected</#if>>市派单</option>
                    <option value="2" <#if '2'==capYesswCaseAnalyze.sendCaseType>selected</#if>>回退再派</option>
                    <option value="3" <#if '3'==capYesswCaseAnalyze.sendCaseType>selected</#if>>直退市中心</option>
                    <option value="4" <#if '4'==capYesswCaseAnalyze.sendCaseType>selected</#if>>回退市中心</option>
                    <option value="5" <#if '5'==capYesswCaseAnalyze.sendCaseType>selected</#if>>回复市中心</option>-->
                </select>
            </div>

            <label class="control-label col-xs-1">回复市中心时间:</label>
            <div class="col-xs-3">
                <input type="text" name="replyCityTime" id="replyCityTime" lay-verify="datetime"
                       autocomplete="off" class="layui-input" value="${capYesswCaseAnalyze.replyCityTimeStr}">
            </div>

        </div>

        <div class="row">

            <label class="control-label col-xs-1">工单编号:</label>
            <div class="col-xs-3">
                <input type="text" name="yesswNumber" id="yesswNumber" class="layui-input" value="${capYesswCaseAnalyze.yesswNumber}"/>
            </div>

            <label class="control-label col-xs-1">填单时间:</label>
            <div class="col-xs-3">
                <input type="text" lay-verify="datetime" name="createCaseTime" id="createCaseTime" value="${capYesswCaseAnalyze.createCaseTimeStr}"
                       autocomplete="off" class="layui-input"/>
            </div>

        </div>


        <div class="form-actions" style="text-align: center;margin-top: 20px">
            <button class="layui-btn layui-btn-warm" id="sub">
                保存
            </button>
            <button class="layui-btn layui-btn-primary" id="back">
                返回
            </button>
        </div>

        <#--<div style="width: 100%;height: 55px;background-color: white;border-top:1px solid #e6e6e6;
            position: fixed;bottom: 1px;margin-left:-20px;">
            <div class="layui-form-item" style="margin-right: 30px;margin-top: 8px;text-align: center">
                <#if !detail>
                <button class="layui-btn layui-btn-warm" id="sub">
                    保存
                </button>
                </#if>
                <button class="layui-btn layui-btn-primary" id="back">
                    返回
                </button>
            </div>
        </div>-->
      <#--<div style="width: 100%;height: 55px;background-color: white;border-top:1px solid #e6e6e6;
      position: fixed;bottom: 1px;margin-left:-20px;">
        <div class="layui-form-item" style=" float: right;margin-right: 30px;margin-top: 8px">

          &lt;#&ndash;<button  class="layui-btn layui-btn-normal" lay-filter="add" lay-submit>
            确认
          </button>&ndash;&gt;
          <button  class="layui-btn layui-btn-primary" id="back">
            返回
          </button>
        </div>-->
      </div>
  </div>
<#--</div>-->
<script>
    layui.laytpl.toDateString = function(d, format){
        var date = new Date(d || new Date())
                ,ymd = [
            this.digit(date.getFullYear(), 4)
            ,this.digit(date.getMonth() + 1)
            ,this.digit(date.getDate())
        ]
                ,hms = [
            this.digit(date.getHours())
            ,this.digit(date.getMinutes())
            ,this.digit(date.getSeconds())
        ];

        format = format || 'yyyy-MM-dd HH:mm:ss';

        return format.replace(/yyyy/g, ymd[0])
                .replace(/MM/g, ymd[1])
                .replace(/dd/g, ymd[2])
                .replace(/HH/g, hms[0])
                .replace(/mm/g, hms[1])
                .replace(/ss/g, hms[2]);
    };

    //数字前置补零
    layui.laytpl.digit = function(num, length, end){
        var str = '';
        num = String(num);
        length = length || 2;
        for(var i = num.length; i < length; i++){
            str += '0';
        }
        return num < Math.pow(10, length) ? str + (num|0) : num;
    };

    layui.use([ 'layer', 'table'], function () {
        $ = layui.jquery;
        var layer = layui.layer;
        var table = layui.table;
        var laydate = layui.laydate;

        laydate.render({
            elem: '#finishTime',
            type: 'datetime'
        });

        laydate.render({
            elem: '#replyCityTime',
            type: 'datetime'
        });

        laydate.render({
           elem: '#createCaseTime',
           type: 'datetime'
        });

        //方法级渲染
        table.render({
            id: 'adviceDealList',
            elem: '#adviceDealList'
            , url: '/caseAnalyze/showProcessList?yesswCaseAnalyzeId=${capYesswCaseAnalyze.id}'
            , cols: [[
                {field: 'acceptOffice', title: '承办单位', width: '20%', sort: true}
                , {field: 'createDate', title: '时间', width: '20%',templet: '<div>{{ layui.laytpl.toDateString(d.createDate,"yyyy-MM-dd HH:mm:ss") }}</div>'}
                , {field: 'remark', title: '回复内容', width: '40%', sort: true}
                , {field: 'remarks', title: '操作', width: '20%', toolbar: "#toolBar"}
            ]]
            , page: false
            ,  height: 'full-83'
        });

        var $ = layui.$, active = {
            select: function () {
                var title = $('#title').val();
                var content = $('#content').val();
                table.reload('adviceDealList', {
                    where: {
                        title: null,
                        content: null
                    }
                });
            },
            reload:function(){
                $('#title').val('');
                $('#content').val('');
                table.reload('adviceDealList', {
                    where: {
                        roleName: null,
                        remark: null
                    }
                });
            },
            addProcess: function () {
                addProcess('添加处理情况', '/caseAnalyze/addProcessPage?yesswCaseAnalyzeId=${capYesswCaseAnalyze.id}', 800, 600);
            },
            update: function () {
                var checkStatus = table.checkStatus('adviceDealList')
                        , data = checkStatus.data;
                if (data.length != 1) {
                    layer.msg('请选择一行编辑', {icon: 5});
                    return false;
                }
                update('编辑', 'updateAdvice?id=' + data[0].id, 800, 600);
            },
            detail: function () {
                var checkStatus = table.checkStatus('adviceDealList')
                        , data = checkStatus.data;
                if (data.length != 1) {
                    layer.msg('请选择一行查看', {icon: 5});
                    return false;
                }
                detail('查看信息', 'updateAdvice?id=' + data[0].id, 800, 600);
            }
        };


        //监听表格复选框选择
        table.on('checkbox(user)', function (obj) {
            //console.log(obj)
        });
        //监听工具条
        table.on('tool(user)', function (obj) {
            var data = obj.data;
            if (obj.event === 'detail') {
                detail('查看', 'showUpdateDeal?id=' + data.id, 800, 600);
            } else if (obj.event === 'del') {
                layer.confirm('确定删除处理情况?', function(){
                    del(data.id);
                });
            } else if (obj.event === 'edit') {
                update('编辑', '/caseAnalyze/editProcessPage?id=' + data.id, 800, 600);
            }
        });

        $('.layui-col-md12 .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
        $('.select .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });



        //自定义验证规则
        /*form.verify({
            title: function (value) {
            if (value.trim() == "") {
              return "标题不能为空";
            }
          }
        });*/

        $('#back').click(function () {
            /*var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);*/
            location.href="/caseAnalyze/yesswList";

        });
        //监听提交
        $("#sub").click(function () {
            var id = $("#yesswCaseAnalyzeId").val();
            var finishTime = $("#finishTime").val();
            var sendCaseType = $("#sendCaseType").val();
            var replyCityTime = $("#replyCityTime").val();
            var yesswNumber = $("#yesswNumber").val();
            var createCaseTime = $("#createCaseTime").val();

            if (replyCityTime == "") {
                layer.msg("回复市中心时间为必填项");
                return;
            }
            if (yesswNumber == "") {
                layer.msg("工单编号不能为空");
                return;
            }
            if (createCaseTime == "") {
                layer.msg("填单时间不能为空");
                return;
            }
            $.ajax({
                url: '/caseAnalyze/saveAnalyze',
                type: 'post',
                data: {id: id, finishTime: finishTime, sendCaseType: sendCaseType, replyCityTime: replyCityTime, yesswNumber: yesswNumber, createCaseTime: createCaseTime},
                success: function (data) {
                    layer.msg("保存完成");
                    location.href="/caseAnalyze/yesswList";
                }
            })

            //先ajax验证是否至少有一条提交完的处理信息并且有的情况下全部都是处理完的。
            /*var id = $("#id").val();
            $.ajax({
                url: "checkDealBeforeProblemSubmit",
                type: "get",
                data: {id: id},
                success: function (d) {
                    if (d.flag) {
                        location.href="updateProblemSubmit?id=";
                    } else {
                        layer.msg(d.msg,{icon:5,offset: 'rb',area:['120px','80px'],anim:2});
                    }
                }
            });*/
        })

    });


    function del(id) {
        $.ajax({
            url: "/caseAnalyze/delProcess",
            type: "post",
            data: {id: id},
            success: function (d) {
                if(d.msg){
                    layer.msg(d.msg,{icon:6,offset: 'rb',area:['120px','80px'],anim:2});
                    layui.table.reload('adviceDealList');
                }else{
                    layer.msg(d.msg,{icon:5,offset: 'rb',area:['120px','80px'],anim:2});
                }
            }
        });
    }
    function detail(title, url, w, h) {
        var number = 1;
        if (title == null || title == '') {
            title = false;
        }
        ;
        if (url == null || url == '') {
            url = "/error/404";
        }
        ;
        if (w == null || w == '') {
            w = ($(window).width() * 0.9);
        }
        ;
        if (h == null || h == '') {
            h = ($(window).height() - 50);
        }
        ;
        layer.open({
            id: 'user-detail',
            type: 2,
            area: [w + 'px', h + 'px'],
            fix: false,
            maxmin: true,
            shadeClose: true,
            shade: 0.4,
            title: title,
            content: url + '&detail=true',
            // btn:['关闭']
        });
    }
    /**
     * 更新用户
     */
    function update(title, url, w, h) {
        if (title == null || title == '') {
            title = false;
        }
        if (url == null || url == '') {
            url = "/error/404";
        }
        if (w == null || w == '') {
            w = ($(window).width() * 0.9);
        }
        if (h == null || h == '') {
            h = ($(window).height() - 50);
        }
        layer.open({
            id: 'user-update',
            type: 2,
            area: [w + 'px', h + 'px'],
            fix: false,
            maxmin: true,
            shadeClose: false,
            shade: 0.4,
            title: title,
            content: url + '&detail=false',
            end: function () {
                location.reload();
            }
        });
    }



    /*弹出层*/
    /*
     参数解释：
     title   标题
     url     请求的url
     id      需要操作的数据id
     w       弹出层宽度（缺省调默认值）
     h       弹出层高度（缺省调默认值）
     */
    function addProcess(title, url, w, h) {
        if (title == null || title == '') {
            title = false;
        }
        ;
        if (url == null || url == '') {
            url = "/error/404";
        }
        ;
        if (w == null || w == '') {
            w = ($(window).width() * 0.9);
        }
        ;
        if (h == null || h == '') {
            h = ($(window).height() - 50);
        }
        ;
        layer.open({
            id: 'deal-add',
            type: 2,
            area: [w + 'px', h + 'px'],
            fix: false,
            maxmin: true,
            shadeClose: false,
            shade: 0.4,
            title: title,
            content: url,
            end: function () {
                location.reload();
            }
        });
    }

</script>
</body>

</html>

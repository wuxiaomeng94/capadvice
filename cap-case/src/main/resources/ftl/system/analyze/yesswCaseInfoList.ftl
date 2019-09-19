<#-- Created by IntelliJ IDEA.
 User: zxm
 Date: 2017/12/19
 Time: 18:00
 To change this template use File | Settings | File Templates.
 角色管理-->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>意见</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
    <link rel="stylesheet" href="${re.contextPath}/plugin/lenos/main.css">
    <link rel="stylesheet" href="${re.contextPath}/plugin/bootstrap/bootstrap-3.3.0/dist/css/bootstrap.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet" />
    <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js"
            charset="utf-8"></script>
    <style>

        .red-btn {
            background-color: #dd4b39 !important;
        }

    </style>
    <script type="text/javascript">
        $(document).ready(function () {
            $('#pleasedFlag').select2();
            $('#acceptOffice').select2();
            $('#solveFlag').select2();
        });
    </script>
</head>

<body>
<div class=" form-horizontal select" style="margin-top: 20px">


    <div class="form-group">
        <label class="control-label col-xs-1">案件编号:</label>
        <div class="col-xs-2">
            <input class="layui-input" name="yesswNumber" height="20px" id="yesswNumber" autocomplete="off" style="width: 100%">
        </div>

        <label class="control-label col-xs-1">填单时间:</label>
        <div class="col-xs-4">
            <input type="text" name="createDateStart" id="createDateStart" lay-verify="date" autocomplete="off" class="layui-input col-xs-4">
            <label class="control-label col-xs-1">至:</label>
            <input type="text" name="createDateEnd" id="createDateEnd" lay-verify="date" autocomplete="off" class="layui-input col-xs-4">
        </div>

        <#--<label class="control-label col-xs-1">至:</label>
        <div class="col-xs-2">
            <input type="text" name="createDateEnd" id="createDateEnd" lay-verify="date" autocomplete="off" class="layui-input">
        </div>-->
        <label class="control-label col-xs-1">满意度:</label>
        <div class="col-xs-2">
            <select name="pleasedFlag" id="pleasedFlag" style="width: 100%;display: inline-block">
                <option value="">请选择</option>
                <option value="满意">满意</option>
                <option value="不满意">不满意</option>
                <option value="不需要回复">不需要回复</option>
                <option value="拒绝接听">拒绝接听</option>
                <option value="未接听">未接听</option>
            </select>
        </div>

    </div>

    <div class="form-group">
        <label class="control-label col-xs-1">承办单位:</label>
        <div class="col-xs-2">
            <select name="acceptOffice" id="acceptOffice" style="width: 100%;display: inline-block">
                <option value="">请选择</option>
                <#list officeList as item>
                    <option value="${item.name}">${item.name}</option>
                </#list>
            </select>
            <#--<input class="layui-input" name="acceptOffice" height="20px" id="acceptOffice" autocomplete="off">-->
        </div>

        <label class="control-label col-xs-1">回复市中心时间:</label>
        <div class="col-xs-4">
            <input type="text" name="recoveryTimeStart" id="recoveryTimeStart" lay-verify="date" autocomplete="off" class="layui-input col-xs-4">
            <label class="control-label col-xs-1">至:</label>
            <input type="text" name="recoveryTimeEnd" id="recoveryTimeEnd" lay-verify="date" autocomplete="off" class="layui-input col-xs-4">
        </div>

        <label class="control-label col-xs-1">提交人:</label>
        <div class="col-xs-2">
            <input class="layui-input" name="createByStr" height="20px" id="createByStr" autocomplete="off" style="width: 100%">
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-xs-1">是否解决:</label>
        <div class="col-xs-2">
            <select name="solveFlag" id="solveFlag" style="width: 100%;display: inline-block">
                <option value="">请选择</option>
                <option value="是">是</option>
                <option value="否">否</option>
                <option value="退市">退市</option>
            </select>
        </div>

        <label class="control-label col-xs-1">主要内容:</label>
        <div class="col-xs-4">
            <input type="text" name="yesswContent" id="yesswContent" class="layui-input col-xs-5" height="20px" style="width:100%"/>
        </div>

    </div>


    <div class="form-group">
        <div class="controls col-sm-12" style="text-align:center;">
            <button class="select-on layui-btn layui-btn-sm red-btn" data-type="select"><#--<i class="layui-icon"></i>-->
                查询
            </button>
            <button class="select-on layui-btn layui-btn-sm red-btn" id="addCase" onclick="addCase()">
                添加
            </button>
            <button class="select-on layui-btn layui-btn-sm red-btn" onclick="expExcel()">
                导出excel
            </button>
            <button class="select-on layui-btn layui-btn-sm red-btn" onclick="impExcel()">
                导入excel
            </button>
        </div>
    </div>
</div>

<#--<div class="lenos-search">



    <div class="select" id="selectForm">
        案件编号:
        <div class="layui-inline">
            <input class="layui-input" name="yesswNumber" height="20px" id="yesswNumber" autocomplete="off">
        </div>

        提交时间：
        <div class="layui-inline">
            <input type="text" name="createDateStart" id="createDateStart" lay-verify="date" autocomplete="off" class="layui-input">
        </div>
        至：
        <div class="layui-inline">
            <input type="text" name="createDateEnd" id="createDateEnd" lay-verify="date" autocomplete="off" class="layui-input">
        </div>

        承办单位：
        <div class="layui-inline">
            <input class="layui-input" name="acceptOffice" height="20px" id="acceptOffice" autocomplete="off">
        </div>

        提交人:
        <div class="layui-inline">
            <input class="layui-input" name="createByStr" height="20px" id="createByStr" autocomplete="off">
        </div>
        &lt;#&ndash;案件状态：
        <div class="layui-inline">
            <input class="layui-input" name="yesswStatus" height="20px" id="yesswStatus" autocomplete="off">
        </div>&ndash;&gt;
        &lt;#&ndash;是否满意：
        <div class="layui-inline">
            <select class="layui-select" id="pleasedFlag">
                <option value="">请选择</option>
                <option value=""></option>
            </select>
        </div>&ndash;&gt;

        <button class="select-on layui-btn layui-btn-sm red-btn" data-type="select"><i class="layui-icon"></i>
        </button>
        <button class="select-on layui-btn layui-btn-sm red-btn" id="addCase" onclick="addCase()">
            添加
        </button>
        <button class="select-on layui-btn layui-btn-sm red-btn" onclick="expExcel()">
            导出excel
        </button>
        <button class="select-on layui-btn layui-btn-sm red-btn" onclick="impExcel()">
            导入excel
        </button>
        &lt;#&ndash;<button class="select-on layui-btn layui-btn-sm red-btn" id="loadYesswData" onclick="loadYesswData()">
            获取数据
        </button>
        <button class="select-on layui-btn layui-btn-sm red-btn" onclick="expExcel()">
            导出excel
        </button>&ndash;&gt;
        &lt;#&ndash;<button class="select-on layui-btn layui-btn-sm red-btn">
            导入满意度excel
        </button>&ndash;&gt;

        <button class="layui-btn layui-btn-sm icon-position-button red-btn" id="refresh" style="float: right;"
                data-type="reload">
            <i class="layui-icon">ဂ</i>
        </button>
    </div>

</div>-->
<div class="layui-col-md12" style="height:40px;margin-top:3px;">
    <div class="layui-btn-group">
    <#--<@shiro.hasPermission name="advice:add">
    <button class="layui-btn layui-btn-normal" data-type="add">
        <i class="layui-icon">&#xe608;</i>新增
    </button>
    </@shiro.hasPermission>-->

    <#--<@shiro.hasPermission name="role:update">-->
    <#--    <button class="layui-btn layui-btn-normal" data-type="update">
            <i class="layui-icon">&#xe642;</i>编辑
        </button>
    &lt;#&ndash; </@shiro.hasPermission>&ndash;&gt;
    <@shiro.hasPermission name="role:select">
    <button class="layui-btn layui-btn-normal" data-type="detail">
        <i class="layui-icon">&#xe605;</i>查看
    </button>-->
    <#--</@shiro.hasPermission>-->
    </div>
</div>
<table id="adviceList" class="layui-hide" lay-filter="user"></table>
<script type="text/html" id="toolBar">

  <#--<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">详情</a>-->
  <a class="layui-btn layui-btn-xs  layui-btn-normal" lay-event="edit">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
<#--{{#if(d.status == '1'){ }}-->
<#--<@shiro.hasPermission name="advice:update">-->
  <#--<a class="layui-btn layui-btn-xs  layui-btn-normal" lay-event="edit">处理</a>-->
<#--</@shiro.hasPermission>-->
<#--{{# } }}-->
<#--<@shiro.hasPermission name="advice:del">
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</@shiro.hasPermission>-->
</script>
<script type="text/html" id="typeTemp">
    {{#  if(d.type =='1'){ }}
    举报问题
    {{# }if(d.type =='2') { }}
    意见建议
    {{#  } }}
</script>
<script type="text/html" id="reportTypeTemp">
    {{#  if(d.reportType =='1'){ }}
    实名举报
    {{# }if(d.reportType =='2') { }}
    匿名举报
    {{#  } }}
</script>
<script type="text/html" id="statusTemp">
    {{#  if(d.status =='1'){ }}
    处理中
    {{# }if(d.status =='2') { }}
    已结案
    {{# }if(d.status == '3') { }}
    已回复
    {{#  } }}
</script>
<script type="text/html" id="sendCaseTypeTemp">
    {{#  if(d.sendCaseType =='1'){ }}
    市派单
    {{# }if(d.sendCaseType =='2') { }}
    回退再派
    {{# }if(d.sendCaseType == '3') { }}
    直退市中心
    {{# }if(d.sendCaseType == '4') { }}
    回退市中心
    {{# }if(d.sendCaseType == '5') { }}
    回复市中心
    {{#  } }}
</script>

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

    document.onkeydown = function (e) { // 回车提交表单
        var theEvent = window.event || e;
        var code = theEvent.keyCode || theEvent.which;
        if (code == 13) {
            $(".select .select-on").click();
        }
    }
    layui.use(['table','laydate'], function () {
        var table = layui.table;
        var laydate = layui.laydate;
        //方法级渲染
        table.render({
            id: 'adviceList',
            elem: '#adviceList'
            , url: '/caseAnalyze/showYesswList'
            , cols: [[
                //{checkbox: true, fixed: true, width: '5%'}
                 {field: 'yesswNumber', title: '工单编号', width: '15%'}
                , {field: 'acceptOffice', title: '承办单位', width: '10%', sort: true}
                , {field: 'sendCaseType', title: '派单类型', width: '10%', templet: '#sendCaseTypeTemp', sort: true}
                , {field: 'yesswStatus', title: '工单状态', width: '10%', sort: true}
                , {field: 'finishTime', title: '到期时间', width: '10%',templet: '<div>{{ layui.laytpl.toDateString(d.finishTime,"yyyy-MM-dd HH:mm:ss") }}</div>',sort: true}
                , {field: 'createCaseTime', title: '填单时间', width: '15%',templet: '<div>{{ layui.laytpl.toDateString(d.createCaseTime,"yyyy-MM-dd HH:mm:ss") }}</div>',sort: true}
                , {field: 'createByStr', title: '提交人', width: '10%', sort: true}
                , {field: 'acceptOfficeProcess', title: '承办单位转派情况', width: '10%'}
                , {field: 'remark', title: '操作', width: '10%', toolbar: "#toolBar"}
            ]]
            , done: function (res, curr, count) {
                //console.log(curr);
                //$("#currentPage").val(curr);

                for (var i=0;i<res.data.length;i++){
                    if(res.data[i].closeFinishTimeFlag == '是'){
                        $("table tbody tr").eq(i).css('color','red');
                    }
                }
            }
            , page: true
            ,  height: 'full-83'
        });

        //日期
        laydate.render({
            elem: '#createDateStart',
            type: 'datetime'
        });
        laydate.render({
            elem: '#createDateEnd',
            type: 'datetime'
        });

        laydate.render({
            elem: '#recoveryTimeStart',
            type: 'datetime'
        });

        laydate.render({
            elem: '#recoveryTimeEnd',
            type: 'datetime'
        });

        var $ = layui.$, active = {
            select: function () {
                /*var title = $('#title').val();
                var content = $('#content').val();*/
                let reportType = $("#reportType").val();
                let yesswNumber = $("#yesswNumber").val();
                let yesswCreatetimeStart = $("#yesswCreatetimeStart").val();
                let yesswCreatetimeEnd = $("#yesswCreatetimeEnd").val();
                let yesswStatus = $("#yesswStatus").val();

                let createDateStart = $("#createDateStart").val();
                let createDateEnd = $("#createDateEnd").val();
                let acceptOffice = $("#acceptOffice").val();
                let createByStr = $("#createByStr").val();

                let recoveryTimeStart = $("#recoveryTimeStart").val();
                let recoveryTimeEnd = $("#recoveryTimeEnd").val();

                let pleasedFlag = $("#pleasedFlag").val();
                let solveFlag = $("#solveFlag").val();

                let yesswContent = $("#yesswContent").val();
                let title = $("#title").val();
                table.reload('adviceList', {
                    where: {
                        yesswNumber: yesswNumber,
                        yesswCreatetimeStart: yesswCreatetimeStart,
                        yesswCreatetimeEnd: yesswCreatetimeEnd,
                        yesswStatus: yesswStatus,
                        createDateStart: createDateStart,
                        createDateEnd: createDateEnd,
                        acceptOffice: acceptOffice,
                        createByStr: createByStr,
                        pleasedFlag: pleasedFlag,
                        recoveryTimeStart: recoveryTimeStart,
                        recoveryTimeEnd: recoveryTimeEnd,
                        solveFlag: solveFlag,
                        yesswContent: yesswContent
                    }
                });
            },
            reload:function(){
                $('#title').val('');
                $('#content').val('');
                table.reload('adviceList', {
                    where: {
                        roleName: null,
                        remark: null
                    }
                });
            },
            add: function () {
                add('添加意见', 'showAddAdvice', 700, 450);
            },
            update: function () {
                var checkStatus = table.checkStatus('roleList')
                        , data = checkStatus.data;
                if (data.length != 1) {
                    layer.msg('请选择一行编辑', {icon: 5});
                    return false;
                }
                update('编辑角色', 'updateAdvice?id=' + data[0].id, 700, 450);
            },
            detail: function () {
                var checkStatus = table.checkStatus('roleList')
                        , data = checkStatus.data;
                if (data.length != 1) {
                    layer.msg('请选择一行查看', {icon: 5});
                    return false;
                }
                detail('查看角色信息', 'updateAdvice?id=' + data[0].id, 700, 450);
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
                detail('编辑角色', '/caseAnalyze/editCase?id=' + data.id, 700, 450);
            } else if (obj.event === 'del') {
                layer.confirm('确定删除?', function(){
                    del(data.id);
                });
            } else if (obj.event === 'edit') {
                update('编辑意见', '/caseAnalyze/editCase?id=' + data.id, 700, 450);
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

    });
    function del(id) {
        $.ajax({
            url: "/caseAnalyze/delYesswCaseAnalyze",
            type: "post",
            data: {id: id},
            success: function (d) {
                if(d.msg){
                    layer.msg(d.msg,{icon:6,offset: 'rb',area:['120px','80px'],anim:2});
                    layui.table.reload('adviceList');
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
        /*layer.open({
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
        });*/
        //意见信息中要添加对应的处理信息，改成跳页面不用弹框的方式
        location.href= url+"&detail=true";
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
        /*layer.open({
            id: 'advice-update',
            type: 2,
            area: [w + 'px', h + 'px'],
            fix: false,
            maxmin: true,
            shadeClose: false,
            shade: 0.4,
            title: title,
            content: url + '&detail=false'
        });*/
        //意见信息中要添加对应的处理信息，改成跳页面不用弹框的方式
        location.href= url+"&detail=false";
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
    function add(title, url, w, h) {
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
            id: 'advice-add',
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

    function addCase() {
        layer.open({
            id: 'advice-add',
            type: 2,
            area: [800 + 'px', 600 + 'px'],
            fix: false,
            maxmin: true,
            shadeClose: false,
            shade: 0.4,
            title: '获取数据',
            content: '/caseAnalyze/addCase',
            end: function () {
                location.reload();
            }
        });
    }

    function loadYesswData() {
        layer.open({
            id: 'advice-add',
            type: 2,
            area: [700 + 'px', 600 + 'px'],
            fix: false,
            maxmin: true,
            shadeClose: false,
            shade: 0.4,
            title: '获取数据',
            content: '/case/loadYesswDataPage'
        });
    }

    function expExcel() {

        let yesswNumber = $("#yesswNumber").val();
        //let yesswCreatetimeStart = $("#yesswCreatetimeStart").val();
        //let yesswCreatetimeEnd = $("#yesswCreatetimeEnd").val();
        let createDateStart = $("#createDateStart").val();
        let createDateEnd = $("#createDateEnd").val();

        let acceptOffice = $("#acceptOffice").val();
        let createByStr = $("#createByStr").val();
        let pleasedFlag = $("#pleasedFlag").val();
        let solveFlag = $("#solveFlag").val();

        let recoveryTimeStart = $("#recoveryTimeStart").val();
        let recoveryTimeEnd = $("#recoveryTimeEnd").val();

        let yesswContent = $("#yesswContent").val();
        location.href="/caseAnalyze/expExcel?yesswNumber="+yesswNumber+"&createDateStart="+createDateStart+"&createDateEnd="+createDateEnd+"&acceptOffice="+acceptOffice+"&createByStr="+createByStr+"&pleasedFlag="+pleasedFlag+"&recoveryTimeStart="+recoveryTimeStart+"&recoveryTimeEnd="+recoveryTimeEnd+"&solveFlag="+solveFlag+"&yesswContent="+yesswContent;
        var index = layer.load(2, {time: 0});
        var timer = setInterval(function(){
            $.ajax({
                url: '/caseAnalyze/getEndFlag',
                success: function(data){
                    //console.log(data.data);
                        if (data.data == "1") {
                        clearInterval(timer);
                        layer.close(index);
                    }
                },
                error:function(e){
                    console.log(e.responseText);
                }
            });
        }, 500);

        /*$("#selectForm").attr("action","/case/expExcel");
        $("#selectForm").submit();
        $("#selectForm").attr("action","/case/capYesswCaseList");*/
    }


    function impExcel() {
        layer.open({
            id: 'advice-add',
            type: 2,
            area: [700 + 'px', 600 + 'px'],
            fix: false,
            maxmin: true,
            shadeClose: false,
            shade: 0.4,
            title: '导入excel',
            content: '/caseAnalyze/impExcelPage',
            end: function () {
                location.reload();
            }
        });
    }



</script>
</body>

</html>

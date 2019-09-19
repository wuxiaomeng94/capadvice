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
            $('#acceptOffice').select2();

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
<div class="x-body">
  <form class="form-horizontal layui-form" style="margin-left: 20px;">
    <#--<div style="width:100%;height:400px;overflow: auto;">
    <div class="layui-form-item">
      <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
        <legend style="font-size:16px;">案件信息</legend>
      </fieldset>
    </div>-->
        <div class="form-group">
            <h4 class="text-center">案件信息</h4>
        </div>

        <div class="form-group">
            <label class="control-label col-xs-2">案件编号</label>
            <div class="col-xs-6">
                <input type="text" name="yesswNumber" id="yesswNumber" style="width: 100%" lay-verify="yesswNumber"/>
            </div>
        </div>

        <div class="form-group">

            <label class="control-label col-xs-2">承办单位</label>
            <div class="col-xs-6">
                <select name="acceptOffice" id="acceptOffice" style="width: 100%" lay-verify="acceptOffice">
                    <option value="">请选择</option>
                        <#list officeList as item>
                            <option value="${item.name}">${item.name}</option>
                        </#list>
                </select>
            </div>

        </div>

        <div class="form-group">
            <label class="control-label col-xs-2">到期时间</label>
            <div class="col-xs-6">
                <input type="text" name="finishTime" id="finishTime" lay-verify="datetime" autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-xs-2">填单时间</label>
            <div class="col-xs-6">
                <input type="text" name="createCaseTime" id="createCaseTime" lay-verify="datetime" autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="form-group">

            <label class="control-label col-xs-2">派件类型</label>
            <div class="col-xs-6"><#-- sendCaseTypeDict -->
                <select name="sendCaseType" id="sendCaseType" style="width: 100%" lay-verify="sendCaseType">
                    <option value="">请选择</option>
                    <#list sendCaseTypeDict as item>
                        <option value="${item.key}">${item.value}</option>
                    </#list>
                </select>
            </div>

        </div>




  <div style="width: 100%;height: 55px;background-color: white;border-top:1px solid #e6e6e6;
  position: fixed;bottom: 1px;margin-left:-20px;">
    <div class="layui-form-item" style=" float: right;margin-right: 30px;margin-top: 8px">

      <button  class="layui-btn layui-btn-normal" lay-filter="add" lay-submit>
        确认
      </button>
      <#--<button  class="layui-btn layui-btn-primary" id="close">
        取消
      </button>-->
    </div>
  </div>
  </form>
</div>
<script>
  layui.use(['form','layer','laydate'], function(){
    $ = layui.jquery;
    var form = layui.form
        ,layer = layui.layer;
    var laydate = layui.laydate;
    //自定义验证规则
    form.verify({
        yesswNumber: function (value) {
            if (value.trim()=="") {
                return "问题编号不能为空";
            }
        },
        acceptOffice: function (value) {
            if (value.trim()=="") {
                return "承办单位不能为空";
            }
        },
        sendCaseType: function (value) {
            if (value.trim()=="") {
                return "派件类型不能为空";
            }
        }
        /*title: function(value){
        if(value.trim()==""){
          return "标题不能为空";
        }
      }*/
        /*filingOverTime: function (value) {
            if (value.trim()=="") {
                return "派单时间范围不能为空";
            }
        },
        filingBeginTime: function (value) {
            if (value.trim()=="") {
                return "派单时间范围不能为空";
            }
        }*/
    });

      laydate.render({
          elem: '#finishTime',
          type: 'datetime'
      });

      laydate.render({
         elem: '#createCaseTime',
         type: 'datetime'
      });

   $('#close').click(function(){
     var index = parent.layer.getFrameIndex(window.name);
     parent.layer.close(index);
   });
    //监听提交

    form.on('submit(add)', function(data){

        /*$.ajax({
            url: "/caseAnalyze/checkYesswNumberExist",
            type: "get",
            data: data.field,
            success: function (d) {
                if (d.flag) {

                } else {
                    layer.msg("该案件号已存在");
                }
            }
        });*/
        var loadIndex = layer.load(2, {time: 0});
        $.ajax({
            url:'/caseAnalyze/saveCase',
            type:'post',
            data:data.field,
            traditional: true,
            success:function(d){
                var index = parent.layer.getFrameIndex(window.name);
                if(d.flag){
                    layer.close(loadIndex);
                    parent.layer.close(index);
                    //window.parent.layui.table.reload(tableId);
                    //window.top.layer.msg(d.msg,{icon:6,offset: 'rb',area:['200px','80px'],anim:2});
                }else{
                    layer.close(loadIndex);
                    layer.msg(d.msg,{icon:5});
                }
            },error:function(e){
                layer.close(loadIndex);
                layer.alert("发生错误", {icon: 6},function () {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                });
            }
        });


        //layerAjax('/caseAnalyze/saveCase',data.field,'adviceList');
      return false;
    });
  });
</script>
</body>

</html>

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
  <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
  <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js" charset="utf-8"></script>
  <script type="text/javascript" src="${re.contextPath}/plugin/ztree/js/jquery.ztree.core.js"></script>
  <script type="text/javascript" src="${re.contextPath}/plugin/ztree/js/jquery.ztree.excheck.js" charset="utf-8"></script>
  <script type="text/javascript" src="${re.contextPath}/plugin/tools/tool.js"></script>
    <script  type="text/javascript">
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
  <form class="layui-form layui-form-pane" style="margin-left: 20px;">
    <div style="width:100%;height:400px;overflow: auto;">
    <div class="layui-form-item">
      <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
        <legend style="font-size:16px;">获取12345案件信息</legend>
      </fieldset>
    </div>
    <div class="layui-form-item">
      <label for="roleName" class="layui-form-label">
        <span class="x-red">*</span>派单时间
      </label>
      <div class="layui-input-inline">
        <#--<input type="text"  id="title" name="title"  lay-verify="title"
               autocomplete="off" class="layui-input">-->
            <input type="text" name="filingBeginTime" id="filingBeginTime" lay-verify="datetime" autocomplete="off" class="layui-input">
      </div>
      <#--<div id="ms" class="layui-form-mid layui-word-aux">
        <span class="x-red">*</span><span id="ums">标题必填</span>
      </div>-->
    </div>
    <div class="layui-form-item">
    <div class="layui-inline">
      <label for="remark" class="layui-form-label">
        <span class="x-red">*</span>至
      </label>
      <div class="layui-input-inline">
          <input type="text" name="filingOverTime" id="filingOverTime" lay-verify="datetime" autocomplete="off" class="layui-input">
      </div>
    </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-inline">
            <label for="remark" class="layui-form-label">
                <#--<span class="x-red">*</span>-->授权码
            </label>
            <div class="layui-input-inline">
                <input type="text" name="tokenStr" id="tokenStr"  autocomplete="off" class="layui-input">
            </div>
        </div>
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
        /*title: function(value){
        if(value.trim()==""){
          return "标题不能为空";
        }
      }*/
        filingOverTime: function (value) {
            if (value.trim()=="") {
                return "派单时间范围不能为空";
            }
        },
        filingBeginTime: function (value) {
            if (value.trim()=="") {
                return "派单时间范围不能为空";
            }
        }
    });

      laydate.render({
          elem: '#filingBeginTime',
          type: 'datetime'
      });
      laydate.render({
          elem: '#filingOverTime',
          type: 'datetime'
      });

   $('#close').click(function(){
     var index = parent.layer.getFrameIndex(window.name);
     parent.layer.close(index);
   });
    //监听提交
    form.on('submit(add)', function(data){
        /*var zTree = $.fn.zTree.getZTreeObj("treeDemo");
         var jsonArr= zTree.getCheckedNodes(true);
         var menus=[];
         for(var item in jsonArr){
             menus.push(jsonArr[item].id);
         }
        data.field.menus=menus;*/
      //layerAjax('loadData',data.field,'adviceList');
        var loadIndex = layer.load(2, {time: 0});
        $.ajax({
            url:'loadData',
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


      return false;
    });
  });
</script>
</body>

</html>

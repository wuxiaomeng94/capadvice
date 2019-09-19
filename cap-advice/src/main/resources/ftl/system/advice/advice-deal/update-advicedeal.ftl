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
  <script type="text/javascript" src="${re.contextPath}/plugin/fileinput/mess.js"></script>
  <script type="text/javascript" src="${re.contextPath}/plugin/fileinput/uploadCommon.js"></script>


    <script type="text/javascript">
        $(document).ready(function () {
            var flag = '${detail}';
            if (flag) {
                $("form").disable();
            }


            var uploadCommon = new UploadCommon();
            var arr = ['cap_advice_deal'];

            uploadCommon.loadFile("http://39.97.230.101:8001/token/getToken", arr, "1");
            $("#filesDynCode").val(uploadCommon.getDynCode());



        });

        (function ($) {
            $.fn.disable = function () {
                return $(this).find("*").each(function () {
                    $(this).attr("disabled", "disabled");
                });
            }
        })(jQuery);
    </script>
</head>

<body>
<div class="x-body">
  <form class="layui-form layui-form-pane" style="margin-left: 20px;" id="inputForm">
      <input type="hidden" id="adviceId" name="capAdviceId" value="${capAdviceDeal.capAdviceId}"/>
      <input type="hidden" id="id" name="id" value="${capAdviceDeal.id}"/>
      <input type="hidden" id="status" name="status" value="${capAdviceDeal.status}"/>
    <div style="width:100%;height:400px;overflow: auto;">
    <div class="layui-form-item">
      <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
        <legend style="font-size:16px;">意见信息</legend>
      </fieldset>
    </div>
    <#--<div class="layui-form-item">
      <label for="title" class="layui-form-label">
        <span class="x-red">*</span>标题
      </label>
      <div class="layui-input-inline">
        <input type="text"  id="title" name="title"  lay-verify="title"
               autocomplete="off" class="layui-input" value="${capAdviceDeal.title}">
      </div>
    </div>-->
    <div class="layui-form-item">
      <label for="content" class="layui-form-label">
        <span class="x-red">*</span>内容
      </label>
      <div class="layui-col-xs7">
        <textarea id="content" name="content" lay-verify="content" class="layui-textarea" style="resize: none">${capAdviceDeal.content}</textarea>
      </div>
    </div>
        <#if !detail>
            <cloudFile projectName="cap_advice_deal" buisId="${capAdviceDeal.id}" fileUniqueCode="cap_advice_deal" filesDynCode="" tableName="cap_advice_deal" fileReadOnly="false" ></cloudFile>
        </#if>
        <#if detail>
            <cloudFile projectName="cap_advice_deal" buisId="${capAdviceDeal.id}" fileUniqueCode="cap_advice_deal" filesDynCode="" tableName="cap_advice_deal" fileReadOnly="true" ></cloudFile>
        </#if>

        <input type="hidden" id="filesDynCode" name="filesDynCode"/>

    </div>


<#if !detail>
  <div style="width: 100%;height: 55px;background-color: white;border-top:1px solid #e6e6e6;
  position: fixed;bottom: 1px;margin-left:-20px;">
    <div class="layui-form-item" style=" float: right;margin-right: 30px;margin-top: 8px">

      <button  class="layui-btn layui-btn-warm" lay-filter="add" lay-submit >
        保存
      </button>
      <button  class="layui-btn layui-btn-warm" lay-filter="add" lay-submit onclick="$('#status').val('2')">
        提交
      </button>
      <button  class="layui-btn layui-btn-primary" id="close" type="button">
        取消
      </button>
    </div>
  </div>
</#if>
  </form>
</div>
<script>
  layui.use(['form','layer'], function(){
    $ = layui.jquery;
    var form = layui.form
        ,layer = layui.layer;

    //自定义验证规则
    form.verify({
        content: function (value) {
            if (value.trim()=="") {
                return "内容不能为空";
            }
        }
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
        //var inputForm = $("#inputForm");
        //inputForm.submit();
        layerAjax('saveDeal',data.field,'adviceDealList');
        /*var inputForm = $("#inputForm");
        var formdata = new FormData(inputForm);
        $.ajax({
            url:'saveDeal',
            type:'post',
            data:formdata,
            traditional: true,
            success:function(d){
                var index = parent.layer.getFrameIndex(window.name);
                if(d.flag){
                    parent.layer.close(index);
                    window.parent.layui.table.reload('adviceDealList');
                    window.top.layer.msg(d.msg,{icon:6,offset: 'rb',area:['200px','80px'],anim:2});
                }else{
                    layer.msg(d.msg,{icon:5});
                }
            }
        });*/

      return false;
    });
  });



  /*$(function(){
      var uploadCommon = new UploadCommon();
      //var filesDynCode =uploadCommon.filesDynCode;
      var arr = ['cap_advice_deal'];
      uploadCommon.loadFile("http://localhost/token/getToken", arr, "1");
      $("#filesDynCode").val(uploadCommon.getDynCode());
  });*/

</script>
</body>

</html>

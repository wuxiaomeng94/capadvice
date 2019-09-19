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
  <form class="form-horizontal layui-form" style="margin-left: 20px;" id="subForm" enctype="multipart/form-data" method="post" action="/caseAnalyze/uploadExcel">


      <div class="form-group">
          <label class="control-label col-xs-2">excel模板下载：</label>
          <div class="controls col-xs-2">
              <a href="/caseAnalyze/downloadTemp">excel模板</a>
          </div>
      </div>

      <div class="form-group">

          <label class="control-label col-xs-2 no-padding-right">选择需导入的excel文件:</label>
          <div class="col-xs-2">
              <input type="file" class="file" id="excelFile" name="excelFile"/>
          </div>

      </div>



      <div style="text-align: center">
          <button type="button" class="btn btn-info btn-sm" id="submitBtn" onclick="impExcel()">导入</button>
      </div>
  </div>
  </form>
<#--</div>-->
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
        accpetOffice: function (value) {
            if (value.trim()=="") {
                return "承办单位不能为空";
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
      layerAjax('/caseAnalyze/saveProcess',data.field,'adviceList');
      return false;
    });
  });
  
  
  function impExcel() {
      let myFormData = new FormData();
      let name = $("#excelFile").val();
      myFormData.append("excelFile", $("#excelFile").get(0).files[0]);
      myFormData.append("name",name);
      $.ajax({
          url:'/caseAnalyze/uploadExcel',
          type:'post',
          data:myFormData,
          traditional: true,
          processData : false,
          contentType : false,
          success:function(data){
              layer.msg(data.msg);
              let index = parent.layer.getFrameIndex(window.name);
              if (data.code === 200) {
                  parent.layer.close(index);
              }
          }
      });

  }
  
</script>
</body>

</html>

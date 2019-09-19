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
            $('#solveFlag').select2();
            $('#pleasedFlag').select2();
            $('#adjustFlag').select2();
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
  <form class="form-horizontal layui-form" style="margin-left: 20px;">
    <#--<div style="width:100%;height:400px;overflow: auto;">-->
    <#--<div class="layui-form-item">
      <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
        <legend style="font-size:16px;">案件信息</legend>
      </fieldset>
    </div>-->

        <#--<input type="hidden" name="id" id="id" value="${capAcceptOffice.id}"/>-->
        <input type="hidden" name="id" id="id" value="${process.id}"/>
        <input type="hidden" name="yesswCaseId" id="yesswCaseId" value="${capYesswCaseAnalyze.id}"/>

        <input type="hidden" name="sendCaseType" id="sendCaseType" value="${capYesswCaseAnalyze.sendCaseType}"/>

        <div class="form-group">
            <h4 class="text-center">案件信息</h4>
        </div>

        <div class="form-group">
            <label class="control-label col-xs-2">案件编号</label>
            <div class="col-xs-6">
                <input type="text" name="yesswNumber" id="yesswNumber" readonly="readonly"
                       value="${capYesswCaseAnalyze.yesswNumber}" style="width: 100%"/>
            </div>
        </div>

        <div class="form-group">

            <label class="control-label col-xs-2">承办单位</label>
            <div class="col-xs-6">
                <select name="acceptOffice" id="acceptOffice" style="width: 100%;display: inline-block"  lay-verify="acceptOffice">
                    <option value="">请选择</option>
                        <#list officeList as item>
                            <option value="${item.name}" <#if item.name == process.acceptOffice>selected</#if>>${item.name}</option>
                        </#list>
                </select>
            </div>

        </div>

        <div class="form-group">

            <label class="control-label col-xs-2">是否解决</label>
            <div class="col-xs-6">
                <select name="solveFlag" id="solveFlag" style="width: 100%;display: inline-block" lay-verify="solveFlag">
                    <option value="">请选择</option>
                    <option value="是" <#if '是'==process.solveFlag>selected</#if>>是</option>
                    <option value="否" <#if '否'==process.solveFlag>selected</#if>>否</option>
                    <option value="退市" <#if '退市'==process.solveFlag>selected</#if>>退市</option>
                </select>
            </div>

        </div>

        <div class="form-group">
            <label class="control-label col-xs-2">是否满意</label>
            <div class="col-xs-6">
                <select name="pleasedFlag" id="pleasedFlag" style="width: 100%;display: inline-block" lay-verify="pleasedFlag">
                    <option value="">请选择</option>
                    <option value="满意" <#if '满意'==process.pleasedFlag>selected</#if>>满意</option>
                    <option value="不满意" <#if '不满意'==process.pleasedFlag>selected</#if>>不满意</option>
                    <option value="不需要回复" <#if '不需要回复'==process.pleasedFlag>selected</#if>>不需要回复</option>
                    <option value="拒绝接听" <#if '拒绝接听'==process.pleasedFlag>selected</#if>>拒绝接听</option>
                    <option value="未接听" <#if '未接听'==process.pleasedFlag>selected</#if>>未接听</option>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-xs-2">是否经过指挥协调</label>
            <div class="col-xs-6">
                <select name="adjustFlag" id="adjustFlag" style="width: 100%;display: inline-block">
                    <option value="">请选择</option>
                    <option value="是" <#if '是'==process.adjustFlag>selected</#if>>是</option>
                    <option value="否" <#if '否'==process.adjustFlag>selected</#if>>否</option>
                </select>
            </div>
        </div>

        <div class="form-group">

            <label class="control-label col-xs-2">回复内容</label>
            <div class="col-xs-6">
                <textarea name="remark" id="remark"  style="resize: none;height: 100px;width: 100%">${process.remark}</textarea>
            </div>

        </div>
    <#--<div class="layui-form-item">
      &lt;#&ndash;<label for="roleName" class="layui-form-label">
        <span class="x-red">*</span>派单时间
      </label>&ndash;&gt;


        <div class="layui-form-item">
            <div class="layui-inline">
                <label for="remark" class="layui-form-label">
                    &lt;#&ndash;<span class="x-red">*</span>&ndash;&gt;案件编号
                </label>
                <div class="layui-input-inline">
                    <input type="text" name="yesswNumber" readonly="readonly" id="yesswNumber"
                           autocomplete="off" class="layui-input" value="${capYesswCaseAnalyze.yesswNumber}">
                </div>
            </div>
        </div>

        <div class="layui-form-item">
                <label for="remark" class="layui-form-label">
                &lt;#&ndash;<span class="x-red">*</span>&ndash;&gt;承办单位
                </label>
                <div class="layui-input-block">
                    &lt;#&ndash;<input type="text" name="fullName" id="fullName"  autocomplete="off" class="layui-input">  officeList&ndash;&gt;
                    <select name="acceptOffice" id="acceptOffice" class="layui-select" style="display: inline-block;width: 33%">
                        <option value="">请选择</option>
                        <#list officeList as item>
                            <option value="${item.name}">${item.name}</option>
                        </#list>

                    </select>
                </div>
        </div>

    </div>-->
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
        acceptOffice: function (value) {
            if (value.trim()=="") {
                return "承办单位不能为空";
            }
        },
        solveFlag: function (value) {
            let sendCaseType = $("#sendCaseType").val();
            if (sendCaseType == '1' || sendCaseType == '2') {
                if (value.trim()=="") {
                    return "是否解决选项不能为空";
                }
            }
        },
        pleasedFlag: function (value) {
            /*if (value.trim()=="") {
                return "满意度选项不能为空";
            }*/
            let sendCaseType = $("#sendCaseType").val();
            let solveFlag = $("#solveFlag").val();
            if (sendCaseType == '1' || sendCaseType == '2') {
                if (solveFlag!="退市") {
                    if (value.trim()=="") {
                        return "满意度选项不能为空";
                    }
                }
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
      //layerAjax('/caseAnalyze/saveProcess',data.field,'adviceList');
        var loadIndex = layer.load(2, {time: 0});
        $.ajax({
            url:'/caseAnalyze/saveProcess',
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

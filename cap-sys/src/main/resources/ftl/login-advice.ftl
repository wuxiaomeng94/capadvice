
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>后台登录</title>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />

<#--<link rel="shortcut icon" href="<%=request.getContextPath()%>/plugin/x-admin/favicon.ico" type="image/x-icon" />-->
    <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${re.contextPath}/plugin/x-admin/css/font.css">
    <link rel="stylesheet" href="${re.contextPath}/plugin/x-admin/css/xadmin.css">
    <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
    <style>
        .login-header {
            height: 100px;
            background-color: white;
        }
        .login-header img:nth-child(1) {
            float:left;
        }
        .login-header img:nth-child(2) {
            float: right;
        }
        .login-body {
            background: url("${re.contextPath}/plugin/x-admin/images/login_09.png") no-repeat center;
            background-size: cover;
            height: 560px;
        }
        .login-form {
            width: 350px;
            height: 300px;
            background-color: #ffffff;
            box-sizing: border-box;
            border-radius: 15px;
            margin-top: 8%;
            margin-right: 10%;
            float: right;
        }
        .login-ele {
            margin-top: 50px;
        }
        .login-input {
            width: 300px;
            margin-top: 20px;
            margin-left: 30px;
            vertical-align: middle !important;
        }
        .login-submit {
            display: inline-block;
            vertical-align: middle;
            padding: 12px 24px;
            margin: 0px;
            font-size: 18px;
            line-height: 24px;
            text-align: center;
            white-space: nowrap;
            vertical-align: middle;
            cursor: pointer;
            color: #ffffff;
            /*background-color: #189F92;*/
            background-color: red;
            border-radius: 25px;
            border: none;
            -webkit-appearance: none;
            outline:none;
            /*width:100%;*/
            width: 200px !important;
            height: 40px;
            margin-top: 40px;
            margin-left: 20%;
        }
        .login-footer {
            /*background-color: #E9E9E9;*/
            width: 100%;
        }

    </style>
</head>
<body>


<div class="login-header">
    <img src="${re.contextPath}/plugin/x-admin/images/login_03.png">
    <img src="${re.contextPath}/plugin/x-admin/images/login_05.png">
</div>
<div class="login-body">

<#--<div class="message">首信呼叫前后端分离脚手架</div>-->
<#--<div class="message">登录页面</div>-->
    <div id="darkbannerwrap"></div>
    <div class="login-body">
        <form method="post" action="${re.contextPath}/login" class="layui-form login-form" >

            <div class="login-ele">
            <div class="login-input">
                <img src="${re.contextPath}/plugin/x-admin/images/login_12.png" style="width: 30px;height: 30px">
                <input name="username" placeholder="用户名" autocomplete="off"  type="text" lay-verify="username" class="layui-input" style="display: inline;width: 80%">
            </div>

            <#--<hr class="hr15">-->
            <div class="login-input">
                <img src="${re.contextPath}/plugin/x-admin/images/lock.png" style="width: 30px;height: 30px">
                <input name="password" lay-verify="password" placeholder="密码" autocomplete="off"  type="password" class="layui-input" style="display: inline;width: 80%">
            </div>

            <#--<hr class="hr15">-->
        <#--<div  class="layui-inline">
            <label class="layui-form-label" style="width:40px;padding: 9px 0px;">验证码&nbsp;</label>
               <div class="layui-input-inline">
                     <input type="text" name="code" style="width:150px;height:35px;" autocomplete="off" lay-verify="code"   class="layui-input">
              </div>
            <div class="layui-input-inline">
                <img src="" id="code">
            </div>
        </div>-->
        <#--<div>-->
        <#--<label class="layui-form-label" style="width:40px;padding: 9px 0px;">记住我</label>  由于不好验证 目前去掉-->
        <#--<input type="checkbox"   name="rememberMe" lay-skin="switch" lay-filter="switchTest" lay-text="ON|OFF">-->
        <#--</div>-->
            <#--<hr class="hr15">-->
            <input value="登录" lay-submit lay-filter="login" style="width:100%;" type="submit" class="login-submit">
            <#--<hr class="hr20" >-->
            </div>

        </form>
    </div>

</div>
<div class="login-footer">
    <div style="text-align: center;margin-top:30px">
        <p>版权所有：呼叫中心党支部</p>
    </div>
</div>

<script>
    var layer;
    $(function  () {
        layui.use(['form','layer'], function(){
            var form = layui.form;
            form.verify({
                username:function(v){
                    if(v.trim()==''){
                        return "用户名不能为空";
                    }
                }
                ,password:function(v){
                    if(v.trim()==''){
                        return "密码不能为空";
                    }
                }
                /*code:function(v){
                    if(v.trim()==''){
                        return '验证码不能为空';
                    }
                }*/
            });

            form.render();
        });
        layer = layui.layer;
        var msg='${message}';
        if(msg.trim()!=""){
            layer.msg(msg, {icon: 5,anim:6,offset: 't'});
        }
        $("#code").click(function(){
            var url = "${re.contextPath}/getCode?"+new Date().getTime();
            this.src = url;
        }).click().show();
        $('#code').on('mouseover',function(){
            layer.tips('点击刷新验证码', this,{time:1000});
        });
    })

    if (window != top)
        top.location.href = location.href;
</script>


<!-- 底部结束 -->
</body>
</html>

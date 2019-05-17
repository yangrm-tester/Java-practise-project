<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>权限</title>
    <jsp:include page="/common/backend_common.jsp"/>
    <jsp:include page="/common/page.jsp"/>
</head>
<body class="no-skin" youdao="bind" style="background: white">
<input id="gritter-light" checked="" type="checkbox" class="ace ace-switch ace-switch-5"/>

<div class="page-header">
    <h1>
        权限模块管理
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            维护权限模块和权限点关系
        </small>
    </h1>
</div>
<div class="main-content-inner">
    <div class="col-sm-3">
        <div class="table-header">
            权限模块列表&nbsp;&nbsp;
            <a class="green" href="#">
                <i class="ace-icon fa fa-plus-circle orange bigger-130 aclModule-add"></i>
            </a>
        </div>
        <div id="aclModuleList">

        </div>
    </div>
    <div class="col-sm-9">
        <div class="col-xs-12">
            <div class="table-header">
                权限点列表&nbsp;&nbsp;
                <a class="green" href="#">
                    <i class="ace-icon fa fa-plus-circle orange bigger-130 acl-add"></i>
                </a>
            </div>
            <div>
                <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                    <div class="row">
                        <div class="col-xs-6">
                            <div class="dataTables_length" id="dynamic-table_length"><label>
                                展示
                                <select id="pageSize" name="dynamic-table_length" aria-controls="dynamic-table"
                                        class="form-control input-sm">
                                    <option value="10">10</option>
                                    <option value="25">25</option>
                                    <option value="50">50</option>
                                    <option value="100">100</option>
                                </select> 条记录 </label>
                            </div>
                        </div>
                    </div>
                    <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer"
                           role="grid"
                           aria-describedby="dynamic-table_info" style="font-size:14px">
                        <thead>
                        <tr role="row">
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                权限名称
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                权限模块
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                类型
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                URL
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                状态
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                顺序
                            </th>
                            <th class="sorting_disabled" rowspan="1" colspan="1" aria-label=""></th>
                        </tr>
                        </thead>
                        <tbody id="aclList">

                        </tbody>
                    </table>
                    <div class="row" id="aclPage">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="dialog-aclModule-form" style="display: none;">
    <form id="aclModuleForm">
        <table class="table table-striped table-bordered table-hover dataTable no-footer" role="grid">
            <tr>
                <td style="width: 80px;"><label for="parentId">上级模块</label></td>
                <td>
                    <select id="parentId" name="parentId" data-placeholder="选择模块" style="width: 200px;">

                    </select>
                    <input type="hidden" name="id" id="aclModuleId"/>
                </td>
            </tr>
            <tr>
                <td><label for="aclModuleName">名称</label></td>
                <td><input type="text" name="name" id="aclModuleName" value=""
                           class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="aclModuleSeq">顺序</label></td>
                <td><input type="text" name="seq" id="aclModuleSeq"
                           class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="aclModuleStatus">状态</label></td>
                <td>
                    <select id="aclModuleStatus" name="status" data-placeholder="选择状态" style="width: 150px;">
                        <option value="">请选择</option>
                        <option value="1">有效</option>
                        <option value="0">冻结</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="aclModuleRemark">备注</label></td>
                <td><textarea name="remark" id="aclModuleRemark" class="text ui-widget-content ui-corner-all" rows="3"
                              cols="25"></textarea></td>
            </tr>
        </table>
    </form>
</div>
<div id="dialog-acl-form" style="display: none;">
    <form id="aclForm">
        <table class="table table-striped table-bordered table-hover dataTable no-footer" role="grid">
            <tr>
                <td style="width: 80px;"><label for="parentId">所属权限模块</label></td>
                <td>
                    <select id="aclModuleSelectId" name="aclModuleId" data-placeholder="选择权限模块"
                            style="width: 200px;">

                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="aclName">名称</label></td>
                <input type="hidden" name="id" id="aclId"/>
                <td><input type="text" name="name" id="aclName" value="" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
            <tr>
                <td><label for="aclType">类型</label></td>
                <td>
                    <select id="aclType" name="type" data-placeholder="类型" style="width: 150px;">
                        <option value="1">菜单</option>
                        <option value="2">按钮</option>
                        <option value="3">其他</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="aclUrl">URL</label></td>
                <td><input type="text" name="url" id="aclUrl" value="" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
            <tr>
                <td><label for="aclStatus">状态</label></td>
                <td>
                    <select id="aclStatus" name="status" data-placeholder="选择状态" style="width: 150px;">
                        <option value="1">有效</option>
                        <option value="0">无效</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="aclSeq">顺序</label></td>
                <td><input type="text" name="seq" id="aclSeq" value="" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
            <tr>
                <td><label for="aclRemark">备注</label></td>
                <td><textarea name="remark" id="aclRemark" class="text ui-widget-content ui-corner-all" rows="3"
                              cols="25"></textarea></td>
            </tr>
        </table>
    </form>
</div>

<script id="aclModuleListTemplate" type="x-tmpl-mustache">
<ol class="dd-list ">
    {{#aclModuleList}}
        <li class="dd-item dd2-item aclModule-name {{displayClass}}" id="aclModule_{{id}}" href="javascript:void(0)" data-id="{{id}}">
            <div class="dd2-content" style="cursor:pointer;">
            {{name}}
            &nbsp;
            <a class="green {{#showDownAngle}}{{/showDownAngle}}" href="#" data-id="{{id}}" >
                <i class="ace-icon fa fa-angle-double-down bigger-120 sub-aclModule"></i>
            </a>
            <span style="float:right;">
                <a class="green aclModule-edit" href="#" data-id="{{id}}" >
                    <i class="ace-icon fa fa-pencil bigger-100"></i>
                </a>
                &nbsp;
                <a class="red aclModule-delete" href="#" data-id="{{id}}" data-name="{{name}}">
                    <i class="ace-icon fa fa-trash-o bigger-100"></i>
                </a>
            </span>
            </div>
        </li>
    {{/aclModuleList}}
</ol>






































</script>

<script id="aclListTemplate" type="x-tmpl-mustache">
{{#aclList}}
<tr role="row" class="acl-name odd" data-id="{{id}}"><!--even -->
    <td><a href="#" class="acl-edit" data-id="{{id}}">{{name}}</a></td>
    <td>{{showAclModuleName}}</td>
    <td>{{showType}}</td>
    <td>{{url}}</td>
    <td>{{#bold}}{{showStatus}}{{/bold}}</td>
    <td>{{seq}}</td>
    <td>
        <div class="hidden-sm hidden-xs action-buttons">
            <a class="green acl-edit" href="#" data-id="{{id}}">
                <i class="ace-icon fa fa-pencil bigger-100"></i>
            </a>
            <a class="red acl-role" href="#" data-id="{{id}}">
                <i class="ace-icon fa fa-flag bigger-100"></i>
            </a>
        </div>
    </td>
</tr>
{{/aclList}}






































</script>

<script type="text/javascript">


    $(function () {
        var aclModuleList; // 存储树形权限模块列表
        var aclModuleMap = {}; // 存储map格式权限模块信息
        var optionStr = ""; //下拉框选项
        var lastSelectedId = -1; //上次缓存的aclModuleId 默认是-1
        var aclOptionStr = "";
        var aclMap = {}; // 存储map格式的权限点信息

        var aclModuleListTemplate = $("#aclModuleListTemplate").html();
        Mustache.parse(aclModuleListTemplate);

        var aclListTemplate = $("#aclListTemplate").html();
        Mustache.parse(aclListTemplate);

        //加载权限模块列表
        loadAclModuleTree();


        function loadAclModuleTree() {
            $.ajax({
                url: "${ctx}/sys/aclModule/tree.json",
                method: "GET",
                success: function (result) {
                    console.log(result);
                    if (result.code == "0") {
                        aclModuleList = result.data;
                        //去渲染页面
                        var rendered = Mustache.render(aclModuleListTemplate, {
                            aclModuleList: aclModuleList,
                            //去掉没有子类的数据 的下拉图标
                            "showDownAngle": function () {
                                return function (text, render) {
                                    return (this.sysAclModuleLevelDTOList && this.sysAclModuleLevelDTOList.length > 0) ? "" : "hidden";
                                }
                            },
                            "displayClass": function () {
                                return "";
                            }
                        });
                        $("#aclModuleList").html(rendered);
                        //递归渲染他们的下一级数据
                        recursiveaclModuleList(aclModuleList);
                        bindAclModuleClick();
                    } else {
                        showMessage("权限列表模块", result.msg, false);
                    }
                }
            });
        };
        //递归渲染权限模块的下一级数据
        function recursiveaclModuleList(aclModuleList) {
            if (aclModuleList != null && aclModuleList.length > 0) {
                $.each(aclModuleList, function (index, aclModule) {
                    //存储权限模块数据到map
                    aclModuleMap[aclModule.id] = aclModule;
                    console.log(aclModule);
                    if (aclModule.sysAclModuleLevelDTOList != null && aclModule.sysAclModuleLevelDTOList.length > 0) {
                        var childRender = Mustache.render(aclModuleListTemplate, {
                            aclModuleList: aclModule.sysAclModuleLevelDTOList,
                            "showDownAngle": function () {
                                return function (text, render) {
                                    return (this.sysAclModuleLevelDTOList && this.sysAclModuleLevelDTOList.length > 0) ? "" : "hidden";
                                }
                            },
                            "displayClass": function () {
                                return "";
                            }
                        });
                        //console.log("#aclModule_" + aclModule.id)
                        $("#aclModule_" + aclModule.id).append(childRender);
                        recursiveaclModuleList(aclModule.sysAclModuleLevelDTOList);
                    }
                })
            }
        }

        /**绑定权限模块列表的点击操作---start*/
        function bindAclModuleClick() {
            /**权限模块下拉上拉小图标的显示---start*/
            $(".sub-aclModule").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                $(this).parent().parent().parent().children().children(".aclModule-name").toggleClass("hidden");
                if ($(this).is(".fa-angle-double-down")) {
                    $(this).removeClass("fa-angle-double-down").addClass("fa-angle-double-up");
                } else {
                    $(this).removeClass("fa-angle-double-up").addClass("fa-angle-double-down");
                }
            });
            /**权限模块下拉上拉小图标的显示---end*/

            /**权限模块编辑操作---start*/
            $(".aclModule-edit").click(function (e) {
                e.preventDefault(); //阻止默认事件
                e.stopPropagation(); //阻止冒泡事件
                //获取点击列表的id
                var aclModuleId = $(this).attr("data-id");
                console.log(aclModuleId);
                var editAclModule = aclModuleMap[aclModuleId];
                console.log(editAclModule);
                $("#dialog-aclModule-form").dialog({
                    modal: true,
                    title: "编辑权限模块",
                    open: function (event, ui) {
                        //去除右上角的X号
                        $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                        //渲染下拉菜单
                        optionStr = "<option value=\"0\">-----</option>";
                        recursiveRenderAclModuleSelect(aclModuleList, 1);
                        $("#aclModuleForm")[0].reset(); //阻止第二次进入新增数据页面显示第一次新增的数据
                        $("#parentId").html(optionStr);
                        //接下来填充数据
                        if (aclModuleId != null && aclModuleMap[aclModuleId] != null) {
                            var aclModuleSelected = aclModuleMap[aclModuleId];
                            $("#aclModuleId").val(aclModuleSelected.id);
                            $("#parentId").val(aclModuleSelected.parentId);
                            $("#aclModuleName").val(aclModuleSelected.name);
                            $("#aclModuleSeq").val(aclModuleSelected.seq);
                            $("#aclModuleStatus").val(aclModuleSelected.status);
                            $("#aclModuleRemark").val(aclModuleSelected.remark);
                        }
                    },
                    buttons: {
                        "编辑": function () {
                            updateAclModule(false, function () {
                                $("#dialog-aclModule-form").dialog("close");
                            }, function (data) {
                                showMessage("编辑权限模块", data.msg, false);
                            })

                        },
                        "取消": function () {
                            $("#dialog-aclModule-form").dialog("close");
                        }
                    }
                });
            });
            /**权限模块编辑操作---end*/

            /**权限模块删除操作---start*/
            $(".aclModule-delete").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                var aclModuleId = $(this).attr("data-id");
                var aclModuleName = $(this).attr("data-name");
                if (confirm("确定要删除权限模块[" + aclModuleName + "]吗？")) {
                    console.log("删除权限模块id:" + aclModuleId + ",删除权限模块name:" + aclModuleName);
                    $.ajax({
                        url: "${ctx}/sys/aclModule/delete.json",
                        data: {
                            aclModuleId: aclModuleId
                        },
                        method: "POST",
                        success: function (result) {
                            if (result.code == "0") {
                                showMessage("删除权限模块", "操作成功", false);
                                loadAclModuleTree();
                            } else {
                                showMessage("删除权限模块", result.msg, false);
                            }
                        },
                        error: function (result) {
                            showMessage("删除权限模块", result.msg, false);
                        }
                    });
                }
            });
            /**权限模块删除操作---end*/

            /**权限模块整个列表点击变色操作 ---start*/
            $(".aclModule-name").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                var aclModuleId = $(this).attr("data-id");
                //handleSelectedDept(aclModuleId);
                handleSelectedAclModule(aclModuleId);

            })
            /**权限模块整个列表点击变色操作 ---end*/

            function handleSelectedAclModule(aclModuleId) {
                if (lastSelectedId != -1) {  //说明 已被缓存过了 则要去掉缓存数据的样式
                    var lastAclModule = $("#aclModule_" + lastSelectedId + " .dd2-content:first");
                    lastAclModule.removeClass("btn-yellow");
                    lastAclModule.removeClass("no-hover");
                }
                //给当前的数据设置样式
                var currentAclModule = $("#aclModule_" + aclModuleId + " .dd2-content:first");
                currentAclModule.addClass("btn-yellow");
                currentAclModule.addClass("no-hover");
                lastSelectedId = aclModuleId;
                loadAclList(aclModuleId);
            }
        }

        /**加载用户列表数据*/
        function loadAclList(aclModuleId) {
            //加载数据 渲染页面 分页去查询数据
            var pageSize = $("#pageSize").val(); //每页展示数据
            var pageNo = $("#aclPage").val() || 1;//页码数
            var requestUrl = "${ctx}/sys/aclModule/page.json?aclModuleId=" + aclModuleId;
            $.ajax({
                url: requestUrl,
                data: {
                    pageNo: pageNo,
                    pageSize: pageSize
                },
                success: function (result) {
                    //renderUserListAndPage();
                    renderAclListAndPage(result, requestUrl);
                },
                error: function (result) {
                    showMessage("权限点列表", result.msg, true);
                }
            });
        }

        function renderAclListAndPage(result, url) {
            if (result != null && result.code == "0" && result.data.total > 0) {
                var rendered = Mustache.render(aclListTemplate, {
                    aclList: result.data.data,
                    "showAclModuleName": function () {
                        return aclModuleMap[this.aclModuleId].name;
                    },
                    "showStatus": function () {
                        return this.status == 1 ? "有效" : "无效";
                    },
                    "showType": function () {
                        return this.type == 1 ? "菜单" : (this.type == 2 ? "按钮" : "其他");
                    },
                    "bold": function () {
                        return function (text, render) {
                            var status = render(text);
                            if (status == '有效') {
                                return "<span class='label label-sm label-success'>有效</span>";
                            } else if (status == '无效') {
                                return "<span class='label label-sm label-warning'>无效</span>";
                            } else {
                                return "<span class='label'>删除</span>";
                            }
                        }
                    }
                });
                $("#aclList").html(rendered);
                bindAclClick();
                $.each(result.data.data, function (i, acl) {
                    aclMap[acl.id] = acl;
                })
            } else {
                $("#aclList").html("");
            }

            //渲染分页信息
            var pageSize = $("#pageSize").val();
            var pageNo = $("#aclPage .pageNo").val() || 1;
            renderPage(url, result.data.total, pageNo, pageSize, result.data.total > 0 ? result.data.data.length : 0, "aclPage", renderAclListAndPage);
        }


        function bindAclClick() {
            /**编辑权限点*/
            $(".acl-edit").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                var aclId = $(this).attr("data-id");
                var acl = aclMap[aclId];
                $("#dialog-acl-form").dialog({
                    modal: true,
                    title: "权限点编辑",
                    open: function (event, ui) {
                        $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                        //渲染权限模块下拉列表
                        aclOptionStr = "<option value=\"\">-----</option>";
                        recursiveRenderAclModuleOptionSelect(aclModuleList, 1);
                        $("#aclModuleSelectId").html(aclOptionStr);
                        if (acl != null) {
                            $("#aclModuleSelectId").val(acl.aclModuleId);
                            $("#aclName").val(acl.name);
                            $("#aclType").val(acl.type);
                            $("#aclUrl").val(acl.url);
                            $("#aclStatus").val(acl.status);
                            $("#aclSeq").val(acl.seq);
                            $("#aclRemark").val(acl.remark);
                            $("#aclId").val(acl.id);
                        }
                    },
                    buttons: {
                        "取消": function () {
                            $("#dialog-acl-form").dialog("close");
                        },
                        "保存": function () {
                            updateAcl(false, function (data) {
                                $("#dialog-acl-form").dialog("close");
                                loadAclList(lastSelectedId);
                            }, function (data) {
                                showMessage("编辑权限点", data.msg, true);
                            })
                        }
                    }
                })
            });

            /**关联权限点*/
            $(".acl-role").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                var aclId = $(this).attr("data-id");
                $.ajax({
                    url: "${ctx}/sys/acl/aclRole.json",
                    method: "POST",
                    data: {
                        aclId: aclId
                    },
                    success: function (result) {
                        if (result.code == "0") {
                            console.log(result.data);
                        } else {
                            showMessage("获取角色权限点", result.msg, false);
                        }
                    },
                    error: function (result) {
                        showMessage("获取角色权限点", result.msg, false);
                    }
                });
            });

        };

        /**绑定权限模块列表的点击操作---end*/

        /** 权限模块 新增 start*/
        $(".aclModule-add").click(function (e) {
            e.preventDefault();//阻止默认事件
            e.stopPropagation(); //阻止冒泡事件
            console.log("aclModule-add");
            $("#dialog-aclModule-form").dialog({
                modal: true,
                title: "新增权限模块",
                open: function (event, ui) {
                    $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                    //渲染下拉菜单的内容 默认是value=0的菜单 最大的父级
                    console.log(aclModuleList);
                    optionStr = "<option value=\"0\">-----</option>";
                    recursiveRenderAclModuleSelect(aclModuleList, 1);
                    $("#aclModuleForm")[0].reset(); //阻止第二次进入新增数据页面显示第一次新增的数据
                    $("#parentId").html(optionStr);
                },
                buttons: {
                    "添加": function () {
                        //添加权限模块
                        updateAclModule(true, function () {
                            $("#dialog-aclModule-form").dialog("close"); //成功的回调函数是 关闭对话框
                        }, function (data) {
                            showMessage("新增权限模块", data.msg, false);
                        });
                    },
                    "取消": function () {
                        $("#dialog-aclModule-form").dialog("close");
                    }
                }
            });
        });
        /** 权限模块 新增 end*/
        function updateAclModule(isSaveAclModule, successCallback, failCallback) { //多学会封装函数
            console.log($("#aclModuleForm").serialize());
            console.log($("#aclModuleForm").serializeArray());
            $.ajax({
                url: isSaveAclModule ? "${ctx}/sys/aclModule/save.json" : "${ctx}/sys/aclModule/update.json",
                method: "POST",
                data: $("#aclModuleForm").serialize(),  //serializeArray 返回的是JSON结构的对象 serialize将表单内容序列化成一个字符串
                success: function (result) {
                    if (result.code == "0") {
                        if (successCallback) {
                            successCallback(result)
                        }
                        showMessage("权限列表", result.msg, true);//需要优化提示
                        //添加成功需要重新刷新加载权限列表Tree
                        loadAclModuleTree();
                    } else {
                        if (failCallback) {
                            failCallback(result)
                        }
                    }
                },
                error: function (result) {
                    showMessage("权限模块", result.msg, false);
                }
            })
        }


        /**递归新增权限模块下拉列表的数据 ----start*/
        function recursiveRenderAclModuleSelect(aclModuleList, level) {
            if (aclModuleList != null && aclModuleList.length > 0) {
                $.each(aclModuleList, function (index, aclModule) {
                    aclModuleMap[aclModule.id] = aclModule;
                    var prefix = ""; //分级别  区分父级别和子级别
                    if (level > 1) {
                        for (var i = 1; i < level; i++) {
                            prefix += "--";
                        }
                    }
                    var renderOptionStr = Mustache.render("<option value='{{id}}'>{{name}}</option>", {
                        id: aclModule.id,
                        name: prefix + aclModule.name
                    })
                    optionStr += renderOptionStr;
                    //继续递归下一级选项
                    if (aclModule.sysAclModuleLevelDTOList != null && aclModule.sysAclModuleLevelDTOList.length > 0) {
                        console.log("load childOption")
                        recursiveRenderAclModuleSelect(aclModule.sysAclModuleLevelDTOList, level + 1);
                    }
                });
            }
        }

        /**递归新增权限模块下拉列表的数据----end*/


        /**权限点ACL的操作----start*/

        /**1.权限点ACL新增操作---start*/
        $(".acl-add").click(function (e) {
            e.preventDefault();
            e.stopPropagation();
            $("#dialog-acl-form").dialog({
                modal: true,
                title: "权限模块新增",
                open: function (event, ui) {
                    $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                    //渲染权限模块下拉列表
                    aclOptionStr = "<option value=\"\">-----</option>";
                    recursiveRenderAclModuleOptionSelect(aclModuleList, 1);
                    $("#aclForm")[0].reset();
                    $("#aclModuleSelectId").html(aclOptionStr);
                    $("#aclModuleSelectId").val(lastSelectedId);
                },
                buttons: {
                    "取消": function () {
                        $("#dialog-acl-form").dialog("close");
                    },
                    "保存": function (e) {
                        e.preventDefault();
                        updateAcl(true, function (data) {
                            $("#dialog-acl-form").dialog("close");
                            loadAclList(lastSelectedId);
                        }, function (data) {
                            showMessage("新增权限点", data.msg, false);
                        })
                    }

                }
            });
        });

        /**递归渲染所属权限模块下拉列表*/
        function recursiveRenderAclModuleOptionSelect(aclModuleList, level) {
            if (aclModuleList != null && aclModuleList.length > 0) {
                $.each(aclModuleList, function (index, aclModule) {
                    aclModuleMap[aclModule.id] = aclModule; //缓存数据
                    var prefix = "";
                    if (level > 1) {
                        for (var i = 1; i < level; i++) {
                            prefix += "--";
                        }
                    }
                    var renderOptionStr = Mustache.render("<option value=\"{{id}}\">{{name}}</option>", {
                        id: aclModule.id,
                        name: prefix + aclModule.name
                    });
                    aclOptionStr += renderOptionStr;
                    if (aclModule.sysAclModuleLevelDTOList != null && aclModule.sysAclModuleLevelDTOList.length > 0) {
                        recursiveRenderAclModuleOptionSelect(aclModule.sysAclModuleLevelDTOList, level + 1);
                    }
                })
            }
        };

        /**权限点保存函数*/
        function updateAcl(isSave, successCallBack, failCallBack) {
            var aclData = $("#aclForm").serialize();
            $.ajax({
                url: isSave ? "${ctx}/sys/acl/save.json" : "${ctx}/sys/acl/update.json",
                data: aclData,
                success: function (result) {
                    if (result.code == "0") {
                        if (successCallBack != null) {
                            successCallBack(result);
                        }
                    } else {
                        if (failCallBack != null) {
                            failCallBack(result);
                        }
                    }
                },
                error: function (result) {
                    showMessage("权限模块点", result.msg, true);
                }

            })
            ;
        }

        /**2.权限点ACL新增操作---end*/


        /**权限点ACL的操作----end*/

    });
</script>

</body>
</html>

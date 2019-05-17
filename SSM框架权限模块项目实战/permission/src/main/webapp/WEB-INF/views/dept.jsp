<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>部门管理</title>
    <jsp:include page="/common/backend_common.jsp"/>
    <jsp:include page="/common/page.jsp"/>
</head>
<body class="no-skin" youdao="bind" style="background: white">
<input id="gritter-light" checked="" type="checkbox" class="ace ace-switch ace-switch-5"/>

<div class="page-header">
    <h1>
        用户管理
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            维护部门与用户关系
        </small>
    </h1>
</div>
<div class="main-content-inner">
    <div class="col-sm-3">
        <div class="table-header">
            部门列表&nbsp;&nbsp;
            <a class="green" href="#">
                <i id="dept-add" class="ace-icon fa fa-plus-circle orange bigger-130 dept-add"></i>
            </a>
        </div>
        <div id="deptList">
        </div>
    </div>
    <div class="col-sm-9">
        <div class="col-xs-12">
            <div class="table-header">
                用户列表&nbsp;&nbsp;
                <a class="green" href="#">
                    <i id="user-add" class="ace-icon fa fa-plus-circle orange bigger-130 user-add"></i>
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
                                姓名
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                所属部门
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                邮箱
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                电话
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                状态
                            </th>
                            <th class="sorting_disabled" rowspan="1" colspan="1" aria-label=""></th>
                        </tr>
                        </thead>
                        <tbody id="userList"></tbody>
                    </table>
                    <div class="row" id="userPage">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%--新增部门的dialog 对话框--%>
<div id="dialog-dept-form" style="display: none;">
    <form id="deptForm">
        <table class="table table-striped table-bordered table-hover dataTable no-footer" role="grid">
            <tr>
                <td style="width: 80px;"><label for="parentId">上级部门</label></td>
                <td>
                    <select id="parentId" name="parentId" data-placeholder="选择部门" style="width: 200px;">
                    </select>
                    <input type="hidden" name="id" id="deptId"/>
                </td>
            </tr>
            <tr>
                <td><label for="deptName">名称</label></td>
                <td><input type="text" name="name" id="deptName" value="" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
            <tr>
                <td><label for="deptSeq">顺序</label></td>
                <td><input type="text" name="seq" id="deptSeq" value="" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
            <tr>
                <td><label for="deptRemark">备注</label></td>
                <td><textarea name="remark" id="deptRemark" class="text ui-widget-content ui-corner-all" rows="3"
                              cols="25"></textarea></td>
            </tr>
        </table>
    </form>
</div>
<%--新增系统用户的dialog--%>
<div id="dialog-user-form" style="display: none;">
    <form id="userForm">
        <table class="table table-striped table-bordered table-hover dataTable no-footer" role="grid">
            <tr>
                <td style="width: 80px;"><label for="parentId">所在部门</label></td>
                <td>
                    <select id="deptSelectId" name="deptId" data-placeholder="选择部门" style="width: 200px;">
                        <option value="">请选择部门</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="userName">名称</label></td>
                <input type="hidden" name="id" id="userId"/>
                <td><input type="text" name="username" id="userName" value=""
                           class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="userMail">邮箱</label></td>
                <td><input type="text" name="mail" id="userMail" value="" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
            <tr>
                <td><label for="userTelephone">电话</label></td>
                <td><input type="text" name="telephone" id="userTelephone" value=""
                           class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="userStatus">状态</label></td>
                <td>
                    <select id="userStatus" name="status" data-placeholder="选择状态" style="width: 150px;">
                        <option value="1">有效</option>
                        <option value="0">无效</option>
                        <option value="2">删除</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="userRemark">备注</label></td>
                <td><textarea name="remark" id="userRemark" class="text ui-widget-content ui-corner-all" rows="3"
                              cols="25"></textarea></td>
            </tr>
        </table>
    </form>
</div>

<script id="deptListTemplate" type="x-tmpl-mustache">
    <ol>
                {{#deptList}}
                <li class="dd-item dd2-item dept-name" id="dept_{{id}}" href="javascript:void(0)" data-id="{{id}}">
                    <div class="dd2-content" style="cursor:pointer;">
                    {{name}}
                    <span style="float:right;">
                        <a id ="dept-edit" class="green dept-edit" href="#" data-id="{{id}}" >
                            <i class="ace-icon fa fa-pencil bigger-100"></i>
                        </a>
                        &nbsp;
                        <a id ="dept-delete" class="red dept-delete" href="#" data-id="{{id}}" data-name="{{name}}">
                            <i class="ace-icon fa fa-trash-o bigger-100"></i>
                        </a>
                    </span>
                    </div>
                </li>
            {{/deptList}}
    </ol>










































</script>

<script id="userListTemplate" type="x-tmpl-mustache">
{{#userList}}
<tr role="row" class="user-name odd" data-id="{{id}}"><!--even -->
    <td><a href="#" class="user-edit" data-id="{{id}}">{{username}}</a></td>
    <td>{{showDeptName}}</td>
    <td>{{mail}}</td>
    <td>{{telephone}}</td>
    <td>{{#bold}}{{showStatus}}{{/bold}}</td> <!-- 此处套用函数对status做特殊处理 -->
    <td>
        <div class="hidden-sm hidden-xs action-buttons">
            <a class="green user-edit" href="#" data-id="{{id}}">
                <i class="ace-icon fa fa-pencil bigger-100"></i>
            </a>
            <a class="red user-acl" href="#" data-id="{{id}}">
                <i class="ace-icon fa fa-flag bigger-100"></i>
            </a>
        </div>
    </td>
</tr>
{{/userList}}










































</script>

<script type="text/javascript">

    var deptList; //存储数据
    var deptMap = {};// 存储map格式的部门信息
    var optionStr = ""; //下拉框选项
    var sysUerOptionStr = ""; //系统用户部门下拉
    var lastClickDeptId = -1; //记录点击部门的id
    var userMap = {}; //存储用户信息
    //mustache模板引擎渲染 部门列表
    var deptListTemplate = $("#deptListTemplate").html();
    Mustache.parse(deptListTemplate);

    //mustache模板引擎渲染 用户列表
    var userListTemplate = $("#userListTemplate").html();
    Mustache.parse(userListTemplate);

    //加载部门列表树
    loadDeptTree();
    console.log(deptMap);


    //发ajax请求去拿请求数据 加载tree列表
    function loadDeptTree() {
        $.ajax({
                url: "${ctx}/sys/dept/tree.json",
                type: "GET",
                success: function (result) {
                    if (result.code == "0") {
                        deptList = result.data;
                        var rendered = Mustache.render(deptListTemplate, {deptList: deptList});
                        $("#deptList").html(rendered);
                        //这里只渲染一层，所以要一层一层的去渲染 递归
                        recursiveDeptList(deptList);
                        console.log("1");
                        console.log($("#dept-edit"));
                        bindDeptClick();
                    } else {
                        showMessage("加载部门列表失败", result.msg, false);
                    }
                }
            }
        );
    }

    function recursiveDeptList(deptList) {
        //只有deptList存在 才会继续去显示
        if (deptList != null && deptList.length > 0) {
            $(deptList).each(function (index, dept) {
                deptMap[dept.id] = dept;
                if (dept.sysDeptTreeResponseDtoList != null && dept.sysDeptTreeResponseDtoList.length > 0) {
                    var rendered1 = Mustache.render(deptListTemplate, {deptList: dept.sysDeptTreeResponseDtoList});
                    $("#dept_" + dept.id).append(rendered1);
                    recursiveDeptList(dept.sysDeptTreeResponseDtoList);
                }
            });
        }
    }
    /**
     * 新增部门代码和新增部门业务逻辑
     * */
    /** 部门列表新增======start*/
    $("#dept-add").click(function (e) {
        e.preventDefault();//阻止默认事件
        e.stopPropagation() //阻止冒泡事件
        //点击显示对话框
        $("#dialog-dept-form").dialog({
            modal: true,
            title: "新增部门",
            //打开diaolog加载上级部门选项中的内容
            open: function (envent, ui) {
                console.log($(this).parent())
                //取消掉diaolog上面右边的叉号
                $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                //拼接下拉框的数据
                optionStr = "<option value=\"0\">------</option>";
                //console.log(deptList);
                recursiveRenderDeptSelect(deptList, 1);//level 表示层级关系  刚开始是第一层 接下来就是第二层，第二层则会有前缀
                $("#deptForm")[0].reset();// 这里的reset方法是js的 jQuery对象转js对象 $("#deptForm")[0]  推荐
                $("#parentId").html(optionStr);


            },
            buttons: {
                "添加": function () {
                    updateDept(true, function (data) {
                        $("#dialog-dept-form").dialog("close");
                    }, function (data) {
                        showMessage("新增部门", data.msg, false);
                    });  // 新增跟编辑公用一个方法，加个参数去决定是update部门还是save部门
                },
                "取消": function () {
                    $("#dialog-dept-form").dialog("close");
                }
            }
        })
    });
    //加载下拉框列表
    function recursiveRenderDeptSelect(deptList, level) {
        if (deptList != null && deptList.length > 0) {
            $(deptList).each(function (index, dept) {
                deptMap[dept.id] = dept;
                var blank = ""; //给名字加前缀 区别父子关系
                if (level > 1) {
                    for (var i = 1; i < level; i++) {
                        blank += "..";
                    }
                }
                optionStr += Mustache.render("<option value='{{id}}'>{{name}}</option>", {
                    id: dept.id,
                    name: blank + dept.name
                });
                //继续递归子部门列表
                if (dept.sysDeptTreeResponseDtoList != null && dept.sysDeptTreeResponseDtoList.length > 0) {
                    recursiveRenderDeptSelect(dept.sysDeptTreeResponseDtoList, level + 1);
                }
            });
        }
    }
    //新增部门列表
    function updateDept(isSaveDept, sucessCb, failCb) {
        console.log($("#deptForm").serialize());
        $.ajax({
            url: isSaveDept ? "${ctx}/sys/dept/save.json" : "${ctx}/sys/dept/update.json",
            data: $("#deptForm").serialize(),
            type: "POST",
            success: function (result) {
                if (result.code == "0") {

                    if (sucessCb != null) {
                        sucessCb(result);
                    }
                    showMessage("部门列表", result.msg, true);//需要优化提示
                    //添加成功需要重新刷新加载部门列表Tree
                    loadDeptTree();

                } else {
                    if (failCb != null) {
                        failCb(result);
                    }
                }
            }
        })
    }
    /** 部门列表新增======end*/


    //绑定部门事件,就是渲染的模板上面有编辑和删除2个按钮 需要给其绑定事件，因为是render渲染出来的 跟正常的dom操作有点不一样
    function bindDeptClick() {
        /** 部门列表编辑======start*/
        $(".dept-edit").click(function (e) {
            e.preventDefault();
            e.stopPropagation();
            //console.log("ok");
            //获取deptId，是data_id的值 部门id
            console.log($(this).attr("data-id"));
            var deptId = $(this).attr("data-id");
            $("#dialog-dept-form").dialog({
                modal: true,
                title: "更新部门",
                open: function (event, ui) {
                    //取消掉diaolog右上角的叉号
                    $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                    //给更新的部门数据 展示其值
                    //1.拼接下拉的数据
                    //拼接下拉框的数据
                    optionStr = "<option value=\"0\">------</option>";
                    //console.log(deptList);
                    recursiveRenderDeptSelect(deptList, 1);//level 表示层级关系  刚开始是第一层 接下来就是第二层，第二层则会有前缀
                    $("#deptForm")[0].reset();// 这里的reset方法是js的 jQuery对象转js对象 $("#deptForm")[0]  推荐
                    $("#parentId").html(optionStr);
                    $("#deptId").val(deptId);
                    //待编辑的部门数据 通过页面 data-id属性 绑定的deptI从deptMap中获取
                    var editDept = deptMap[deptId];
                    if (editDept != null) {
                        /**部门列表数据展示
                         * */
                        $("#parentId").val(editDept.parentId);
                        $("#deptName").val(editDept.name);
                        $("#deptSeq").val(editDept.parentId);
                        $("#deptRemark").val(editDept.remark);
                    }
                },
                buttons: {
                    "更新": function (e) {
                        e.preventDefault();
                        updateDept(false, function (data) {
                            $("#dialog-dept-form").dialog("close");
                        }, function (data) {
                            showMessage("新增部门", data.msg, false);
                        });  // 新增跟编辑公用一个方法，加个参数去决定是update部门还是save部门

                    },
                    "取消": function () {
                        $("#dialog-dept-form").dialog("close");
                    }
                }
            })
        });
        /** 部门列表编辑======end*/

        /** 部门列表删除======start*/
        $(".dept-delete").click(function (e) {
            e.preventDefault(); //阻止默认事件
            e.stopPropagation(); //阻止事件冒泡
            var deptId = $(this).attr("data-id");
            var deptName = $(this).attr("data-name");
            if (confirm("确定要删除【" + deptName + "】吗？")) {
                //发请求去做删除处理,逻辑后续处理
                console.log("delete dept : id = " + deptId + " ,deptName = " + deptName);
                $.ajax({
                    url: "${ctx}/sys/dept/delete.json",
                    data: {
                        deptId: deptId
                    },
                    method: "POST",
                    success: function (result) {
                        if (result.code == "0") {
                            showMessage("删除部门", "操作成功", false);
                            loadDeptTree();
                        } else {
                            showMessage("删除部门", result.msg, false);
                        }
                    },
                    error: function (result) {
                        showMessage("删除部门", result.msg, false);
                    }
                });
            }
        });
        /** 部门列表删除======end*/

        /** 部门列表点击操作======start*/
        $(".dept-name").click(function (e) {
            e.preventDefault(); //阻止默认事件
            e.stopPropagation(); //阻止冒泡事件
            console.log($(this));
            var deptId = $(this).attr("data-id");
            handleSelectedDept(deptId);
        });
        /** 部门列表点击操作======start*/
    };

    //操作页面部门点击后样式显示
    function handleSelectedDept(deptId) {
        //先判断上次点击是否存在，存在则移除其样式
        if (lastClickDeptId != -1) {
            var lastDept = $("#dept_" + lastClickDeptId + " .dd2-content:first");
            lastDept.removeClass("btn-yellow");
            lastDept.removeClass("no-hover");
        }
        var currentDept = $("#dept_" + deptId + " .dd2-content:first");
        currentDept.addClass("btn-yellow"); //ace模板样式
        currentDept.addClass("no-hover");
        lastClickDeptId = deptId;
        loadUserList(deptId);
    }

    //加载用户列表
    function loadUserList(deptId) {
        //给每个部门加载用户列表
        var pageSize = $("#pageSize").val(); //每页数据展示
        var pageNo = $("#userPage").val() || 1;//当前页数,没有则默认是第一页
        var url = "${ctx}/sys/user/page.json?deptId=" + deptId;
        //接下来就是要去请求后台去查询数据
        $.ajax({
            url: url,
            data: {
                pageSize: pageSize,
                pageNo: pageNo
            },
            success: function (result) {
                renderUserListAndPage(result, url);
            }
        })
    };
    //渲染用户列表数据  和分页的按钮
    function renderUserListAndPage(result, url) {
        if (result != null && url != null) {
            if (result.code == "0") {//代表请求数据成功
                var rendered = Mustache.render(userListTemplate, {
                    userList: result.data.data,
                    "showDeptName": function () {
                        return deptMap[this.deptId].name
                    },
                    "showStatus": function () {
                        return this.status == 1 ? '有效' : (this.status == 0 ? '无效' : '删除');
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
                $("#userList").html(rendered);
                bindUserClick(); //绑定用户的动作
                //用户的数据存储下来了
                $.each(result.data.data, function (i, user) {
                    userMap[user.id] = user;
                })
            } else {
                $("#userList").html("");
            }
            var pageSize = $("#pageSize").val();
            var pageNo = $("#userPage .pageNo").val() || 1;
            renderPage(url, result.data.total, pageNo, pageSize, result.data.total > 0 ? result.data.data.length : 0, "userPage", renderUserListAndPage);
        } else {
            showMessage("获取部门下用户列表", result.msg, false);
        }
    };

    /** 新增用户列表操作======start*/
    $("#user-add").click(function (e) {
        e.preventDefault()//阻止默认事件;
        console.log("add");
        $("#dialog-user-form").dialog({
            modal: true,
            title: "新增系统用户",
            open: function (event, ui) {
                //去掉右侧dialog自带的叉号
                $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                //接下来的事情就是渲染所在部门下拉框
                optionStr = "<option value=\"\">请选择部门</option>"
                console.log(deptList);
                recursiveRenderDeptSelect(deptList, 1);//level 表示层级关系  刚开始是第一层 接下来就是第二层，第二层则会有前缀
                //保证每次进来都是最新的表单，没有被填写过，则需要把表单重置下reset方法 是js方法
                $("#userForm")[0].reset();
                $("#deptSelectId").html(optionStr);
            },
            buttons: {
                "添加": function (e) {
                    e.preventDefault()//阻止默认事件
                    //新增用户
                    updateUser(true, function (data) {
                        $("#dialog-user-form").dialog("close");
                        loadUserList(lastClickDeptId);
                    }, function (data) {
                        showMessage("新增用户", data.msg, false)
                    });
                },
                "取消": function () {
                    $("#dialog-user-form").dialog("close");
                }
            }
        });
    });
    /** 新增用户列表操作======end*/



    //模板里面绑定操作
    function bindUserClick() {
        /** 编辑用户列表操作======start*/
        $(".user-edit").click(function (e) {
            e.preventDefault();//阻止默认行为
            e.stopPropagation();//阻止冒泡事件
            console.log("edit");
            var userId = $(this).attr("data-id");
            $("#dialog-user-form").dialog({
                modal: true,
                title: "编辑系统用户",
                open: function (event, ui) {
                    //去掉右侧dialog自带的叉号
                    $(".ui-dialog-titlebar-close", $(this).parent()).hide()
                    optionStr = "<option value=\"\">请选择部门</option>";
                    recursiveRenderDeptSelect(deptList, 1);
                    $("#userForm")[0].reset();
                    $("#deptSelectId").html(optionStr);
                    var user = userMap[userId];
                    if (user != null) {
                        $("#userId").val(user.id);
                        $("#deptSelectId").val(user.deptId);
                        $("#userName").val(user.username);
                        $("#userMail").val(user.mail);
                        $("#userTelephone").val(user.telephone);
                        $("#userStatus").val(user.status);
                        $("#userRemark").val(user.remark);
                    }
                },
                buttons: {
                    "更新": function () {
                        updateUser(false, function (data) {
                            $("#dialog-user-form").dialog("close");
                            loadUserList(lastClickDeptId);
                        }, function (data) {
                            showMessage("更新用户", data.msg, false);
                        })
                    },
                    "取消": function () {
                        $("#dialog-user-form").dialog("close");
                    }
                }
            });

        });
        /** 编辑用户列表操作======end*/

        /**查看用户的角色和权限点*/
        $(".user-acl").click(function (e) {
            e.preventDefault();
            e.stopPropagation();
            var userId = $(this).attr("data-id");
            console.log(userId);
            $.ajax({
                url: "${ctx}/sys/user/userAcl",
                data: {
                    userId: userId
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

    }


    function updateUser(isCreate, successCallback, failCallback) {
        //发送ajax请求去新增或者编辑
        $.ajax({
            url: isCreate ? "${ctx}/sys/user/save.json" : "${ctx}/sys/user/update.json",
            type: 'POST',
            data: $("#userForm").serializeArray(),
            success: function (result) {
                if (result.code == "0") {
                    loadDeptTree();//加载部门列表tree
                    if (successCallback) {
                        successCallback(result);
                    }
                } else {
                    if (failCallback) {
                        failCallback(result);
                    }
                }
            }
        })
    }
</script>

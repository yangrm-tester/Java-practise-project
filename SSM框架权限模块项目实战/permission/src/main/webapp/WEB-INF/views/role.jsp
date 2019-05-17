<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
    <title>角色</title>
    <jsp:include page="/common/backend_common.jsp"/>
    <link rel="stylesheet" href="/ztree/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="/assets/css/bootstrap-duallistbox.min.css" type="text/css">
    <script type="text/javascript" src="/ztree/jquery.ztree.all.min.js"></script>
    <script type="text/javascript" src="/assets/js/jquery.bootstrap-duallistbox.min.js"></script>
    <style type="text/css">
        .bootstrap-duallistbox-container .moveall, .bootstrap-duallistbox-container .removeall {
            width: 50%;
        }

        .bootstrap-duallistbox-container .move, .bootstrap-duallistbox-container .remove {
            width: 49%;
        }
    </style>
</head>
<body class="no-skin" youdao="bind" style="background: white">
<input id="gritter-light" checked="" type="checkbox" class="ace ace-switch ace-switch-5"/>
<div class="page-header">
    <h1>
        角色管理
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            维护角色与用户, 角色与权限关系
        </small>
    </h1>
</div>
<div class="main-content-inner">
    <div class="col-sm-3">
        <div class="table-header">
            角色列表&nbsp;&nbsp;
            <a class="green" href="#">
                <i class="ace-icon fa fa-plus-circle orange bigger-130 role-add"></i>
            </a>
        </div>
        <div id="roleList"></div>
    </div>
    <div class="col-sm-9">
        <div class="tabbable" id="roleTab">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a data-toggle="tab" href="#roleAclTab">
                        角色与权限
                    </a>
                </li>
                <li>
                    <a data-toggle="tab" href="#roleUserTab">
                        角色与用户
                    </a>
                </li>
            </ul>
            <div class="tab-content">
                <div id="roleAclTab" class="tab-pane fade in active">
                    <ul id="roleAclTree" class="ztree"></ul>
                    <button class="btn btn-info saveRoleAcl" type="button">
                        <i class="ace-icon fa fa-check bigger-110"></i>
                        保存
                    </button>
                </div>

                <div id="roleUserTab" class="tab-pane fade">
                    <div class="row">
                        <div class="box1 col-md-6">待选用户列表</div>
                        <div class="box1 col-md-6">已选用户列表</div>
                    </div>
                    <select multiple="multiple" size="10" name="roleUserList" id="roleUserList">
                    </select>
                    <div class="hr hr-16 hr-dotted"></div>
                    <button class="btn btn-info saveRoleUser" type="button">
                        <i class="ace-icon fa fa-check bigger-110"></i>
                        保存
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="dialog-role-form" style="display: none;">
    <form id="roleForm">
        <table class="table table-striped table-bordered table-hover dataTable no-footer" role="grid">
            <tr>
                <td><label for="roleName">名称</label></td>
                <td>
                    <input type="text" name="name" id="roleName" value="" class="text ui-widget-content ui-corner-all">
                    <input type="hidden" name="id" id="roleId"/>
                </td>
            </tr>
            <tr>
                <td><label for="roleStatus">状态</label></td>
                <td>
                    <select id="roleStatus" name="status" data-placeholder="状态" style="width: 150px;">
                        <option value="1">可用</option>
                        <option value="0">冻结</option>
                    </select>
                </td>
            </tr>
            <td><label for="roleRemark">备注</label></td>
            <td><textarea name="remark" id="roleRemark" class="text ui-widget-content ui-corner-all" rows="3"
                          cols="25"></textarea></td>
            </tr>
        </table>
    </form>
</div>
<script id="roleListTemplate" type="x-tmpl-mustache">
<ol class="dd-list ">
    {{#roleList}}
        <li class="dd-item dd2-item role-name" id="role_{{id}}" href="javascript:void(0)" data-id="{{id}}">
            <div class="dd2-content" style="cursor:pointer;">
            {{name}}
            <span style="float:right;">
                <a class="green role-edit" href="#" data-id="{{id}}" >
                    <i class="ace-icon fa fa-pencil bigger-100"></i>
                </a>
                &nbsp;
                <a class="red role-delete" href="#" data-id="{{id}}" data-name="{{name}}">
                    <i class="ace-icon fa fa-trash-o bigger-100"></i>
                </a>
            </span>
            </div>
        </li>
    {{/roleList}}
</ol>































































</script>

<script id="selectedUsersTemplate" type="x-tmpl-mustache">
{{#userList}}
    <option value="{{id}}" selected="selected">{{username}}</option>
{{/userList}}































































</script>

<script id="unSelectedUsersTemplate" type="x-tmpl-mustache">
{{#userList}}
    <option value="{{id}}">{{username}}</option>
{{/userList}}































































</script>

<script type="text/javascript">
    $(function () {
        var roleMap = {}; //role角色map
        var lastRoleId = -1; //上次点击的role Id
        var selectFirstTab = true;
        var hasMultiSelect = false;

        // zTree
        <!-- 树结构相关 开始 -->
        var zTreeObj = [];
        var modulePrefix = 'm_';
        var aclPrefix = 'a_';
        var nodeMap = {};

        var setting = {
            check: {
                enable: true,
                chkDisabledInherit: true,
                chkboxType: {"Y": "ps", "N": "ps"}, //auto check 父节点 子节点
                autoCheckTrigger: true
            },
            data: {
                simpleData: {
                    enable: true,
                    rootPId: 0
                }
            },
            callback: {
                onClick: onClickTreeNode
            }
        };


        var roleListTemplate = $("#roleListTemplate").html();
        Mustache.parse(roleListTemplate);
        var selectedUsersTemplate = $("#selectedUsersTemplate").html();
        Mustache.parse(selectedUsersTemplate);
        var unSelectedUsersTemplate = $("#unSelectedUsersTemplate").html();
        Mustache.parse(unSelectedUsersTemplate);
        loadRoleList();

        /**加载用户角色列表---start*/
        function loadRoleList() {
            $.ajax({
                url: "${ctx}/sys/role/list.json",
                success: function (result) {
                    if (result.code == "0") {
                        var roleList = result.data;
                        var roleListRender = Mustache.render(roleListTemplate, {
                            roleList: roleList
                        });
                        $("#roleList").html(roleListRender);
                        bindRoleClick();//绑定点击事件
                        $.each(roleList, function (index, role) {
                            roleMap[role.id] = role;  //存储role数据
                        })
                    } else {
                        showMessage("角色用户列表", "角色用户列表获取失败", true);
                    }
                },
                error: function (result) {
                    showMessage("角色用户列表", "角色用户列表获取失败", true);
                }
            });
        }


        /**加载用户角色列表---end*/

        function bindRoleClick() {
            /**编辑角色用户*/
            $(".role-edit").click(function (e) {
                e.preventDefault();//阻止默认事件
                e.stopPropagation();//阻止冒泡事件
                var roleId = $(this).attr("data-id");
                var role = roleMap[roleId];
                console.log(role);
                $("#dialog-role-form").dialog({
                    modal: true,
                    title: "编辑角色用户",
                    open: function (event, ui) {
                        $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                        if (role != null) {
                            $("#roleName").val(role.name);
                            $("#roleStatus").val(role.status);
                            $("#roleRemark").val(role.remark);
                            $("#roleId").val(role.id);
                        }
                    },
                    buttons: {
                        "取消": function () {
                            $("#dialog-role-form").dialog("close");
                        },
                        "保存": function () {
                            updateRole(false, function (data) {
                                $("#dialog-role-form").dialog("close");
                            }, function (data) {
                                showMessage("角色用户", data.msg, true)
                            })
                        }
                    }
                });
            });
            /**删除角色用户*/
            $(".role-delete").click(function (e) {
                e.preventDefault();//阻止默认事件
                e.stopPropagation();//阻止冒泡事件
                var roleId = $(this).attr("data-id");
                var role = roleMap[roleId];
                if (confirm("确定要删除【" + role.name + "】角色用户吗？")) {
                    $.ajax({
                        url: "${ctx}/sys/role/delete.json?id=" + role.id,
                        success: function (result) {
                            if (result.code == "0") {
                                showMessage("角色用户删除", result.msg, true);
                                loadRoleList();
                            } else {
                                showMessage("角色用户删除", result.msg, true);
                            }
                        },
                        error: function (result) {
                            showMessage("角色用户删除", "操作异常", true);
                        }
                    });
                }
            });

            /**角色用户点击变色事件*/
            $(".role-name").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                var roleId = $(this).attr("data-id");
                handleSelectedRole(roleId);
            });
        }

        function handleSelectedRole(roleId) {
            if (roleId != null && lastRoleId != -1) {
                var lastRole = $("#role_" + lastRoleId + " .dd2-content:first");
                lastRole.removeClass("btn-yellow");
                lastRole.removeClass("no-hover");
            }
            var currentRole = $("#role_" + roleId + " .dd2-content:first");
            currentRole.addClass("btn-yellow");
            currentRole.addClass("no-hover");
            lastRoleId = roleId;
            $('#roleTab a:first').trigger('click');
            if (selectFirstTab) {
                loadRoleAcl(roleId);
            }
        }

        /**加载权限点和角色之间的关系*/
        function loadRoleAcl(roleId) {
            if (roleId == -1) {
                return;
            }
            $.ajax({
                url: "${ctx}/sys/role/roleAclTree.json",
                data: {
                    roleId: roleId
                },
                method: "POST",
                success: function (result) {
                    if (result != null && result.code == "0") {
                        console.log(result.data);
                        //页面渲染树列表
                        var aclModuleDTOList = result.data;
                        renderRoleTree(aclModuleDTOList);
                    } else {
                        showMessage("获取权限点列表", result.msg, true);
                    }

                },
                error: function (result) {
                    showMessage("获取权限点列表", result.msg, true);
                }
            });
        }

        /**页面渲染权限点tree*/
        function renderRoleTree(aclModuleDTOList) {
            if (aclModuleDTOList != null && aclModuleDTOList.length > 0) {
                zTreeObj = [];
                recursivePrepareTreeData(aclModuleDTOList);
                for (var key in nodeMap) {
                    zTreeObj.push(nodeMap[key])
                }
                console.log(zTreeObj);
                $.fn.zTree.init($("#roleAclTree"), setting, zTreeObj)
            }
        }

        function onClickTreeNode(event, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("roleAclTree");
            zTree.expandNode(treeNode);

        }

        /**获取选中的权限点id*/
        function getSelectAclId() {
            var aclTreeObj = $.fn.zTree.getZTreeObj("roleAclTree");
            var aclNodes = aclTreeObj.getCheckedNodes(true);
            var selectedAcl = "";
            for (var i = 0; i < aclNodes.length; i++) {
                if (aclNodes[i].id.startsWith(aclPrefix)) {
                    selectedAcl += "," + aclNodes[i].dataId;
                }
            }
            return selectedAcl.length > 0 ? selectedAcl.substring(1) : selectedAcl;
        }

        function recursivePrepareTreeData(aclModuleDTOList) {
            if (aclModuleDTOList != null && aclModuleDTOList.length > 0) {
                // prepare nodeMap
                $.each(aclModuleDTOList, function (index, aclModuleDTO) {

                    var hasChecked = false;
                    //准备权限点的数据
                    var sysAclDTOList = aclModuleDTO.sysAclDTOList;
                    if (sysAclDTOList != null && sysAclDTOList.length > 0) {
                        $.each(sysAclDTOList, function (index, sysAclDTO) {
                            zTreeObj.push({
                                id: aclPrefix + sysAclDTO.id,
                                pId: modulePrefix + sysAclDTO.aclModuleId,
                                name: sysAclDTO.name + ((sysAclDTO.type == 1) ? "(菜单)" : ((sysAclDTO.type == 2) ? "(按钮)" : "")),
                                chkDisabled: !sysAclDTO.hasAcl,
                                checked: sysAclDTO.checked,
                                dataId: sysAclDTO.id
                            })
                            if (sysAclDTO.checked) {
                                hasChecked = true;
                            }
                        });
                    }
                    //权限模块
                    //判断权限模块 或者权限点不为空
                    if ((aclModuleDTO.sysAclModuleLevelDTOList != null && aclModuleDTO.sysAclModuleLevelDTOList.length > 0) || (aclModuleDTO.sysAclDTOList != null && aclModuleDTO.sysAclDTOList.length > 0)) {
                        nodeMap[modulePrefix + aclModuleDTO.id] = {
                            id: modulePrefix + aclModuleDTO.id,
                            pId: modulePrefix + aclModuleDTO.parentId,
                            name: aclModuleDTO.name,
                            open: hasChecked
                        };
                        var tempAclModule = nodeMap[modulePrefix + aclModuleDTO.id];
                        while (hasChecked && tempAclModule) {
                            if (tempAclModule) {
                                nodeMap[tempAclModule.id] = {
                                    id: tempAclModule.id,
                                    pId: tempAclModule.pId,
                                    name: tempAclModule.name,
                                    open: true
                                }
                            }
                            tempAclModule = nodeMap[tempAclModule.pId];
                        }
                    }
                    recursivePrepareTreeData(aclModuleDTO.sysAclModuleLevelDTOList);
                });
            }
        }

        /**角色用户新增----start*/
        $(".role-add").click(function (e) {
            e.preventDefault();//阻止默认事件
            e.stopPropagation();//阻止冒泡事件
            $("#dialog-role-form").dialog({
                modal: true,
                title: "角色用户添加",
                open: function (event, ui) {
                    $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                    $("#roleForm")[0].reset(); //表单重置
                },
                buttons: {
                    "取消": function () {
                        $("#dialog-role-form").dialog("close");
                    },
                    "保存": function (data) {
                        updateRole(true, function (data) {
                            $("#dialog-role-form").dialog("close");
                        }, function (data) {
                            showMessage("角色用户", data.msg, true);
                        })
                    }
                }
            })
        });
        /**角色用户新增----end*/

        /**用户新增或编辑方法---start*/
        function updateRole(isSave, successCallBack, failCallBack) {
            var data = $("#roleForm").serialize();
            $.ajax({
                url: isSave ? "${ctx}/sys/role/save.json" : "${ctx}/sys/role/update.json",
                data: data,
                success: function (result) {
                    if (result.code == "0") {
                        if (successCallBack) {
                            successCallBack()
                        }
                        showMessage("角色用户", result.msg, true);
                        loadRoleList();
                    } else {
                        if (failCallBack) {
                            failCallBack(result);
                        }
                    }
                },
                error: function (result) {
                    showMessage("角色用户", result.msg, true);
                }
            });
        }

        /**用户新增或编辑方法---end*/


        /**角色权限点关系保存操作---start*/

        $(".saveRoleAcl").click(function (e) {
            e.preventDefault();
            if (lastRoleId == -1) {
                showMessage("保存角色与权限点的关系", "请现在左侧选择需要操作的角色", false);
                return;
            }
            $.ajax({
                url: "${ctx}/sys/role/saveRoleAcl.json",
                data: {
                    roleId: lastRoleId,
                    aclIds: getSelectAclId()
                },
                method: "POST",
                success: function (result) {
                    if (result.code == "0") {
                        loadRoleAcl(lastRoleId);
                        showMessage("保存角色与权限点的关系", "操作成功", false);
                    } else {
                        showMessage("保存角色与权限点的关系", result.msg, false);
                    }
                },
                error: function (result) {
                    showMessage("保存角色与权限点的关系", result.msg, false);
                }
            });
        });

        /**角色权限点关系保存操作---end*/


        /**tab之间切换操作*/
        $("#roleTab a[data-toggle='tab']").on("shown.bs.tab", function (e) {
            if (lastRoleId == -1) {
                showMessage("加载角色关系", "请先在左侧选择操作的角色", false);
                return;
            }
            if (e.target.getAttribute("href") == '#roleAclTab') {
                selectFirstTab = true;
                loadRoleAcl(lastRoleId);
            } else {
                selectFirstTab = false;
                loadRoleUser(lastRoleId);
            }
        });

        /**加载角色用户数据*/
        function loadRoleUser(selectedRoleId) {
            $.ajax({
                url: "${ctx}/sys/role/getUserList.json",
                data: {
                    roleId: selectedRoleId
                },
                method: "POST",
                success: function (result) {
                    if (result.code == 0) {
                        console.log(result.data);
                        //渲染待选用户列表
                        var unSelectedUserList = result.data.unselectedUserList;

                        var selectedUserList = result.data.selectedUserList;

                        var renderUnSelectedUserList = Mustache.render(unSelectedUsersTemplate, {
                            userList: unSelectedUserList
                        });
                        var renderSelectedUserList = Mustache.render(selectedUsersTemplate, {
                            userList: selectedUserList
                        });
                        $("#roleUserList").html(renderUnSelectedUserList + renderSelectedUserList);
                        if (!hasMultiSelect) {
                            $('select[name="roleUserList"]').bootstrapDualListbox({
                                showFilterInputs: false,
                                moveOnSelect: false,
                                infoText: false
                            });
                            hasMultiSelect = true;
                        } else {
                            $('select[name="roleUserList"]').bootstrapDualListbox('refresh', true);
                        }
                    } else {
                        showMessage("加载角色用户数据", result.msg, false);
                    }
                },
                error: function (result) {
                    showMessage("加载角色用户数据", result.msg, false);
                }
            });
        }

        /**保存用户角色数据*/
        $(".saveRoleUser").click(function (e) {
            e.preventDefault();//阻止默认事件
            if (lastRoleId == -1) {
                showMessage("保存角色与用户的关系", "请现在左侧选择需要操作的角色", false);
                return;
            }
            console.log($("#roleUserList").val());
            $.ajax({
                url: "${ctx}/sys/role/saveRoleUser.json",
                data: {
                    roleId: lastRoleId,
                    userIds: $("#roleUserList").val() ? $("#roleUserList").val().join(",") : ""
                },
                success: function (result) {
                    if (result.code == "0") {
                        loadRoleUser(lastRoleId);
                        showMessage("权限角色关系", "操作成功", true)
                    } else {
                        showMessage("权限角色关系", result.msg, true)
                    }
                }
                ,
                error: function (result) {
                    showMessage("权限角色关系", result.msg, true)
                }
            })
            ;
        });
    })

</script>
</body>
</html>

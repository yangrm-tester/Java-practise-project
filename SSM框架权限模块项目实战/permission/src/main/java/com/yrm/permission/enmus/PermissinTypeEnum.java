package com.yrm.permission.enmus;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className PermissinTypeEnum
 * @createTime 2019年05月15日 16:56:00
 */
public enum PermissinTypeEnum {
    /**
     * 部门
     **/
    DEPT("dept", 1),
    USER("user", 2),
    ACL_MODULE("aclModule", 3),
    ACL("acl", 4),
    ROLE("role", 5),
    ROLE_ACL("roleAcl", 6),
    ROLE_USER("roleUser", 7);

    /**
     * 权限类型名字
     */
    private String permissionTypeName;

    /**
     * 权限类型编码
     */
    private Integer permissionTypeCode;

    PermissinTypeEnum(String permissinTypeName, Integer permissionTypeCode) {
        this.permissionTypeName = permissinTypeName;
        this.permissionTypeCode = permissionTypeCode;
    }


    public String getPermissinTypeName() {
        return permissionTypeName;
    }

    public void setPermissinTypeName(String permissionTypeName) {
        this.permissionTypeName = permissionTypeName;
    }

    public Integer getPermissionTypeCode() {
        return permissionTypeCode;
    }

    public void setPermissionTypeCode(Integer permissionTypeCode) {
        this.permissionTypeCode = permissionTypeCode;
    }
}

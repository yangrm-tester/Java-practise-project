package com.yrm.permission.enmus;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className ErrorCodeEnum
 * @createTime 2019年03月22日 16:45:00
 */
public enum ErrorCodeEnum {
    /**
     * success 成功
     */
    SUCCESS("0", "success"),
    ILLGEAL_PARMS("1001", "request params error"),
    /**
     * dept errorcode
     */
    SAME_LEVEL_EXISTS_SAME_DEPT_ERROR("1002", "同一层级下存在相同名称的部门"),
    SYSDEPT_INSERT_ERROR("1003", "insert sysDept error"),
    SYSDEPT_SAVE_ERROR("1004", "save sysDept error"),
    TREE_LIST_SHOW_ERROR("1005", "tree list show error"),
    UPDATE_SYSDEPT_ERROR("1006", "sysDept update error"),
    UPDATE_SYSDEPT_NOT_EXISTS("1007", "sysDept is not exists"),
    DELETE_SYSDEPT_ERROR("1008", "delete sysDept error"),
    SYSDEPT_NOT_EXIST_ERROR("1009", "sysDept not exist error"),
    EXIST_CHILD_SYSDEPET_ERROR("1010", "dept has child dept error"),
    SYSDEPT_HAS_USER_ERROR("1011", "dept has user error"),


    /**
     * user errorcode
     */
    USER_SAVE_ERROR("2003", "save user error"),
    PHONE_IS_OCCUPIED("2004", "telephone is occupied"),
    MAIL_IS_OCCUPIED("2005", "mail is occupied"),
    USER_NOT_EXISTS("2006", "user is not exists"),
    USER_UPDATE_ERROR("2007", "update user error"),
    SYSDEPT_NOT_EXISTS("2008", "dept is not exists,save user error"),

    /**
     * pageData errorcode
     */
    PAGE_DATA_ERROR("3001", "get pageDate error"),

    /**
     * aclModule errorcode
     */
    ACL_MODULE_SAVE_ERROR("4001", "aclModule save error"),
    ACL_MODULE_UPDATE_ERROR("4002", "aclModule update error"),
    ACL_MODULE_DELETE_ERROR("4006", "aclModule delete error"),
    SAME_LEVEL_EXISTS_SAME_ACL_MODULE_ERROR("4003", "save level exists same acl module"),
    UPDATE_ACL_MODULE_NOT_EXISTS("4004", "update aclModule is not exists"),
    GET_ACL_MODULE_TREE_LIST_ERROR("4005", "get aclModule tree list error"),
    DELETE_ACL_MODULE_NOT_EXISTS("4007", "delete aclModule is not exists"),
    ACL_MODULE_HAS_CHILD_ERROR("4008", " aclModule has child error"),
    ACL_MODULE_HAS_ACL_ERROR("4009", " aclModule has acl error"),

    /**
     * acl errorcode
     */
    ACL_SAVE_ERROR("5001", "acl save error"),
    SAME_ACL_IN_SAME_ACLMODULE_ERROR("5002", "same acl in the same aclModule"),
    ACL_DB_INSERT_ERROR("5003", "insert db acl error"),
    ACL_UPDATE_ERROR("5004", "update acl error"),
    NEED_UPDATE_ACL_NOT_EXIST("5005", "acl is not exist"),
    ACL_DB_UPDATE_ERROR("5006", "update db acl error"),
    ACL_DELETE_ERROR("5007", "delete acl error"),
    NEED_DELET_ACL_NOT_EXIST("5008", "delete acl is not exist"),
    ACL_DB_DELETE_ERROR("5009", "delete db acl error"),
    ACL_PAGE_DATA_ERROR("5010", "get page data error"),

    /**
     * role errorcode
     */
    ROLE_SAVE_ERROR("6001", "role save error"),
    ROLE_UPDATE_ERROR("6002", "role update error"),
    ROLE_DELETE_ERROR("6003", "role delete error"),
    ROLE_EXIST_ERROR("6003", "role exist error"),
    ROLE_INSERT_DB_ERROR("6004", "role insert db error"),
    ROLE_NOT_EXIST_ERROR("6005", "role not exist error"),
    ROLE_UPDATE_DB_ERROR("6006", "role update db error"),
    ROLE_GET_LIST_ERROR("6007", "get roleList error"),
    ROLE_DELETE_DB_ERROR("6008", "role delete db error"),

    /**
     * role tree errorcode
     */
    USER_NOT_LOGIN_ERROR("7001", "user is not login error"),
    GET_ACL_TREE_ERROR("7002", "get acl tree error"),


    /**
     * 权限角色关系 errorcode
     */
    SAVE_ROLE_ACL_ERROR("8001", "save role acl error"),


    GET_ROLE_USER_ERROR("8002", "get role user list error"),

    SAVE_ROLE_USER_ERROR("8003", "save role user error"),

    GET_ACLS_ROLES_ERROR("8004", "get aclList roleList error"),
    GET_USERS_ROLES_ERROR("8005", "get userList roleList error"),

    /**
     * 日志 errorcode
     */
    SAVE_DEPT_LOG_ERROR("9001", "save dept log error"),
    SAVE_USER_LOG_ERROR("9002", "save user log error"),
    SAVE_ACL_MODULE_LOG_ERROR("9003", "save aclModule log error"),
    SAVE_ACL_LOG_ERROR("9004", "save acl log error"),
    SAVE_ROLE_LOG_ERROR("9005", "save role log error"),
    SAVE_ROLE_ACL_LOG_ERROR("9006", "save role-acl log error"),
    SAVE_ROLE_USER_LOG_ERROR("9007", "save role-user log error"),

    SAVE_LOG_ILLEGAL_PARAMS_ERROR("9008", "save  log  params error"),
    GET_SYSLOG_PAGE_DATA_ERROR("9009","get sysLog pageData error"),
    SEARCH_TIME_FORMAT_ERROR("9010","search time format error");


    /**
     * 错误码
     */
    private String code;
    /**
     * 错误信息
     */
    private String errorMessage;

    ErrorCodeEnum(String code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

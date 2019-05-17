package com.yrm.permission.service.impl;

import com.yrm.permission.bean.PageQuery;
import com.yrm.permission.bean.PageResult;
import com.yrm.permission.common.RequestHolder;
import com.yrm.permission.dao.SysLogMapper;
import com.yrm.permission.dto.SysLogRequestDTO;
import com.yrm.permission.dto.SysLogSearchDTO;
import com.yrm.permission.enmus.ErrorCodeEnum;
import com.yrm.permission.enmus.PermissinTypeEnum;
import com.yrm.permission.entity.*;
import com.yrm.permission.exception.PermissionException;
import com.yrm.permission.service.SysLogService;
import com.yrm.permission.util.IpUtil;
import com.yrm.permission.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysLogServiceImpl
 * @createTime 2019年05月15日 17:13:00
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    private static final Logger logger = LoggerFactory.getLogger(SysLogServiceImpl.class);

    private static final Integer RECOVER_STATUS = 1;

    private static final Integer NOT_RECOVER_STATUS = 0;

    private static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final ThreadLocal<SimpleDateFormat> simpleDateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>();


    @Autowired
    private SysLogMapper sysLogMapper;

    private SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormatThreadLocal.get();
    }

    @Override
    public void saveDeptLog(SysDept before, SysDept after) throws Exception {
        logger.info("Method [saveDeptLog] call start .Input params===>[before={},after={}]", JsonUtil.obj2String(before), JsonUtil.obj2String(after));
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(PermissinTypeEnum.DEPT.getPermissionTypeCode());
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonUtil.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonUtil.obj2String(after));
        sysLog.setOperator(RequestHolder.getSysUser().getUsername());
        sysLog.setOperateIp(IpUtil.getIpAttr(RequestHolder.getHttpServletRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(RECOVER_STATUS);
        int insertSysLogCount = sysLogMapper.insertSelective(sysLog);
        logger.info("Method [saveDeptLog] call end .Results===>[insertSysLogCount={}]", insertSysLogCount);
        if (insertSysLogCount <= 0) {
            throw new PermissionException(ErrorCodeEnum.SAVE_DEPT_LOG_ERROR);
        }
    }

    @Override
    public void saveUserLog(SysUser before, SysUser after) throws Exception {
        logger.info("Method [saveUserLog] call start .Input params===>[before={},after={}]", JsonUtil.obj2String(before), JsonUtil.obj2String(after));
        SysLogWithBLOBs sysLogWithBLOBs = packageLog(before, after);
        int insertSysLogCount = sysLogMapper.insertSelective(sysLogWithBLOBs);
        logger.info("Method [saveUserLog] call end .Results===>[insertSysLogCount={}]", insertSysLogCount);
        if (insertSysLogCount <= 0) {
            throw new PermissionException(ErrorCodeEnum.SAVE_USER_LOG_ERROR);
        }
    }

    @Override
    public void saveAclModuleLog(SysAclModule before, SysAclModule after) throws Exception {
        logger.info("Method [saveAclModuleLog] call start .Input params===>[before={},after={}]", JsonUtil.obj2String(before), JsonUtil.obj2String(after));
        SysLogWithBLOBs sysLogWithBLOBs = packageLog(before, after);
        int insertSysLogCount = sysLogMapper.insertSelective(sysLogWithBLOBs);
        logger.info("Method [saveAclModuleLog] call end .Results===>[insertSysLogCount={}]", insertSysLogCount);
        if (insertSysLogCount <= 0) {
            throw new PermissionException(ErrorCodeEnum.SAVE_ACL_MODULE_LOG_ERROR);
        }
    }

    @Override
    public void saveAclLog(SysAcl before, SysAcl after) throws Exception {
        logger.info("Method [saveAclLog] call start .Input params===>[before={},after={}]", JsonUtil.obj2String(before), JsonUtil.obj2String(after));
        SysLogWithBLOBs sysLogWithBLOBs = packageLog(before, after);
        int insertSysLogCount = sysLogMapper.insertSelective(sysLogWithBLOBs);
        logger.info("Method [saveAclLog] call end .Results===>[insertSysLogCount={}]", insertSysLogCount);
        if (insertSysLogCount <= 0) {
            throw new PermissionException(ErrorCodeEnum.SAVE_ACL_LOG_ERROR);
        }
    }

    @Override
    public void saveRoleLog(SysRole before, SysRole after) throws Exception {
        logger.info("Method [saveRoleLog] call start .Input params===>[before={},after={}]", JsonUtil.obj2String(before), JsonUtil.obj2String(after));
        SysLogWithBLOBs sysLogWithBLOBs = packageLog(before, after);
        int insertSysLogCount = sysLogMapper.insertSelective(sysLogWithBLOBs);
        logger.info("Method [saveRoleLog] call end .Results===>[insertSysLogCount={}]", insertSysLogCount);
        if (insertSysLogCount <= 0) {
            throw new PermissionException(ErrorCodeEnum.SAVE_ROLE_LOG_ERROR);
        }
    }

    @Override
    public PageResult<SysLogWithBLOBs> getPageData(SysLogRequestDTO sysLogRequestDTO, PageQuery pageQuery) throws Exception {
        logger.info("Method [getPageData] call start. Input params===>[sysLogRequestDTO={},pageQuery={}]", JsonUtil.obj2String(sysLogRequestDTO), JsonUtil.obj2String(pageQuery));
        //方法代码行数太多 注意封装...
        if (sysLogRequestDTO == null && pageQuery == null) {
            throw new PermissionException(ErrorCodeEnum.ILLGEAL_PARMS);
        }
        //参数封装 校验下时间日期的格式
        SysLogSearchDTO sysLogSearchDTO = SysLogSearchDTO.packageSysLogSearchDTO(sysLogRequestDTO);
        String fromTime = sysLogRequestDTO.getFromTime();
        if (StringUtils.isNotBlank(fromTime)) {
            Date fromDate = checkTime(fromTime);
            if (fromDate == null) {
                throw new PermissionException(ErrorCodeEnum.SEARCH_TIME_FORMAT_ERROR);
            }
            sysLogSearchDTO.setFromTime(fromDate);
        }
        String toTime = sysLogRequestDTO.getToTime();
        if (StringUtils.isNotBlank(toTime)) {
            Date toDate = checkTime(toTime);
            if (toDate == null) {
                throw new PermissionException(ErrorCodeEnum.SEARCH_TIME_FORMAT_ERROR);
            }
            sysLogSearchDTO.setToTime(toDate);
        }
        //处理分页数据
        Integer pageNo = pageQuery.getPageNo();
        Integer pageSize = pageQuery.getPageSize();
        pageNo = pageNo == null ? Integer.valueOf(1) : pageNo;
        pageSize = pageSize == null ? Integer.valueOf(10) : pageSize;
        //总数
        int totalCount = sysLogMapper.countBySysLogRequestDTO(sysLogSearchDTO);

        //总页码数
        int totalPageNo = totalCount % pageSize == 0 ? totalCount / pageSize : (int) Math.floor(totalCount / pageSize) + 1;

        int startIndex = (pageNo - 1) * pageSize;
        //当前页数量
        List<SysLogWithBLOBs> sysLogWithBLOBsList = sysLogMapper.getCurrentPageData(sysLogSearchDTO, startIndex, pageSize);

        PageResult<SysLogWithBLOBs> pageResult = new PageResult<SysLogWithBLOBs>();
        pageResult.setTotal(totalCount);
        pageResult.setTotalPageNo(totalPageNo);
        pageResult.setData(sysLogWithBLOBsList);
        logger.info("Method [getPageData] call end. Results===>[pageResult={}]", JsonUtil.obj2String(pageResult));
        return pageResult;
    }

    /**
     * @Param: [time]
     * @return: boolean
     * @Description:
     */

    private Date checkTime(String time) {
        SimpleDateFormat simpleDateFormat = simpleDateFormatThreadLocal.get();
        Date date;
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(TIME_PATTERN);
            simpleDateFormatThreadLocal.set(simpleDateFormat);
        }
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException pe) {
            logger.error("time convert error .Error===>[{}]", pe.getMessage());
            return null;
        }
        return date;
    }


    /**
     * 封装log 公共代码
     *
     * @throws :
     * @Param: [before, after]
     * @return: void
     */

    private SysLogWithBLOBs packageLog(Object before, Object after) throws PermissionException {
        if (before == null && after == null) {
            throw new PermissionException(ErrorCodeEnum.SAVE_LOG_ILLEGAL_PARAMS_ERROR);
        }
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setOldValue(before == null ? "" : JsonUtil.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonUtil.obj2String(after));
        sysLog.setOperator(RequestHolder.getSysUser().getUsername());
        sysLog.setOperateIp(IpUtil.getIpAttr(RequestHolder.getHttpServletRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(RECOVER_STATUS);

        /**
         * SysDept
         * */
        if (before instanceof SysDept || after instanceof SysDept) {
            sysLog.setType(PermissinTypeEnum.DEPT.getPermissionTypeCode());
            sysLog.setTargetId(after == null ? ((SysDept) before).getId() : ((SysDept) after).getId());
        }
        /**
         * SysDept
         * */
        else if (before instanceof SysUser || after instanceof SysUser) {
            sysLog.setType(PermissinTypeEnum.USER.getPermissionTypeCode());
            sysLog.setTargetId(after == null ? ((SysUser) before).getId() : ((SysUser) after).getId());
        }
        /**SysAclModule*/
        else if (before instanceof SysAclModule || after instanceof SysAclModule) {
            sysLog.setType(PermissinTypeEnum.ACL_MODULE.getPermissionTypeCode());
            sysLog.setTargetId(after == null ? ((SysAclModule) before).getId() : ((SysAclModule) after).getId());
        }
        /**SysAcl*/
        else if (before instanceof SysAcl || after instanceof SysAcl) {
            sysLog.setType(PermissinTypeEnum.ACL.getPermissionTypeCode());
            sysLog.setTargetId(after == null ? ((SysAcl) before).getId() : ((SysAcl) after).getId());
        }
        /**SysRole*/
        else if (before instanceof SysRole || after instanceof SysRole) {
            sysLog.setType(PermissinTypeEnum.ROLE.getPermissionTypeCode());
            sysLog.setTargetId(after == null ? ((SysRole) before).getId() : ((SysRole) after).getId());
        }
        return sysLog;
    }
}

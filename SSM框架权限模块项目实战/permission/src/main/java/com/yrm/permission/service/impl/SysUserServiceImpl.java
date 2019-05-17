package com.yrm.permission.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.yrm.permission.Constant.Constant;
import com.yrm.permission.bean.PageQuery;
import com.yrm.permission.bean.PageResult;
import com.yrm.permission.common.RequestHolder;
import com.yrm.permission.dao.*;
import com.yrm.permission.dto.AclListAndRoleListDTO;
import com.yrm.permission.dto.SysAclDTO;
import com.yrm.permission.dto.SysAclModuleLevelDTO;
import com.yrm.permission.dto.SysUserRequestDTO;
import com.yrm.permission.enmus.ErrorCodeEnum;
import com.yrm.permission.entity.SysAcl;
import com.yrm.permission.entity.SysDept;
import com.yrm.permission.entity.SysRole;
import com.yrm.permission.entity.SysUser;
import com.yrm.permission.exception.PermissionException;
import com.yrm.permission.service.SysAclModuleService;
import com.yrm.permission.service.SysLogService;
import com.yrm.permission.service.SysUserService;
import com.yrm.permission.util.IpUtil;
import com.yrm.permission.util.JsonUtil;
import com.yrm.permission.util.Md5Util;
import com.yrm.permission.util.PasswordUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysUserServiceImpl
 * @createTime 2019年04月03日 14:42:00
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    private static final Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    private static final String COMMON_PASSWORD = "123456";


    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleAclMapper sysRoleAclMapper;

    @Autowired
    private SysAclMapper sysAclMapper;

    @Autowired
    private SysAclModuleService sysAclModuleService;

    @Autowired
    private SysLogService sysLogService;

    @Override
    public void saveSysUser(SysUserRequestDTO sysUserRequestDTO) throws Exception {
        logger.info("Method [saveSysUser] call start. Input params===>[sysUserRequestDTO={}]", JsonUtil.obj2String(sysUserRequestDTO));
        //save 之前需要校验
        if (!SysUserRequestDTO.checkParam(sysUserRequestDTO)) {
            throw new PermissionException(ErrorCodeEnum.ILLGEAL_PARMS.getCode(), ErrorCodeEnum.ILLGEAL_PARMS.getErrorMessage());
        }
        //校验手机号码是否已存在
        if (checkPhoneExists(sysUserRequestDTO.getTelephone(), sysUserRequestDTO.getId())) {
            throw new PermissionException(ErrorCodeEnum.PHONE_IS_OCCUPIED.getCode(), ErrorCodeEnum.PHONE_IS_OCCUPIED.getErrorMessage());
        }
        //校验邮箱是否已存在
        if (checkMailExists(sysUserRequestDTO.getMail(), sysUserRequestDTO.getId())) {
            throw new PermissionException(ErrorCodeEnum.MAIL_IS_OCCUPIED.getCode(), ErrorCodeEnum.MAIL_IS_OCCUPIED.getErrorMessage());
        }
        SysUser sysUser = SysUser.adpat(sysUserRequestDTO);
        //继续补充其他参数 password
        String encryptedPassword = Md5Util.encrypt(PasswordUtil.generatePassword());

        //方便测试  这块密码写死
        String encryptedPassword1 = Md5Util.encrypt(COMMON_PASSWORD);

        //所属部门校验
        Integer deptId = sysUserRequestDTO.getDeptId();
        SysDept sysDept = sysDeptMapper.selectByPrimaryKey(deptId);
        if (sysDept == null) {
            throw new PermissionException(ErrorCodeEnum.SYSDEPT_NOT_EXISTS.getCode(), ErrorCodeEnum.SYSDEPT_NOT_EXISTS.getErrorMessage());
        }
        sysUser.setPassword(encryptedPassword);
        sysUser.setOperateIp(IpUtil.getIpAttr(RequestHolder.getHttpServletRequest()));
        sysUser.setOperator(RequestHolder.getSysUser().getUsername());
        sysUser.setOperateTime(new Date());
        int insertSysUserCount = sysUserMapper.insertSelective(sysUser);
        sysLogService.saveUserLog(null, sysUser);
        if (insertSysUserCount > 0) {
            //todo send email
        } else {
            throw new PermissionException(ErrorCodeEnum.USER_SAVE_ERROR.getCode(), ErrorCodeEnum.USER_SAVE_ERROR.getErrorMessage());
        }
        logger.info("Method [saveSysUser] call end. Results===>[insertSysUserCount={}]", insertSysUserCount);
    }

    @Override
    public void updateSysUser(SysUserRequestDTO sysUserRequestDTO) throws Exception {
        logger.info("Method [updateSysUser] call start. Input params===>[sysUserRequestDTO={}]", JsonUtil.obj2String(sysUserRequestDTO));
        if (!SysUserRequestDTO.checkParam(sysUserRequestDTO)) {
            throw new PermissionException(ErrorCodeEnum.ILLGEAL_PARMS.getCode(), ErrorCodeEnum.ILLGEAL_PARMS.getErrorMessage());
        }
        //校验待更新的数据存在与否
        SysUser beforeSysUser = sysUserMapper.selectByPrimaryKey(sysUserRequestDTO.getId());
        if (beforeSysUser == null) {
            throw new PermissionException(ErrorCodeEnum.USER_NOT_EXISTS.getCode(), ErrorCodeEnum.USER_NOT_EXISTS.getErrorMessage());
        }
        //校验手机号码是否已存在
        if (checkPhoneExists(sysUserRequestDTO.getTelephone(), sysUserRequestDTO.getId())) {
            throw new PermissionException(ErrorCodeEnum.PHONE_IS_OCCUPIED.getCode(), ErrorCodeEnum.PHONE_IS_OCCUPIED.getErrorMessage());
        }
        //校验邮箱是否已存在
        if (checkMailExists(sysUserRequestDTO.getMail(), sysUserRequestDTO.getId())) {
            throw new PermissionException(ErrorCodeEnum.MAIL_IS_OCCUPIED.getCode(), ErrorCodeEnum.MAIL_IS_OCCUPIED.getErrorMessage());
        }
        //所属部门校验
        Integer deptId = sysUserRequestDTO.getDeptId();
        SysDept sysDept = sysDeptMapper.selectByPrimaryKey(deptId);
        if (sysDept == null) {
            throw new PermissionException(ErrorCodeEnum.SYSDEPT_NOT_EXISTS.getCode(), ErrorCodeEnum.SYSDEPT_NOT_EXISTS.getErrorMessage());
        }
        //封装需要更新的user
        SysUser afterSysUser = SysUser.adpat(sysUserRequestDTO);
        afterSysUser.setId(sysUserRequestDTO.getId());
        afterSysUser.setOperateIp(IpUtil.getIpAttr(RequestHolder.getHttpServletRequest()));
        afterSysUser.setOperator(RequestHolder.getSysUser().getUsername());
        afterSysUser.setOperateTime(new Date());
        int updateSysUserCount = sysUserMapper.updateByPrimaryKeySelective(afterSysUser);
        sysLogService.saveUserLog(beforeSysUser, afterSysUser);
        if (updateSysUserCount <= 0) {
            throw new PermissionException(ErrorCodeEnum.USER_SAVE_ERROR.getCode(), ErrorCodeEnum.USER_SAVE_ERROR.getErrorMessage());
        }
        logger.info("Method [updateSysUser] call end. Results===>[afterSysUser={},updateSysUserCount={}]", JsonUtil.obj2String(afterSysUser), updateSysUserCount);
    }

    @Override
    public SysUser findUserByKeyword(String keyword) throws Exception {
        logger.info("Method [findUserByKeyword] call start,Input params==>[keyword={}]", keyword);
        if (StringUtils.isBlank(keyword)) {
            throw new PermissionException(ErrorCodeEnum.ILLGEAL_PARMS.getCode(), ErrorCodeEnum.ILLGEAL_PARMS.getErrorMessage());
        }
        SysUser sysUser = sysUserMapper.findByKeyword(keyword);
        logger.info("Method [findUserByKeyword] call end .Results===>[sysUser={}]", JsonUtil.obj2String(sysUser));
        return sysUser != null ? sysUser : null;
    }

    @Override
    public PageResult getPageData(Integer deptId, PageQuery pageQuery) throws Exception {
        logger.info("Method [getPageData] call start .Input params===>[deptId={},pageQuery={}]", deptId, JsonUtil.obj2String(pageQuery));
        if (deptId == null || pageQuery == null) {
            throw new PermissionException(ErrorCodeEnum.ILLGEAL_PARMS.getCode(), ErrorCodeEnum.ILLGEAL_PARMS.getErrorMessage());
        }
        //定义当前页面码
        Integer pageNo = pageQuery.getPageNo();
        pageNo = (pageNo == null ? Constant.DEFAULT_PAGENO : pageNo);
        //获得当前页的数量
        Integer pageSize = pageQuery.getPageSize();
        pageSize = (pageSize == null ? Constant.DEFAULT_PAGESIZE : pageSize);
        // 获取当前页面数据的起始位置
        Integer startIndex = (pageNo - 1) * pageSize;
        //获取当前页面的数据
        List<SysUser> currentPageSysUserList = sysUserMapper.getCurrentPageData(deptId, startIndex, pageSize);
        //获取deptId的总量
        int totalCount = sysUserMapper.getTotalCountByDeptId(deptId);
        //总页码数
        int totalPageNo = totalCount % pageSize == 0 ? totalCount / pageSize : (int) Math.floor(totalCount / pageSize) + 1;
        PageResult pageResult = new PageResult();
        pageResult.setTotal(totalCount);
        pageResult.setData(currentPageSysUserList);
        pageResult.setTotalPageNo(totalPageNo);
        logger.info("Method [getPageData] call end; Results===>[pageResult={}]", JsonUtil.obj2String(pageResult));
        return pageResult;
    }


    @Override
    public AclListAndRoleListDTO getAclListAndRoleList(Integer userId) throws Exception {
        logger.info("Method [getAclListAndRoleList] call start.Input params===>[userId={}]", userId);
        if (userId == null) {
            throw new PermissionException(ErrorCodeEnum.ILLGEAL_PARMS);
        }
        AclListAndRoleListDTO aclListAndRoleListDTO = new AclListAndRoleListDTO();
        //get roleList
        List<SysRole> sysRoleList = getRoleListByUserId(userId);
        aclListAndRoleListDTO.setSysRoleList(sysRoleList);
        List<SysAclModuleLevelDTO> sysAclModuleLevelDTOList = getSysAclModuleLevelDTOList(userId);
        aclListAndRoleListDTO.setSysAclModuleLevelDTOList(sysAclModuleLevelDTOList);
        logger.info("Method [getAclListAndRoleList] call end.Results===>[aclListAndRoleListDTO={}]", JsonUtil.obj2String(aclListAndRoleListDTO));
        return aclListAndRoleListDTO;
    }

    private List<SysAclModuleLevelDTO> getSysAclModuleLevelDTOList(Integer userId) throws Exception {
        logger.info("Method [getSysAclModuleLevelDTOList] call start.Input params===>[userId={}]", userId);
        if (userId == null) {
            throw new PermissionException(ErrorCodeEnum.ILLGEAL_PARMS);
        }
        //通过userid拿到roleId
        List<Integer> roleIdListByUserId = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(roleIdListByUserId)) {
            return Collections.EMPTY_LIST;
        }
        //通过roleId 拿到 aclId
        List<Integer> aclIdListByRoleIdList = sysRoleAclMapper.getAclListByRoleIdList(roleIdListByUserId);
        if (CollectionUtils.isEmpty(aclIdListByRoleIdList)) {
            return Collections.EMPTY_LIST;
        }
        //在通过aclId 拿到sysAcl
        List<SysAcl> sysAcls = sysAclMapper.getAclListByAclIds(aclIdListByRoleIdList);
        if (CollectionUtils.isEmpty(sysAcls)) {
            return Collections.EMPTY_LIST;
        }
        //组织成树结构
        logger.info("Method [getSysAclModuleLevelDTOList] call end.Results===>[sysAcls={}]", JsonUtil.obj2String(sysAcls));
        return sysAclListToTree(sysAcls);
    }

    private List<SysAclModuleLevelDTO> sysAclListToTree(List<SysAcl> sysAcls) throws Exception {
        logger.info("Method [sysAclListToTree] call start.Input params===>[sysAcls={}]", JsonUtil.obj2String(sysAcls));
        if (CollectionUtils.isEmpty(sysAcls)) {
            throw new PermissionException(ErrorCodeEnum.ILLGEAL_PARMS);
        }
        //封装成sysAclDTO去处理
        List<SysAclDTO> sysAclDTOList = new ArrayList<SysAclDTO>();
        for (SysAcl sysAcl : sysAcls) {
            SysAclDTO sysAclDTO = SysAclDTO.adaptSysAclDTO(sysAcl);
            sysAclDTO.setHasAcl(true);
            sysAclDTO.setChecked(true);
            sysAclDTOList.add(sysAclDTO);
        }
        //拿到权限模块的树形结构
        List<SysAclModuleLevelDTO> sysAclModuleLevelDTOList = sysAclModuleService.sysAclModuleTree();
        Multimap<Integer, SysAclDTO> sysAclDTOMap = ArrayListMultimap.create();
        for (SysAclDTO sysAclDTO : sysAclDTOList) {
            sysAclDTOMap.put(sysAclDTO.getAclModuleId(), sysAclDTO);
        }
        //把权限点绑到权限模块下面
        bindSysAclDTOToSysAclModuleDTO(sysAclModuleLevelDTOList, sysAclDTOMap);
        logger.info("Method [sysAclListToTree] call end.Results===>[sysAclModuleLevelDTOList={}]", JsonUtil.obj2String(sysAclModuleLevelDTOList));
        return sysAclModuleLevelDTOList;
    }

    private void bindSysAclDTOToSysAclModuleDTO(List<SysAclModuleLevelDTO> sysAclModuleLevelDTOList, Multimap<Integer, SysAclDTO> sysAclDTOMap) {
        logger.info("Method [bindSysAclDTOToSysAclModuleDTO] call start.Input params===>[sysAclModuleLevelDTOList={},sysAclDTOMap={}]", JsonUtil.obj2String(sysAclModuleLevelDTOList), JsonUtil.obj2String(sysAclDTOMap));
        if (CollectionUtils.isEmpty(sysAclModuleLevelDTOList) || sysAclDTOMap == null || sysAclDTOMap.size() == 0) {
            throw new PermissionException(ErrorCodeEnum.ILLGEAL_PARMS);
        }
        for (SysAclModuleLevelDTO sysAclModuleLevelDTO : sysAclModuleLevelDTOList) {
            List<SysAclDTO> sysAclDTOS = (List<SysAclDTO>) sysAclDTOMap.get(sysAclModuleLevelDTO.getId());
            //排序下 按照seq
            Collections.sort(sysAclDTOS, new Comparator<SysAclDTO>() {
                @Override
                public int compare(SysAclDTO o1, SysAclDTO o2) {
                    return o1.getSeq() - o2.getSeq();
                }
            });
            sysAclModuleLevelDTO.setSysAclDTOList(sysAclDTOS);
            //递归子模块绑定权限点
            if (CollectionUtils.isNotEmpty(sysAclModuleLevelDTO.getSysAclModuleLevelDTOList())) {
                bindSysAclDTOToSysAclModuleDTO(sysAclModuleLevelDTO.getSysAclModuleLevelDTOList(), sysAclDTOMap);
            }
        }
    }


    private List<SysRole> getRoleListByUserId(Integer userId) throws PermissionException {
        logger.info("Method [getRoleListByUserId] call start .Input params===>[userId={}]", userId);
        if (userId == null) {
            throw new PermissionException(ErrorCodeEnum.ILLGEAL_PARMS);
        }
        List<Integer> roleIdListByUserId = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(roleIdListByUserId)) {
            return Collections.EMPTY_LIST;
        }
        List<SysRole> sysRoleList = sysRoleMapper.getSysRoleListByIdList(roleIdListByUserId);
        logger.info("Method [getRoleListByUserId] call end .Results===>[roleIdListByUserId={}.sysRoleList={}]", JsonUtil.obj2String(roleIdListByUserId), JsonUtil.obj2String(sysRoleList));
        return CollectionUtils.isEmpty(sysRoleList) ? Collections.EMPTY_LIST : sysRoleList;
    }


    private boolean checkMailExists(String mail, Integer userId) {
        int userByMailCount = sysUserMapper.findByMail(mail, userId);
        return userByMailCount > 0 ? true : false;

    }

    private boolean checkPhoneExists(String telephone, Integer userId) {
        int userByPhoneCount = sysUserMapper.findByPhone(telephone, userId);
        return userByPhoneCount > 0 ? true : false;
    }
}

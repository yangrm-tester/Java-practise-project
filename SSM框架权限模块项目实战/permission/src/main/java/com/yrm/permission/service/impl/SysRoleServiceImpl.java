package com.yrm.permission.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.yrm.permission.Constant.Constant;
import com.yrm.permission.common.RequestHolder;
import com.yrm.permission.dao.*;
import com.yrm.permission.dto.SysAclDTO;
import com.yrm.permission.dto.SysAclModuleLevelDTO;
import com.yrm.permission.dto.SysRoleRequestDTO;
import com.yrm.permission.dto.SysUserResponseDTO;
import com.yrm.permission.enmus.ErrorCodeEnum;
import com.yrm.permission.entity.*;
import com.yrm.permission.exception.PermissionException;
import com.yrm.permission.service.SysAclModuleService;
import com.yrm.permission.service.SysLogService;
import com.yrm.permission.service.SysRoleService;
import com.yrm.permission.util.IpUtil;
import com.yrm.permission.util.JsonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.Role;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysRoleServiceImpl
 * @createTime 2019年05月03日 16:47:00
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    private static final Logger logger = LoggerFactory.getLogger(SysRoleServiceImpl.class);

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleAclMapper sysRoleAclMapper;

    @Autowired
    private SysAclMapper sysAclMapper;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    private SysAclModuleService sysAclModuleService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysLogService sysLogService;

    @Override
    public void save(SysRoleRequestDTO sysRoleRequestDTO, HttpServletRequest request) throws Exception {
        logger.info("Method [save] call start.Input params===>{sysRoleRequestDTO=[]}", JsonUtil.obj2String(sysRoleRequestDTO));
        if (!SysRoleRequestDTO.checkParam(sysRoleRequestDTO)) {
            throw new PermissionException(ErrorCodeEnum.ILLGEAL_PARMS);
        }
        //校验相同的数据 id name 保存木有id
        if (sysRoleMapper.getSysRoleByNameAndId(sysRoleRequestDTO.getName(), sysRoleRequestDTO.getId()) != null) {
            throw new PermissionException(ErrorCodeEnum.ROLE_EXIST_ERROR);
        }
        //封装sysRole
        SysRole sysRole = SysRoleRequestDTO.packageSysRole(sysRoleRequestDTO);
        sysRole.setOperator(RequestHolder.getSysUser().getUsername());
        sysRole.setOperateTime(new Date());
        sysRole.setOperateIp(IpUtil.getIpAttr(request));
        int insertSysRoleCount = sysRoleMapper.insertSelective(sysRole);
        sysLogService.saveRoleLog(null, sysRole);
        logger.info("Method [save] call end.Results===>{insertSysRoleCount=[]}", insertSysRoleCount);
        if (insertSysRoleCount <= 0) {
            throw new PermissionException(ErrorCodeEnum.ROLE_INSERT_DB_ERROR);
        }
    }

    @Override
    public void update(SysRoleRequestDTO sysRoleRequestDTO, HttpServletRequest request) throws Exception {
        logger.info("Method [update] call start .Input params===>[sysRoleRequestDTO={}]", JsonUtil.obj2String(sysRoleRequestDTO));
        if (!SysRoleRequestDTO.checkParam(sysRoleRequestDTO)) {
            throw new PermissionException(ErrorCodeEnum.ILLGEAL_PARMS);
        }
        //待更新的数据是否存在
        SysRole sysRoleInDB = sysRoleMapper.selectByPrimaryKey(sysRoleRequestDTO.getId());
        if (sysRoleInDB == null) {
            throw new PermissionException(ErrorCodeEnum.ROLE_NOT_EXIST_ERROR);
        }
        //校验相同的数据 id name 保存木有id
        if (sysRoleMapper.getSysRoleByNameAndId(sysRoleRequestDTO.getName(), sysRoleRequestDTO.getId()) != null) {
            throw new PermissionException(ErrorCodeEnum.ROLE_EXIST_ERROR);
        }
        SysRole updateSysRole = SysRoleRequestDTO.packageSysRole(sysRoleRequestDTO);
        updateSysRole.setOperateIp(IpUtil.getIpAttr(request));
        updateSysRole.setOperator(RequestHolder.getSysUser().getUsername());
        updateSysRole.setOperateTime(new Date());
        int updateSysRoleCount = sysRoleMapper.updateByPrimaryKeySelective(updateSysRole);
        sysLogService.saveRoleLog(sysRoleInDB, updateSysRole);
        if (updateSysRoleCount <= 0) {
            throw new PermissionException(ErrorCodeEnum.ROLE_UPDATE_DB_ERROR);
        }
    }

    @Override
    public List<Role> getRoleList() {
        logger.info("Method [getRoleList] call start. Input params===>[{}]");
        List<Role> sysRoleList = sysRoleMapper.getSysRoleList();
        logger.info("Method [getRoleList] call end .Results===>[sysRoleList={}]", JsonUtil.obj2String(sysRoleList));
        return sysRoleList;
    }

    @Override
    public void delete(Integer id) throws Exception {
        logger.info("Method [delete] call start .Input params===>[id={}]", id);
        if (id == null) {
            throw new PermissionException(ErrorCodeEnum.ILLGEAL_PARMS);
        }
        //校验数据存在性
        SysRole sysRoleInDB = sysRoleMapper.selectByPrimaryKey(id);
        if (sysRoleInDB == null) {
            throw new PermissionException(ErrorCodeEnum.ROLE_NOT_EXIST_ERROR);
        }
        int deleteSysRoleCount = sysRoleMapper.deleteByPrimaryKey(id);
        sysLogService.saveRoleLog(sysRoleInDB, null);
        logger.info("Method [delete] call start .Results===>[deleteSysRoleCount={}]", deleteSysRoleCount);
        if (deleteSysRoleCount <= 0) {
            throw new PermissionException(ErrorCodeEnum.ROLE_DELETE_DB_ERROR);
        }
    }

    @Override
    public List<SysAclModuleLevelDTO> getRoleAclTree(Integer roleId) throws Exception {
        logger.info("Method [getRoleAclTree] call start.Input params===>[roleId={}]", roleId);
        //当前用户的权限点 在角色权限列表可能暂时没有什么用
        List<SysAcl> userAclList = getCurrentUserAclList();
        //角色用户的权限点
        List<SysAcl> roleAclList = getAclListByRoleId(roleId);
        //获取所有的权限集合
        List<SysAcl> allSysAclList = sysAclMapper.getAllAclList();
        List<SysAclDTO> sysAclDTOList = new ArrayList<SysAclDTO>();
        Set<Integer> userAclSet = listToSet(userAclList);
        Set<Integer> roleAclSet = listToSet(roleAclList);
        for (SysAcl sysAcl : allSysAclList) {
            SysAclDTO sysAclDTO = SysAclDTO.adaptSysAclDTO(sysAcl);
            sysAclDTO.setHasAcl(Boolean.FALSE);
            sysAclDTO.setChecked(Boolean.FALSE);
            //用户权限点在所有权限点集合中的 就表示用户拥有该权限点 可以把hasAcl 设置为true
            if (userAclSet.contains(sysAcl.getId())) {
                sysAclDTO.setHasAcl(Boolean.TRUE);
            }
            //角色权限点在所有权限点集合中的 就表示角色拥有该权限点 可以把checked 钩钩打上 设置为true
            if (roleAclSet.contains(sysAcl.getId())) {
                sysAclDTO.setChecked(Boolean.TRUE);
            }
            sysAclDTOList.add(sysAclDTO);
        }
        logger.info("Method [getRoleAclTree] call end.Results===>[userAclList={},roleAclList={},allSysAclList={},sysAclDTOList={},userAclSet={},roleAclSet={}]", JsonUtil.obj2String(userAclList), JsonUtil.obj2String(roleAclList), JsonUtil.obj2String(allSysAclList), JsonUtil.obj2String(sysAclDTOList), JsonUtil.obj2String(userAclSet), JsonUtil.obj2String(roleAclSet));
        return aclListToTree(sysAclDTOList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRoleAcl(Integer roleId, String aclIds) throws Exception {
        logger.info("Method [saveRoleAcl] call start.Input params===>[roleId={},aclIds={}]", roleId, aclIds);
        if (roleId == null) {
            throw new PermissionException(ErrorCodeEnum.ILLGEAL_PARMS);
        }
        //check role is exist
        if (sysRoleMapper.selectByPrimaryKey(roleId) == null) {
            throw new PermissionException(ErrorCodeEnum.ROLE_NOT_EXIST_ERROR);
        }
        if (StringUtils.isNotBlank(aclIds)) {
            List<Integer> aclIdList = handAclIdsToList(aclIds);
            //这里需要判断下 是不是所有的权限点和角色之间都是存在关系的 只是没有存在关系的才去存库，有关系的表明已经存库了，不需要去插表（多思考的业务性的东西）
            List<Integer> roleIdList = new ArrayList<Integer>();
            roleIdList.add(roleId);
            List<Integer> aclIdListInDB = sysRoleAclMapper.getAclListByRoleIdList(roleIdList);
            if (CollectionUtils.isEmpty(aclIdListInDB)) {
                //直接存
                insertSysRoleAcl(aclIdList, roleId);
                logger.info("Method [saveRoleAcl] call end.Results===>[aclIdListInDB={}]", JsonUtil.obj2String(aclIdListInDB));
            } else {
                //先删除
                deleteSysRoleAcl(roleId);
                //后存储
                insertSysRoleAcl(aclIdList, roleId);
                logger.info("Method [saveRoleAcl] call end.Results===>[aclIdListInDB={}]", JsonUtil.obj2String(aclIdListInDB));
            }
        } else {
            deleteSysRoleAcl(roleId);
            logger.info("Method [saveRoleAcl] call end.Results===>[aclIds={}]", aclIds);
        }
    }

    @Override
    public SysUserResponseDTO getUserList(Integer roleId) throws Exception {
        logger.info("Method [getUserList] call start.Input params===>[roleId={}]", roleId);
        if (roleId == null) {
            throw new PermissionException(ErrorCodeEnum.ILLGEAL_PARMS);
        }
        //与对应角色关联的用户列表ID
        List<Integer> userIdListByRoleId = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        //所有用户列表
        List<SysUser> allSysUserList = sysUserMapper.getAllUserList();
        //未与该角色相关联的用户列表
        List<SysUser> unSelectedUserList = new ArrayList<SysUser>();
        List<SysUser> selectedUserList;
        SysUserResponseDTO sysUserResponseDTO = new SysUserResponseDTO();
        if (CollectionUtils.isEmpty(userIdListByRoleId)) {
            sysUserResponseDTO.setUnselectedUserList(allSysUserList);
            sysUserResponseDTO.setSelectedUserList(Collections.EMPTY_LIST);
            logger.info("Method [getUserList] call end.Results===>[sysUserResponseDTO={}]", JsonUtil.obj2String(sysUserResponseDTO));
            return sysUserResponseDTO;
        } else {
            //通过用户列表ID获取用户信息
            selectedUserList = sysUserMapper.getUserListByUserIds(userIdListByRoleId);
            sysUserResponseDTO.setSelectedUserList(selectedUserList);
            Set<Integer> selectSysUserIdSets = new HashSet<Integer>();
            //这块代码写的比较复杂 肯定能有优化的地方 需要提高 集合相关操作
            //set封装
            for (SysUser selectedSysUser : selectedUserList) {
                selectSysUserIdSets.add(selectedSysUser.getId());
            }
            //比较set 全部的set包含已选择的set则不能被添加到未选中的集合中
            for (SysUser sysUser : allSysUserList) {
                if (!selectSysUserIdSets.contains(sysUser.getId())) {
                    unSelectedUserList.add(sysUser);
                }
            }
            sysUserResponseDTO.setUnselectedUserList(unSelectedUserList);
            logger.info("Method [getUserList] call end.Results===>[sysUserResponseDTO={}]", JsonUtil.obj2String(sysUserResponseDTO));
            return sysUserResponseDTO;
        }
    }

    @Override
    public void saveRoleUser(Integer roleId, String userIds) throws Exception {
        logger.info("Method [saveRoleUser] call start.Input params===>[roleId={},userIds={}]", roleId, userIds);
        if (roleId == null) {
            throw new PermissionException(ErrorCodeEnum.ILLGEAL_PARMS);
        }
        //check role is exist
        if (sysRoleMapper.selectByPrimaryKey(roleId) == null) {
            throw new PermissionException(ErrorCodeEnum.ROLE_NOT_EXIST_ERROR);
        }
        if (StringUtils.isNotBlank(userIds)) {
            List<Integer> userIdList = handAclIdsToList(userIds);

            List<Integer> userIdListInDB = sysRoleUserMapper.getUserIdListByRoleId(roleId);
            if (CollectionUtils.isEmpty(userIdListInDB)) {
                //直接存
                insertSysRoleUser(userIdList, roleId);
                logger.info("Method [saveRoleAcl] call end.Results===>[userIdListInDB={}]", JsonUtil.obj2String(userIdListInDB));
            } else {
                //先删除
                deleteSysRoleUser(roleId);
                //后存储
                insertSysRoleUser(userIdList, roleId);
                logger.info("Method [saveRoleAcl] call end.Results===>[aclIdListInDB={}]", JsonUtil.obj2String(userIdListInDB));
            }
        } else {
            deleteSysRoleUser(roleId);
            logger.info("Method [saveRoleAcl] call end.Results===>[aclIds={}]", userIds);
        }

    }

    @Override
    public boolean hasUrlAcl(String servletPath) throws Exception {
        logger.info("Method [hasUrlAcl] call start .Input params===>[servletPath={}]", servletPath);
        if (StringUtils.isBlank(servletPath)) {
            throw new PermissionException(ErrorCodeEnum.ILLGEAL_PARMS);
        }
        //通过url查询权限集合点
        List<SysAcl> sysAclListByUrl = sysAclMapper.getSysAclByUrl(servletPath);
        if (CollectionUtils.isEmpty(sysAclListByUrl)) {
            return true;
        }
        //获取当前用的权限
        List<SysAcl> userAclList = getCurrentUserAclList();
        Set<Integer> userAclIdSets = Sets.newConcurrentHashSet();
        for (SysAcl sysAcl : userAclList) {
            userAclIdSets.add(sysAcl.getId());
        }
        boolean hasValidAcl = false;
        // 规则：只要有一个权限点有权限，那么我们就认为有访问权限
        for (SysAcl acl : sysAclListByUrl) {
            // 判断一个用户是否具有某个权限点的访问权限  // 权限点无效
            if (acl == null || acl.getStatus() != 1) {
                continue;
            }
            hasValidAcl = true;
            if (userAclIdSets.contains(acl.getId())) {
                return true;
            }
        }
        if (!hasValidAcl) {
            return true;
        }
        return false;
    }

    private void insertSysRoleUser(List<Integer> userIdList, Integer roleId) {
        List<SysRoleUser> sysRoleUserList = new ArrayList<SysRoleUser>();
        for (Integer userId : userIdList) {
            SysRoleUser sysRoleUser = new SysRoleUser();
            sysRoleUser.setUserId(userId);
            sysRoleUser.setRoleId(roleId);
            sysRoleUser.setOperator(RequestHolder.getSysUser().getUsername());
            sysRoleUser.setOperateTime(new Date());
            sysRoleUser.setOperateIp(Constant.OPERATOR_IP);
            sysRoleUserList.add(sysRoleUser);
        }
        sysRoleUserMapper.batchInsert(sysRoleUserList);
    }

    private void deleteSysRoleUser(Integer roleId) {
        sysRoleUserMapper.deleteByRoleId(roleId);
    }

    private void insertSysRoleAcl(List<Integer> aclIdList, Integer roleId) {
        List<SysRoleAcl> sysRoleAclList = new ArrayList<SysRoleAcl>();
        for (Integer aclId : aclIdList) {
            SysRoleAcl sysRoleAcl = new SysRoleAcl();
            sysRoleAcl.setAclId(aclId);
            sysRoleAcl.setRoleId(roleId);
            sysRoleAcl.setOperator(RequestHolder.getSysUser().getUsername());
            sysRoleAcl.setOperateTime(new Date());
            sysRoleAcl.setOperateIp(Constant.OPERATOR_IP);
            sysRoleAclList.add(sysRoleAcl);
        }
        sysRoleAclMapper.batchInsert(sysRoleAclList);

    }

    private void deleteSysRoleAcl(Integer roleId) {
        sysRoleAclMapper.deleteByRoleId(roleId);
    }


    private List<Integer> handAclIdsToList(String ids) throws Exception {
        if (StringUtils.isBlank(ids)) {
            throw new PermissionException(ErrorCodeEnum.ILLGEAL_PARMS);
        }
        String[] split = ids.split(",");
        Integer[] integerArray = new Integer[split.length];
        for (int i = 0; i < split.length; i++) {
            integerArray[i] = Integer.valueOf(split[i]);
        }
        return Arrays.asList(integerArray);
    }

    private List<SysAclModuleLevelDTO> aclListToTree(List<SysAclDTO> sysAclDTOList) throws Exception {
        logger.info("Method [aclListToTree] call start.Input params===>[sysAclDTOList={}]", JsonUtil.obj2String(sysAclDTOList));
        if (CollectionUtils.isEmpty(sysAclDTOList)) {
            return Collections.EMPTY_LIST;
        }
        List<SysAclModuleLevelDTO> aclModuleLevelList = sysAclModuleService.sysAclModuleTree();
        Multimap<Integer, SysAclDTO> moduleIdAclMap = ArrayListMultimap.create();
        for (SysAclDTO acl : sysAclDTOList) {
            if (acl.getStatus() == 1) {
                moduleIdAclMap.put(acl.getAclModuleId(), acl);
            }
        }
        bindAclListWithOrder(aclModuleLevelList, moduleIdAclMap);
        logger.info("Method [aclListToTree] call end.Results===>[aclModuleLevelList={}]", JsonUtil.obj2String(aclModuleLevelList));
        return aclModuleLevelList;
    }

    /**
     * 权限点放到权限模块下面
     *
     * @throws Exception
     * @Param: [aclModuleLevelList, moduleIdAclMap]
     * @return: void
     */

    private void bindAclListWithOrder(List<SysAclModuleLevelDTO> aclModuleLevelList, Multimap<Integer, SysAclDTO> moduleIdAclMap) {
        logger.info("Method [bindAclListWithOrder] call start.Input params===>[aclModuleLevelList={},moduleIdAclMap={}]", JsonUtil.obj2String(aclModuleLevelList), JsonUtil.obj2String(moduleIdAclMap));
        if (CollectionUtils.isEmpty(aclModuleLevelList) || moduleIdAclMap == null || moduleIdAclMap.size() == 0) {
            return;
        }
        for (SysAclModuleLevelDTO sysAclModuleLevelDTO : aclModuleLevelList) {
            List<SysAclDTO> aclDtoList = (List<SysAclDTO>) moduleIdAclMap.get(sysAclModuleLevelDTO.getId());
            if (CollectionUtils.isNotEmpty(aclDtoList)) {
                Collections.sort(aclDtoList, new Comparator<SysAclDTO>() {
                    @Override
                    public int compare(SysAclDTO o1, SysAclDTO o2) {
                        return o1.getSeq() - o2.getSeq();
                    }
                });
                sysAclModuleLevelDTO.setSysAclDTOList(aclDtoList);
            }
            //递归去把权限点放到模块下面
            bindAclListWithOrder(sysAclModuleLevelDTO.getSysAclModuleLevelDTOList(), moduleIdAclMap);
        }
        logger.info("Method [bindAclListWithOrder] call end.Results===>[sysAclModuleLevelDTO={}]", JsonUtil.obj2String(aclModuleLevelList));
    }

    private Set<Integer> listToSet(List<SysAcl> sysAclList) {
        if (sysAclList == null) {
            return Collections.EMPTY_SET;
        }
        Set<Integer> sets = new TreeSet<Integer>();
        for (SysAcl sysAcl : sysAclList) {
            sets.add(sysAcl.getId());
        }
        return sets;
    }

    private List<SysAcl> getAclListByRoleId(Integer roleId) throws Exception {
        logger.info("Method [getAclListByRoleId] call start .Input params===>[roleId={}]", roleId);
        if (roleId == null) {
            throw new PermissionException(ErrorCodeEnum.ILLGEAL_PARMS);
        }
        //先去找权限点Id
        List<Integer> roleIdList = new ArrayList<Integer>();
        roleIdList.add(roleId);
        List<Integer> aclList = sysRoleAclMapper.getAclListByRoleIdList(roleIdList);
        if (CollectionUtils.isEmpty(aclList)) {
            return Collections.EMPTY_LIST;
        }
        List<SysAcl> sysAclList = sysAclMapper.getAclListByAclIds(aclList);
        logger.info("Method [getAclListByRoleId] call end .Results===>[aclList={},sysAclList={}]", JsonUtil.obj2String(aclList), JsonUtil.obj2String(sysAclList));
        return sysAclList == null ? Collections.EMPTY_LIST : sysAclList;
    }

    private List<SysAcl> getCurrentUserAclList() throws Exception {
        logger.info("Method [getCurrentUserAclList] call start .Input params===>[{}]");
        SysUser sysUser = RequestHolder.getSysUser();
        List<SysAcl> sysAclListSuperAdmin = null;
        if (isSuperAdmin()) {
            //超级管理员拥有所有的权限点
            sysAclListSuperAdmin = sysAclMapper.getAllAclList();
            return sysAclListSuperAdmin == null ? Collections.<SysAcl>emptyList() : sysAclListSuperAdmin;
        }
        if (sysUser == null) {
            throw new PermissionException(ErrorCodeEnum.USER_NOT_LOGIN_ERROR);
        }
        Integer userId = sysUser.getId();
        //查到用户对应的角色集合
        List<Integer> userRoleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(userRoleIdList)) {
            return Collections.EMPTY_LIST;
        }
        //拿到角色id去查对应的权限点的Id
        List<Integer> userAclIdList = sysRoleAclMapper.getAclListByRoleIdList(userRoleIdList);
        if (CollectionUtils.isEmpty(userAclIdList)) {
            return Collections.EMPTY_LIST;
        }
        //权限id去找sysAcl
        List<SysAcl> sysAclList = sysAclMapper.getAclListByAclIds(userAclIdList);
        logger.info("Method [getCurrentUserAclList] call start .Results===>[sysAclListSuperAdmin={},userRoleIdList={},userAclIdList={},sysAclList={}]", JsonUtil.obj2String(sysAclListSuperAdmin), JsonUtil.obj2String(userRoleIdList), JsonUtil.obj2String(userAclIdList), JsonUtil.obj2String(sysAclList));
        return sysAclList == null ? Collections.EMPTY_LIST : sysAclList;
    }

    /**
     * 超级管理员判断条件
     */
    private Boolean isSuperAdmin() {
        return true;
    }

}

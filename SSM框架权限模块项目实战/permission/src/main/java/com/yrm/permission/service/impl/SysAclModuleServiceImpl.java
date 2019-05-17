package com.yrm.permission.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.yrm.permission.Constant.Constant;
import com.yrm.permission.bean.PageQuery;
import com.yrm.permission.bean.PageResult;
import com.yrm.permission.common.RequestHolder;
import com.yrm.permission.dao.SysAclMapper;
import com.yrm.permission.dao.SysAclModuleMapper;
import com.yrm.permission.dto.SysAclModuleLevelDTO;
import com.yrm.permission.dto.SysAclModuleRequestDTO;
import com.yrm.permission.enmus.ErrorCodeEnum;
import com.yrm.permission.entity.SysAcl;
import com.yrm.permission.entity.SysAclModule;
import com.yrm.permission.exception.PermissionException;
import com.yrm.permission.service.SysAclModuleService;
import com.yrm.permission.service.SysLogService;
import com.yrm.permission.util.JsonUtil;
import com.yrm.permission.util.LevelUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysAclModuleServiceImpl
 * @createTime 2019年04月09日 20:34:00
 */
@Service
public class SysAclModuleServiceImpl implements SysAclModuleService {

    private static final Logger logger = LoggerFactory.getLogger(SysAclModuleServiceImpl.class);

    @Autowired
    private SysAclModuleMapper sysAclModuleMapper;

    @Autowired
    private SysAclMapper sysAclMapper;

    @Autowired
    private SysLogService sysLogService;

    @Override
    public void saveSysAclModule(SysAclModuleRequestDTO sysAclModuleRequestDTO) throws Exception {
        logger.info("Method [saveSysAclModule] call start. Input params===>[sysAclModuleRequestDTO={}]", JsonUtil.obj2String(sysAclModuleRequestDTO));
        if (sysAclModuleRequestDTO == null || sysAclModuleRequestDTO.getName() == null || sysAclModuleRequestDTO.getSeq() == null || sysAclModuleRequestDTO.getRemark() == null) {
            throw new PermissionException(ErrorCodeEnum.ILLGEAL_PARMS.getCode(), ErrorCodeEnum.ILLGEAL_PARMS.getErrorMessage());
        }
        //需要校验 权限模块存在不 同级别下有相同数据  level级别是用parentId + id来生成的
        if (checkSameAclModuleInSameLevel(sysAclModuleRequestDTO)) {
            throw new PermissionException(ErrorCodeEnum.SAME_LEVEL_EXISTS_SAME_ACL_MODULE_ERROR.getCode(), ErrorCodeEnum.SAME_LEVEL_EXISTS_SAME_ACL_MODULE_ERROR.getErrorMessage());
        }
        //todo 还有其他校验？
        //封装SysAclModule
        SysAclModule sysAclModule = SysAclModuleRequestDTO.adapt(sysAclModuleRequestDTO);
        //接下来需要封装 status level operater opterater_time 和operater_ip
        Integer parentId = (sysAclModuleRequestDTO.getParentId() == null ? 0 : sysAclModuleRequestDTO.getParentId());
        String level = LevelUtil.generateLevel(parentId, getLevel(parentId));
        if (StringUtils.isBlank(level)) {
            sysAclModule.setLevel(LevelUtil.LEVEL_ROOT);
        }
        sysAclModule.setLevel(level);
        sysAclModule.setStatus(Constant.ACL_MODULE_STATUS_NORMAL);
        sysAclModule.setOperator(RequestHolder.getSysUser().getUsername());
        sysAclModule.setOperateIp(Constant.OPERATOR_IP);
        sysAclModule.setOperateTime(new Date());
        //存表
        int insertSysAclModuleCount = sysAclModuleMapper.insertSelective(sysAclModule);
        sysLogService.saveAclModuleLog(null, sysAclModule);
        logger.info("Method [saveSysAclModule] call end. Results===>[insertSysAclModuleCount={}]", insertSysAclModuleCount);
        if (insertSysAclModuleCount > 0) {
            //todo send mail
        } else {
            throw new PermissionException(ErrorCodeEnum.ACL_MODULE_UPDATE_ERROR.getCode(), ErrorCodeEnum.ACL_MODULE_UPDATE_ERROR.getErrorMessage());
        }
    }


    @Override
    public void updateSysAclModule(SysAclModuleRequestDTO sysAclModuleRequestDTO) throws Exception {
        logger.info("Method [updateSysAclModule] call start. Input params===>[sysAclModuleRequestDTO={}]", JsonUtil.obj2String(sysAclModuleRequestDTO));
        if (sysAclModuleRequestDTO == null || sysAclModuleRequestDTO.getId() == null || sysAclModuleRequestDTO.getName() == null || sysAclModuleRequestDTO.getParentId() == null || sysAclModuleRequestDTO.getRemark() == null || sysAclModuleRequestDTO.getSeq() == null) {
            throw new PermissionException(ErrorCodeEnum.ILLGEAL_PARMS);
        }
        //获得需要更新前的SysAclModule
        SysAclModule sysAclModuleBefore = sysAclModuleMapper.selectByPrimaryKey(sysAclModuleRequestDTO.getId());
        if (sysAclModuleBefore == null) {
            throw new PermissionException(ErrorCodeEnum.UPDATE_ACL_MODULE_NOT_EXISTS);
        }
        //组装需要更新的后SysAclModule
        SysAclModule sysAclModuleAfter = SysAclModuleRequestDTO.adapt(sysAclModuleRequestDTO);
        sysAclModuleAfter.setLevel(LevelUtil.generateLevel(sysAclModuleRequestDTO.getParentId(), getLevel(sysAclModuleRequestDTO.getParentId())));
        sysAclModuleAfter.setOperateTime(new Date());
        sysAclModuleAfter.setOperateIp(Constant.OPERATOR_IP);
        sysAclModuleAfter.setOperator(RequestHolder.getSysUser().getUsername());
        updateWithChild(sysAclModuleBefore, sysAclModuleAfter);
        sysLogService.saveAclModuleLog(sysAclModuleBefore, sysAclModuleAfter);
        logger.info("Method [updateSysAclModule] call end. Resluts===>[sysAclModuleBefore={},sysAclModuleAfter={}]", JsonUtil.obj2String(sysAclModuleBefore), JsonUtil.obj2String(sysAclModuleAfter));
    }

    @Override
    public List<SysAclModuleLevelDTO> sysAclModuleTree() throws Exception {
        logger.info("Method [sysAclModuleTree] call start .Input params===>[{}]");
        //获取所有的list
        List<SysAclModule> sysAclModuleList = sysAclModuleMapper.getAllSysModuleList();
        List<SysAclModuleLevelDTO> sysAclModuleLevelDTOList = new ArrayList<SysAclModuleLevelDTO>();
        if (sysAclModuleList != null && sysAclModuleList.size() > 0) {
            for (SysAclModule sysAclModule : sysAclModuleList) {
                SysAclModuleLevelDTO sysAclModuleLevelDTO = SysAclModuleLevelDTO.adapt(sysAclModule);
                sysAclModuleLevelDTOList.add(sysAclModuleLevelDTO);
            }
            logger.info("Method [sysAclModuleTree] call end Results===>[sysAclModuleLevelDTOList={}]", JsonUtil.obj2String(sysAclModuleLevelDTOList));
            return sysAclModuleListToTree(sysAclModuleLevelDTOList);
        }
        return Collections.EMPTY_LIST;
    }


    @Override
    public PageResult<SysAcl> getPageData(Integer aclModuleId, PageQuery pageQuery) throws Exception {
        logger.info("Method [getPageData] call start .Input params===>{aclModuleId=[],pageQuery=[]}", aclModuleId, JsonUtil.obj2String(pageQuery));
        if (aclModuleId == null || pageQuery == null) {
            throw new PermissionException(ErrorCodeEnum.ILLGEAL_PARMS);
        }
        Integer pageNo = pageQuery.getPageNo() == null ? Constant.DEFAULT_PAGENO : pageQuery.getPageNo();
        Integer pageSize = pageQuery.getPageSize() == null ? Constant.DEFAULT_PAGESIZE : pageQuery.getPageSize();
        // 获取当前页面数据的起始位置
        Integer startIndex = (pageNo - 1) * pageSize;
        PageResult<SysAcl> pageResult = new PageResult<SysAcl>();
        Integer totalCount = sysAclMapper.getTotalCount(aclModuleId);
        pageResult.setTotal(totalCount);
        pageResult.setData(sysAclMapper.getPageData(aclModuleId, startIndex, pageSize));
        //总量
        int totalPageCount = totalCount % pageSize == 0 ? totalCount / pageSize : (int) (Math.floor(totalCount / pageSize)) + 1;
        pageResult.setTotalPageNo(Integer.valueOf(totalPageCount));
        return pageResult;
    }

    @Override
    public void deleteAclModule(Integer aclModuleId) throws Exception {
        logger.info("Method [deleteAclModule] call start.Input params===>[aclModuleId={}]", aclModuleId);
        if (aclModuleId == null) {
            throw new PermissionException(ErrorCodeEnum.ILLGEAL_PARMS);
        }
        //check aclModule exist
        SysAclModule sysAclModuleInDB = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
        if (sysAclModuleInDB == null) {
            throw new PermissionException(ErrorCodeEnum.DELETE_ACL_MODULE_NOT_EXISTS);
        }
        //check child aclModule
        if (sysAclModuleMapper.countByParentId(aclModuleId) > 0) {
            throw new PermissionException(ErrorCodeEnum.ACL_MODULE_HAS_CHILD_ERROR);
        }
        //check has acl
        if (sysAclMapper.countByAclModuleId(aclModuleId) > 0) {
            throw new PermissionException(ErrorCodeEnum.ACL_MODULE_HAS_ACL_ERROR);
        }
        //delete
        int deleteCount = sysAclModuleMapper.deleteByPrimaryKey(aclModuleId);
        sysLogService.saveAclModuleLog(sysAclModuleInDB, null);
        logger.info("Method [deleteAclModule] call end.Results===>[deleteCount={}]", deleteCount);
        if (deleteCount <= 0) {
            throw new PermissionException(ErrorCodeEnum.ACL_MODULE_DELETE_ERROR);
        }
    }


    private List<SysAclModuleLevelDTO> sysAclModuleListToTree(List<SysAclModuleLevelDTO> sysAclModuleLevelDTOList) throws Exception {
        logger.info("Method [sysAclModuleListToTree] call start. Input params===>[sysAclModuleLevelDTOList={}]", JsonUtil.obj2String(sysAclModuleLevelDTOList));
        //找出根集合并且排序 以及把数据都放到Multimap 集合中(原因是会存在level相同的key)
        List<SysAclModuleLevelDTO> rootList = new ArrayList<SysAclModuleLevelDTO>();
        Multimap<String, SysAclModuleLevelDTO> sysAclModuleLevelDTOMap = ArrayListMultimap.create();
        if (sysAclModuleLevelDTOList != null && sysAclModuleLevelDTOList.size() > 0) {
            for (SysAclModuleLevelDTO sysAclModuleLevelDTO : sysAclModuleLevelDTOList) {
                sysAclModuleLevelDTOMap.put(sysAclModuleLevelDTO.getLevel(), sysAclModuleLevelDTO);
                if (sysAclModuleLevelDTO.getLevel().equals(LevelUtil.LEVEL_ROOT)) {
                    rootList.add(sysAclModuleLevelDTO);
                }
            }
            //对rootList通过seq排序
            Collections.sort(rootList, sysAclModuleLevelDTOComparator);
            //递归去列出权限模块树
            transformAclModuleTree(rootList, LevelUtil.LEVEL_ROOT, sysAclModuleLevelDTOMap);
            logger.info("Method [sysAclModuleListToTree] call end.Results===>[rootList={}]", JsonUtil.obj2String(rootList));
            return rootList;
        }
        return Collections.EMPTY_LIST;
    }

    private void transformAclModuleTree(List<SysAclModuleLevelDTO> rootList, String levelRoot, Multimap<String, SysAclModuleLevelDTO> sysAclModuleLevelDTOMap) {

        if (rootList != null && StringUtils.isNotBlank(levelRoot) && sysAclModuleLevelDTOMap != null) {
            for (SysAclModuleLevelDTO sysAclModuleLevelDTO : rootList) {
                //计算出下个level的数据 存进来
                String childLevel = LevelUtil.generateLevel(sysAclModuleLevelDTO.getId(), levelRoot);
                //在map中拿到对应的数据
                List<SysAclModuleLevelDTO> sysAclModuleLevelDTOS = (List<SysAclModuleLevelDTO>) sysAclModuleLevelDTOMap.get(childLevel);
                //没有子系统模块
                if (sysAclModuleLevelDTOS != null && sysAclModuleLevelDTOS.size() > 0) {
                    sysAclModuleLevelDTO.setSysAclModuleLevelDTOList(sysAclModuleLevelDTOS);
                    transformAclModuleTree(sysAclModuleLevelDTOS, childLevel, sysAclModuleLevelDTOMap);
                } else {
                    sysAclModuleLevelDTO.setSysAclModuleLevelDTOList(Collections.EMPTY_LIST);
                }
            }
        }

    }

    /**
     * 更新权限模块
     *
     * @Param: [sysAclModuleBefore, sysAclModuleAfter]
     * @return: void
     */
    @Transactional(rollbackFor = Exception.class)
    private void updateWithChild(SysAclModule sysAclModuleBefore, SysAclModule sysAclModuleAfter) throws Exception {
        logger.info("Method [updateWithChild] call start. Input params===>[sysAclModuleBefore={},sysAclModuleAfter={}]", JsonUtil.obj2String(sysAclModuleBefore), JsonUtil.obj2String(sysAclModuleAfter));
        if (sysAclModuleBefore == null || sysAclModuleAfter == null) {
            throw new PermissionException(ErrorCodeEnum.UPDATE_ACL_MODULE_NOT_EXISTS);
        }
        //接下来需要判断有没有更新级别 如果有更新级别则需要更新该级别的子子孙孙
        String beforeLevlel = sysAclModuleBefore.getLevel();
        String afterLevel = sysAclModuleAfter.getLevel();
        if (StringUtils.isBlank(beforeLevlel) || StringUtils.isBlank(afterLevel)) {
            throw new PermissionException(ErrorCodeEnum.ACL_MODULE_UPDATE_ERROR);
        }
        //这里需要递归更新子子孙孙的level
        if (!beforeLevlel.equals(afterLevel)) {
            //找出需要更新的数据
            //准备level的查询参数
            StringBuilder levelParam = new StringBuilder(beforeLevlel);
            levelParam.append(LevelUtil.LEVEL_SEPARATOR).append(sysAclModuleBefore.getId());
            List<SysAclModule> sysAclModuleList = sysAclModuleMapper.selectNeedUpdateAclModule(levelParam.toString());
            if (sysAclModuleList != null && sysAclModuleList.size() > 0) {
                for (SysAclModule sysAclModuleChild : sysAclModuleList) {
                    sysAclModuleChild.setLevel(afterLevel + sysAclModuleChild.getLevel().substring(beforeLevlel.length()));
                    sysAclModuleMapper.updateLevelById(sysAclModuleChild);
                }
            }
        }
        //这里需要正常更新其他部门的数据
        sysAclModuleMapper.updateByPrimaryKey(sysAclModuleAfter);
    }

    /**
     * 检查同一级别的权限模块名字是否相同
     *
     * @Param: [sysAclModuleRequestDTO]
     * @return: boolean
     */
    private boolean checkSameAclModuleInSameLevel(SysAclModuleRequestDTO sysAclModuleRequestDTO) {
        logger.info("Method [checkSameAclModuleInSameLevel] call start. Input params===>[sysAclModuleRequestDTO={}]", JsonUtil.obj2String(sysAclModuleRequestDTO));
        int countByNameAndParentId = sysAclModuleMapper.countByNameAndParentId(sysAclModuleRequestDTO.getName(), sysAclModuleRequestDTO.getParentId());
        logger.info("Method [checkSameAclModuleInSameLevel] call end . Results===>[countByNameAndParentId={}]", countByNameAndParentId);
        return countByNameAndParentId > 0 ? true : false;
    }


    /**
     * 通过父id获取父级的level
     *
     * @Param: [parentId]
     * @return: java.lang.String
     */

    private String getLevel(Integer parentId) {
        logger.info("Method [getLevel] call start. Input parmas===>[parentId={}]", parentId);
        if (parentId == null) {
            return null;
        }
        String level = sysAclModuleMapper.selectAclModuleLevelById(parentId);
        logger.info("Method [getLevel] call end .Results===>[level={}]", level);
        if (StringUtils.isBlank(level)) {
            return null;
        }
        return level;
    }

    private Comparator<SysAclModuleLevelDTO> sysAclModuleLevelDTOComparator = new Comparator<SysAclModuleLevelDTO>() {
        @Override
        public int compare(SysAclModuleLevelDTO o1, SysAclModuleLevelDTO o2) {
            if (o1.getSeq() > o2.getSeq()) {
                return 1;
            } else if (o1.getSeq().equals(o2.getSeq())) {
                return 0;
            } else {
                return -1;
            }
        }
    };
}


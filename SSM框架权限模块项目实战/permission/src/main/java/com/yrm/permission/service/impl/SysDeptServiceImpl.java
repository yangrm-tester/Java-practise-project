package com.yrm.permission.service.impl;

import com.yrm.permission.common.ApiResult;
import com.yrm.permission.common.RequestHolder;
import com.yrm.permission.dao.SysDeptMapper;
import com.yrm.permission.dao.SysUserMapper;
import com.yrm.permission.dto.SysDeptRequestDTO;
import com.yrm.permission.dto.SysDeptTreeResponseDto;
import com.yrm.permission.enmus.ErrorCodeEnum;
import com.yrm.permission.entity.SysDept;
import com.yrm.permission.exception.PermissionException;
import com.yrm.permission.service.SysDeptService;
import com.yrm.permission.service.SysLogService;
import com.yrm.permission.util.IpUtil;
import com.yrm.permission.util.JsonUtil;
import com.yrm.permission.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysDeptServiceImpl
 * @createTime 2019年03月22日 19:26:00
 */

@Service
public class SysDeptServiceImpl implements SysDeptService {
    private static final Logger logger = LoggerFactory.getLogger(SysDeptServiceImpl.class);

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysLogService sysLogService;

    @Override
    public ApiResult saveSysDept(SysDeptRequestDTO sysDeptRequestDTO) throws Exception {
        logger.info("Method [saveSysDept] start,Input params===>[sysDeptRequestDTO={}]", JsonUtil.obj2String(sysDeptRequestDTO));
        Integer parentId = sysDeptRequestDTO.getParentId();

        SysDept sysDept = new SysDept();
        if (checkSysDeptIsExistInSameLevel(sysDeptRequestDTO)) {
            return ApiResult.failResult(ErrorCodeEnum.SAME_LEVEL_EXISTS_SAME_DEPT_ERROR.getCode(), ErrorCodeEnum.SAME_LEVEL_EXISTS_SAME_DEPT_ERROR.getErrorMessage());
        }
        BeanUtils.copyProperties(sysDeptRequestDTO, sysDept);
        /***父级节点为空则赋值为0表示根节点*/
        parentId = (parentId == null ? 0 : parentId);
        sysDept.setParentId(parentId);
        String level = LevelUtil.generateLevel(parentId, getLevel(parentId));
        sysDept.setLevel(level);
        sysDept.setOperator(RequestHolder.getSysUser().getUsername());
        sysDept.setOperateIp(IpUtil.getIpAttr(RequestHolder.getHttpServletRequest()));
        sysDept.setOperateTime(new Date());
        int insertNumber = sysDeptMapper.insert(sysDept);
        sysLogService.saveDeptLog(null, sysDept);
        logger.info("Method [saveSysDept] end,Result params===>[sysDept={},insertNumber={}]", JsonUtil.obj2String(sysDept), insertNumber);
        return insertNumber > 0 ? ApiResult.okResult(sysDept) : ApiResult.failResult(ErrorCodeEnum.SYSDEPT_INSERT_ERROR.getCode(), ErrorCodeEnum.SYSDEPT_INSERT_ERROR.getErrorMessage());
    }

    @Override
    public List<SysDeptTreeResponseDto> getTreeList() throws Exception {
        logger.info("Method [getTreeList] call start. Input params===>{}");
        List<SysDept> deptList = sysDeptMapper.getSysDeptTreeList(LevelUtil.LEVEL_ROOT);
        List<SysDeptTreeResponseDto> sysDeptTreeResponseDtoList = new ArrayList<SysDeptTreeResponseDto>();
        if (deptList != null && deptList.size() > 0) {
            for (SysDept sysDept : deptList) {
                SysDeptTreeResponseDto sysDeptTreeResponseDto = SysDeptTreeResponseDto.adapt(sysDept);
                sysDeptTreeResponseDto.setSysDeptTreeResponseDtoList(getChildTreeList(sysDept.getId()));
                sysDeptTreeResponseDtoList.add(sysDeptTreeResponseDto);
            }
            Collections.sort(sysDeptTreeResponseDtoList, deptSeqComparator);
            logger.info("Method [getTreeList] call end. Results===>{sysDeptTreeResponseDtoList={}}", JsonUtil.obj2String(sysDeptTreeResponseDtoList));
            return sysDeptTreeResponseDtoList;
        } else {
            logger.info("Method [getTreeList] call end. Results===>{sysDeptTreeResponseDtoList={}}", JsonUtil.obj2String(sysDeptTreeResponseDtoList));
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public void updateSysDept(SysDeptRequestDTO sysDeptRequestDTO) throws Exception {
        logger.info("Method [updateSysDept] call start. Input params===>[sysDeptRequestDTO={}]", JsonUtil.obj2String(sysDeptRequestDTO));
        if (sysDeptRequestDTO != null) {
            SysDept sysDeptBefore = sysDeptMapper.selectByPrimaryKey(sysDeptRequestDTO.getId());
            if (sysDeptBefore == null) {
                throw new PermissionException(ErrorCodeEnum.UPDATE_SYSDEPT_NOT_EXISTS.getCode(), ErrorCodeEnum.UPDATE_SYSDEPT_NOT_EXISTS.getErrorMessage());
            }
            //封装需要更新的sysDept
            SysDept sysDeptAfter = new SysDept();
            sysDeptAfter.setId(sysDeptRequestDTO.getId());
            sysDeptAfter.setName(sysDeptRequestDTO.getName());
            sysDeptAfter.setParentId(sysDeptRequestDTO.getParentId());
            sysDeptAfter.setRemark(sysDeptRequestDTO.getRemark());
            sysDeptAfter.setSeq(sysDeptRequestDTO.getSeq());
            sysDeptAfter.setLevel(LevelUtil.generateLevel(sysDeptRequestDTO.getParentId(), getLevel(sysDeptRequestDTO.getParentId())));
            sysDeptAfter.setOperateIp(IpUtil.getIpAttr(RequestHolder.getHttpServletRequest()));
            sysDeptAfter.setOperateTime(new Date());
            sysDeptAfter.setOperator(RequestHolder.getSysUser().getUsername());
            updateWithChild(sysDeptBefore, sysDeptAfter);
            sysLogService.saveDeptLog(sysDeptBefore, sysDeptAfter);
            logger.info("Method [updateSysDept] call end .Resluts===>[sysDeptBefore={},sysDeptAfter={}]", JsonUtil.obj2String(sysDeptBefore), JsonUtil.obj2String(sysDeptAfter));
        }
    }

    @Override
    public void deleteSysDept(Integer deptId) throws Exception {
        logger.info("Method [deleteSysDept] call start . Input params===>[deptId={}]", deptId);
        if (deptId == null) {
            throw new PermissionException(ErrorCodeEnum.ILLGEAL_PARMS);
        }
        //check dept exist
        SysDept sysDeptInDB = sysDeptMapper.selectByPrimaryKey(deptId);
        if (sysDeptInDB == null) {
            throw new PermissionException(ErrorCodeEnum.SYSDEPT_NOT_EXIST_ERROR);
        }
        //check child dept
        List<SysDept> childSysDeptList = sysDeptMapper.getChildList(deptId);
        if (CollectionUtils.isNotEmpty(childSysDeptList)) {
            throw new PermissionException(ErrorCodeEnum.EXIST_CHILD_SYSDEPET_ERROR);
        }
        //check user
        int countByDeptId = sysUserMapper.getTotalCountByDeptId(deptId);
        if (countByDeptId > 0) {
            throw new PermissionException(ErrorCodeEnum.SYSDEPT_HAS_USER_ERROR);
        }
        //delete
        int deleteCountByDeptId = sysDeptMapper.deleteByPrimaryKey(deptId);
        sysLogService.saveDeptLog(sysDeptInDB, null);
        logger.info("Method [deleteSysDept] call end . Results===>[deleteCountByDeptId={}]", deleteCountByDeptId);
        if (deleteCountByDeptId <= 0) {
            throw new PermissionException(ErrorCodeEnum.DELETE_SYSDEPT_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    private void updateWithChild(SysDept sysDeptBefore, SysDept sysDeptAfter) throws Exception {
        logger.info("Method [updateWithChild] call start.Input params===>[sysDeptBefore={},sysDeptAfter={}]", JsonUtil.obj2String(sysDeptBefore), JsonUtil.obj2String(sysDeptAfter));
        if (sysDeptAfter == null || sysDeptBefore == null) {
            throw new PermissionException(ErrorCodeEnum.UPDATE_SYSDEPT_NOT_EXISTS.getCode(), ErrorCodeEnum.UPDATE_SYSDEPT_NOT_EXISTS.getErrorMessage());
        }
        //判断是否要更新Level 如果更新Level则需要更新他的子子孙孙，不是则就更新本身即可
        String beforeLevel = sysDeptBefore.getLevel();
        String afterLevel = sysDeptAfter.getLevel();
        if (StringUtils.isNotBlank(beforeLevel) && StringUtils.isNotBlank(afterLevel) && !beforeLevel.equals(afterLevel)) {
            StringBuilder sb = new StringBuilder(beforeLevel);
            String needUpdateSysDeptLevel = sb.append(LevelUtil.LEVEL_SEPARATOR).append(sysDeptBefore.getId()).toString();
            List<SysDept> sysDeptList = sysDeptMapper.getChildListByLevel(needUpdateSysDeptLevel);
            if (null != sysDeptList && sysDeptList.size() > 0) {
                for (SysDept sysDept : sysDeptList) {
                    String level = sysDept.getLevel();
                    if (level.indexOf(beforeLevel) == 0) {
                        //拼接新level=更新后的level+以前后部分level
                        level = afterLevel + level.substring(beforeLevel.length());
                        sysDept.setLevel(level);
                    }
                    sysDeptMapper.updateSysDeptLevel(sysDept);
                }
            }
        }
        int updateSysDeptOtherNumber = sysDeptMapper.updateByPrimaryKey(sysDeptAfter);
        logger.info("Method [updateWithChild] call end.Results===>[updateSysDeptOthreNumber={}]", updateSysDeptOtherNumber);
    }

    /**
     * 校验统一级别下不能有相同的部门
     */
    private boolean checkSysDeptIsExistInSameLevel(SysDeptRequestDTO sysDeptRequestDTO) {
        logger.info("Method [checkSysDeptIsExistInSameLevel] start,Input params===>[id={},name={},parentId={},remark={},seq={}]", sysDeptRequestDTO.getId(), sysDeptRequestDTO.getName(), sysDeptRequestDTO.getParentId(), sysDeptRequestDTO.getRemark(), sysDeptRequestDTO.getSeq());
        if (sysDeptRequestDTO == null) {
            return false;
        }
        int sysDeptCount = sysDeptMapper.selectCountByDeptNameAndParentId(sysDeptRequestDTO.getName(), sysDeptRequestDTO.getParentId());
        logger.info("Method [checkSysDeptIsExistInSameLevel] end,Result params===>[sysDeptCount={}]", sysDeptCount);
        return sysDeptCount > 0 ? true : false;

    }

    private String getLevel(Integer parentId) {
        SysDept sysDept = sysDeptMapper.selectByPrimaryKey(parentId);
        if (sysDept == null) {
            return null;
        }
        return sysDept.getLevel();
    }

    private List<SysDeptTreeResponseDto> getChildTreeList(Integer deptId) {
        logger.info("Method [getChildTreeList] call start. Input params===>[id = {}]", deptId);
        List<SysDeptTreeResponseDto> sysDeptTreeResponseDtoList = new ArrayList<SysDeptTreeResponseDto>();

        if (deptId != null) {
            List<SysDept> childList = sysDeptMapper.getChildList(deptId);
            if (null != childList && childList.size() > 0) {
                for (SysDept sysDept : childList) {
                    SysDeptTreeResponseDto sysDeptTreeResponseDto = SysDeptTreeResponseDto.adapt(sysDept);
                    sysDeptTreeResponseDto.setSysDeptTreeResponseDtoList(getChildTreeList(sysDept.getId()));
                    sysDeptTreeResponseDtoList.add(sysDeptTreeResponseDto);
                }
                Collections.sort(sysDeptTreeResponseDtoList, deptSeqComparator);
                logger.info("Method [getTreeList] call end. Results===>{sysDeptTreeResponseDtoList={}}", JsonUtil.obj2String(sysDeptTreeResponseDtoList));
                return sysDeptTreeResponseDtoList;
            }
            logger.info("Method [getTreeList] call end. Results===>{sysDeptTreeResponseDtoList={}}", JsonUtil.obj2String(sysDeptTreeResponseDtoList));
            return Collections.EMPTY_LIST;
        }
        logger.info("Method [getTreeList] call end. Results===>{sysDeptTreeResponseDtoList={}}", JsonUtil.obj2String(sysDeptTreeResponseDtoList));
        return Collections.EMPTY_LIST;
    }


    private Comparator<SysDeptTreeResponseDto> deptSeqComparator = new Comparator<SysDeptTreeResponseDto>() {
        @Override
        public int compare(SysDeptTreeResponseDto o1, SysDeptTreeResponseDto o2) {
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

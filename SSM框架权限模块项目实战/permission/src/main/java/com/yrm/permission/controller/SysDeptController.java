package com.yrm.permission.controller;

import com.yrm.permission.common.ApiResult;
import com.yrm.permission.dto.SysDeptLevelDTO;
import com.yrm.permission.dto.SysDeptRequestDTO;
import com.yrm.permission.dto.SysDeptTreeResponseDto;
import com.yrm.permission.enmus.ErrorCodeEnum;
import com.yrm.permission.service.SysDeptService;
import com.yrm.permission.service.SysTreeService;
import com.yrm.permission.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysDeptController
 * @createTime 2019年03月22日 16:26:00
 */
@Controller
@RequestMapping("/sys/dept")
public class SysDeptController {

    private static final Logger logger = LoggerFactory.getLogger(SysDeptController.class);
    private static final String PAGE_DEPT = "dept";

    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysTreeService sysTreeService;

    @RequestMapping("/dept.page")
    public ModelAndView page() {
        return new ModelAndView(PAGE_DEPT);
    }


    /**
     * 部门保存
     *
     * @Param: [sysDeptRequestDTO]
     * @return: com.yrm.permission.common.ApiResult
     * @Description:
     */

    @RequestMapping("/save.json")
    @ResponseBody
    public ApiResult saveSysDept(SysDeptRequestDTO sysDeptRequestDTO) {
        ApiResult apiResult = new ApiResult();
        if (sysDeptRequestDTO != null) {
            logger.info("Method [saveSysDept] start,Input params==>[id={},name={},parentId={},remark={},seq={}]", sysDeptRequestDTO.getId(), sysDeptRequestDTO.getName(), sysDeptRequestDTO.getParentId(), sysDeptRequestDTO.getRemark(), sysDeptRequestDTO.getSeq());
            if (!checkSysDeptRequestDTO(sysDeptRequestDTO)) {
                apiResult.setCode(ErrorCodeEnum.ILLGEAL_PARMS.getCode());
                apiResult.setMsg(ErrorCodeEnum.ILLGEAL_PARMS.getErrorMessage());
                return apiResult;
            }
            try {
                apiResult = sysDeptService.saveSysDept(sysDeptRequestDTO);
            } catch (Exception e) {
                logger.info("Method [saveSysDept] call error,Error msg===>{}", e.getMessage());
                return ApiResult.failResult(ErrorCodeEnum.SYSDEPT_SAVE_ERROR.getCode(), ErrorCodeEnum.SYSDEPT_SAVE_ERROR.getErrorMessage());
            }
            logger.info("Method [saveSysDept] end,Results params===>[apiResult={}]", JsonUtil.obj2String(apiResult));
            return apiResult;
        }
        apiResult.setCode(ErrorCodeEnum.ILLGEAL_PARMS.getCode());
        apiResult.setMsg(ErrorCodeEnum.ILLGEAL_PARMS.getErrorMessage());
        return apiResult;
    }

    /**
     * 部门列表tree展示（我的想法）
     *
     * @Param: []
     * @return: com.yrm.permission.common.ApiResult
     * @Description:
     */

    @RequestMapping("/tree.json")
    @ResponseBody
    public ApiResult tree() {
        logger.info("Method [tree] call start,Input parmas===>{}");
        ApiResult apiResult = new ApiResult();
        List<SysDeptTreeResponseDto> sysDeptTreeResponseDtoList;
        try {
            sysDeptTreeResponseDtoList = sysDeptService.getTreeList();
        } catch (Exception e) {
            logger.error("Method [tree] call error,Error message===>{}", e.getMessage());
            apiResult.setCode(ErrorCodeEnum.TREE_LIST_SHOW_ERROR.getCode());
            apiResult.setMsg(ErrorCodeEnum.TREE_LIST_SHOW_ERROR.getErrorMessage());
            return apiResult;
        }
        logger.info("Method [tree] call end .Results===>[sysDeptTreeResponseDtoList={}]", JsonUtil.obj2String(sysDeptTreeResponseDtoList));
        return ApiResult.okResult(sysDeptTreeResponseDtoList);
    }


    /**
     * 部门列表tree展示另外一种方式(老师的想法)
     *
     * @Param: []
     * @return: com.yrm.permission.common.ApiResult
     * @Description:
     */

    @RequestMapping("/newTree.json")
    @ResponseBody
    public ApiResult newTree() {
        logger.info("Method [newTree] call start. Input params===>{}");
        ApiResult apiResult = new ApiResult();
        List<SysDeptLevelDTO> sysDeptLevelDTOList;
        try {
            sysDeptLevelDTOList = sysTreeService.getTreeList();
        } catch (Exception e) {
            logger.error("Method [newTree] call error . Error Message===>{}", e.getMessage());
            apiResult.setMsg(ErrorCodeEnum.TREE_LIST_SHOW_ERROR.getCode());
            apiResult.setMsg(ErrorCodeEnum.TREE_LIST_SHOW_ERROR.getErrorMessage());
            return apiResult;
        }
        apiResult.setData(sysDeptLevelDTOList);
        apiResult.setCode(ErrorCodeEnum.SUCCESS.getCode());
        apiResult.setMsg(ErrorCodeEnum.SUCCESS.getErrorMessage());
        logger.info("Method [newTree] call end. Results===>[sysDeptLevelDTOList={}]", JsonUtil.obj2String(sysDeptLevelDTOList));
        return apiResult;
    }


    /**
     * @Param: []
     * @return: com.yrm.permission.common.ApiResult
     * @Description:
     */

    @RequestMapping("/update.json")
    @ResponseBody
    public ApiResult updateSysDept(SysDeptRequestDTO sysDeptRequestDTO) {
        logger.info("Method [updateSysDept] call start . Input params===>[sysDeptRequestDTO={}]", JsonUtil.obj2String(sysDeptRequestDTO));
        ApiResult apiResult = new ApiResult();
        if (sysDeptRequestDTO != null) {
            try {
                sysDeptService.updateSysDept(sysDeptRequestDTO);
            } catch (Exception e) {
                logger.error("Method [updateSysDept] call error . Error message==>{}", e.getMessage());
                apiResult.setCode(ErrorCodeEnum.UPDATE_SYSDEPT_ERROR.getCode());
                apiResult.setMsg(ErrorCodeEnum.UPDATE_SYSDEPT_ERROR.getErrorMessage());
                return apiResult;
            }
            apiResult.setCode(ErrorCodeEnum.SUCCESS.getCode());
            apiResult.setMsg(ErrorCodeEnum.SUCCESS.getErrorMessage());
            logger.info("Method [updateSysDept] call end. Results===>[apiResult={}]", JsonUtil.obj2String(apiResult));
            return apiResult;
        }
        apiResult.setCode(ErrorCodeEnum.ILLGEAL_PARMS.getCode());
        apiResult.setMsg(ErrorCodeEnum.ILLGEAL_PARMS.getErrorMessage());
        logger.info("Method [updateSysDept] call end. Results===>[apiResult={}]", JsonUtil.obj2String(apiResult));
        return apiResult;
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public ApiResult delete(Integer deptId) {
        logger.info("Method [delete] call start .Input params===>[deptId={}]", deptId);
        ApiResult apiResult = new ApiResult();
        if (deptId == null) {
            apiResult.setErrorCodeEnum(ErrorCodeEnum.ILLGEAL_PARMS);
            return apiResult;
        }
        try {
            sysDeptService.deleteSysDept(deptId);
        } catch (Exception e) {
            logger.error("Method [delete] call error . Error message===>{}", e.getMessage());
            apiResult.setCode(ErrorCodeEnum.DELETE_SYSDEPT_ERROR.getCode());
            apiResult.setMsg(ErrorCodeEnum.DELETE_SYSDEPT_ERROR.getErrorMessage());
            return apiResult;
        }
        apiResult.setCode(ErrorCodeEnum.SUCCESS.getCode());
        apiResult.setMsg(ErrorCodeEnum.SUCCESS.getErrorMessage());
        logger.info("Method [delete] call end . Results===>[apiResult={}]", JsonUtil.obj2String(apiResult));
        return apiResult;
    }


    private boolean checkSysDeptRequestDTO(SysDeptRequestDTO sysDeptRequestDTO) {
        return sysDeptRequestDTO != null && StringUtils.isNotBlank(sysDeptRequestDTO.getName()) && sysDeptRequestDTO.getSeq() != null;
    }

}

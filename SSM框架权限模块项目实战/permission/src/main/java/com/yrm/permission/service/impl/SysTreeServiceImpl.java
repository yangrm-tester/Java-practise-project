package com.yrm.permission.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.yrm.permission.dao.SysDeptMapper;
import com.yrm.permission.dto.SysDeptLevelDTO;
import com.yrm.permission.entity.SysDept;
import com.yrm.permission.service.SysTreeService;
import com.yrm.permission.util.JsonUtil;
import com.yrm.permission.util.LevelUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysTreeServiceImpl
 * @createTime 2019年03月26日 19:26:00
 */
@Service
public class SysTreeServiceImpl implements SysTreeService {
    private static final Logger logger = LoggerFactory.getLogger(SysTreeService.class);
    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Override
    public List<SysDeptLevelDTO> getTreeList() throws Exception {
        logger.info("Method [getTreeList] call start. Input params===>{}");
        List<SysDept> allDeptList = sysDeptMapper.getAllDeptList();
        List<SysDeptLevelDTO> sysDeptLevelDTOList = new ArrayList<SysDeptLevelDTO>();
        if (null != allDeptList && allDeptList.size() > 0) {
            for (SysDept sysDept : allDeptList) {
                SysDeptLevelDTO adapt = SysDeptLevelDTO.adapt(sysDept);
                sysDeptLevelDTOList.add(adapt);
            }
            logger.info("Method [getTreeList] call end. Results===>[sysDeptLevelDTOList={}]", JsonUtil.obj2String(sysDeptLevelDTOList));
            return deptListToTree(sysDeptLevelDTOList);
        }
        return Collections.EMPTY_LIST;


    }

    private List<SysDeptLevelDTO> deptListToTree(List<SysDeptLevelDTO> sysDeptLevelDTOList) throws Exception {
        logger.info("Method [deptListToTree] call start . Input params===>[sysDeptLevelDTOList={}]", JsonUtil.obj2String(sysDeptLevelDTOList));
        List<SysDeptLevelDTO> rootList = new ArrayList<SysDeptLevelDTO>();
        //用这个map目的是为了key相同 value不同的情况，到时候拿出来的时候直接把key对应的所有value集合拿出来
        Multimap<String, SysDeptLevelDTO> levelDeptMap = ArrayListMultimap.create();
        if (null != sysDeptLevelDTOList && sysDeptLevelDTOList.size() > 0) {
            for (SysDeptLevelDTO sysDeptLevelDTO : sysDeptLevelDTOList) {
                levelDeptMap.put(sysDeptLevelDTO.getLevel(), sysDeptLevelDTO);
                if (LevelUtil.LEVEL_ROOT.equals(sysDeptLevelDTO.getLevel())) {
                    rootList.add(sysDeptLevelDTO);
                }
            }
            //对根部门进行排序
            Collections.sort(rootList, deptSeqComparator);
            // 递归生成树
            transformDeptTree(rootList, LevelUtil.LEVEL_ROOT, levelDeptMap);
            logger.info("Method [deptListToTree] call end . Results===>[]");
            return rootList;
        }
        logger.info("Method [deptListToTree] call end . Results===>[]");
        return Collections.EMPTY_LIST;
    }

    private void transformDeptTree(List<SysDeptLevelDTO> sysDeptLevelDTOList, String levelRoot, Multimap<String, SysDeptLevelDTO> sysDeptLevelDTOMap) throws Exception {
        logger.info("Method [transformDeptTree] call start. Input params===>[rootList={},levelRoot={},sysDeptLevelDTOMap={}]", JsonUtil.obj2String(sysDeptLevelDTOList), levelRoot, JsonUtil.obj2String(sysDeptLevelDTOMap));
        if (sysDeptLevelDTOList != null && StringUtils.isNotBlank(levelRoot) && sysDeptLevelDTOMap != null) {
            for (int i = 0; i < sysDeptLevelDTOList.size(); i++) {
                //遍历所有元素
                SysDeptLevelDTO sysDeptLevelDTO = sysDeptLevelDTOList.get(i);
                //获取下一层级
                String nextLevel = LevelUtil.generateLevel(sysDeptLevelDTO.getId(), levelRoot);
                //获得对应层级的部门
                List<SysDeptLevelDTO> deptLevelDTOS = (List<SysDeptLevelDTO>) sysDeptLevelDTOMap.get(nextLevel);
                if (deptLevelDTOS != null && deptLevelDTOS.size() > 0) {
                    Collections.sort(deptLevelDTOS, deptSeqComparator);
                    sysDeptLevelDTO.setSysDeptLevelDTOList(deptLevelDTOS);
                    transformDeptTree(deptLevelDTOS, nextLevel, sysDeptLevelDTOMap);
                }else {
                    sysDeptLevelDTO.setSysDeptLevelDTOList(Collections.EMPTY_LIST);
                }
            }
        }

    }


    private Comparator<SysDeptLevelDTO> deptSeqComparator = new Comparator<SysDeptLevelDTO>() {
        @Override
        public int compare(SysDeptLevelDTO o1, SysDeptLevelDTO o2) {
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

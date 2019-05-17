package com.yrm.permission.dao;

import com.yrm.permission.entity.SysDept;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 杨汝明
 * @version 1.0.0
 * @className SysDeptMapper
 * @createTime 2019年03月21日 16:24:00
 */

public interface SysDeptMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);

    /**
      *  通过父级id查找父级的level
      *  @param parentId 父级Id
      *  @Param: parentId
      *  @return: [String]
      *  @Description:
      */
    String getParentLevelByParentId(@Param("parentId") Integer parentId);

    /**
     *  查询相同的部门信息
     *  @param  parentId 父级Id
     *  @param  name 部门名称
     *  @Param: parentId，name
     *  @return: [String]
     *  @Description:
     */
    int selectCountByDeptNameAndParentId(@Param("name") String name,@Param("parentId") Integer parentId );
    /**
     *  查询root级别的部门list数据
     *  @param levelRoot rootLevel
     *  @Param: [levelRoot]
     *  @return: java.util.List<com.yrm.permission.entity.SysDept>
     *  @Description:
     */
    List<SysDept> getSysDeptTreeList(@Param("levelRoot") String levelRoot);
    /**
     *  查询子级别的部门
     *  @param id 父id
     *  @return: java.util.List<com.yrm.permission.entity.SysDept>
     *  @Description:
     *
     */
    List<SysDept> getChildList(@Param("id") Integer id);
    /**
     *  查询所有级别部门
     *  @Param: []
     *  @return: java.util.List<com.yrm.permission.entity.SysDept>
     *  @Description:
     *
     */
    List<SysDept> getAllDeptList();
    /**
     *  根据部门级别查出所有子部门
     *  @Param: [level]
     *  @return: java.util.List<com.yrm.permission.entity.SysDept>
     *  @Description:
     */
    List<SysDept> getChildListByLevel(@Param("needUpdateSysDeptLevel") String needUpdateSysDeptLevel);
    /**
     *  批量更新部门
     *  @Param: [sysDeptList]
     *  @return: void
     *  @Description:
     */
    int batchUpdateLevel(@Param("sysDeptList") List<SysDept> sysDeptList);


    /**
     *  更新部门级别
     *  @Param: [sysDept]
     *  @return: int
     */

    void updateSysDeptLevel(@Param("sysDept") SysDept sysDept);
}
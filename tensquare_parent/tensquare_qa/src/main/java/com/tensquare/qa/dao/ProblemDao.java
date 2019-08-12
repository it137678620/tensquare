package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    /**
     * 根据标签id 查询最新的问题
     */
    @Query("select p from Problem p where id in (select problemid from Pl where labelid=?1) order by replytime desc")
    public Page<Problem> findNewListByLabelId(String labelId, Pageable pageable);

    /**
     * 根据标签id 查询热门的问题 （回复数量最多）
     * @param labelId
     * @param pageable
     * @return
     */
    @Query("select p from Problem p where id in (select problemid from Pl where labelid=?1) order by reply desc ")
    public Page<Problem> findHotListByLabelId(String labelId,Pageable pageable);

    /**
     * 根据标签id 查询等待回答的问题 （回复数量等于0）
     * @param labelId
     * @param pageable
     * @return
     */
    @Query("select p from Problem p where id in (select problemid from Pl where labelid=?1)  and reply=0 order by createtime desc ")
    public Page<Problem> findWaitListByLabelId(String labelId,Pageable pageable);
}

package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;

import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LabelService {
    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 创建查询条件
     *
     * @param searchMap
     * @return
     */
    public Specification<Label> crateSpecification(Map searchMap) {
        return new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // 1.创建查询条件的集合
                List<Predicate> predicateList = new ArrayList<>();
                // 2.判断labelname是否等于空
                if (!StringUtils.isEmpty(searchMap.get("labelname"))) {
                    // 2.1 添加到集合中
                    predicateList.add(criteriaBuilder.like(root.get("labelname").as(String.class), "%" + (String) searchMap.get("labelname") + "%"));
                }

                if (!StringUtils.isEmpty(searchMap.get("state"))) {
                    predicateList.add(criteriaBuilder.equal(root.get("state").as(String.class), (String) searchMap.get("state")));
                }
                if (!StringUtils.isEmpty(searchMap.get("recommend"))) {
                    predicateList.add(criteriaBuilder.equal(root.get("recommend").as(String.class), (String) searchMap.get("recommend")));
                }

                //3. 返回查询条件
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }

    /**
     * 条件查询
     *
     * @param searchMap
     * @return
     */
    public Page<Label> findSearch(Map searchMap, int page, int size) {

        Specification<Label> labelSpecification = crateSpecification(searchMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);

        return labelDao.findAll(labelSpecification,pageRequest);
    }

    /**
     * 查询所有标签
     *
     * @return
     */
    public List<Label> findAll() {
        return labelDao.findAll();
    }

    /**
     * 根据ID查询标签
     *
     * @param id
     * @return
     */
    public Label findById(String id) {
        return labelDao.findById(id).get();
    }

    /**
     * 添加标签
     *
     * @param label
     */
    public void add(Label label) {
        label.setId(idWorker.nextId() + "");
        labelDao.save(label);
    }

    /**
     * 修改标签
     *
     * @param label
     */
    public void update(Label label) {
        labelDao.save(label);
    }

    /**
     * 删除标签
     *
     * @param id
     */
    public void delete(String id) {
        labelDao.deleteById(id);
    }
}

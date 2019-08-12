package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    /**
     * 条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search/{page}/{size}",method = RequestMethod.POST)
    public Result<Label> findSearch(@RequestBody Map searchMap,@PathVariable("page") int page,@PathVariable("size") int size){
        return new Result<>(true, StatusCode.OK,"查询成功",labelService.findSearch(searchMap,page,size));
    }

    /**
     * 查询全部列表
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result<List> findAll(){
         return new Result<>(true, StatusCode.OK,"查询成功",labelService.findAll());
    }

    /**
     * 根据id查询标签
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result<Label> findById(@PathVariable("id") String id){
        return new Result<>(true, StatusCode.OK,"查询成功",labelService.findById(id));
    }

    /**
     * 添加标签
     * @param label
     * @return
     */
    @RequestMapping(method=RequestMethod.POST)
    public Result<Void> add(@RequestBody Label label){
        labelService.add(label);
        return new Result<>(true,StatusCode.OK,"增加成功");
    }

    /**
     * 修改标签
     * @param label
     * @return
     */
    @RequestMapping(method=RequestMethod.PUT)
    public Result<Void> update(@RequestBody Label label){
        labelService.update(label);
        return new Result<>(true,StatusCode.OK,"修改成功");
    }

    /**
     * 删除标签
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method=RequestMethod.DELETE)
    public Result<Void> update(@PathVariable String id){
        labelService.delete(id);
        return new Result<>(true,StatusCode.OK,"删除成功");
    }
}

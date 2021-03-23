package com.sysu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sysu.mapper.TravelItemMapper;
import com.sysu.entity.PageResult;
import com.sysu.entity.QueryPageBean;
import com.sysu.pojo.TravelItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = TravelItemService.class)
@Transactional
public class TravelItemServiceImpl implements TravelItemService {

    @Autowired
    TravelItemMapper travelItemMapper;
    @Override
    public void add(TravelItem travelItem) {
        travelItemMapper.add(travelItem);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //使用分页插件 PageHelper github包 进行查询
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //用sql语句进行关键字模糊查询  这个Page??
        Page<TravelItem> page = travelItemMapper.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void delete(Integer id) {
        //先判断有无外键关联
        Long count = travelItemMapper.selectTravelGroup_TravelItemByTravelItemId(id);
        if (count>0){
            throw new RuntimeException("自由行有关联无法删除!");
        }else {
            //无关联删除
            travelItemMapper.delete(id);
        }
    }

    @Override
    public TravelItem queryOneItemById(Integer id) {
       TravelItem travelItem = travelItemMapper.queryOneItemById(id);
        return travelItem;
    }

    @Override
    public void edit(TravelItem travelItem) {
        travelItemMapper.edit(travelItem);
    }

    @Override
    public List<TravelItem> findAll() {
        return travelItemMapper.findAll();
    }
}

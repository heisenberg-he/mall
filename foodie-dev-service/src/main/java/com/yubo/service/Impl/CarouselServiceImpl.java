package com.yubo.service.Impl;

import com.yubo.enums.YesOrNo;
import com.yubo.mapper.CarouselMapper;
import com.yubo.pojo.Carousel;
import com.yubo.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;
    /**
     * 获取轮播图
     * @return
     */
    @Override
    public List<Carousel> getAll() {
        Example example = new Example(Carousel.class);
        example.orderBy("sort").desc();
        Example.Criteria carousel = example.createCriteria();
        carousel.andEqualTo("isShow", YesOrNo.YES.type);
        List<Carousel> carousels = carouselMapper.selectByExample(example);
        return carousels;
    }


}

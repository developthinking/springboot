package com.wt.lbsWeb.service;

import com.wt.lbsWeb.base.service.BaseService;
import com.wt.lbsWeb.dao.AreaLabelDao;
import com.wt.lbsWeb.entity.AreaLabelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 标签Service
 * Created by Administrator on 2017/11/9.
 */
@Service(value = "areaLabelService")
public class AreaLabelService extends BaseService<AreaLabelEntity> {

    @Autowired
    private AreaLabelDao areaLabelDao;

    @Override
    public AreaLabelDao getDao() {
        return areaLabelDao;
    }

    /**
     * 根据标签获得所有标签
     * 参数：
     *      标签对象
     */
    public List<String> getLabels(AreaLabelEntity areaPoiLabel) {
        List<String> labels = new ArrayList<>();
        String label = areaPoiLabel.getLabelId();
        String level = areaPoiLabel.getLevelType();
        labels.add(areaPoiLabel.getLabelId());
        switch (level) {
            case "1":
                List<String> secondClassLabels = areaLabelDao.getSecondClassLabelsByFirstClassId(label);
                List<String> thirdClassLabels = areaLabelDao.getThirdClassLabelsByFirstClassId(label);
                labels.addAll(secondClassLabels);
                labels.addAll(thirdClassLabels);
                break;
            case "2":
                List<String> thirdClassLabelsBy2 = areaLabelDao.getThirdClassLabelsBySecondClassId(label);
                labels.addAll(thirdClassLabelsBy2);
                break;
            case "3":

                break;
        }
        return labels;
    }
}

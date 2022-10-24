package doan.oishii_share_cong_thuc_nau_an.service;

import doan.oishii_share_cong_thuc_nau_an.common.vo.DishCategoryVo;
import doan.oishii_share_cong_thuc_nau_an.web.entities.DishCategory;

import java.util.List;

public interface CategoryService {

    public List<DishCategoryVo> findAllCategory();
}

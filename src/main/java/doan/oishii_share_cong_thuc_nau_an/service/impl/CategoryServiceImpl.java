package doan.oishii_share_cong_thuc_nau_an.service.impl;

import doan.oishii_share_cong_thuc_nau_an.common.vo.DishCategoryVo;
import doan.oishii_share_cong_thuc_nau_an.repositories.CategoryRepository;
import doan.oishii_share_cong_thuc_nau_an.service.CategoryService;
import doan.oishii_share_cong_thuc_nau_an.web.entities.DishCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<DishCategoryVo> findAllCategory() {
        return categoryRepository.findAllCategory();
    }
}

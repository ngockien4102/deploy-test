package doan.oishii_share_cong_thuc_nau_an.service.impl;

import doan.oishii_share_cong_thuc_nau_an.Exception.ErrorCode;
import doan.oishii_share_cong_thuc_nau_an.Exception.NotFoundException;
import doan.oishii_share_cong_thuc_nau_an.common.logging.LogUtils;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishCategoryVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.MessageVo;
import doan.oishii_share_cong_thuc_nau_an.repositories.CategoryRepository;
import doan.oishii_share_cong_thuc_nau_an.service.CategoryService;
import doan.oishii_share_cong_thuc_nau_an.web.entities.Blog;
import doan.oishii_share_cong_thuc_nau_an.web.entities.DishCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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

    @Override
    public Page<DishCategoryVo> getListCategory(String searchData, Integer pageIndex, Integer pageSize) {
        if (searchData == null) {
            searchData = "";
        }
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return categoryRepository.getListCategory("%" + searchData.trim() + "%", pageable);
    }

    @Override
    public ResponseEntity<?> getCategoryInfo(Integer categoryId) {
        LogUtils.getLog().info("START getCategoryInfo");
        DishCategoryVo dishCategoryVo = categoryRepository.getCategoryInfo(categoryId);
        if(dishCategoryVo == null){
            LogUtils.getLog().info("END getCategoryInfo");
            return ResponseEntity.ok(new MessageVo("Không tồn tại loại công thức này", "info"));
        }else {
            LogUtils.getLog().info("END getCategoryInfo");
            return ResponseEntity.ok(dishCategoryVo);
        }
    }

    @Override
    public ResponseEntity<?> createCategory(String categoryName, String categoryImage) {
        DishCategory category = new DishCategory();
        category.setName(categoryName);
        category.setDishCategoryImage(categoryImage);
        category.setStatus(1);
        categoryRepository.save(category);
        return ResponseEntity.ok(new MessageVo("lưu loại công thức thành công", "success"));
    }

    @Override
    public ResponseEntity<?> updateCategory(Integer categoryId, String categoryName, String categoryImage) {
        DishCategory category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new NotFoundException(ErrorCode.Not_Found, "loại công thức này không tồn tại "));
        category.setName(categoryName);
        category.setDishCategoryImage(categoryImage);
        categoryRepository.save(category);
        return ResponseEntity.ok(new MessageVo("cập nhật loại công thức thành công", "success"));
    }

    @Override
    public ResponseEntity<?> deleteCategory(Integer categoryId) {
        LogUtils.getLog().info("START deleteCategory");
        List<Integer> checkCategoryExists = categoryRepository.checkCategoryExists(categoryId);
        if(checkCategoryExists == null || checkCategoryExists.size() == 0){
            DishCategory category = categoryRepository.findById(categoryId).orElseThrow(() ->
                    new NotFoundException(ErrorCode.Not_Found, "loại công thức này không tồn tại "));
            category.setStatus(3);
            categoryRepository.save(category);
            LogUtils.getLog().info("END deleteCategory");
            return ResponseEntity.ok(new MessageVo("xóa loại công thức thành công", "success"));
        }else {
            LogUtils.getLog().info("END deleteCategory");
            return ResponseEntity.ok(new MessageVo("Không thể xóa vì đang có công thức thuộc loại này", "success"));
        }
    }
}

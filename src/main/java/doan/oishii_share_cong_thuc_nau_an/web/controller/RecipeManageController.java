package doan.oishii_share_cong_thuc_nau_an.web.controller;

import doan.oishii_share_cong_thuc_nau_an.common.logging.LogUtils;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishFormulaVo;
import doan.oishii_share_cong_thuc_nau_an.dto.Responds.DishEditResponse;
import doan.oishii_share_cong_thuc_nau_an.service.DishServive;
import doan.oishii_share_cong_thuc_nau_an.service.IngredientDetailService;
import doan.oishii_share_cong_thuc_nau_an.web.entities.Dish;
import doan.oishii_share_cong_thuc_nau_an.web.entities.IngredientDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecipeManageController {

    @Autowired
    private DishServive dishServive;

    @Autowired
    private IngredientDetailService ingredientDetailService;

    //lấy danh sách quản lý tất cả công thức
    @GetMapping("/admin/listRecipe")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllRecipe(Model model,
                                          @RequestParam(required = false) String searchData, @RequestParam(required = false) Integer pageIndex) {
        LogUtils.getLog().info("START getAllRecipe");
        if (pageIndex == null) {
            pageIndex = 1;
        }
        Page<DishFormulaVo> dishFormulaVoList = dishServive.getAllRecipe(searchData, pageIndex-1, 5);
        model.addAttribute("dishFormulaVoList", dishFormulaVoList.toList());
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("numOfPages", dishFormulaVoList.getTotalPages());
        LogUtils.getLog().info("END getAllRecipe");
        return ResponseEntity.ok(model);
    }

    //lấy danh sách quản lý  công thức của 1 người tạo
    @GetMapping("/mod/listRecipeOfCreater")
    @PreAuthorize("hasRole('ROLE_ADMIN')or hasRole('ROLE_MOD')")
    public ResponseEntity<?> getRecipeOfCreater(Model model,
                                                @RequestParam(value = "creater") String creater,
                                                @RequestParam(required = false) String searchData,@RequestParam(required = false) Integer pageIndex) {
        LogUtils.getLog().info("START getRecipeOfCreater");
        if (pageIndex == null) {
            pageIndex = 1;
        }
        Page<DishFormulaVo> dishFormulaVoList = dishServive.getRecipeOfCreater(creater, searchData,pageIndex-1, 5);
        model.addAttribute("dishFormulaVoList", dishFormulaVoList.toList());
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("numOfPages", dishFormulaVoList.getTotalPages());
        LogUtils.getLog().info("END getRecipeOfCreater");
        return ResponseEntity.ok(model);
    }

    @PostMapping("/mod/createrecipe")
    public ResponseEntity<String> CreateRecipe(@RequestBody Dish dishRequest) {
        dishServive.CreateNewRecipe(dishRequest);
        return new ResponseEntity<>("Thêm món ăn thành công !!!", HttpStatus.OK);
    }

    @GetMapping("/mod/getdishbyid")
    public ResponseEntity<?>getDishById(@RequestParam("dish_id")Integer id){
        DishEditResponse dishEditResponse = dishServive.getDishById(id);
        return ResponseEntity.ok(dishEditResponse);
    }

    @PutMapping("/mod/editrecipe")
    public ResponseEntity<?> updateRecipe(@RequestParam("recipe_id") Integer dishId ,@RequestBody Dish dishRequest) {
        dishServive.editRecipe(dishId,dishRequest);
        return new ResponseEntity<>("update success", HttpStatus.OK);
    }

    @DeleteMapping("/mod/deleterecipe")
    public ResponseEntity<?> deleteRecipe(@RequestParam("recipe_id") Integer recipeId) {
        dishServive.deleteRecipe(recipeId);
        return new ResponseEntity<>("xóa món ăn thành công!!!", HttpStatus.OK);
    }


}

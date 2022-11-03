package doan.oishii_share_cong_thuc_nau_an.service;

import doan.oishii_share_cong_thuc_nau_an.common.vo.DishDetailVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishFormulaVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishVo;
import doan.oishii_share_cong_thuc_nau_an.dto.Responds.DishEditResponse;
import doan.oishii_share_cong_thuc_nau_an.dto.Responds.DishResponse;
import doan.oishii_share_cong_thuc_nau_an.web.entities.Dish;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DishServive {

    List<DishVo> getTop5VoteWeek();

    List<DishVo> getTop5VoteMonth();

    Page<DishFormulaVo> getAllRecipe(String searchData, Integer pageIndex, Integer pageSize);

    Page<DishFormulaVo> getRecipeOfCreater(String creater, String searchData, Integer pageIndex, Integer pageSize);

    DishDetailVo getDishDetail(Integer dishId);

    List<DishResponse> getDishByName(String name,Integer pageIndex);

    List<DishResponse> getDishByCate(Integer cate,Integer pageIndex);

    List<DishResponse> getTop5New();

    void CreateNewRecipe(Dish dishRequest);

     void editRecipe(Integer dishId,Dish dishRequest);

    void deleteRecipe(Integer recipeId);

    DishDetailVo getDishByBMIUser(String meal, String mainIngredient, Double calo);

    List<Integer> getListDishByBMIUser(Double totalCalo);

    List<String> getListMainIngredient();

    DishEditResponse getDishById(Integer id);

    String searchMainIngredient(String ingredient);
}

package doan.oishii_share_cong_thuc_nau_an.service;

import doan.oishii_share_cong_thuc_nau_an.common.vo.DishVo;
import doan.oishii_share_cong_thuc_nau_an.web.entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FavoriteRecipeService {
    Page<DishVo> getFavoriteRecipe(Integer accountId,String searchData, Integer pageIndex, Integer pageSize);

    ResponseEntity<?> addFavoriteRecipes(Integer dishId, Account account);

    ResponseEntity<?> removeFavoriteRecipes(Integer dishId, Account account);
}

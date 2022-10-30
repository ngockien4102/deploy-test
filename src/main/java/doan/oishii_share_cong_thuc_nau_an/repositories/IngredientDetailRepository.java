package doan.oishii_share_cong_thuc_nau_an.repositories;

import doan.oishii_share_cong_thuc_nau_an.common.vo.DishVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.IngredientDetailVo;
import doan.oishii_share_cong_thuc_nau_an.web.entities.IngredientDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IngredientDetailRepository extends JpaRepository<IngredientDetail, Integer> {

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.IngredientDetailVo(" +
            "id.ingredientDetailID, id.quantity, id.unit, id.name, id.calo, id.mainIngredient )" +
            "from IngredientDetail id join id.dishID d where d.dishID = :dishId and d.status <> 3")
    public List<IngredientDetailVo> findIngredientDetailVoByDishID(Integer dishId);

    //    @Modifying
//    @Query( "delete IngredientDetail id where id.ingredientID.ingredientID = :id")
//    int deleteByIngredientID(Integer id);
    @Modifying
    @Transactional
    @Query(value = "DELETE id FROM dbo.ingredient_detail id JOIN dbo.dish d ON d.dish_id = id.dish_id WHERE d.dish_id=:dishId", nativeQuery = true)
    public void deleteIngredientDetailByDishID(@Param("dishId") Integer dishId);

}

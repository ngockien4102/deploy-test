package doan.oishii_share_cong_thuc_nau_an.repositories;

import doan.oishii_share_cong_thuc_nau_an.common.vo.IngredientChangeVo;
import doan.oishii_share_cong_thuc_nau_an.web.entities.IngredientChange;
import doan.oishii_share_cong_thuc_nau_an.web.entities.IngredientDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientChangeRepository extends JpaRepository<IngredientChange, Integer> {

//    @Modifying
//    @Query( "delete IngredientChange ic where ic.ingredientID.ingredientID = :id")
//    int deleteByIngredientID(Integer id);
    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.IngredientChangeVo(" +
            "ic.ingredientChangeID, ic.quantity, ic.unit," +
            " ic.name,  ic.calo) " +
            " from IngredientChange ic join ic.ingredientDetail id " +
            "where id.ingredientDetailID = :ingredientDetailId ")
    IngredientChangeVo getIngredientChange(Integer ingredientDetailId);
}

package doan.oishii_share_cong_thuc_nau_an.common.vo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDetailVo {

    //IngredientDetail------------

    private Integer ingredientDetailId;

    private Integer quantity;

    private String unit;

    private String name;


    private Integer calo;

    //Ingredient-------------------

//    private Integer ingredientID;
//
//    private String ingredientName;
//
//    private Integer ingredientCalo;
//
//    private Double ingredientPrice;
//
//    //IngredientUnit--------------
//
//    private Integer ingredientUnitID;
//
//    private String unitName;



}

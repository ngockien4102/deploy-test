package doan.oishii_share_cong_thuc_nau_an.web.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "IngredientDetail" )
@Data
//@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "ingredientDetailID"
//)
public class IngredientDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_detail_id")
    private Integer ingredientDetailID;

    @Column(name = "name", columnDefinition = "nvarchar(max)")
    private String name;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit", columnDefinition = "nvarchar(max)")
    private String unit;

    @Column(name = "calo",nullable=true)
    private Integer calo;

//    @ManyToOne
//    @JoinColumn(name = "ingredient_id", referencedColumnName = "ingredient_id", nullable = false)
////    @JsonBackReference
//    private Ingredient ingredientID;

    @ManyToOne
    @JoinColumn(name = "dish_id", referencedColumnName = "dish_id", nullable = false)
//    @JsonBackReference(value = "ingredient-dish")
    private Dish dishID;

    @OneToOne(mappedBy = "ingredientDetail")
//    @JsonBackReference
    private IngredientChange ingredientChange;
}

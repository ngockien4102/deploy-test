package doan.oishii_share_cong_thuc_nau_an.dto.Responds;

import doan.oishii_share_cong_thuc_nau_an.web.entities.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DishEditResponse {
    private Integer dishID;
    private String name;
    private String origin;
    private Integer calo;
    private Integer level;
    private Integer numberPeopleForDish;
    private Integer size;
    private Integer time;
    private String video;
    private LocalDate createDate;
    private Integer status;
    private Formula formulaId;
    private List<Step> listStep;
    private List<DishImage> listDishImage;
    private List<DishCategory> idDishCategory;
    private List<IngredientDetail> listIngredientDetail;







}

package doan.oishii_share_cong_thuc_nau_an.web.controller;

import doan.oishii_share_cong_thuc_nau_an.common.vo.BMIVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishDetailVo;
import doan.oishii_share_cong_thuc_nau_an.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BMIController {
    @Autowired
    private BMIService bmiService;

    @Autowired
    private DishServive dishServive;

    @Autowired
    private DishCommentService dishCommentService;

    @Autowired
    private DishImageService dishImageService;

    @Autowired
    private IngredientDetailService ingredientDetailService;

    @Autowired
    private StepService stepService;

    @GetMapping("/getInformationBMIUser/{username}")
    public ResponseEntity<?> getInformationBMIUser(@PathVariable("username") String username) {
        BMIVo informationBMIUser = bmiService.getInformationBMIByUser(username);
        return ResponseEntity.ok(informationBMIUser);
    }

    @PutMapping("/UpdateProfileBMI/{username}")
    public ResponseEntity<?> UpdateProfileBMI(@PathVariable String username, Double H, Double W, Double R) {
        bmiService.updateProfile(username, H, W, R);
        return new ResponseEntity<>("update successfull", HttpStatus.OK);
    }

    @GetMapping("/getDishByBMIUser")
    public ResponseEntity<?> getDishByBMIUser(@RequestParam("meal") String meal, @RequestParam("mainIngredient") String mainIngredient, @RequestParam("totalCalo") Integer totalCalo) {
        DishDetailVo dishDetailVo = dishServive.getDishByBMIUser(meal, mainIngredient, totalCalo);
        dishDetailVo.setStepList(stepService.findByFormulaID(dishDetailVo.getFormulaID()));
        dishDetailVo.setDishImageList(dishImageService.findByDishID(dishDetailVo.getDishID()));
        //dishDetailVo.setDishCommentList(dishCommentService.findDishCommentByDishId(dishDetailVo.getDishID()));
        dishDetailVo.setIngredientDetailList(ingredientDetailService.findIngredientDetailVoByDishID(dishDetailVo.getDishID()));
        return ResponseEntity.ok(dishDetailVo);
    }

    @GetMapping("/getListDishByBMIUser")
    public ResponseEntity<?> getListDishByBMIUser(@RequestParam("totalCalo") Integer totalCalo) {
        List<Integer> listIDDishBySuggest = dishServive.getListDishByBMIUser(totalCalo);
        DishDetailVo dishDetailVo = new DishDetailVo();
        for (int i = 0; i < listIDDishBySuggest.size(); i++) {
            dishDetailVo = dishServive.getDishDetail(listIDDishBySuggest.get(i));
            dishDetailVo.setStepList(stepService.findByFormulaID(dishDetailVo.getFormulaID()));
            dishDetailVo.setDishImageList(dishImageService.findByDishID(dishDetailVo.getDishID()));
            //dishDetailVo.setDishCommentList(dishCommentService.findDishCommentByDishId(dishDetailVo.getDishID()));
            dishDetailVo.setIngredientDetailList(ingredientDetailService.findIngredientDetailVoByDishID(dishDetailVo.getDishID()));
        }
        return ResponseEntity.ok(dishDetailVo);
    }
}

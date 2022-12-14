package doan.oishii_share_cong_thuc_nau_an.web.controller;

import doan.oishii_share_cong_thuc_nau_an.Exception.ErrorCode;
import doan.oishii_share_cong_thuc_nau_an.Exception.NotFoundException;
import doan.oishii_share_cong_thuc_nau_an.common.vo.BMIVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishDetailVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.MessageVo;
import doan.oishii_share_cong_thuc_nau_an.dto.Requests.BMI.BMIRequest;
import doan.oishii_share_cong_thuc_nau_an.dto.Requests.ProfileRequest;
import doan.oishii_share_cong_thuc_nau_an.service.*;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
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
        return bmiService.getInformationBMIByUser(username);
        //return ResponseEntity.ok(informationBMIUser);
    }

    @PutMapping("/UpdateProfileBMI")
    public ResponseEntity<?> UpdateProfileBMI(@RequestBody BMIRequest bmiRequest) {
        bmiService.updateProfile(bmiRequest);
        return new ResponseEntity<>("update successfull", HttpStatus.OK);
    }

    @GetMapping("/getMainIngredient")
    public ResponseEntity<?> getMainIngredient() {
        List<String> listMainIngredient = dishServive.getListMainIngredient();
        if(listMainIngredient == null){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageVo( "Kh??ng c?? nguy??n li???u ch??nh n??o!!!","error"));
        }
        return ResponseEntity.ok(listMainIngredient);
    }

    @GetMapping("/getDishByBMIUser")
    public ResponseEntity<?> getDishByBMIUser(@RequestParam("meal") String meal, @RequestParam("mainIngredient") String mainIngredient, @RequestParam("totalCalo") Double totalCalo) {
        if(totalCalo == null){
            throw new NotFoundException(ErrorCode.Not_Found, "Total calo ch??a c?? y??u c???u b???n t??nh BMI tr?????c");
        }
        if(totalCalo == null){
            throw new NotFoundException(ErrorCode.Not_Found, "mainIngredient ch??a c??!");
        }
        if(totalCalo == null){
            throw new NotFoundException(ErrorCode.Not_Found, "meal ch??a c??!");
        }
        DishDetailVo dishDetailVo = dishServive.getDishByBMIUser(meal, mainIngredient, totalCalo);
        if(dishDetailVo == null){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageVo( "M??n ??n theo nguy??n li???u ch??nh n??y ho???c nguy??n li???u ch??nh n??y kh??ng t???n t???i","error"));
        }
        //dishDetailVo.setStepList(stepService.findByFormulaID(dishDetailVo.getFormulaID()));
        dishDetailVo.setDishImageList(dishImageService.findByDishID(dishDetailVo.getDishID()));
        //dishDetailVo.setDishCommentList(dishCommentService.findDishCommentByDishId(dishDetailVo.getDishID()));
        //dishDetailVo.setIngredientDetailList(ingredientDetailService.findIngredientDetailVoByDishID(dishDetailVo.getDishID()));
        return ResponseEntity.ok(dishDetailVo);
    }

    @GetMapping("/getListDishByBMIUser")
    public ResponseEntity<?> getListDishByBMIUser(@RequestParam("totalCalo") Double totalCalo) {
        if(totalCalo == null){
            throw new NotFoundException(ErrorCode.Not_Found, "Total calo ch??a c?? y??u c???u b???n t??nh BMI tr?????c");
        }
        List<Integer> listIDDishBySuggest = dishServive.getListDishByBMIUser(totalCalo);
        List<DishDetailVo> dishDetailVo = new ArrayList<>();
        for (int i = 0; i < listIDDishBySuggest.size(); i++) {
            DishDetailVo dishDetailVo1 = new DishDetailVo();
            dishDetailVo1 = dishServive.getDishDetail(listIDDishBySuggest.get(i));
            dishDetailVo1.setDishImageList(dishImageService.findByDishID(dishDetailVo1.getDishID()));
            dishDetailVo.add(dishDetailVo1);
            //dishDetailVo.setStepList(stepService.findByFormulaID(dishDetailVo.getFormulaID()));
            //dishDetailVo.setDishCommentList(dishCommentService.findDishCommentByDishId(dishDetailVo.getDishID()));
            //dishDetailVo.setIngredientDetailList(ingredientDetailService.findIngredientDetailVoByDishID(dishDetailVo.getDishID()));
        }
        return ResponseEntity.ok(dishDetailVo);
    }

    @GetMapping("/searchMainIngredient")
    public ResponseEntity<?> searchMainIngredient(@RequestParam("ingredient") String ingredient) {
        if(ingredient == null ){
            throw new NotFoundException(ErrorCode.Not_Found, "Y??u c???u nh???p nguy??n li???u ch??nh");
        }
        String mainIngredient = dishServive.searchMainIngredient(ingredient);
        if(mainIngredient == null){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageVo( "Kh??ng c?? nguy??n li???u ch??nh n??o!!!","error"));
        }
        return ResponseEntity.ok(mainIngredient);
    }
}

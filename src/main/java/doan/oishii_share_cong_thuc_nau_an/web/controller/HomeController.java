package doan.oishii_share_cong_thuc_nau_an.web.controller;

import doan.oishii_share_cong_thuc_nau_an.common.vo.DishCategoryVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishDetailVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishImageVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishVo;
import doan.oishii_share_cong_thuc_nau_an.dto.Requests.ProfileRequest;
import doan.oishii_share_cong_thuc_nau_an.dto.Responds.DishResponse;
import doan.oishii_share_cong_thuc_nau_an.service.*;
import doan.oishii_share_cong_thuc_nau_an.web.entities.DishCategory;
import doan.oishii_share_cong_thuc_nau_an.web.entities.DishImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HomeController {

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

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private HomeService homeService;

    @GetMapping("/getTop5VoteWeek")
    public ResponseEntity<?> getTop5VoteWeek() {

        List<DishVo> top5VoteWeek = dishServive.getTop5VoteWeek();
        for(DishVo dishVo : top5VoteWeek){
            List<DishImageVo> imageList = dishImageService.findByDishID(dishVo.getDishId());
            if(null != imageList && imageList.size() != 0) {
                dishVo.setUrlImage(imageList.get(0).getUrl());
            }
            if(dishVo.getAvgStarRate() == null){
                dishVo.setAvgStarRate(0.0);
            }
        }
        return ResponseEntity.ok(top5VoteWeek);

    }

    @GetMapping("/getTop5VoteMonth")
    public ResponseEntity<?> getTop5VoteMonth() {

        List<DishVo> top5VoteMonth = dishServive.getTop5VoteMonth();
        for(DishVo dishVo : top5VoteMonth){
            List<DishImageVo> imageList = dishImageService.findByDishID(dishVo.getDishId());
            if(null != imageList && imageList.size() != 0) {
                dishVo.setUrlImage(imageList.get(0).getUrl());
            }
            if(dishVo.getAvgStarRate() == null){
                dishVo.setAvgStarRate(0.0);
            }
        }
        return ResponseEntity.ok(top5VoteMonth);

    }

    @GetMapping("/getCategories")
    public ResponseEntity<?> getAllCategories() {
        List<DishCategoryVo> listCategory = categoryService.findAllCategory();
        return ResponseEntity.ok(listCategory);
    }



    //get profile by id
    // http://localhost:8080/profile/{id}
    @GetMapping("/getprofile/{id}")
    public ResponseEntity<ProfileRequest> getUserProfile(@PathVariable("id") Integer profileId) {
        ProfileRequest profileRequest = homeService.getProfile(profileId);
        return ResponseEntity.ok(profileRequest);
    }

    //update profile by id
    // http://localhost:8080/updateprofile/{id}
    @PutMapping("/updateprofile/{id}")
    public ResponseEntity<?> UpdateProfile(@PathVariable("id") Integer profileId, @RequestBody ProfileRequest profileRequest)  {
        homeService.updateProfile(profileId, profileRequest);
        return new ResponseEntity<>("update successfull", HttpStatus.OK);
    }

    //search recipe by name
    //http://localhost:8080/searchdishbyname/{name}
    @GetMapping("/searchdishbyname/{name}")
    public ResponseEntity<List<DishResponse>> getDishByName(@PathVariable("name") String name) {
        List<DishResponse> dishes = dishServive.getDishByName(name);
        return ResponseEntity.ok(dishes);
    }

//    search recipe by cate
//    http://localhost:8080/searchdishbycate/{cate}
    @GetMapping("/searchdishbycate/{cateId}")
    public ResponseEntity<List<DishResponse>> getDishBycate(@PathVariable("cateId") Integer cate) {
        List<DishResponse> dishes = dishServive.getDishByCate(cate);
        return ResponseEntity.ok(dishes);
    }

    //get 5 recipe newest
    //http://localhost:8080/gettop5new
    @GetMapping("/gettop5new")
    public ResponseEntity<List<DishResponse>> getTop5New(){
        List<DishResponse> dishResponseList = dishServive.getTop5New();
        return ResponseEntity.ok(dishResponseList);
    }


}

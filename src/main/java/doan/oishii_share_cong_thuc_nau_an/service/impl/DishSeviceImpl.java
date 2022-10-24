package doan.oishii_share_cong_thuc_nau_an.service.impl;

import doan.oishii_share_cong_thuc_nau_an.Exception.ErrorCode;
import doan.oishii_share_cong_thuc_nau_an.Exception.NotFoundException;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishDetailVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishFormulaVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishVo;
import doan.oishii_share_cong_thuc_nau_an.dto.Requests.Dish.DishImageResquest;
import doan.oishii_share_cong_thuc_nau_an.dto.Responds.DishEditResponse;
import doan.oishii_share_cong_thuc_nau_an.dto.Responds.DishResponse;
import doan.oishii_share_cong_thuc_nau_an.mapper.DishMapper;
import doan.oishii_share_cong_thuc_nau_an.repositories.*;
import doan.oishii_share_cong_thuc_nau_an.service.DishServive;
import doan.oishii_share_cong_thuc_nau_an.web.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;


@Service
public class DishSeviceImpl implements DishServive {

    @Autowired
    private DishTopVoRepository dishTopVoRepository;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private FormulaRepository formulaRepository;

    @Autowired
    private DishImageRepository dishImageRepository;

    @Autowired
    private IngredientDetailRepository ingredientDetailRepository;

    @Autowired
    private IngredientChangeRepository ingredientChangeRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    DishCommentServiceImpl commentService;

    @Autowired
    DishCommentRepository dishCommentRepository;

    @Override
    public List<DishVo> getTop5VoteWeek() {
        return dishTopVoRepository.getTop5Dish(7);
    }

    @Override
    public List<DishVo> getTop5VoteMonth() {
        return dishTopVoRepository.getTop5Dish(30);
    }

    @Override
    public Page<DishFormulaVo> getAllRecipe(String searchData, Integer pageIndex, Integer pageSize) {
        if (searchData == null) {
            searchData = "";
        }
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return dishRepository.findAllRecipe("%" + searchData.trim() + "%",pageable);
    }

    @Override
    public Page<DishFormulaVo> getRecipeOfCreater(String creater, String searchData,Integer pageIndex, Integer pageSize) {
        if (searchData == null) {
            searchData = "";
        }
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return dishRepository.getRecipeOfCreater(creater, "%" + searchData.trim() + "%", pageable);
    }

    @Override
    public DishDetailVo getDishDetail(Integer dishID) {
        return dishRepository.getDishDetail(dishID);
    }

    public List<DishResponse> getDishByName(String name) {
        List<Dish> dishList = dishRepository.findDishByNameLike(name.trim());
        if (dishList.isEmpty()) {
            throw new NotFoundException(ErrorCode.Not_Found, "Don't have recipe for name " + name);
        }
        List<DishResponse> dishResponseList = new ArrayList<>();
        for (Dish d : dishList) {
            DishResponse dishResponse = new DishResponse();
            dishResponse.setDishID(d.getDishID());
            dishResponse.setName(d.getName());
            dishResponse.setLevel(d.getLevel());
            dishResponse.setSize(d.getSize());
            dishResponse.setDescription(d.getFormulaId().getSummary());
            dishResponse.setTime(d.getTime());
            dishResponse.setVerifier(d.getFormulaId().getAccount().getName());
            dishResponse.setCreateDate(d.getCreateDate());

            double star = 0;
            int quantity = 0;
            try {
                List<Object[]> objects = dishCommentRepository.getStarRate(d.getDishID());
                for (Object[] objects1 : objects) {
                    star = Double.parseDouble(objects1[0].toString());
                    quantity = (int) objects1[1];
                }
            } catch (NullPointerException e) {

            }
            double starRate = star / quantity;

            BigDecimal bd = new BigDecimal(starRate).setScale(2, RoundingMode.HALF_UP);
            double formatStarRate = bd.doubleValue();

            dishResponse.setStarRate(formatStarRate);

            dishResponse.setQuantityRate(quantity);


            for (DishImage di : d.getListDishImage()) {
                dishResponse.setImage(di.getUrl());
                break;
            }
            dishResponseList.add(dishResponse);
        }
        return dishResponseList;
    }

//    public List<DishResponse> getDishByCate(String cate) throws NotFoundException {
//        List<Dish> dishList = dishRepository.findDishByCategory(cate);
//        if (dishList.isEmpty()) {
//            throw new NotFoundException(ErrorCode.Not_Found, "Don't have recipe for category " + cate);
//        }
//        List<DishResponse> dishResponseList = new ArrayList<>();
//        dishMapper.mapperDishToDishRequest(dishList, dishResponseList);
//        return dishResponseList;
//    }

    public List<DishResponse> getTop5New() {
        List<Dish> dishList = dishRepository.getTop5ByNew();
        if (dishList.isEmpty()) {
            throw new NotFoundException(ErrorCode.Not_Found, "Top 5 recipe is null!!!");
        }
        List<DishImageResquest> dishImageResquestList;
        List<DishResponse> dishResponseList = new ArrayList<>();
        for (Dish d : dishList) {
            DishResponse dishResponse = new DishResponse();
            dishResponse.setDishID(d.getDishID());
            dishResponse.setName(d.getName());
            dishResponse.setLevel(d.getLevel());
            dishResponse.setSize(d.getSize());
            dishResponse.setDescription(d.getFormulaId().getSummary());
            dishResponse.setTime(d.getTime());
            dishResponse.setVerifier(d.getFormulaId().getAccount().getName());
            dishResponse.setCreateDate(d.getCreateDate());

            double star = 0;
            int quantity = 0;
            try {
                List<Object[]> objects = dishCommentRepository.getStarRate(d.getDishID());
                for (Object[] objects1 : objects) {
                    star = Double.parseDouble(objects1[0].toString());
                    quantity = (int) objects1[1];
                }
            } catch (NullPointerException e) {

            }
            double starRate = star / quantity;

            BigDecimal bd = new BigDecimal(starRate).setScale(2, RoundingMode.HALF_UP);
            double formatStarRate = bd.doubleValue();

            dishResponse.setStarRate(formatStarRate);

            dishResponse.setQuantityRate(quantity);

            for (DishImage di : d.getListDishImage()) {
                dishResponse.setImage(di.getUrl());
            }
            dishResponseList.add(dishResponse);
        }
        return dishResponseList;
    }


    public void CreateNewRecipe(Dish dishRequest) {
        Dish dish = new Dish();
        dish.setName(dishRequest.getName());
        dish.setOrigin(dishRequest.getOrigin());
        dish.setCalo(dishRequest.getCalo());
        dish.setLevel(dishRequest.getLevel());
        dish.setNumberPeopleForDish(dishRequest.getNumberPeopleForDish());
        dish.setSize(dishRequest.getSize());
        dish.setTime(dishRequest.getTime());
        dish.setVideo(dishRequest.getVideo());
        dish.setCreateDate(LocalDate.now());
        dish.setStatus(1);

        Formula formula = insertFormulaAndStep(dishRequest);
        dish.setFormulaId(formula);
        int dishId = dishRepository.save(dish).getDishID();
        dish.setDishID(dishId);
        dishRequest.setDishID(dishId);

        insertIngredientDetailAndIngredientChange(dishRequest);

        insertCategory(dish.getDishID(), dishRequest);
        insertImage(dishRequest);
    }

    @Override
    public void deleteRecipe(Integer recipeId) {
        Dish dish = dishRepository.getById(recipeId);
        dish.setStatus(3);
        dishRepository.save(dish);
    }

    void insertIngredientDetailAndIngredientChange(Dish dishRequest) {
        List<IngredientDetail> ingredientDetailList = dishRequest.getListIngredientDetail();
        for (IngredientDetail detail : ingredientDetailList) {
            IngredientDetail ingredientDetail = null;
            if (detail.getIngredientDetailID() != null) {
                ingredientDetail.setIngredientDetailID(detail.getIngredientDetailID());
            } else {
                ingredientDetail = new IngredientDetail();
            }
            ingredientDetail.setName(detail.getName());
            ingredientDetail.setQuantity(detail.getQuantity());
            ingredientDetail.setUnit(detail.getUnit());
            ingredientDetail.setCalo(detail.getCalo());
            ingredientDetail.setDishID(dishRequest);
            ingredientDetail.setIngredientDetailID(ingredientDetailRepository.save(ingredientDetail).getIngredientDetailID());

            IngredientChange change = null;
            if (detail.getIngredientChange().getIngredientChangeID() != null) {
                change.setIngredientChangeID(detail.getIngredientChange().getIngredientChangeID());
            } else {
                change = new IngredientChange();
            }
            change.setName(detail.getIngredientChange().getName());
            change.setQuantity(detail.getIngredientChange().getQuantity());
            change.setUnit(detail.getIngredientChange().getUnit());
            change.setCalo(detail.getIngredientChange().getCalo());
            change.setIngredientDetail(ingredientDetail);
            ingredientChangeRepository.save(change);
        }
    }

    void insertImage(Dish dishRequest) {
        List<DishImage> dishImages = dishRequest.getListDishImage();
        for (DishImage d : dishImages) {
            DishImage dishImage;
            if (d.getDishImageID() != null) {
                dishImage = dishImageRepository.getById(d.getDishImageID());
            } else {
                dishImage = new DishImage();
            }
            dishImage.setUrl(d.getUrl());
            dishImage.setNote(d.getNote());
            dishImage.setDishID(dishRequest);
            dishImageRepository.save(dishImage);
        }
    }

    void insertCategory(int dishId, Dish dishRequest) {
        List<DishCategory> dishCategories = dishRequest.getIdDishCategory();
        for (DishCategory dc : dishCategories) {
            dishRepository.insertDishCategory(dishId, dc.getDishCategoryID());
        }
    }

    Formula insertFormulaAndStep(Dish dishRequest) {
        Formula formula = null;
        if (dishRequest.getFormulaId().getFormulaID() != null) {
            formula.setFormulaID(dishRequest.getFormulaId().getFormulaID());
        } else {
            formula = new Formula();
        }
        formula.setDescribe(dishRequest.getFormulaId().getDescribe());
        formula.setSummary(dishRequest.getFormulaId().getSummary());

        Account account = accountRepository.findAccountByUserName(dishRequest.getFormulaId().getAccount().getUserName());
        if (account==null){
            throw new NotFoundException(ErrorCode.Not_Found,"tài khoản không có quyền hoặc đã bị khóa!!!");
        }

        formula.setAccount(account);
        formula.setFormulaID(formulaRepository.save(formula).getFormulaID());


        Set<Step> stepSet = dishRequest.getFormulaId().getListStep();
        for (Step s : stepSet) {
            Step step = null;
            if (s.getFormulaID() != null) {
                step.setStepID(s.getStepID());
            } else {
                step = new Step();
            }
            step.setDescribe(s.getDescribe());
            step.setTitle(s.getTitle());
            step.setFormulaID(formula);
            stepRepository.save(step);
        }
        return formula;
    }

    @Override
    public void editRecipe(Dish dishRequest) {
        Dish dish = dishRepository.getById(dishRequest.getDishID());
        dish.setName(dishRequest.getName());
        dish.setOrigin(dishRequest.getOrigin());
        dish.setCalo(dishRequest.getCalo());
        dish.setLevel(dishRequest.getLevel());
        dish.setNumberPeopleForDish(dishRequest.getNumberPeopleForDish());
        dish.setSize(dishRequest.getSize());
        dish.setTime(dishRequest.getTime());
        dish.setVideo(dishRequest.getVideo());
        dish.setCreateDate(LocalDate.now());

        Formula formula = updateFormulaAndStep(dishRequest);
        dish.setFormulaId(formula);
        dishRepository.save(dish);

        updateIngredientDetailAndIngredientChange(dishRequest);

        updateCategory(dish.getDishID(), dishRequest);
        updateImage(dishRequest);
    }

    void updateIngredientDetailAndIngredientChange(Dish dishRequest) {
        List<IngredientDetail> ingredientDetailList = dishRequest.getListIngredientDetail();
        for (IngredientDetail detail : ingredientDetailList) {
            IngredientDetail ingredientDetail = null;
            if (detail.getIngredientDetailID() != null) {
                ingredientDetail.setIngredientDetailID(detail.getIngredientDetailID());
            } else {
                ingredientDetail = new IngredientDetail();
            }
            ingredientDetail.setName(detail.getName());
            ingredientDetail.setQuantity(detail.getQuantity());
            ingredientDetail.setUnit(detail.getUnit());
            ingredientDetail.setCalo(detail.getCalo());
            ingredientDetail.setDishID(dishRequest);
            ingredientDetail.setIngredientDetailID(ingredientDetailRepository.save(ingredientDetail).getIngredientDetailID());

            IngredientChange change = null;
            if (detail.getIngredientChange().getIngredientChangeID() != null) {
                change.setIngredientChangeID(detail.getIngredientChange().getIngredientChangeID());
            } else {
                change = new IngredientChange();
            }
            change.setName(detail.getIngredientChange().getName());
            change.setQuantity(detail.getIngredientChange().getQuantity());
            change.setUnit(detail.getIngredientChange().getUnit());
            change.setCalo(detail.getIngredientChange().getCalo());
            change.setIngredientDetail(ingredientDetail);
            ingredientChangeRepository.save(change);
        }
    }

    void updateImage(Dish dishRequest) {
        List<DishImage> dishImages = dishRequest.getListDishImage();
        for (DishImage d : dishImages) {
            DishImage dishImage;
            if (d.getDishImageID() != null) {
                dishImage = dishImageRepository.getById(d.getDishImageID());
            } else {
                dishImage = new DishImage();
            }
            dishImage.setUrl(d.getUrl());
            dishImage.setNote(d.getNote());
            dishImage.setDishID(dishRequest);
            dishImageRepository.save(dishImage);
        }
    }

    void updateCategory(int dishId, Dish dishRequest) {
        List<DishCategory> dishCategories = dishRequest.getIdDishCategory();
        for (DishCategory dc : dishCategories) {
            dishRepository.insertDishCategory(dishId, dc.getDishCategoryID());
        }
    }

    Formula updateFormulaAndStep(Dish dishRequest) {
        Formula formula = null;
        if (dishRequest.getFormulaId().getFormulaID() != null) {
            formula.setFormulaID(dishRequest.getFormulaId().getFormulaID());
        } else {
            formula = new Formula();
        }
        formula.setDescribe(dishRequest.getFormulaId().getDescribe());
        formula.setSummary(dishRequest.getFormulaId().getSummary());

        Account account = accountRepository.findAccountByUserName(dishRequest.getFormulaId().getAccount().getUserName());
        if (account==null){
            throw new NotFoundException(ErrorCode.Not_Found,"tài khoản không có quyền hoặc đã bị khóa!!!");
        }

        formula.setAccount(account);
        formula.setFormulaID(formulaRepository.save(formula).getFormulaID());


        Set<Step> stepSet = dishRequest.getFormulaId().getListStep();
        for (Step s : stepSet) {
            Step step = null;
            if (s.getFormulaID() != null) {
                step.setStepID(s.getStepID());
            } else {
                step = new Step();
            }
            step.setDescribe(s.getDescribe());
            step.setTitle(s.getTitle());
            step.setFormulaID(formula);
            stepRepository.save(step);
        }
        return formula;
    }

    @Override
    public DishDetailVo getDishByBMIUser(String meal, String mainIngredient, Integer calo) {
        Integer CaloDetail = 0;
        if(meal.equals("Bua sang")){
            CaloDetail = calo*25/100;
        } else if(meal.equals("Bua trua")){
            CaloDetail = calo*25/100;
        } else {
            CaloDetail = calo*25/100;
        }
        List<DishDetailVo> a = dishRepository.getDishByBMIUser(meal, mainIngredient, CaloDetail);
        return a.get(0);
    }

    @Override
    public List<Integer> getListDishByBMIUser(Integer totalCalo) {
        List<Integer> listID = new ArrayList<>();
        int breakfastCalo = totalCalo*25/100;
        int lunchCalo = totalCalo*25/100;
        int dinnerCalo = totalCalo*15/100;

        List<DishDetailVo> listDishBreakfastByBMIUser = new ArrayList<DishDetailVo>();
        listDishBreakfastByBMIUser = dishRepository.getListDishByBMIUser(breakfastCalo , "bca");
        List<DishDetailVo> listDishLunchByBMIUser = dishRepository.getListDishByBMIUser(lunchCalo, "luoc");
        List<DishDetailVo> listDishDinnerByBMIUser = dishRepository.getListDishByBMIUser(dinnerCalo, "luoc");

        Collections.shuffle(listDishBreakfastByBMIUser);
        Collections.shuffle(listDishLunchByBMIUser);
        Collections.shuffle(listDishDinnerByBMIUser);

        int dishBreakfastId = 0;
        int dishLunchId = 0;
        int dishDinnerId = 0;

        for (int i = 0; i < listDishBreakfastByBMIUser.size(); i++) {
            dishBreakfastId = listDishBreakfastByBMIUser.get(i).getDishID();
            break;
        }

        for (int i = 0; i < listDishLunchByBMIUser.size(); i++) {
            dishLunchId = listDishLunchByBMIUser.get(i).getDishID();
            break;
        }

        for (int i = 0; i < listDishDinnerByBMIUser.size(); i++) {
            dishDinnerId = listDishDinnerByBMIUser.get(i).getDishID();
            break;
        }

        listID.add(dishBreakfastId);
        listID.add(dishLunchId);
        listID.add(dishDinnerId);

        return listID;
    }

//    @Override
//    public void getDishById(Integer id) {
//        Dish dish = dishRepository.getById(id);
//        if(dish==null){
//            throw new NotFoundException(ErrorCode.Not_Found,"không tìm thấy món ăn hoặc món ăn đã bị xóa!!!");
//        }
//        DishEditResponse response = new DishEditResponse();
//        response.setName(dishRequest.getName());
//        response.setOrigin(dishRequest.getOrigin());
//        response.setCalo(dishRequest.getCalo());
//        response.setLevel(dishRequest.getLevel());
//        response.setNumberPeopleForDish(dishRequest.getNumberPeopleForDish());
//        response.setSize(dishRequest.getSize());
//        response.setTime(dishRequest.getTime());
//        response.setVideo(dishRequest.getVideo());
//        response.setCreateDate(LocalDate.now());
//    }
}

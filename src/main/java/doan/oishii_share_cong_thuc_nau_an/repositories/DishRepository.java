package doan.oishii_share_cong_thuc_nau_an.repositories;

import doan.oishii_share_cong_thuc_nau_an.common.vo.DishDetailVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishFormulaVo;
import doan.oishii_share_cong_thuc_nau_an.web.entities.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DishRepository extends JpaRepository<Dish, Integer> {


    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.DishFormulaVo" +
            "(d.dishID,d.name, cast(d.createDate as string) ,f.formulaID,f.describe,a.userName,f.summary)" +
            " from Dish d join d.formulaId f join f.account a " +
            "where d.status = 1 and a.status <> 3 and (cast(d.dishID as string ) LIKE :searchData " +
            "or d.name LIKE :searchData)")
    public Page<DishFormulaVo> findAllRecipe(@Param("searchData") String searchData, Pageable pageable);

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.DishFormulaVo" +
            "(d.dishID,d.name, cast(d.createDate as string) ,f.formulaID,f.describe,a.userName,f.summary)" +
            " from Dish d join d.formulaId f join f.account a where a.userName = :creater " +
            "AND (cast(d.dishID as string ) LIKE :searchData " +
            "OR d.name LIKE :searchData) and d.status = 1 and a.status <> 3")
    public Page<DishFormulaVo> getRecipeOfCreater(String creater, @Param("searchData") String searchData, Pageable pageable);


    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.DishDetailVo(" +
            "d.dishID,d.name,d.origin,d.calo, d.level, d.numberPeopleForDish,d.size, cast(d.createDate as string), d.video, " +
            "f.formulaID,f.describe,a.userName,f.summary) " +
            "from Dish d join d.formulaId f join f.account a where  d.dishID = :dishID")
    public DishDetailVo getDishDetail(Integer dishID);

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.DishDetailVo(" +
            "d.dishID,d.name,d.origin,d.calo, d.level, d.numberPeopleForDish,d.size, cast(d.createDate as string), d.video , " +
            "f.formulaID,f.describe,a.userName,f.summary) from Dish d join d.formulaId f join f.account a where  d.dishID = :dishID")
    public DishDetailVo getDishDetail2(Integer dishID);

    @Query("select d from Dish d where d.name like %?1% and d.status=1")
    public List<Dish> findDishByNameLike(String name,Pageable pageable);

    @Query(value = "select d.* from dish d left join dish_dish_category ddc on d.dish_id = ddc.dish_id\n" +
            "\t\t\t\t\t join dish_category dc on ddc.dish_categoryid = dc.dish_category_id\n" +
            "\t\t\t\t\t where dc.dish_category_id = :id and d.status=1", nativeQuery = true)
    public List<Dish> findDishByDishCategory(Integer id,Pageable pageable);

    @Query(value = "select distinct top 5 * from dish d where d.status=1 order by d.create_date desc ", nativeQuery = true)
    public List<Dish> getTop5ByNew();

    @Query(value = "insert into dish_dish_category(dish_id,dish_categoryid)\n" +
            "values (:dishId,:cateId)", nativeQuery = true)
    @Modifying
    @Transactional
    public void insertDishCategory(@Param("dishId") int dishId, @Param("cateId") int cateId);

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.DishDetailVo(" +
            "d.dishID,d.name,d.origin,d.calo, d.level, d.numberPeopleForDish,d.size, cast(d.createDate as string), d.video, " +
            "f.formulaID,f.describe,a.userName,f.summary) " +
            "from Dish d join d.listIngredientDetail lid " +
            "join d.idDishCategory dc " +
            "join d.formulaId f join f.account a " +
            "where dc.name = :meal and lid.name = :mainIngredient and d.calo >= 0 and d.calo <= :calo")
    public List<DishDetailVo> getDishByBMIUser(String meal, String mainIngredient, Integer calo);

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.DishDetailVo(" +
            "d.dishID,d.name,d.origin,d.calo, d.level, d.numberPeopleForDish,d.size, cast(d.createDate as string), d.video, " +
            "f.formulaID,f.describe,a.userName,f.summary) " +
            "from Dish d join d.idDishCategory dc join d.formulaId f join f.account a " +
            "where d.calo >= 0 and d.calo <= :calo and dc.name = :meal")
    public List<DishDetailVo> getListDishByBMIUser(Integer calo, String meal);

    @Query("select d from Dish d where d.status=1 and d.dishID = :id")
    public Dish findByDishID(Integer id);
}

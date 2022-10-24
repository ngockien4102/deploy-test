package doan.oishii_share_cong_thuc_nau_an.repositories;

import doan.oishii_share_cong_thuc_nau_an.common.vo.DishImageVo;
import doan.oishii_share_cong_thuc_nau_an.web.entities.DishImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishImageRepository extends JpaRepository<DishImage, Integer> {

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.DishImageVo(" +
            "di.dishImageID, di.url, di.note) from DishImage di where di.dishID.dishID = :dishId")
    public List<DishImageVo> findByDishID(Integer dishId);


}

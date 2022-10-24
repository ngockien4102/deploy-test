package doan.oishii_share_cong_thuc_nau_an.repositories;


import doan.oishii_share_cong_thuc_nau_an.common.vo.DishVo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishTopVoRepository extends JpaRepository<DishVo, Integer> {

    @Query(name = "DishVoQuery", nativeQuery = true)
    List<DishVo> getTop5Dish(Integer numberDay);

}

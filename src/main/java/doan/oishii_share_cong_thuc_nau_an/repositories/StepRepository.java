package doan.oishii_share_cong_thuc_nau_an.repositories;


import doan.oishii_share_cong_thuc_nau_an.common.vo.DishVo;
import doan.oishii_share_cong_thuc_nau_an.web.entities.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StepRepository extends JpaRepository<Step, Integer> {



    @Query("select s from Step s where s.formulaID.formulaID = :formulaId")
    public List<Step> findByFormulaID(Integer formulaId);
}

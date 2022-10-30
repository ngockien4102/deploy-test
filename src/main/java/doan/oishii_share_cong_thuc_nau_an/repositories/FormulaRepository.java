package doan.oishii_share_cong_thuc_nau_an.repositories;

import doan.oishii_share_cong_thuc_nau_an.web.entities.Formula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormulaRepository extends JpaRepository<Formula,Integer> {
    public Formula findByDish_DishID(Integer dishId);
}

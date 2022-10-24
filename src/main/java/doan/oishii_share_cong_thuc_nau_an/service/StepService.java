package doan.oishii_share_cong_thuc_nau_an.service;

import doan.oishii_share_cong_thuc_nau_an.web.entities.Step;

import java.util.List;

public interface StepService {

     List<Step> findByFormulaID(Integer dishId);
}

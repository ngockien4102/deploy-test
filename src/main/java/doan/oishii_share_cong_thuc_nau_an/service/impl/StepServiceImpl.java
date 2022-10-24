package doan.oishii_share_cong_thuc_nau_an.service.impl;

import doan.oishii_share_cong_thuc_nau_an.repositories.StepRepository;
import doan.oishii_share_cong_thuc_nau_an.service.StepService;
import doan.oishii_share_cong_thuc_nau_an.web.entities.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StepServiceImpl implements StepService {

    @Autowired
    private StepRepository stepRepository;

    @Override
    public List<Step> findByFormulaID(Integer formulaId) {
        return stepRepository.findByFormulaID(formulaId);
    }
}

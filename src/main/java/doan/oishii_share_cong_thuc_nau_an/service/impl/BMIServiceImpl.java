package doan.oishii_share_cong_thuc_nau_an.service.impl;

import doan.oishii_share_cong_thuc_nau_an.Exception.NotFoundException;
import doan.oishii_share_cong_thuc_nau_an.common.vo.BMIVo;
import doan.oishii_share_cong_thuc_nau_an.repositories.BMIRepository;
import doan.oishii_share_cong_thuc_nau_an.service.BMIService;
import doan.oishii_share_cong_thuc_nau_an.web.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BMIServiceImpl implements BMIService {

    @Autowired
    private BMIRepository bmiRepository;
    @Override
    public BMIVo getInformationBMIByUser(String username) {
        Account account = bmiRepository.getInformationBMIUser(username);
        BMIVo bmiVo = new BMIVo();
        bmiVo.setName(account.getName());
        bmiVo.setHigh(account.getHigh());
        bmiVo.setWeight(account.getWeight());
        bmiVo.setMobility(account.getMobility());
        return bmiVo;
    }

    @Override
    public void updateProfile(String username, Double H, Double W, Double R) {
        Account account = bmiRepository.findByUserName(username).orElseThrow(() -> new NotFoundException(0,"Username " + username + " Not exist or account was blocked "));
        account.setHigh(H);
        account.setWeight(W);
        account.setMobility(R);
        bmiRepository.save(account);
    }
}

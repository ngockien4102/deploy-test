package doan.oishii_share_cong_thuc_nau_an.service.impl;

import doan.oishii_share_cong_thuc_nau_an.Exception.ErrorCode;
import doan.oishii_share_cong_thuc_nau_an.Exception.NotFoundException;
import doan.oishii_share_cong_thuc_nau_an.common.vo.BMIVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.MessageVo;
import doan.oishii_share_cong_thuc_nau_an.dto.Requests.BMI.BMIRequest;
import doan.oishii_share_cong_thuc_nau_an.repositories.BMIRepository;
import doan.oishii_share_cong_thuc_nau_an.service.BMIService;
import doan.oishii_share_cong_thuc_nau_an.web.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.ws.Response;
import java.time.LocalDate;
import java.time.Period;

@Service
public class BMIServiceImpl implements BMIService {

    @Autowired
    private BMIRepository bmiRepository;

    @Override
    public ResponseEntity<?> getInformationBMIByUser(String username) {
        Account account = bmiRepository.getInformationBMIUser(username);
        if( account.getGender() == null || account.getDob() == null){
            return ResponseEntity.ok(new MessageVo("Thông tin ngày tháng năm sinh hoặc giới tính của bạn đang trống. Yêu cầu update thông tin account.", "error"));
        }

        BMIVo bmiVo = new BMIVo();
        bmiVo.setUsername(account.getUserName());
        bmiVo.setName(account.getName());
        bmiVo.setGender(account.getGender());
        bmiVo.setHigh(account.getHigh());
        bmiVo.setWeight(account.getWeight());
        bmiVo.setMobility(account.getMobility());
        bmiVo.setTotalCalo(account.getTotalCalo());
        bmiVo.setTarget(account.getTarget());
        bmiVo.setDob(account.getDob());
        return ResponseEntity.ok(bmiVo);
    }

    @Override
    public void updateProfile(BMIRequest bmiRequest) {
        if(bmiRequest.getHigh().equals("") || bmiRequest.getWeight().equals("") || bmiRequest.getDob().equals("") || bmiRequest.getGender().equals("") || bmiRequest.getR().equals("") || bmiRequest.getTarget().equals("")){
            throw new NotFoundException(ErrorCode.Not_Found, "Bạn chưa nhập đủ thông tin BMI yêu cầu kiểm tra lại");
        }
        Account account = bmiRepository.findByUserName(bmiRequest.getUsername()).orElseThrow(() -> new NotFoundException(0,"Username " + bmiRequest.getUsername() + " Not exist or account was blocked "));
        Double totalCalo = 0.0;
        Double weight = bmiRequest.getWeight();
        Double high = bmiRequest.getHigh();
        Double r = bmiRequest.getR();
        int dob =(Period.between(bmiRequest.getDob(), LocalDate.now()).getYears());
        if(bmiRequest.getGender().equals("Nam")){
            totalCalo = (66 + (6.2 * weight) + (12.7 * high) -(6.76 * dob)) * r;
        } else if(bmiRequest.getGender().equals("Nữ")) {
            totalCalo = (655.1 + (4.35 * weight) + (4.7 * high) -(4.7 * dob)) * r;
        }

        if(bmiRequest.getTarget().equals("Tăng cân")){
            totalCalo = totalCalo + 500;
        } else if (bmiRequest.getTarget().equals("Giảm cân")) {
            totalCalo = totalCalo - 500;
        }

        account.setHigh(high);
        account.setWeight(weight);
        account.setMobility(r);
        account.setTotalCalo((double) Math.round(totalCalo));
        account.setTarget(bmiRequest.getTarget());
        bmiRepository.save(account);
    }
}

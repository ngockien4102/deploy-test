package doan.oishii_share_cong_thuc_nau_an.service;

import doan.oishii_share_cong_thuc_nau_an.Exception.BadRequestException;
import doan.oishii_share_cong_thuc_nau_an.common.vo.BMIVo;
import doan.oishii_share_cong_thuc_nau_an.dto.Requests.BMI.BMIRequest;
import doan.oishii_share_cong_thuc_nau_an.dto.Requests.ProfileRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
public interface BMIService {

    ResponseEntity<?> getInformationBMIByUser(String username);

    public void updateProfile(BMIRequest bmiRequest);
}

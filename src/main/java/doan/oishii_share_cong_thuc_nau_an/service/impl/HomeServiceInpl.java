package doan.oishii_share_cong_thuc_nau_an.service.impl;

import doan.oishii_share_cong_thuc_nau_an.Exception.ErrorCode;
import doan.oishii_share_cong_thuc_nau_an.Exception.NotFoundException;
import doan.oishii_share_cong_thuc_nau_an.dto.Requests.ProfileEditRequest;
import doan.oishii_share_cong_thuc_nau_an.dto.Requests.ProfileRequest;
import doan.oishii_share_cong_thuc_nau_an.repositories.AccountRepository;
import doan.oishii_share_cong_thuc_nau_an.service.HomeService;
import doan.oishii_share_cong_thuc_nau_an.web.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class HomeServiceInpl implements HomeService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public ProfileRequest getProfile(Integer profileId) throws NotFoundException {
        Account account = accountRepository.findById(profileId).orElseThrow(() -> new NotFoundException(ErrorCode.Not_Found,"Không tìm thấy người dùng!!!"));
        ProfileRequest request = new ProfileRequest();
        request.setProfileId(account.getAccountId());
        request.setName(account.getName());
        request.setUserName(account.getUserName());
        request.setAddress(account.getAddress());
        request.setDob(account.getDob());
        request.setEmail(account.getEmail());
        request.setGender(account.getGender());
        request.setHigh(account.getHigh());
        request.setPhone(account.getPhone());
        request.setWeight(account.getWeight());
        request.setStatus(account.getStatus());
        request.setRole(account.getRole());
        request.setAvatarImage(account.getAvatarImage());
        request.setUpdateDate(account.getUpdateDate());
        request.setCreateDate(account.getCreateDate());
        return request;
    }

    @Override
    public void updateProfile(Integer profileId, ProfileEditRequest profileRequest) throws NotFoundException {
        Account account = accountRepository.findById(profileId).orElseThrow(() -> new NotFoundException(ErrorCode.Not_Found,"Không tìm thấy người dùng!!!"));
        account.setName(profileRequest.getName());
        account.setAddress(profileRequest.getAddress());
        account.setDob(profileRequest.getDob());
        account.setEmail(profileRequest.getEmail());
        account.setGender(profileRequest.getGender());
        account.setHigh(profileRequest.getHigh());
        account.setPhone(profileRequest.getPhone());
        account.setWeight(profileRequest.getWeight());
        account.setUpdateDate(LocalDate.now());
        account.setAvatarImage(profileRequest.getAvatarImage());
        accountRepository.save(account);
    }
}

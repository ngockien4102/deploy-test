package doan.oishii_share_cong_thuc_nau_an.service;

import doan.oishii_share_cong_thuc_nau_an.Exception.BadRequestException;
import doan.oishii_share_cong_thuc_nau_an.dto.Requests.ProfileEditRequest;
import doan.oishii_share_cong_thuc_nau_an.dto.Requests.ProfileRequest;

public interface HomeService {
    ProfileRequest getProfile(Integer username);

    void updateProfile(Integer profileId, ProfileEditRequest profileRequest) throws BadRequestException;
}

package doan.oishii_share_cong_thuc_nau_an.dto.Requests;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileEditRequest {

    private String name;

    private String email;

    private LocalDate dob;

    private String gender;

    private String phone;

    private String address;

    private Double high;

    private Double weight;

    private String avatarImage;
}

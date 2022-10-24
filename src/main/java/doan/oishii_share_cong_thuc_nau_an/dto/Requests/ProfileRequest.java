package doan.oishii_share_cong_thuc_nau_an.dto.Requests;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileRequest {

    private int profileId;

    private String name;

    private String userName;

    private String email;

    private LocalDate dob;

    private String gender;

    private String phone;

    private String address;

    private int status;

    private String role;

    private Double high;

    private Double weight;

    private LocalDate createDate;

    private LocalDate updateDate;

    private String avatarImage;

}

package doan.oishii_share_cong_thuc_nau_an.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@NoArgsConstructor
@Data
public class BMIVo {

    private int profileId;

    private String name;

    private String email;

    private LocalDate dob;

    private String gender;

    private String phone;

    private String address;

    private int status;

    private String role;

    private Double high;

    private Double weight;

    private Double mobility;



    public BMIVo(int profileId, String name, String email, LocalDate dob, String gender, String phone, String address, int status, String role, Double high, Double weight, Double mobility) {
        this.profileId = profileId;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.status = status;
        this.role = role;
        this.high = high;
        this.weight = weight;
        this.mobility = mobility;
    }
}

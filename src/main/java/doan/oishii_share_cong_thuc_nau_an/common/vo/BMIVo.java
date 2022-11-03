package doan.oishii_share_cong_thuc_nau_an.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@NoArgsConstructor
@Data
public class BMIVo {

    private String username;

    private String name;

    private LocalDate dob;

    private String gender;

    private Double high;

    private Double weight;

    private Double mobility;

    private Double totalCalo;

    private String target;
}

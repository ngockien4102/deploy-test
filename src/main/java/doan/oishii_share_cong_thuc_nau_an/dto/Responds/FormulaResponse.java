package doan.oishii_share_cong_thuc_nau_an.dto.Responds;

import doan.oishii_share_cong_thuc_nau_an.web.entities.Account;
import lombok.Data;

@Data
public class FormulaResponse {
    private Integer formulaID;
    private String describe;
    private String summary;
    private String createdBy;
}

package doan.oishii_share_cong_thuc_nau_an.Exception;

import lombok.Data;

@Data
public class ErrorCode {
    public static final int Not_Found = 80801;
    public static final int INTERNAL_SERVER_ERROR = Not_Found + 1;
}

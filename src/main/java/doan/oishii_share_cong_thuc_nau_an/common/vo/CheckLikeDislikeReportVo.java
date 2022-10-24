package doan.oishii_share_cong_thuc_nau_an.common.vo;

import doan.oishii_share_cong_thuc_nau_an.web.entities.Account;
import doan.oishii_share_cong_thuc_nau_an.web.entities.DishComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckLikeDislikeReportVo {


    private Integer accountId;

    private Integer dishCommentId;

    private Integer checkLike;

    private Integer checkDislike;

    private Integer checkReport;

    public CheckLikeDislikeReportVo( Integer checkLike, Integer checkDislike,Integer checkReport) {
        this.checkLike = checkLike;
        this.checkDislike = checkDislike;
        this.checkReport =checkReport;
    }
    public CheckLikeDislikeReportVo(Integer checkLike, Integer checkDislike) {
        this.checkLike = checkLike;
        this.checkDislike = checkDislike;
    }

}

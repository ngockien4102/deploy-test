package doan.oishii_share_cong_thuc_nau_an.service;

import doan.oishii_share_cong_thuc_nau_an.common.vo.BlogCommentAccountVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.CheckLikeDislikeReportVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishCommentAccountVo;
import doan.oishii_share_cong_thuc_nau_an.web.entities.Account;
import doan.oishii_share_cong_thuc_nau_an.web.entities.DishComment;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BlogCommentService {
    public Page<BlogCommentAccountVo> findBlogCommentByBlogId(Integer blogId, Integer pageIndex, Integer pageSize);

    ResponseEntity<?> createComment(Integer blogId, String content,  Account account);

    ResponseEntity<?> updateComment(Integer blogId, Integer blogCommentId, String content,  Account account);

    ResponseEntity<?>  reportComment(Integer blogCommentId , Account account);

    ResponseEntity<?> likeBlogComment(Integer blogCommentId, Account account);

    ResponseEntity<?> dislikeBlogComment(Integer blogCommentId, Account account);

    ResponseEntity<?> deleteBlogComment(Integer blogCommentId,  Account account);

    Page<BlogCommentAccountVo> findReportBlogComment(String searchData,Integer pageIndex, Integer pageSize);

    ResponseEntity<?> approveComment( Integer blogCommentId);
}

package doan.oishii_share_cong_thuc_nau_an.repositories;

import doan.oishii_share_cong_thuc_nau_an.common.vo.BlogVo;
import doan.oishii_share_cong_thuc_nau_an.web.entities.Blog;
import doan.oishii_share_cong_thuc_nau_an.web.entities.DishCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {


    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.BlogVo(" +
            "b.blogID, b.title, b.content,b.totalLike,b.totalDisLike," +
            "b.numberComment, a.accountId, a.userName, a.avatarImage , b.status)" +
            " from Blog b join b.account a where b.status = 1 and a.status <> 3" +
            " and ( b.title like :searchData or a.userName like :searchData ) order by b.blogID desc " )
    public Page<BlogVo> getListBlogActive (String searchData, Pageable pageable);

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.BlogVo(" +
            "b.blogID, b.title, b.content,b.totalLike,b.totalDisLike," +
            "b.numberComment, a.accountId, a.userName, a.avatarImage, b.status )" +
            " from Blog b join b.account a where b.status = 0 and a.status <> 3" +
            " and ( b.title like :searchData or a.userName like :searchData ) order by b.blogID desc" )
    public Page<BlogVo> getListBlogPending (String searchData, Pageable pageable);

    @Query("select new doan.oishii_share_cong_thuc_nau_an.common.vo.BlogVo(" +
            "b.blogID, b.title, b.content,b.totalLike,b.totalDisLike," +
            "b.numberComment, a.accountId, a.userName, a.avatarImage, b.status )" +
            " from Blog b join b.account a where b.status = 1 and a.status <> 3" +
            " and b.blogID = :blogId" )
    public BlogVo getBlogDetail (Integer blogId);




}

package doan.oishii_share_cong_thuc_nau_an.web.controller;

import doan.oishii_share_cong_thuc_nau_an.common.vo.BlogVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishCommentAccountVo;
import doan.oishii_share_cong_thuc_nau_an.service.DishCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ReportManageController {

    @Autowired
    private DishCommentService dishCommentService;

    //danh sách những comment bị report
    @GetMapping("/admin/getListDishCommentReport")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getListBlog(Model model, @RequestParam(required = false) String searchData ,
                                         @RequestParam(required = false) Integer pageIndex) {
        if (pageIndex == null) {
            pageIndex = 1;
        }
        Page<DishCommentAccountVo> dishCommentAccountVoList = dishCommentService.findReportDishComment(searchData,pageIndex-1, 5);
        model.addAttribute("dishCommentAccountVoList", dishCommentAccountVoList.toList());
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("numOfPages", dishCommentAccountVoList.getTotalPages());
        return ResponseEntity.ok(model);
    }

}

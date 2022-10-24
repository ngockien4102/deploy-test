package doan.oishii_share_cong_thuc_nau_an.web.controller;

import doan.oishii_share_cong_thuc_nau_an.common.vo.AccountManageVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.DishFormulaVo;
import doan.oishii_share_cong_thuc_nau_an.service.AccountService;
import doan.oishii_share_cong_thuc_nau_an.web.entities.Account;
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
public class AccountManageController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/admin/listAccount")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllAccountActive(Model model, @RequestParam(required = false) String searchData,
                                                 @RequestParam(required = false) Integer pageIndex) {
        if (pageIndex == null) {
            pageIndex = 1;
        }
        Page<AccountManageVo> listAccountActive = accountService.findAll(searchData,pageIndex-1, 5 );
        model.addAttribute("listAccountActive", listAccountActive.toList());
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("numOfPages", listAccountActive.getTotalPages());
        return ResponseEntity.ok(model);
    }


}

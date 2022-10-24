package doan.oishii_share_cong_thuc_nau_an.service.impl;

import doan.oishii_share_cong_thuc_nau_an.common.vo.AccountManageVo;
import doan.oishii_share_cong_thuc_nau_an.repositories.AccountRepository;
import doan.oishii_share_cong_thuc_nau_an.service.AccountService;
import doan.oishii_share_cong_thuc_nau_an.web.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class AccountServiceImpl implements AccountService {


    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void updateResetPassword(String newPassword, String email) throws AccountNotFoundException{

        Account account = accountRepository.findAccountByEmail(email);
        if (account != null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(newPassword);
            account.setPassword(encodedPassword);
            accountRepository.save(account);
        } else {
            throw new AccountNotFoundException("Could not find any account with the email " + email);
        }
    }

    @Override
    public void changePassword(String userName, String newPass) throws AccountNotFoundException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            Account account = accountRepository.findAccountByUserName(userName);
            if(account != null){
                String encodedPassword = passwordEncoder.encode(newPass);
                account.setPassword(encodedPassword);
                accountRepository.save(account);
            }else {
                throw new AccountNotFoundException("Could not find any account with the username " + userName);
            }
    }

    @Override
    public Page<AccountManageVo> findAll(String searchData,Integer pageIndex, Integer pageSize) {
        if (searchData == null) {
            searchData = "";
        }
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return accountRepository.findAll("%" + searchData.trim() + "%",pageable);
    }


    @Override
    public void updateResetPasswordToken(String token, String email) throws AccountNotFoundException{

        Account account = accountRepository.findAccountByEmail(email);
        if (account != null) {
            account.setResetPasswordToken(token);
            accountRepository.save(account);
        } else {
            throw new AccountNotFoundException("Could not find any customer with the email " + email);
        }
    }

    @Override
    public Account getByResetPasswordToken(String token) {
        return accountRepository.findByResetPasswordToken(token);
    }

    @Override
    public void updatePassword(Account account, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        account.setPassword(encodedPassword);
        account.setResetPasswordToken(null);
        accountRepository.save(account);
    }


}

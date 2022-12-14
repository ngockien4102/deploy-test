package doan.oishii_share_cong_thuc_nau_an.web.controller;

import doan.oishii_share_cong_thuc_nau_an.common.logging.LogUtils;
import doan.oishii_share_cong_thuc_nau_an.common.utils.JwtResponse;
import doan.oishii_share_cong_thuc_nau_an.common.utils.JwtUtils;
import doan.oishii_share_cong_thuc_nau_an.common.utils.LoginRequest;
import doan.oishii_share_cong_thuc_nau_an.common.utils.SignupRequest;
import doan.oishii_share_cong_thuc_nau_an.common.vo.MessageVo;
import doan.oishii_share_cong_thuc_nau_an.common.vo.UserDetailsImpl;
import doan.oishii_share_cong_thuc_nau_an.repositories.AccountRepository;
import doan.oishii_share_cong_thuc_nau_an.web.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:3000")
public class LoginController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	AccountRepository accountRepository;


	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest){
		LogUtils.getLog().info("START login");
		if(loginRequest.getUsername() == null || loginRequest.getUsername() == ""){
			return ResponseEntity
					.badRequest()
					.body(new MessageVo( "Ch??a nh???p t??n ng?????i d??ng","error"));
		}
		if(loginRequest.getPassword() == null || loginRequest.getPassword() == ""){
			return ResponseEntity
					.badRequest()
					.body(new MessageVo( "Ch??a nh???p m???t kh???u","error"));
		}
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		Account account = accountRepository.findAccountByUserName(userDetails.getUsername());
		LogUtils.getLog().info("END login");
		return ResponseEntity.ok(new JwtResponse(jwt,
				userDetails.getId(),
				userDetails.getUsername(),
				roles, account.getAvatarImage()));
		}

	@PostMapping("/signout")
	public  ResponseEntity<?> logout(){
		ResponseCookie cookie  = jwtUtils.getCleanJwtCookie();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).
				body(new MessageVo("B???n ???? ????ng xu???t", "info"));
		}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		LogUtils.getLog().info("START registerUser");
		if(signUpRequest.getUsername() == null || signUpRequest.getUsername() == ""){
			return ResponseEntity
					.badRequest()
					.body(new MessageVo( "Ch??a nh???p t??n ng?????i d??ng","error"));
		}
		if(signUpRequest.getPassword() == null || signUpRequest.getPassword() == ""){
			return ResponseEntity
					.badRequest()
					.body(new MessageVo( "Ch??a nh???p m???t kh???u","error"));
		}
		if(signUpRequest.getEmail() == null || signUpRequest.getEmail() == ""){
			return ResponseEntity
					.badRequest()
					.body(new MessageVo( "Ch??a nh???p email","error"));
		}
		if(signUpRequest.getFullname() == null || signUpRequest.getFullname() == ""){
			return ResponseEntity
					.badRequest()
					.body(new MessageVo( "Ch??a nh???p h??? t??n c???a b???n","error"));
		}
		if (accountRepository.existsByUserName(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageVo( "T??n ng?????i d??ng ???? t???n t???i","error"));
		}

		if (accountRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageVo("Email ???? t???n t???i", "error"));
		}

		// Create new user's account
		Account account = new Account(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()),
				signUpRequest.getEmail(), signUpRequest.getFullname());


		account.setRole("ROLE_USER");
		account.setStatus(1);
		accountRepository.save(account);
		LogUtils.getLog().info("END registerUser");
		return ResponseEntity.ok(new MessageVo("B???n ???? ????ng k?? th??nh c??ng", "info"));
	}

//	@PostMapping ("/loginsuccess")
//	public ResponseEntity<?> login(Principal principal) {
//		UserDetails loginedAccount = (UserDetails) ((Authentication) principal).getPrincipal();
//		Account account = accountRepository.findByUserName(loginedAccount.getUsername());
//		if(account == null){
//			MessageVo message = new MessageVo();
//			message.setMessContent("cannot find account");
//			message.setMessType("error");
//			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
//		}
//		return new ResponseEntity<>(account, HttpStatus.OK);
//	}

//	@GetMapping("user/home")
//	public ResponseEntity<Model> showUserHome(Principal principal, Model model) {
//		UserDetails loginedAccount = (UserDetails) ((Authentication) principal).getPrincipal();
//		AccountVo account = accountRepository.findByUserName(loginedAccount.getUsername());
//		System.out.println( "account: " +account.getRole());
//		model.addAttribute("accountLogin", account);
//		return new ResponseEntity<>(model, HttpStatus.OK);
//	}
//
//	@GetMapping("mod/home")
//	public ResponseEntity<Model> showModHome(Principal principal, Model model) {
//		UserDetails loginedAccount = (UserDetails) ((Authentication) principal).getPrincipal();
//		AccountVo account = accountRepository.findByUserName(loginedAccount.getUsername());
//		model.addAttribute("accountLogin", account);
//		return new ResponseEntity<>(model, HttpStatus.OK);
//	}



//	@RequestMapping(value = "/403", method = RequestMethod.GET)
//	public String accessDenied(Model model, Principal principal) {
//		if (principal != null) {
//			UserDetails loginedAccount = (UserDetails) ((Authentication) principal).getPrincipal();
//
//			model.addAttribute("userInfo", loginedAccount.getUsername());
//			String message = "Hi " + principal.getName() //
//					+ "<br> You do not have permission to access this page!";
//			model.addAttribute("message", message);
//		}
//		return "403";
//	}
//
//	@GetMapping("logout")
//	public String logout(HttpSession session) {
//		session.invalidate();
//		return "user-home";
//	}

}

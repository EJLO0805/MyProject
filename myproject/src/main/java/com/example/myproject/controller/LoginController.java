package com.example.myproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.myproject.entity.ShoppingUser;
import com.example.myproject.entity.UserRole;
import com.example.myproject.service.UserDAO;

import jakarta.servlet.http.HttpSession;
@CrossOrigin("http://localhost:3000")
@RestController
public class LoginController {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager manager;

    @PostMapping("/user/signup")
    public ResponseEntity<ShoppingUser> signUpPage(@RequestBody ShoppingUser user) {
        boolean isExist = userDAO.fetchUserByEmail(user.getUserEmail()) == null ? false : true;
        if (!isExist) {
            System.out.println(user.toString());
            user.setUserRole(UserRole.NORMALUSER);
            user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
            userDAO.createNewUser(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.ok(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ShoppingUser> doLogin(@RequestBody ShoppingUser user, HttpSession session){
        String account = user.getUserEmail();
        String password = user.getUserPassword();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(account, password);
        Authentication authentication = null;
        try {
            authentication = manager.authenticate(token);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userEmail = userDetails.getUsername();
            ShoppingUser loginUser = userDAO.fetchUserByEmail(userEmail);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
            return ResponseEntity.ok(loginUser);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/logout")
    public void logoutPage() {
    }

    @GetMapping("/successLogout")
    public ResponseEntity<String> successLogoutPage() {
        return ResponseEntity.ok("登出成功");
    }

}

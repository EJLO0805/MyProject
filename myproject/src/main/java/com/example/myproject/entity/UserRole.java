package com.example.myproject.entity;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority{
    ADMIN,EMPLOYEE,NORMALUSER;

    @Override
    public String getAuthority(){
        return name();
    }

    public static boolean isAuthenticatedAdmin(String userRole){
        UserRole role = UserRole.valueOf(userRole);
        switch (role) {
            case ADMIN:
                return true;
            default:
                return false;
        }
    }

    public static boolean isAuthenticatedEmployee(String userRole){
        UserRole role = UserRole.valueOf(userRole);
        switch (role) {
            case ADMIN:
                return true;
            case EMPLOYEE:
                return true;
            default:
                return false;
        }
    }

    public static boolean isAuthenticatedAllUser(String userRole){
        UserRole role = UserRole.valueOf(userRole);
        switch (role) {
            case ADMIN:
                return true;
            case EMPLOYEE:
                return true;
            case NORMALUSER:
                return true;
            default:
                return false;
        }
    }
}

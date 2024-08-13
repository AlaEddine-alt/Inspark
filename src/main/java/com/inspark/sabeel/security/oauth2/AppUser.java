package com.inspark.sabeel.security.oauth2;

import lombok.Getter;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

@Getter
public class AppUser {
    private String id;
    private String givenName;
    private String familyName;
    private String name;
    private String email;
    private String imageUrl;
    private String birthDate;

    public static AppUser fromGoogleUser(DefaultOidcUser googleUser) {
        AppUser appUser = new AppUser();
        appUser.id = googleUser.getSubject();
        appUser.givenName = googleUser.getGivenName();
        appUser.birthDate = googleUser.getBirthdate();
        appUser.familyName = googleUser.getFamilyName();
        appUser.name = googleUser.getFullName();
        appUser.email = googleUser.getEmail();
        appUser.imageUrl = googleUser.getPicture();
        return appUser;
    }
}

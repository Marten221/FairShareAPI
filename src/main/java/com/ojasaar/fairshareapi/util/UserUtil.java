package com.ojasaar.fairshareapi.util;

import com.ojasaar.fairshareapi.domain.model.Group;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserUtil {
    public static String getUserIdfromContext() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static boolean hasAccessToGroup(String userId, Group group) {
        return group.getOwner().getId().equals(userId) ||
                group.getMembers().stream()
                        .anyMatch(member -> member.getId().equals(userId));
    }

    public static boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}

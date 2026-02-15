package com.viktoria.viktorifit.user.scheduler; 

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.viktoria.viktorifit.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserCleanupScheduler {

    private final UserService userService;

    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void cleanupExpiredUsers() {
        userService.cleanupExpiredInactiveUsers();
    }
}

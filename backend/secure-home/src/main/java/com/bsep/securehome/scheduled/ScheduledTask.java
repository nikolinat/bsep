package com.bsep.securehome.scheduled;

import com.bsep.securehome.model.LockedAccount;
import com.bsep.securehome.service.implementation.LockedAccountService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScheduledTask {

    private LockedAccountService lockedAccountService;

    public ScheduledTask(LockedAccountService lockedAccountService) {
        this.lockedAccountService = lockedAccountService;
    }

    @Scheduled(cron = "0 * * * * *")
    public void checkAccount() {
        for (LockedAccount account : lockedAccountService.findAll()) {
            if (account.getLoginCounts() == 3 && account.getDateTime().plusMinutes(5).isBefore(LocalDateTime.now())) {
                lockedAccountService.delete(account.getUsername());
            } else if (account.getLoginCounts() > 3 && account.getDateTime().plusMinutes(15).isBefore(LocalDateTime.now())) {
                lockedAccountService.delete(account.getUsername());
            }
        }
    }
}

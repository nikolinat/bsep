package com.bsep.admin.app.scheduled;

import com.bsep.admin.app.model.LockedAccount;
import com.bsep.admin.app.service.implementation.LockedAccountService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScheduledTask {

    private LockedAccountService lockedAccountService;

    public ScheduledTask(LockedAccountService lockedAccountService){
        this.lockedAccountService = lockedAccountService;
    }
    @Scheduled(cron = "0 * * * * *")
    public void checkAccount() {
        for(LockedAccount account: lockedAccountService.findAll()){
            if(account.getLoginCounts() >= 3 && account.getDateTime().plusDays(1).isBefore(LocalDateTime.now())){
                lockedAccountService.delete(account.getUsername());
            }
        }
    }
}

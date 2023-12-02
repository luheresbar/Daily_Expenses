package com.luheresbar.daily.persistence.projections;

import java.time.LocalDateTime;

public interface IUserSummary {

    String getUserId();
    LocalDateTime getRegisterDate();

}

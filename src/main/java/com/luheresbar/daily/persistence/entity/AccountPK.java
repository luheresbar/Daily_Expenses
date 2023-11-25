package com.luheresbar.daily.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountPK implements Serializable {

    private String accountName;

    private String userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountPK accountPK)) return false;
        return Objects.equals(accountName, accountPK.accountName) && Objects.equals(userId, accountPK.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountName, userId);
    }


}

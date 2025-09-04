package com.smartacqua;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class SessionStore {
    private String aquaristCode;

    public void setAquaristCode(String code) {
        this.aquaristCode = code;
    }

    public String getAquaristCode() {
        return aquaristCode;
    }
}


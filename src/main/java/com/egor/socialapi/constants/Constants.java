package com.egor.socialapi.constants;

public enum Constants {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private final String role;

    Constants(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}

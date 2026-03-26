package com.w2m.starshipmanager.data.model;

import java.util.List;

public record JwtPrincipal(String username, List<String> roles) {
}

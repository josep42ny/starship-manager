package com.w2m.starshipmanager.util;

import java.util.List;

public record JwtPrincipal(String username, List<String> roles) {
}

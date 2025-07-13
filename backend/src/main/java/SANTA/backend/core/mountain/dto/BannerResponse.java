package SANTA.backend.core.mountain.dto;

import SANTA.backend.core.mountain.domain.Mountain;
import SANTA.backend.core.user.domain.Interest;

import java.util.List;

public record BannerResponse() {
    static Interest interest;
    static List<Mountain> mountains;
}

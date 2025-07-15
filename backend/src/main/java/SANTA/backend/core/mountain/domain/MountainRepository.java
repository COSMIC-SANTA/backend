package SANTA.backend.core.mountain.domain;

import SANTA.backend.core.user.domain.Interest;

import java.util.List;

public interface MountainRepository {
    List<Mountain> findByInterest(Interest interest);
}

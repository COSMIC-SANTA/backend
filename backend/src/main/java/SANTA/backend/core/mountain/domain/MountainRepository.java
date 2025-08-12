package SANTA.backend.core.mountain.domain;

import SANTA.backend.core.mountain.entity.MountainEntity;
import SANTA.backend.core.user.domain.Interest;

import java.util.List;
import java.util.Optional;

public interface MountainRepository {
    List<MountainEntity> findByInterest(Interest interest);

    void saveMountain(Mountain mountain);

    Long findAllCount();

    Optional<MountainEntity> findById(Long mountainId);

    List<MountainEntity> findByName(String name);
}

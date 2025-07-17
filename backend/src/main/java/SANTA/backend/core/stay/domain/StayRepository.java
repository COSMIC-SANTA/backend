package SANTA.backend.core.stay.domain;

import java.util.List;

public interface StayRepository {
    public List<Stay> findByLocation(String location);
}

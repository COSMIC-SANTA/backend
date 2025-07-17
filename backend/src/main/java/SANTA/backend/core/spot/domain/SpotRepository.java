package SANTA.backend.core.spot.domain;

import java.util.List;

public interface SpotRepository {
    public List<Spot> findByLocation(String location);
}

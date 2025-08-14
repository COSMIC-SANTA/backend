package SANTA.backend.core.cafe.domain;

import java.util.List;

public interface CafeRepository {
    public List<Cafe> findByLocation(String location);
}

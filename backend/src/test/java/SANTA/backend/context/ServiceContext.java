package SANTA.backend.context;

import SANTA.backend.core.mountain.domain.MountainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import({MountainRepository.class})
public abstract class RepositoryContext {

    @Autowired
    MountainRepository mountainJPARepository;
}

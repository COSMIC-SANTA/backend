package SANTA.backend.context;

import SANTA.backend.core.mountain.domain.MountainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RepositoryContext {

    @Autowired
    protected MountainRepository mountainRepository;
}

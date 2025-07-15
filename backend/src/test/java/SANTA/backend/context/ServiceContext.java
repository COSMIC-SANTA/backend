package SANTA.backend.context;

import SANTA.backend.core.mountain.application.BannerService;
import SANTA.backend.core.mountain.application.MountainService;
import SANTA.backend.core.mountain.domain.MountainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public abstract class ServiceContext {

    @Autowired
    protected MountainService mountainService;

    @Autowired
    protected BannerService bannerService;

    @Autowired
    protected MountainRepository mountainJPARepository;
}

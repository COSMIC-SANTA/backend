package SANTA.backend.core.user.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class Medal {
    String name;
    String imageUrl;
}

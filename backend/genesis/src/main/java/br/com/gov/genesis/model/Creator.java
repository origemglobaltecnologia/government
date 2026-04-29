package br.com.gov.genesis.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.UUID;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;

@Data
@NoArgsConstructor
@RedisHash("creator")
public class Creator implements Serializable {

    @Id
    private UUID identification;
    
    private String description;
    private LocalDateTime created;
    private Set<UUID> attributes = new HashSet<>();

    public Creator(String description) {
        this.identification = UUID.randomUUID();
        this.description = description;
        this.created = LocalDateTime.now();
    }

    public void addAttribute(UUID attributeUuid) {
        if (attributeUuid != null) {
            this.attributes.add(attributeUuid);
        }
    }
}

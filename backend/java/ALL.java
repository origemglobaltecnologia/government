import java.util.UUID;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;
import java.util.Objects;

public class ALL {
    
    private UUID identification;
    private String description;
    private LocalDateTime created;
    private Set<UUID> attributes;

    public ALL(String description) {
        this.identification = UUID.randomUUID();
        this.description = description;
        this.created = LocalDateTime.now();
        this.attributes = new HashSet<>();
    }

    public void addAttribute(UUID attributeUuid) {
        if (attributeUuid != null) {
            this.attributes.add(attributeUuid);
        }
    }

    @Override
    public String toString() {
        return String.format("UUID: %s | Desc: %-18s | Criado: %s", 
                identification, description, created);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ALL all = (ALL) o;
        return Objects.equals(identification, all.identification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identification);
    }

    public UUID getIdentification() { return identification; }
    public String getDescription() { return description; }
    public LocalDateTime getCreated() { return created; }
    public Set<UUID> getAttributes() { return attributes; }
    public void setDescription(String description) { this.description = description; }
}

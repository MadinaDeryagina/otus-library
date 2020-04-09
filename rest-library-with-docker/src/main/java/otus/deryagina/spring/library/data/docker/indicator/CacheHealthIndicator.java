package otus.deryagina.spring.library.data.docker.indicator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CacheHealthIndicator implements HealthIndicator {

    private final CacheManager cacheManager;

    @Override
    public Health health() {
        Cache cache = cacheManager.getCache("books");
        if (cache == null) {
            log.warn("Cache not available");
            return Health.down().withDetail("smoke test", "cache not available").build();
        }
        return Health.up().build();
   }
}

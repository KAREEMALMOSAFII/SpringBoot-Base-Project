package SpringBootStarterProject.ManagingPackage.Security.Config;

import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Configuration
public class RateLimiterConfig {
    private final Set<String> blockedIPs = new HashSet<>();
    private final Map<String, LocalDateTime> blockedTimes = new HashMap<>();
    private final Duration blockDuration = Duration.ofMinutes(1); // Duration for blocking IPs

    @Bean
    public RateLimiterRegistry rateLimiterRegistry() {
        io.github.resilience4j.ratelimiter.RateLimiterConfig config = io.github.resilience4j.ratelimiter.RateLimiterConfig.custom()
                .limitForPeriod(5) // Maximum number of permits
                .limitRefreshPeriod(Duration.ofMinutes(1)) // Duration of the rate limiting window
                .build();
        return RateLimiterRegistry.of(config);
    }

    public void blockIP(String ip) {
        blockedIPs.add(ip);
        blockedTimes.put(ip, LocalDateTime.now());
    }

    public Set<String> getBlockedIPs() {
        return Collections.unmodifiableSet(blockedIPs);
    }

    @Scheduled(fixedDelay = 60000) // 1 minute delay
    public void unblockExpiredIPs() {
        LocalDateTime now = LocalDateTime.now();
        Set<String> expiredIPs = new HashSet<>();

        for (String ip : blockedIPs) {
            LocalDateTime blockedTime = blockedTimes.get(ip);
            if (blockedTime != null && Duration.between(blockedTime, now).compareTo(blockDuration) >= 0) {
                expiredIPs.add(ip);
            }
        }

        blockedIPs.removeAll(expiredIPs);
        expiredIPs.forEach(blockedTimes::remove);
    }
}

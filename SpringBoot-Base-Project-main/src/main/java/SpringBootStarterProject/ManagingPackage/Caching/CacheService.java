package SpringBootStarterProject.ManagingPackage.Caching;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CacheService {
   // @Autowired
    private final CacheManager cacheManager;

    public void printCacheContents() {
        Cache cache = cacheManager.getCache("students");
        if (cache != null) {
            System.out.println("Cache Name: " + cache.getName());
            Map<Object, Object> cacheContent = (Map<Object, Object>) cache.getNativeCache();
            for (Map.Entry<Object, Object> entry : cacheContent.entrySet()) {
                System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
            }
        } else {
            System.out.println("Cache not found: " + "students");
        }
    }
}

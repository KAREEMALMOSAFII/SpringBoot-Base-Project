package SpringBootStarterProject.ManagingPackage.auditing;
//
//
////import com.ProjectsManagementSystem.user.User;
//import SpringBootStarterProject.UserPackage.Models.BaseUser;
//import SpringBootStarterProject.UserPackage.Models.Client;
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//
//@Component
//public class ApplicationAuditingAware implements AuditorAware<Integer> {
//
//    @Override
//    public Optional<Integer> getCurrentAuditor() {
//        Authentication authentication = SecurityContextHolder
//                .getContext()
//                .getAuthentication();
//        if(authentication == null ||
//        ! authentication.isAuthenticated() ||
//         authentication instanceof AnonymousAuthenticationToken){
//            return Optional.empty();
//        }
//        Client userPrincipal = (Client) authentication.getPrincipal();
//        return Optional.ofNullable(userPrincipal.getId());
//    }
//}

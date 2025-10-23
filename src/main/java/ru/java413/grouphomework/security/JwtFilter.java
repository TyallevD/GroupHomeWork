//package ru.java413.grouphomework.security;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import ru.java413.grouphomework.services.UserService;
//
//import java.io.IOException;
//
//@Component
//public class JwtFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private UserService userService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException{
//        String header = request.getHeader("Authorization");
//        String token = null;
//        String username = null;
//        if (header != null && header.startsWith("Bearer ")) {
//            token = header.substring(7);
//            if (jwtUtil.validateToken(token)) {
//                username = jwtUtil.extractUsername(token);
//            }
//        }
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            var userdetails = userService.loadUserByUsername(username);
//            UsernamePasswordAuthenticationToken authtoken =
//                    new UsernamePasswordAuthenticationToken(userdetails, null, userdetails.getAuthorities());
//            authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            SecurityContextHolder.getContext().setAuthentication(authtoken);
//        }
//        chain.doFilter(request, response);
//    }
//}

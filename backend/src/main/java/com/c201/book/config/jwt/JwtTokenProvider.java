package com.c201.book.config.jwt;

import com.c201.book.api.common.TokenDto;
import com.c201.book.auth.CustomUserDetails;
import com.c201.book.auth.CustomUserDetailsService;
import com.c201.book.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements InitializingBean {

    private static final String AUTHORIZATION_HEADER = "Authorization"; //access token header
    private static final String REFRESH_HEADER = "Refresh";
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";
    // private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            // 만료시간 30분
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;   // 개발을 위해서 한시적으로 access token 시간 늘리기
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7 * 4;  // 만료시간 한달

    private Key key;

    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public void afterPropertiesSet() throws Exception {
        String tempSecretKey = "ibook temp Secret Key book search read review voice algorithm ssafy"; // key 정보를 임시로 설정, 추후 다른 곳에서 받아서 사용할 예정
        String encodedKey = Base64.getEncoder().encodeToString(tempSecretKey.getBytes());
        key = Keys.hmacShaKeyFor(encodedKey.getBytes());
    }

    /**
     * [JWT 토큰 생성]
     * param : Authentication authentication
     * return : TokenDto
     * */
    public TokenDto generateTokenDto(String userId) {
        long now = (new Date()).getTime();

        // 엑세스 토큰 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setSubject(userId)                         // payload "sub": "name"
                .setExpiration(accessTokenExpiresIn)        // payload "exp": 1516239022 (예시)
                .signWith(key, SignatureAlgorithm.HS512)    // header "alg": "HS512"
                .compact();

        // 리프레시 토큰 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        // TokenDto 생성하여 return
        return TokenDto.builder()
                .AuthorizationHeader(AUTHORIZATION_HEADER)
                .RefreshHeader(REFRESH_HEADER)
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * [토큰 인증 및 사용자 정보 가져오기]
     * param : String accessToken
     * return : Authentication
     * */
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(claims.getSubject());

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * [토큰 검증]
     * param : String token
     * return : boolean
     * */
    public boolean validateToken(String token) {

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            // JWT가 올바르게 구성되지 않았을 때
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    /**
     * [토큰 복호화]
     * param : String accessToken
     * return : Claims
     * */
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}

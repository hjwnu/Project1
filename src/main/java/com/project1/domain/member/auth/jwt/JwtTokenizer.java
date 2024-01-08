package com.project1.domain.member.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static java.util.Calendar.MINUTE;

@Component @Slf4j
public class JwtTokenizer {

    @Getter
    @Value("${jwt.access-token-expiration-minutes}")
    private int accessTokenExpirationMinutes;

    @Getter
    @Value("${jwt.refresh-token-expiration-minutes}")
    private int refreshTokenExpirationMinutes;

    private final String SECRET_KEY = System.getenv("SECRET_KEY");

    public String getSecretKey(){
        return SECRET_KEY;
    }
    public String encodedBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }
    public String base64Encoder(String value) {
        byte[] byteKey = value.getBytes();
        return Base64.getEncoder().encodeToString(byteKey);
    }
    public String dataEnDecrypt(String cipherKey, String data, int cipherMode) {
        String result = null;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            byte[] initailizationVector = new byte[16];
            int index = 0;
            for (byte b : cipherKey.getBytes(StandardCharsets.UTF_8)) {
                initailizationVector[index++ % 16] ^= b;
            }
            SecretKeySpec keySpec = new SecretKeySpec(initailizationVector, "AES");
            cipher.init(cipherMode,keySpec);

            if (cipherMode == Cipher.DECRYPT_MODE) {
                result = new String(cipher.doFinal(Hex.decodeHex(data)), StandardCharsets.UTF_8);
            } else if (cipherMode == Cipher.ENCRYPT_MODE) {
                result = new String(Hex.encodeHex(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)))).toUpperCase(Locale.ROOT);
            } else {
                throw new PropertyValueException("cipherMode parameter is invalid", "DataEnDecryption.dataEnDecrypt(int cipherMode", Integer.toString(cipherMode));
            }
        } catch (NoSuchPaddingException |
                 NoSuchAlgorithmException |
                 InvalidKeyException |
                 DecoderException |
                IllegalBlockSizeException |
                BadPaddingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    //jwt 생성
    public String generateAccessToken(Map<String, Object> claims,
                                      String subject,
                                      Date expiration,
                                      String encodedBase64SecretKey) {

        Key key = getKeyFromBase64SecretKey(encodedBase64SecretKey);

        return Jwts.builder()
                .setClaims(claims)//인증된 사용자와 관련된 정보 추가
                .setSubject(subject)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(String subject,
                                       Date expiration,
                                       String encodedBase64SecretKey) {

        Key key = getKeyFromBase64SecretKey(encodedBase64SecretKey);

        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    public Date getTokenExpiration(int expirationMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(MINUTE, expirationMinutes);

        Date expiration = calendar.getTime();

        return expiration;

    }

    // 검증 후, Claims을 반환하는 용도
    public Jws<Claims> getClaims(String jws, String encodedBase64SecretKey) {
        Key key = getKeyFromBase64SecretKey(encodedBase64SecretKey);

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
    }

    // 단순히 검증만 하는 용도로 쓰일 경우
    public void verifySignature(String jws, String encodedBase64SecretKey) {
        Key key = getKeyFromBase64SecretKey(encodedBase64SecretKey);

        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
    }

    //secret Key 생성
    private Key getKeyFromBase64SecretKey(String base64EncodedSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
        // Ensure the key size is at least 256 bits (32 bytes)
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("Key size must be at least 256 bits");
        }

        Key key = Keys.hmacShaKeyFor(Arrays.copyOf(keyBytes, 32)); // Truncate to 256 bits

        return key;

    }

}

package com.huobi.api.client.security;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Utility class to sign messages using HMAC-SHA256.
 */
public class HmacSHA256Signer {

  /**
   * Sign the given message using the given secret.
   * @param message message to sign
   * @param secret secret key
   * @return a signed message
   */
  public static String sign(String message, String secret) {
    try {
      Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
      SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
      sha256_HMAC.init(secretKeySpec);
      byte[] hash = sha256_HMAC.doFinal(message.getBytes());
      return Base64.getEncoder().encodeToString(hash);
    } catch (Exception e) {
      throw new RuntimeException("Unable to sign message.", e);
    }
  }
}

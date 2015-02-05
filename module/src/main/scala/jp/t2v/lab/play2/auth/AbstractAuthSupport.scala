package jp.t2v.lab.play2.auth

import play.api.libs.Crypto
import play.api.mvc.{Result, Cookie}

abstract trait AbstractAuthSupport { self: AuthConfig =>

  def verifyHmac(token: String): Option[String] = {
    val (hmac, value) = token.splitAt(40)
    if (Crypto.sign(value) == hmac) Some(value) else None
  }

  def bakeCookie(token: String)(result: Result): Result ;

}


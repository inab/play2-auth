package jp.t2v.lab.play2.auth

import play.api.libs.Crypto
import play.api.mvc.{Result, RequestHeader}

abstract trait AbstractAuthSupport { self: AuthConfig =>

  def verifyHmac(token: String): Option[String] = {
    val (hmac, value) = token.splitAt(40)
    if (Crypto.sign(value) == hmac) Some(value) else None
  }

  def bakeCookie(token: String)(result: Result): Result ;
  
  def unbakeCookieInternal(request: RequestHeader): Option[String];
  
  def unbakeCookie(request: RequestHeader): Option[String] = {
	val token = unbakeCookieInternal(request)
	if(token.isDefined) {
		verifyHmac(token.get)
	} else {
		None
	}
  };

}


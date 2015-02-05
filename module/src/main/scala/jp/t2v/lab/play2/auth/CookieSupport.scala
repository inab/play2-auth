package jp.t2v.lab.play2.auth

import play.api.libs.Crypto
import play.api.mvc.{Result, Cookie}

trait CookieSupport extends AbstractAuthSupport { self: AuthConfig =>

  override def bakeCookie(token: String)(result: Result): Result = {
    val value = Crypto.sign(token) + token
    val maxAge = if (isTransientCookie) None else Some(sessionTimeoutInSeconds)
    result.withCookies(Cookie(cookieName, value, maxAge, cookiePathOption, cookieDomainOption, cookieSecureOption, cookieHttpOnlyOption))
  }

}


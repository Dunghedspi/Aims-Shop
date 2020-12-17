package itss.nhom7.helper;

import javax.servlet.http.Cookie;

public class utils {
    public Cookie createToken(String name, String value, boolean httpOnly, Long age){
        Cookie newCookie = new Cookie(name, value);
        newCookie.setMaxAge(Math.toIntExact(age));
        newCookie.setHttpOnly(httpOnly);
        newCookie.setPath("/");
        return newCookie;
    }
}

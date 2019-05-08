package pl.webapp.arbitratus.Security;

import java.lang.annotation.ElementType;

@Target({ElementType.PARAMETER, ElementType.TYPE})

public @interface CurrentUser {
}

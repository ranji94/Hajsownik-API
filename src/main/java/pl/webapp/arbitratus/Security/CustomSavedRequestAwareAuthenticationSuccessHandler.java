package pl.webapp.arbitratus.Security;

import org.apache.commons.text.WordUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomSavedRequestAwareAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if(savedRequest == null){
            clearAuthenticationAttributes(request);
            return;
        }
        String targetUrlParam = getTargetUrlParameter();
        if(isAlwaysUseDefaultTargetUrl() || (targetUrlParam!= null && WordUtils.containsAllWords(request.getParameter(targetUrlParam))))
        {
            requestCache.removeRequest(request, response);
            clearAuthenticationAttributes(request);
            return;
        }
        clearAuthenticationAttributes(request);
    }

    public void setRequestCache(RequestCache requestCache)
    {
        this.requestCache = requestCache;
    }
}

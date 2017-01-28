package com.mindshine.clevergrid;

import java.security.Principal;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.mindshine.clevergrid.security.CurrentUser;
import com.mindshine.clevergrid.security.UserDetails;

@Component
public class CurrentUserHandlerMethodArgumentResolver implements
		HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return methodParameter.getParameterAnnotation(CurrentUser.class) != null
				&& methodParameter.getParameterType().equals(UserDetails.class);
	}

	@Override
	public Object resolveArgument(MethodParameter methodParameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		if (this.supportsParameter(methodParameter)) {
			Principal principal = (Principal) webRequest.getUserPrincipal();
			return (UserDetails) ((Authentication) principal).getPrincipal();
		} else {
			return WebArgumentResolver.UNRESOLVED;
		}
	}
}
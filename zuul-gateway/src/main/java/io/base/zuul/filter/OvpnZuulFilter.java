package io.base.zuul.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.constants.ZuulHeaders;
import com.netflix.zuul.context.RequestContext;

@Component
public class OvpnZuulFilter extends ZuulFilter {
	private Logger log = LoggerFactory.getLogger(OvpnZuulFilter.class);

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 6; // PreDecorationFilter=5 + 1
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		log.info(String.format("Before filter ['%s': '%s', '%s': '%s']", ZuulHeaders.X_FORWARDED_PROTO.toLowerCase(),
				ctx.getZuulRequestHeaders().get(ZuulHeaders.X_FORWARDED_PROTO.toLowerCase()), "X-Forwarded-Port",
				ctx.getZuulRequestHeaders().get("x-forwarded-port")));

		final String originalXForwardedProto = ctx.getRequest().getHeader(ZuulHeaders.X_FORWARDED_PROTO.toLowerCase());
		final String originalXForwardedPort = ctx.getRequest().getHeader("x-forwarded-port");

		if (!StringUtils.isEmpty(originalXForwardedProto)) {
			ctx.addZuulRequestHeader(ZuulHeaders.X_FORWARDED_PROTO.toLowerCase(), originalXForwardedProto);
		}

		if (!StringUtils.isEmpty(originalXForwardedPort)) {
			ctx.addZuulRequestHeader("x-forwarded-port", originalXForwardedPort);
		}

		log.info(String.format("After filter ['%s': '%s', '%s': '%s']", ZuulHeaders.X_FORWARDED_PROTO.toLowerCase(),
				ctx.getZuulRequestHeaders().get(ZuulHeaders.X_FORWARDED_PROTO.toLowerCase()), "X-Forwarded-Port",
				ctx.getZuulRequestHeaders().get("x-forwarded-port")));

		return null;
	}
}

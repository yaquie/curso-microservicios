package com.pe.zuulserver.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PostTiempoTranscurridoFilter extends ZuulFilter {
	
	private static Logger log=LoggerFactory.getLogger(PostTiempoTranscurridoFilter.class);

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object run() throws ZuulException {

		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		log.info("Entrando a post filter ");

		Long tiempoInicio = (Long) request.getAttribute("tiempoInicio");
		Long tiempofinal = System.currentTimeMillis();
		Long tiempoTranscurrido = tiempofinal - tiempoInicio;

		log.info(String.format("Tiempo transcurrido en segundos $s ", tiempoTranscurrido.doubleValue()/1000.00));
		log.info(String.format("Tiempo transcurrido en milisegundos $s ", tiempoTranscurrido));

		return true;
	}

	@Override
	public String filterType() {
		return "POST";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}

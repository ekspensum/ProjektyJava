package pl.shopapp.beans.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class LoggingInterceptor {
	
	@AroundInvoke
	public Object audit(InvocationContext ctx) {
		
		System.out.println("Method: "+ctx.getMethod().getName());
		System.out.println("Target: "+ctx.getTarget());
		long start = System.currentTimeMillis();
		Object obj = null;
		try {
			obj = ctx.proceed();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long stop = System.currentTimeMillis();
		System.out.println("Duration: "+(stop-start));
		
		return obj;
	}
}

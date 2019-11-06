package pl.dentistoffice.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class RestAspect {

	
//	@Pointcut("execution(* pl.dentistoffice.service.UserService.loginMobilePatient(String, String))")
//	public void execLoginMobilePatient() {}
//	
//	@Before("execLoginMobilePatient()")
//	public void logLoginMobilePatient(JoinPoint joinPoint) {
//		Signature signature = joinPoint.getSignature();
//		String name = signature.getName();
//		System.out.println("Log Before "+name);
//	}
	
	
	@Pointcut("within(pl.dentistoffice.rest.DoctorRestController)")
//	@Pointcut("execution(* pl.dentistoffice.rest.DoctorRestController.getDoctors())")
	public void doctorRestController() {}
	
//	@After(value="execution(* *.getDoctors(*))")
//	@Before("onlyRestController()")
	@Around("doctorRestController()")
	public Object execAuthentication(ProceedingJoinPoint pjp) throws Throwable {
		
//		Object[] args = pjp.getArgs();
//		for (int i = 0; i < args.length; i++) {
//		System.out.println(args[i].toString());
//		}
//		LoggedPatientValidationService object = (LoggedPatientValidationService) args[0];
//		System.out.println("object "+object.getPatientLoggedMap().size());
		
//		boolean authentication = authRestController.authentication(request, response);
//		System.out.println("REST Aspect - Authentication: "+authentication);
//		if(authentication) {
			try {
				Object proceed = pjp.proceed();
//				System.out.println("REST Aspect "+proceed.toString());
				return proceed;
			} catch (Throwable e) {
				e.printStackTrace();
			}
//		}
		return null;
	}
	
//	@Around(value="execution(* pl.dentistoffice.service.VisitService.getVisitStatus(int))")
//	@Around(value="within(pl.dentistoffice.service.VisitService)")
	@Around(value="within(pl.dentistoffice.rest.VisitRestController)")
	public Object visitStatus(ProceedingJoinPoint proceedingJoinPoint) {
		Object[] args = proceedingJoinPoint.getArgs();
//		for (int i = 0; i < args.length; i++) {
//			System.out.println(args[i].toString());
//		}
		
			try {
				return proceedingJoinPoint.proceed(args);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		return null;
	}

}

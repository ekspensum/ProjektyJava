package pl.dentistoffice.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import pl.dentistoffice.rest.DoctorRestController;
import pl.dentistoffice.rest.VisitRestController;


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
	
	
//	@Pointcut("within(pl.dentistoffice.rest.DoctorRestController)")
////	@Pointcut("execution(* pl.dentistoffice.rest.DoctorRestController.getDoctors())")
//	public void doctorRestController() {}
//	
////	@Around(value="execution(* *.getDoctors(*))")
//	@Around("doctorRestController()")
//	public Object execAuthentication(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//		System.out.println("Get this " + proceedingJoinPoint.getThis().getClass().getName());
//		System.out.println("Get target " + proceedingJoinPoint.getTarget().getClass().getName());
//		
//		try {
//			DoctorRestController target = (DoctorRestController) proceedingJoinPoint.getTarget();
//			boolean authentication = target.getAuthRestController().authentication();
//
////			Object[] args = proceedingJoinPoint.getArgs();
////			for (int i = 0; i < args.length; i++) {
////				System.out.println(args[i].toString());
////			}
//
//			if (authentication) {
//				return proceedingJoinPoint.proceed();
//			}
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
//	@Around(value="execution(* pl.dentistoffice.service.VisitService.getVisitStatus(int))")
//	@Around(value = "within(pl.dentistoffice.rest.VisitRestController)")
//	public Object visitStatus(ProceedingJoinPoint proceedingJoinPoint) {
//		MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
//		System.out.println("Signature " + signature.getMethod().getName());
//
//		try {
//			VisitRestController target = (VisitRestController) proceedingJoinPoint.getTarget();
//			boolean authentication = target.getAuthRestController().authentication();
//			Object[] args = proceedingJoinPoint.getArgs();
//			for (int i = 0; i < args.length; i++) {
//				System.out.println(args[i].toString());
//			}
//
//			if (authentication) {
//				return proceedingJoinPoint.proceed(args);
//			}
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

}

package pl.dentistoffice.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import pl.dentistoffice.rest.DentalTreatmentRestController;
import pl.dentistoffice.rest.DoctorRestController;
import pl.dentistoffice.rest.PatientRestController;
import pl.dentistoffice.rest.VisitRestController;


@Component
@Aspect
public class RestAspect {

	private static final Logger LOGGER = Logger.getLogger(RestAspect.class);
	
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
//		MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
//		System.out.println("Signature " + signature.getMethod().getName());
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
	@Around("execution(* pl.dentistoffice.rest.VisitRestController.getVisitStatus(String)) "
					+ "|| "
					+ "execution(* pl.dentistoffice.rest.VisitRestController.addNewVisitByMobilePatient(..))")
	public Object visitStatus(ProceedingJoinPoint proceedingJoinPoint) {
		try {
			VisitRestController target = (VisitRestController) proceedingJoinPoint.getTarget();
			LOGGER.info("Login to visit contr. "+target.getAuthRestController().getPatientLoggedMap());
			boolean authentication = target.getAuthRestController().authentication();
			Object[] args = proceedingJoinPoint.getArgs();
			if (authentication) {
				return proceedingJoinPoint.proceed(args);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
//	@Around(value="execution(* pl.dentistoffice.rest.PatientRestController.editPatientData(Patient))")
	@Around(value = "within(pl.dentistoffice.rest.PatientRestController)")
	public Object patientAuthAspect(ProceedingJoinPoint proceedingJoinPoint) {
		try {
			PatientRestController target = (PatientRestController) proceedingJoinPoint.getTarget();
			LOGGER.info("Login to patient contr. "+target.getAuthRestController().getPatientLoggedMap());
			boolean authentication = target.getAuthRestController().authentication();
			Object[] args = proceedingJoinPoint.getArgs();
			if (authentication) {
				return proceedingJoinPoint.proceed(args);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
//	@Around(value="execution(* pl.dentistoffice.rest.PatientRestController.editPatientData(Patient))")
	@Around(value = "execution(* pl.dentistoffice.rest.DentalTreatmentRestController.treatments(..))")
	public Object treatmentsAuthAspect(ProceedingJoinPoint proceedingJoinPoint) {
		try {
			DentalTreatmentRestController target = (DentalTreatmentRestController) proceedingJoinPoint.getTarget();
			LOGGER.info("Login to treatments contr. "+target.getAuthRestController().getPatientLoggedMap());
			boolean authentication = target.getAuthRestController().authentication();
			Object[] args = proceedingJoinPoint.getArgs();
			if (authentication) {
				return proceedingJoinPoint.proceed(args);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
}

package pl.dentistoffice.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import pl.dentistoffice.rest.DentalTreatmentRestController;
import pl.dentistoffice.rest.PatientRestController;
import pl.dentistoffice.rest.VisitRestController;


@Component
@Aspect
public class RestAspect {

	private static final Logger LOGGER = Logger.getLogger(RestAspect.class);
		
	@Around(value = "execution(* pl.dentistoffice.rest.VisitRestController.getVisitsAndStatusListForPatient(..)) || "
							+ "execution(* pl.dentistoffice.rest.VisitRestController.addNewVisitByMobilePatient(..)) || "
							+"execution(* pl.dentistoffice.rest.VisitRestController.deleteVisit(..))")
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
	
	@Around(value="execution(* pl.dentistoffice.rest.PatientRestController.editPatientData(..)) "
							+ "execution(* pl.dentistoffice.rest.PatientRestController.logout(..))")
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

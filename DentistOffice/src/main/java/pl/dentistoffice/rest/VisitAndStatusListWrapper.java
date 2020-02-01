package pl.dentistoffice.rest;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import pl.dentistoffice.entity.Visit;
import pl.dentistoffice.entity.VisitStatus;

@Component
@Getter @Setter
public class VisitAndStatusListWrapper {
	
	private List<Visit> visitsList;
	private List<VisitStatus> visitStatusList;
}

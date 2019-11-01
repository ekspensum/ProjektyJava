package pl.dentistoffice.config;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import pl.dentistoffice.entity.WorkingWeek;

public class WorkingMapSerialize extends JsonSerializer<WorkingWeek> {

	@Override
	public void serialize(WorkingWeek value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeFieldName(value.getWorkingWeekMap().toString());
	}

}

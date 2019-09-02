<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<script src="https://www.google.com/recaptcha/api.js?hl=pl"></script>

<h3>Kontakt z nami</h3>
<form:form modelAttribute="emailContactService" id="contactForm" enctype="multipart/form-data" >
	<p>
	<form:input id="subject" path="subject" placeholder="${subjectPrompt}" style="width:250px" />
	<form:errors path="subject" class="msgError"></form:errors>
	</p>
	<p>
	<form:textarea id="message" path="message" class="textareaDescription" placeholder="${messagePrompt}" />
	<form:errors path="message" class="msgError"></form:errors>
	</p>
	<p>
	<form:input id="replyMail" path="replyMail" placeholder="${replyMailPrompt }" style="width:250px" />
	<form:errors path="replyMail" class="msgError"></form:errors>
	</p>
	<p>
	<form:input path="attachment" type="file" name="attachment" placeholder="${attachmentPrompt }" />
	<form:errors path="attachment" class="msgError"></form:errors>
	</p>
	<p class="g-recaptcha" data-sitekey="6LdM-rUUAAAAABOhqqyoOaPVVLui5AVaZoOwQSdk"></p>
	<div class="msgError">${reCaptchaError }</div>

	<p><input type="submit" value="Wyślij" class="navigateButton" /></p>
</form:form>
	<c:if test="${alert == 'YES' }">
		<script>
			sentEmailConfirm();
		</script>
	</c:if>
	<c:if test="${alert == 'NO' }">
		<script>alert("Nie udało się wysłać listu!");</script>
	</c:if>

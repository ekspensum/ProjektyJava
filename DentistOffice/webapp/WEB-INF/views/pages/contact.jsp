<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/pages/taglibs.jsp"%>

<script src="https://www.google.com/recaptcha/api.js?hl=pl"></script>

<h3><s:message code="home.header.contactUs" /></h3>
<form:form modelAttribute="emailContactService" id="contactForm" enctype="multipart/form-data" >
	<table>
		<tr>
			<td><form:input id="subject" path="subject" placeholder="${subjectPrompt}" style="width:250px" /></td>
			<td><form:errors path="subject" class="msgError"></form:errors></td>
		</tr>
		<tr>
			<td><form:textarea id="message" path="message" class="textareaDescription" placeholder="${messagePrompt}" /></td>
			<td><form:errors path="message" class="msgError"></form:errors></td>
		</tr>
		<tr>
			<td><form:input id="replyMail" path="replyMail" placeholder="${replyMailPrompt }" style="width:250px" /></td>
			<td><form:errors path="replyMail" class="msgError"></form:errors></td>
		</tr>
		<tr>
			<td><form:input path="attachment" type="file" name="attachment" placeholder="${attachmentPrompt }" /></td>
			<td><form:errors path="attachment" class="msgError"></form:errors></td>
		</tr>
		<tr>
			<td><span class="g-recaptcha" data-sitekey="6LdM-rUUAAAAABOhqqyoOaPVVLui5AVaZoOwQSdk"></span></td>
			<td class="msgError">${reCaptchaError }</td>
		</tr>
		<tr>
			<td><input type="submit" value="Wyślij" class="navigateButton" /></td>
		</tr>
	</table>
</form:form>
	<c:if test="${alert == 'YES' }">
		<script>
			sentEmailConfirm();
		</script>
	</c:if>
	<c:if test="${alert == 'NO' }">
		<script>alert("Nie udało się wysłać listu!");</script>
	</c:if>

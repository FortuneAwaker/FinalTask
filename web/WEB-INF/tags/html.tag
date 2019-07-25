<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@tag language="java" pageEncoding="UTF-8"%>
<%@attribute name="title" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@attribute name="message" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@attribute name="validator" required="false" rtexprvalue="true" type="java.lang.String"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>

<HTML>
<HEAD>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<TITLE>SportClub - ${title}</TITLE>
	<LINK rel="stylesheet" type="text/css" href="/css/style.css">
	<SCRIPT type="text/javascript" src="/js/main.js"></SCRIPT>
	<c:if test="${not empty message}">
		<SCRIPT type="text/javascript">
			startMessage = "${message}";
		</SCRIPT>
	</c:if>
	<c:if test="${not empty validator}">
		<SCRIPT type="text/javascript" src="/js/validator.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="/js/${validator}"></SCRIPT>
	</c:if>
</HEAD>
<BODY>
<u:headerLine/>
<DIV id="page">
<jsp:doBody/>
</DIV>
<u:footer/>
</BODY>
</HTML>
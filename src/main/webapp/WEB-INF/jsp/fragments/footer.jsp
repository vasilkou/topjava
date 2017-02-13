<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="footer">
    <div class="container">
        <spring:message code="app.footer"/>
    </div>
</div>

<script type="text/javascript" defer>
    var i18n = [];
    <c:forEach var='key' items='<%=new String[]{"common.deleted","common.saved","common.enabled","common.disabled","common.failed"}%>'>
    i18n['${key}'] = '<spring:message code="${key}"/>';
    </c:forEach>

    var supportedLocales = ["ru", "en"];
    var localeCode = "${pageContext.response.locale}".substr(0, 2);
    if ($.inArray(localeCode, supportedLocales) == -1) {
        localeCode = "en";
    }
</script>
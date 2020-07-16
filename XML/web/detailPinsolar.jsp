<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 7/12/2020
  Time: 4:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Detail Pin Solar</title>
    <link rel="stylesheet" type="text/css" href="css/HomeStyle.css">
</head>
<body>

<div class="container-inverter">
    <h2>Pin and Inverter You choosed</h2>
    <h2>
        <a href="PinSolarSuggestServlet">Pin Solar</a>
    </h2>
    <c:set var="listItems" value="${sessionScope.CARTPIN}"/>
    <c:if test="${not empty listItems}">
        <c:forEach var="dto" items="${listItems.items}">
            <div class="column">
                <div class="card-invert">
                    <img src="${dto.value.imagePinSolar}" width="200px" height="200px">
                    <h3>${dto.value.namePinSolar}
                        <input type="hidden" value="${dto.value.idPinSolar}" name="idPinsolar">
                    </h3>
                    <span> Price: <font color="red" size="3">${dto.value.pricePinSolar}</font> VND/ Panel</span>
                    <h4>Pin Capacity:<font color="red" size="3"> ${dto.value.capacityPinSolar} W</font></h4>
                </div>
                <c:url value="DispatcherServlet" var="URLRemove">
                    <c:param name="btAction" value="Remove Item"/>
                    <c:param name="idPinsolar" value="${dto.key}"/>
                </c:url>
                <a href="${URLRemove}" class="buttonremov-pin">Remove</a>
            </div>
        </c:forEach>
    </c:if>
    <c:if test="${empty listItems}">
        <h2>No records in here</h2>
    </c:if>
</div>
</body>
</html>

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

<div class="container-pin">
    <h2>
        <a href="CaculatePinSolarServlet">Pin Product</a>
    </h2>
    <c:set var="inverter" value="${requestScope.INVERTER}"/>
    <c:if test="${not empty inverter}">
        <c:forEach var="dto" items="${inverter}">
            <div class="column">
                <div class="card-invert">
                    <img src="${dto.imagePinSolar}" width="200px" height="200px">
                    <h3>${dto.namePinSolar}
                        <input type="hidden" value="${dto.idPinSolar}" name="idPinsolar">
                    </h3>
                    <span> Price: <font color="red" size="3">${dto.pricePinSolar}</font> VND/ Panel</span>
                    <h4>Pin Capacity:<font color="red" size="3"> ${dto.capacityPinSolar} W</font></h4>
                </div>
            </div>
        </c:forEach>
    </c:if>
    <c:if test="${empty inverter}">
        <h2>Inverter not found</h2>
    </c:if>
</div>
</body>
</html>

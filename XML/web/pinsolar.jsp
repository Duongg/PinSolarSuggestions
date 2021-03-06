<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 7/11/2020
  Time: 10:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Pin Solar Suggestion</title>
    <link rel="stylesheet" type="text/css" href="css/HomeStyle.css">
    <script type="text/javascript" src="js/index.js"></script>
</head>
<body>
<c:set var="result" value="${requestScope.LISTPIN}"/>
<c:set var="money" value="${sessionScope.MONEY}"/>

<c:if test="${not empty result}">
    <div class="container-pin">
        <h2>
            <a href="HomeElectricProductServlet">Electric Product</a>
        </h2>
        <font color="red" size="6">Your total electric bill: ${money} VND</font>
        <c:url value="DispatcherServlet" var="URLCart">
            <c:param name="btAction" value="View Cart"/>
            <c:param name="pinNumber" value="${pin}"/>
            <c:param name="idPinSolar" value="${dto.idPinSolar}"/>
        </c:url>
        <a href="${URLCart}" class="button-view-cart">View Cart</a>
        <h2>Pin Solar</h2>
        <div class="row">
            <c:forEach var="dto" items="${result}">
                <div class="column">
                    <form action="DispatcherServlet">
                        <div class="card">
                            <img class="lazy" src="image/giphy.gif" data-src="${dto.imagePinSolar}" width="200px"
                                 height="200px">
                            <h3>${dto.namePinSolar}
                                <input type="hidden" value="${dto.idPinSolar}" name="idPinsolar">
                            </h3>
                            <span> Price: <font color="red" size="3">${dto.pricePinSolar}</font> VND/ Panel</span>
                            <h4>Pin Capacity:<font color="red" size="3"> ${dto.capacityPinSolar} W</font>
                                <input type="hidden" value="${dto.capacityPinSolar}" name="txtCapacity">
                                <input type="hidden" value="${money}" name="totalMoney">
                            </h4>

                        </div>
                        <input type="submit" class="button-add-pin" name="btAction" value="Add Pin">
                        <input type="hidden" value="${dto.idPinSolar}" name="idPinsolar">
                        <input type="hidden" value="${requestScope.PAGENUMBER}" name="pageNumber">
<%--                        <c:url value="DispatcherServlet" var="URLAddPin">--%>
<%--                            <c:param name="btAction" value="Add Pin"/>--%>
<%--                            <c:param name="idPinSolar" value="${dto.idPinSolar}"/>--%>
<%--                            <c:param name="pageNumber" value="${requestScope.PAGENUMBER}"/>--%>
<%--                        </c:url>--%>
<%--                        <a href="${URLAddPin}" class="button-add-pin">Add Pin</a>--%>
                    </form>
                </div>
            </c:forEach>
        </div>
        <br/>
        <div class="pagination">
            <c:set var="url" value="${sessionScope.QUERY}"/>
            <c:if test="${not empty result}">

                <c:forEach var="i" begin="1" end="${requestScope.MAXPAGE}">

                    <c:if test="${requestScope.PAGENUMBER == i}">
                        <a class="active" href="${url}&pageNumber=${i}">${i}</a>
                    </c:if>
                    <c:if test="${requestScope.PAGENUMBER != i}">
                        <a href="${url}&pageNumber=${i}">${i}</a>
                    </c:if>

                </c:forEach>

            </c:if>
        </div>
    </div>
</c:if>
<c:if test="${empty result}">
    <h3>No records pin</h3>
</c:if>
<br/>
<br/>

<c:set var="dto" value="${sessionScope.PRODUCTDETAIL}"/>
<c:set var="pin" value="${sessionScope.PINNUMBER}"/>
<c:if test="${not empty dto}">
    <c:if test="${not empty pin}">
        <br/>
        <br/>
        <form action="DispatcherServlet">
            <div class="container-addpin">
                <div class="column-invert">
                    <div class="card-pin-add">

                        <img src="${dto.imagePinSolar}" width="200px" height="200px">
                        <h3>${dto.namePinSolar}
                            <input type="hidden" value="${dto.idPinSolar}" name="idPinsolar">
                        </h3>
                        <span> Price: <font color="red" size="3">${dto.pricePinSolar}</font> VND/ Panel</span>
                        <h4>Pin Capacity:<font color="red" size="3"> ${dto.capacityPinSolar} W</font></h4>
                        <h3>
                            <font color="red">You need set up ${pin} panel</font>
                        </h3>
                        <c:url value="DispatcherServlet" var="URLAddToCart">
                            <c:param name="btAction" value="Add To Cart"/>
                            <c:param name="idPinSolar" value="${dto.idPinSolar}"/>
                            <c:param name="pinNumber" value="${pin}"/>
                            <c:param name="pageNumber" value="${requestScope.PAGENUMBER}"/>
                        </c:url>
                        <a href="${URLAddToCart}" class="button-add-tocart">Add To Cart</a>
                            <%--                        <input type="submit" class="button-find" name="btAction" value="Find Inverter">--%>
                            <%--                        <input type="hidden" name="capacityPin" value="${dto.capacityPinSolar}"/>--%>
                            <%--                        <input type="hidden" name="pinNumber" value="${pin}"/>--%>
                        <c:url value="DispatcherServlet" var="UrlInverter">
                            <c:param name="btAction" value="Find Inverter"/>
                            <c:param name="pinNumber" value="${pin}"/>
                            <c:param name="capacityPin" value="${dto.capacityPinSolar}"/>
                        </c:url>
                        <a href="${UrlInverter}" class="button-add-tocart">Find Inverter</a>
                    </div>

                </div>
            </div>

        </form>
        <br/>
    </c:if>
</c:if>
<br/>
<form action="DispatcherServlet">
    <div class="container-inverter">
        <c:set var="inverter" value="${sessionScope.INVERTER}"/>
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
                    <c:url value="DispatcherServlet" var="URLAdd">
                        <c:param name="btAction" value="Add To Cart"/>
                        <c:param name="idPinSolar" value="${dto.idPinSolar}"/>
                        <c:param name="pageNumber" value="${requestScope.PAGENUMBER}"/>
                    </c:url>
                    <a href="${URLAdd}" class="button-find">Add To Cart</a>
                </div>
            </c:forEach>
        </c:if>
        <c:if test="${empty inverter}">
            <h2>Inverter is not found</h2>
        </c:if>
    </div>
</form>
</body>
</html>

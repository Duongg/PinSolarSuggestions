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
<c:set var="money" value="${requestScope.MONEY}"/>
<c:if test="${not empty result}">
    <div class="container-pin">
        <h2>
            <a href="HomeElectricProductServlet">Electric Product</a>
        </h2>
            <h2>Pin Solar</h2>
        <font color="red" size="6">Your total electric bill: ${money} VND</font>
        <div class="row">
            <c:forEach var="dto" items="${result}">
                <div class="column">
                    <form action="DispatcherServlet">
                        <div class="card">
                            <img class="lazy" src="image/giphy.gif" data-src="${dto.imagePinSolar}" width="200px" height="200px">
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

</body>
</html>

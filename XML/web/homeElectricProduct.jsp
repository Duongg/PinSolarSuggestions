<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 6/2/2020
  Time: 9:44 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Home Electric Product</title>
    <link rel="stylesheet" type="text/css" href="css/HomeStyle.css">
</head>
<body>

<div class="container">
    <c:set var="result" value="${requestScope.LISTELECTRICPRODUCT}"/>
    <%--    <c:set var="resultCate" value="${requestScope.LISTCATEGORY}"/>--%>
    <h2>
        <a href="DispatcherServlet">Electric Product</a>
    </h2>
    <form action="DispatcherServlet">
        <div class="box-search">
            <select class="select-brand" value="${LISTCATE}" name="nameCategory">
                <option value="0">-- Category --</option>
                <c:forEach var="cate" items="${LISTCATE}">
                    <option value="${cate}">${cate}</option>
                </c:forEach>
            </select>
            <input type="submit" class="buttonsearch" name="btAction" value="Search">
        </div>
    </form>
    <%--    <c:if test="${not empty resultCate}">--%>
    <%--        <table>--%>
    <%--            <thead>--%>
    <%--            <th>STT</th>--%>
    <%--            <th>Electric Product Name</th>--%>
    <%--            <th>Electric Product Capacity (W)</th>--%>
    <%--            <th>Action</th>--%>
    <%--            </thead>--%>
    <%--            <tbody>--%>
    <%--            <c:forEach var="dto" items="${resultCate}" varStatus="counter">--%>
    <%--                <form action="DispatcherServlet">--%>
    <%--                    <tr>--%>
    <%--                        <td>${counter.count}</td>--%>
    <%--                        <td>${dto.productName}--%>
    <%--                            <input type="hidden" name="idProduct" value="${dto.idProduct}">--%>
    <%--                        </td>--%>
    <%--                        <td>${dto.productCapacity}</td>--%>
    <%--                        <td>--%>
    <%--                            <input type="submit" value="Add" name="btAction">--%>
    <%--                        </td>--%>
    <%--                    </tr>--%>
    <%--                    <tr>--%>
    <%--                    </tr>--%>
    <%--                </form>--%>
    <%--            </c:forEach>--%>
    <%--            </tbody>--%>
    <%--        </table>--%>
    <%--        <br/>--%>
    <%--    </c:if>--%>
    <%--    <c:if test="${empty resultCate}">--%>
    <%--        <h3>No records</h3>--%>
    <%--    </c:if>--%>
    <c:if test="${not empty result}">
        <table>
            <thead>
            <th>STT</th>
            <th>Electric Product Name</th>
            <th>Electric Product Capacity (W)</th>
            <th>Action</th>
            </thead>
            <tbody>
            <c:forEach var="dto" items="${result}" varStatus="counter">
                <form action="DispatcherServlet">
                    <tr>
                        <td>${counter.count}</td>
                        <td>${dto.productName}
                            <input type="hidden" name="idProduct" value="${dto.idProduct}">
                        </td>
                        <td>${dto.productCapacity}</td>
                        <td>
                            <input type="submit" class="buttonadd" value="Add" name="btAction">
                        </td>
                    </tr>
                    <tr>
                    </tr>
                </form>
            </c:forEach>

            </tbody>
        </table>
    </c:if>
    <c:if test="${empty result}">
        <h3>No records</h3>
    </c:if>

    <br/>

    <div class="pagination">
        <c:set var="url" value="${sessionScope.QUERYSTRING}"/>
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
<br/>
<br/>
<br/>
<br/>
<div class="container">
    <c:set var="list" value="${sessionScope.CART}"/>
    <c:if test="${not empty list}">
        <form action="DispatcherServlet">
            <table>
                <thead>
                <th>STT</th>
                <th>Electric Product Name</th>
                <th>Electric Product Capacity (W)</th>
                <th>Quantity</th>
                <th>Using Time</th>
                <th>Action</th>
                </thead>
                <tbody>
                <c:forEach var="dto" items="${list.items}" varStatus="counter">

                    <tr>
                        <td>${counter.count}</td>
                        <td>${dto.value.productName}
                            <input type="hidden" name="idProduct" value="${dto.value.idProduct}">
                        </td>
                        <td>${dto.value.productCapacity}
                            <input type="hidden" name="capacityProduct" value="${dto.value.productCapacity}">
                        </td>
                        <td>
                            <input type="text" name="txtQuantity" value="">
                        </td>
                        <td>
                            <input type="text" name="txtTime" value="">
                        </td>
                        <td><input type="submit" value="Remove" class="buttonremove" name="btAction"></td>
                    </tr>

                </c:forEach>
                </tbody>
            </table>
            <c:set var="money" value="${sessionScope.MONEY}"/>
            <c:set var="capa" value="${sessionScope.CAPA}"/>
            <input type="Submit" value="Caculate Electric Money" name="btAction">
            <c:if test="${not empty capa}">
                <h3>Công suất tiêu thụ 1 tháng: ${capa} W</h3>
            </c:if>
            <c:if test="${not empty money}">
                <h3>Tiền điện phải trả 1 tháng: ${money} VND</h3>
            </c:if>

            <c:if test="${empty money}">
                <h4>No money</h4>
            </c:if>
        </form>
        <br/>
    </c:if>
</div>
</body>
</html>

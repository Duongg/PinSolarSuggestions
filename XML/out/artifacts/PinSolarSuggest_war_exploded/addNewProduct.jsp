<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 7/10/2020
  Time: 11:08 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add New Product</title>
    <link rel="stylesheet" type="text/css" href="css/HomeStyle.css">
</head>
<body>
<div class="container-addnew">
<form action="DispatcherServlet">

        <h2>Add new Product</h2>

        Name Product: <br/>
        <input type="text" name="productName" required></br>
        Capacity Product: <br/>
        <input type="text" name="capacityProduct" required></br>
        Brand Name: <br/>
        <select value="${LISTBRAND}" name="brandProduct" required>
                        <option value="0">-- Brand --</option>
                        <c:forEach var="brand" items="${LISTBRAND}">
                        <option value="${brand}">${brand}</option>
                        </c:forEach>
                    </select>
        <br/>
        <br/>
        Category Name:<br/>
        <select value="${LISTCATEGORY}" name="nameCategory" required>
                        <option value="0">-- Category --</option>
                        <c:forEach var="cate" items="${LISTCATEGORY}">
                        <option value="${cate}">${cate}</option>
                        </c:forEach>
        </select>
        <br/>
        <br/>
        <input type="submit" class="buttonadd" name="btAction" value="Add New">

</form>
</div>
</body>
</html>

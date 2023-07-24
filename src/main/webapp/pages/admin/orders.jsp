<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Orders</title>
</head>
<body>
<h2>Заказы:</h2>
<table>
    <thead>
    <th>Order id</th>
    <th>User id</th>
    <th>Total price</th>
    </thead>
    <tbody>
    <c:forEach items="${orders}" var="order" >
        <tr>
            <td>${order.idUser}</td>
            <td>${order.idOrder}</td>
            <td>${order.totalPrice}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%--DELETE ORDER--%>
</body>
</html>

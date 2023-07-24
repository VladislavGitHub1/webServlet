<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Order basket</title>
</head>
<body>
${user_name}, you you have selected the following products:
<table>
    <thead>
    <th>Name</th>
    <th>Description</th>
    <th>Price</th>
    </thead>
    <tbody>
    <c:forEach items="${medicines_ordered}" var="medicine" >
        <tr>
            <td>${medicine.name}</td>
            <td>${medicine.description}</td>
            <td>${medicine.price}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
Total price = ${total_price}
</body>
</html>

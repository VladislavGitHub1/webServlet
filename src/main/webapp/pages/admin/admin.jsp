<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Admin page</title>
</head>
<body>
<h1>ADMIN PAGE</h1>
<form action="controller">
    <input type="hidden" name="command" value="logOut"/>
    <input type="submit" value="logOut"/>
</form>
<h2></h2>
<form action="controller">
    <input type="hidden" name="command" value="go_to_order_list_page"/>
    <input type="submit" value="Orders page"/>
</form>
<form action="controller">
    <input type="hidden" name="command" value="go_To_DB_Page"/>
    <input type="submit" value="Data base page"/>
</form>
</body>
</html>

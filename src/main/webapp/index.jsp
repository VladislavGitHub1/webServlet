
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Hello!
</h1>
<br/>
<form action="controller">
    <input type="hidden" name="command" value="go_to_login_page"/>
    <input type="submit" name="sub" value="Login"/>
    <br/>
</form>
<form action="controller">
    <input type="hidden" name="command" value="go_to_registration_page"/>
    <input type="submit" name="sub" value="Register"/>
    <br/>
</form>
</body>
</html>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/Testfield/resources/bootstrap/css/bootstrap.css">
        <script src="/Testfield/resources/bootstrap/js/bootstrap.js"></script>
        <title>Hello world</title>
    </head>
    <body>
        <div class="container">
            <h1>${manual.title}!</h1>
            <c:forEach items="${manual.pages}" var="page">
                <c:forEach items="${page.rows}" var="row">
                    <div class="row">
                        <c:forEach items="${row.blocks}" var="block">
                            <div class="col-sm-${block.relBlockWidthTypes[1].amount}">
                                ${block.content}
                            </div>
                        </c:forEach>
                    </div>
                </c:forEach>
            </c:forEach>
        </div>
    </body>
</html>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <title>${manual.title}</title>
        <style>
            .downloable-manual {
                font-family: ${manual.manualConf.fontFamily.cssStyle};
                color: ${manual.manualConf.fontColor.getCssColor()};
            }
        </style>
    </head>
    <body>
        <div class="container downloable-manual">
            <h1>${manual.title}</h1>
            <c:forEach items="${manual.pages}" var="page">
                <c:forEach items="${page.rows}" var="row">
                    <div class="row">
                        <c:forEach items="${row.blocks}" var="block">
                            <div class="${block.getWidthCssStyle()}">
                                ${block.content}
                            </div>
                        </c:forEach>
                    </div>
                </c:forEach>
            </c:forEach>
        </div>
    </body>
</html>

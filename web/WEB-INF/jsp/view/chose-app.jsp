<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Testfield</title>
        
        <jsp:include page="/default-imports" />
        <link rel="stylesheet" href="<c:url value="/resources/testfield_general/style/index.css"/>">
        <script src="<c:url value="/resources/testfield_general/js/choseApp.js"/>"></script>
    </head>
    <body class="background-congruent">
        
        <div class="container">
            
            <div class="row">
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                    <div class="bloque-aplicacion text-center orbitron-font cursor-pointer linkable" data-url="<c:url value="/manuals"/>" >
                        
                        <h1>Manuals</h1>
                        
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                    <div class="bloque-aplicacion text-center orbitron-font cursor-pointer">
                        
                        <h1>Forum</h1>
                        
                    </div>
                </div>
            </div>
            
        </div>
        
    </body>
</html>

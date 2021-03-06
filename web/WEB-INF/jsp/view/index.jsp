<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Testfield</title>
        
        <jsp:include page="/default-imports" />
        <link rel="stylesheet" href="<c:url value="/resources/testfield_general/style/index.css"/>">
        <script src="<c:url value="/resources/testfield_general/js/login.js"/>"></script>
    </head>
    <body class="background-congruent">
        
        <div class="container">
            
            <div class="row">
                <div class="col-xs-12 text-center">
                    
                    <h1 class="orbitron-font color-white"> Testfield </h1>
                    
                </div>
            </div>
            
            <div class="row margin-top-4">
                <div class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-4">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            
                            <form id="form-login" action="<c:url value="/request/login"/>" method="POST">
                                
                                <div class="form-group">
                                    <input type="text" class="form-control" name="userNick" placeholder="User" required>
                                </div>
                                
                                <div class="form-group">
                                    <input type="password" class="form-control" name="password" placeholder="Password" required>
                                </div>
                                
                                <div class="row">
                                    <div class="col-xs-12">
                                        <button type="submit" class="btn btn-default btn-block">Login</button>
                                    </div>
                                </div>
                                
                            </form>
                            
                        </div>
                    </div>
                </div>
            </div>
            
        </div>
        
    </body>
</html>

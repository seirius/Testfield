
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

        <jsp:include page="/default-imports" />
        <jsp:include page="/testfield-manuals" />
    </head>
    <script>
    </script>
    <body>
        <div class="container">
            
            <div class="row">
                <div class="col-xs-12 text-center">
                    <h2>TestField Manuals</h2>
                    <h1>${name}</h1>
                </div>
            </div>

            <div class="row margin-top-3">
                <div class="col-xs-12 col-sm-6 col-sm-offset-3 col-lg-4 col-lg-offset-4">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-12">
                                    <span class="pull-right font-size-2">Login</span>
                                </div>
                            </div>
                        </div>
                        <div class="panel-body">

                            <form action="/Testfield/login-submit" class="" method="POST" role="form" >
                                <div class="row">
                                    <div class="col-xs-12">

                                        <div class="form-group">
                                            <input type="text" class="form-control" name="user" placeholder="User"/>
                                        </div>
                                        <div class="form-group">
                                            <input type="password" class="form-control" name="password" placeholder="Password"/>
                                        </div>
                                        
                                        <div class="row">
                                            <div class="col-xs-4 col-xs-offset-4">
                                                <button type="submit" class="btn btn-info btn-block">Enter</button>
                                            </div>
                                        </div>

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

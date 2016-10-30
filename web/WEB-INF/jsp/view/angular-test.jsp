<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Angular Test</title>
        <jsp:include page="/default-imports" />
        <script src="/Testfield/resources/tests/js/angularTest.js"></script>
    </head>
    <body>
        
        <div ng-app="angularTest" ng-controller="angularController">
            
            <button ng-click="showMessage = !showMessage">Toggle Message</button>
            
            <h2 ng-show="showMessage === true">Secret Message</h2>
            
            <input type="text" placeholder="Leave a message" ng-model="message">
            <h2>{{message}}</h2>
            
            <div class="well" ng-repeat="crib in cribs">

                <h3>{{crib.address}}</h3>
                <p><strong>Type: </strong>{{crib.type}}</p>
                <p><strong>Description: </strong>{{crib.description}}</p>
                <p><strong>Price: </strong>{{crib.price | currency}}</p>

            </div>
            <pre>{{cribs | json}}</pre>
        </div>
        
    </body>
</html>

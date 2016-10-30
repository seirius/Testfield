<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Testfield</title>
        <jsp:include page="/default-imports" />
        <script src="/Testfield/resources/js/jsPDF/jspdf.min.js"></script>
    </head>
    <style>
    </style>
    <script>

        $(document).ready(function () {
            
            $("#toPdf").click(function () {
                var doc = new jsPDF();
                doc.setFontSize(22);
                doc.rect(20, 20, 10, 10, "Cuestionario");
                doc.text(20, 20, "Cuestionario");
                
                doc.save("test.pdf");
            });
        });

    </script>
    <body>
        <div id="htmlToPdf" class="container" style="margin-top: 100px;">
            <div id="div">
                
                <table>
                    <thead>
                        <tr>
                            <td>First name</td>
                            <td>Last name</td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Andriy</td>
                            <td>Yednarovych</td>
                        </tr>
                    </tbody>
                </table>
                
            </div>
        </div>
        
        <div class="container">
            <button id="toPdf" class="btn btn-primary">To PDF</button>
        </div>
        
        <div class="container-fluid">
            
            <embed id="pdfContainer" src="" width="100%" height="100%">
            
        </div>
    </body>
</html>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="navbar navbar-testfield">
    <div class="container-fluid">

        <div class="navbar-header">

            <button class="navbar-toggle" data-toggle="collapse" data-target="#testfieldNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>

            <a href="<c:url value="/"/>" class="navbar-brand text-center">
               <span class="display-block">
                    TestField
                </span>

                <span class="display-block font-size-2">
                    Manuals
                </span>
            </a>

        </div>

        <div class="collapse navbar-collapse" id="testfieldNavbar">

            <ul class="nav navbar-nav">
                <li>

                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        Actions 
                        <span class="caret"></span>
                    </a>

                    <ul class="dropdown-menu">
                        <li><a href="#">Create</a></li>
                        <li><a href="#">Open</a></li>
                        <li><a href="#">Modify</a></li>
                    </ul>

                </li>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        Apps 
                        <span class="caret"></span>
                    </a>

                    <ul class="dropdown-menu">
                        <li><a href="#">Manuals</a></li>
                        <li><a href="#">Forum</a></li>
                    </ul>
                </li>
                <li>
                    <a href="#" id="btnLogout"><span class="glyphicon glyphicon-off"></span> <span>Logout</span></a>
                </li>
            </ul>

        </div>

    </div>
</nav>
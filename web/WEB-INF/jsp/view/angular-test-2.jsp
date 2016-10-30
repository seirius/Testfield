<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Angular Test 2</title>
        <jsp:include page="/default-imports" />
        <script src="/Testfield/resources/tests/js/angularTest_2.js"></script>
        <script src="/Testfield/resources/tests/js/cribsFilter.js"></script>
        <link href="/Testfield/resources/tests/css/style.css" rel="stylesheet" type="text/css"/>
    </head>
    <body ng-app="ngCribs" ng-controller="cribsController">

        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">ng-cribs</a>
                </div>
            </div>
        </nav>

        <div class="container">
            <div class="col-sm-12 price-form">

                <div class="row price-form-row" ng-if="!addListing">

                    <div class="col-sm-6">
                        <div class="input-group">
                            <span class="input-group-addon">Min Price</span>
                            <select name="minPrice" id="minPrice" class="form-control" ng-model="priceInfo.min">
                                <option value="100000">$100,000</option>
                                <option value="200000">$200,000</option>
                                <option value="300000">$300,000</option>
                                <option value="400000">$400,000</option>
                                <option value="500000">$500,000</option>
                                <option value="600000">$600,000</option>
                                <option value="700000">$700,000</option>
                                <option value="800000">$800,000</option>
                                <option value="900000">$900,000</option>
                                <option value="1000000">$1,000,000</option>
                            </select>
                        </div>
                    </div>

                    <div class="col-sm-6">
                        <div class="input-group">
                            <span class="input-group-addon">Max Price</span>
                            <select name="maxPrice" id="maxPrice" class="form-control" ng-model="priceInfo.max">
                                <option value="100000">$100,000</option>
                                <option value="200000">$200,000</option>
                                <option value="300000">$300,000</option>
                                <option value="400000">$400,000</option>
                                <option value="500000">$500,000</option>
                                <option value="600000">$600,000</option>
                                <option value="700000">$700,000</option>
                                <option value="800000">$800,000</option>
                                <option value="900000">$900,000</option>
                                <option value="1000000">$1,000,000</option>
                            </select>
                        </div>
                    </div>

                </div>

                <button class="btn btn-primary" ng-click="addListing = !addListing" ng-show="!addListing">
                    Add Listing
                </button>

                <button class="btn btn-danger" ng-click="addListing = !addListing" ng-show="addListing">
                    Close
                </button>

                <div class="listing-form" ng-if="addListing">

                    <h3>Add a Listing</h3>

                    <div class="row listing-form-row">

                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">Address</span>
                                <input type="text" class="form-control" placeholder="Entere the address" ng-model="newListing.address">
                            </div>
                        </div>

                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">Price</span>
                                <input type="text" class="form-control" placeholder="Entere the price" ng-model="newListing.price">
                            </div>
                        </div>

                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">Property Type</span>
                                <select id="propertyType" 
                                        class="form-control" 
                                        placeholder="Enter the price" 
                                        ng-model="newListing.type">
                                    <option value="House">House</option>
                                    <option value="Condo">Condo</option>
                                    <option value="Duplex">Duplex</option>
                                    <option value="Apartment">Apartment</option>
                                </select>
                            </div>
                        </div>


                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">Description</span>
                                <textarea name="description" 
                                          id="description" 
                                          cols="15" 
                                          rows="5" 
                                          class="form-control"
                                          ng-model="newListing.description" 
                                          placeholder="Enter the description">
                                </textarea>
                            </div>
                        </div>

                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">Bedrooms</span>
                                <input type="text" class="form-control" placeholder="Bedrooms" ng-model="newListing.details.bedrooms">
                            </div>
                        </div>

                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">Bathrooms</span>
                                <input type="text" class="form-control" placeholder="Bathrooms" ng-model="newListing.details.bathrooms">
                            </div>
                        </div>

                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">SqFt</span>
                                <input type="text" class="form-control" placeholder="Square Footage" ng-model="newListing.details.area">
                            </div>
                        </div>

                    </div>

                    <button 
                        class="btn btn-primary listing-button"
                        ng-click="addCrib(newListing)"
                        ng-show="addListing">
                        Add
                    </button>

                    <pre>{{newListing| json}}</pre>

                </div>
                
                <div class="listing-form" ng-if="editListing">

                    <h3>Edit Listing</h3>

                    <div class="row listing-form-row">

                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">Address</span>
                                <input type="text" class="form-control" placeholder="Entere the address" ng-model="existingListing.address">
                            </div>
                        </div>

                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">Price</span>
                                <input type="text" class="form-control" placeholder="Entere the price" ng-model="existingListing.price">
                            </div>
                        </div>

                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">Property Type</span>
                                <select id="propertyType" 
                                        class="form-control" 
                                        placeholder="Enter the price" 
                                        ng-model="existingListing.type">
                                    <option value="House">House</option>
                                    <option value="Condo">Condo</option>
                                    <option value="Duplex">Duplex</option>
                                    <option value="Apartment">Apartment</option>
                                </select>
                            </div>
                        </div>


                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">Description</span>
                                <textarea name="description" 
                                          id="description" 
                                          cols="15" 
                                          rows="5" 
                                          class="form-control"
                                          ng-model="existingListing.description" 
                                          placeholder="Enter the description">
                                </textarea>
                            </div>
                        </div>

                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">Bedrooms</span>
                                <input type="text" class="form-control" placeholder="Bedrooms" ng-model="existingListing.details.bedrooms">
                            </div>
                        </div>

                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">Bathrooms</span>
                                <input type="text" class="form-control" placeholder="Bathrooms" ng-model="existingListing.details.bathrooms">
                            </div>
                        </div>

                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">SqFt</span>
                                <input type="text" class="form-control" placeholder="Square Footage" ng-model="existingListing.details.area">
                            </div>
                        </div>

                    </div>
                    
                        <button 
                            class="btn btn-primary listing-button" 
                            ng-click="saveCribEdit()" 
                            ng-show="editListing">
                            Save
                        </button>

                        <button 
                            class="btn btn-danger listing-button" 
                            ng-click="deleteCrib(existingListing)" 
                            ng-show="editListing">
                            Delete
                        </button>


                </div>

            </div>

        </div>

        

        <div class="container">
            <div class="col-sm-4" ng-repeat="crib in cribs| cribsFilter:priceInfo | orderBy: '-id'">
                <div class="thumbnail">
                    <img ng-src="/Testfield/resources/tests/data/img/{{crib.image}}.jpg" alt="">
                    <div class="caption">

                        <div ng-hide="showDetails === true">
                            <h3>
                                <i class="glyphicon glyphicon-tag"></i> 
                                {{crib.price| currency}} 
                            </h3>
                            <h4>
                                <i class="glyphicon glyphicon-home"></i>
                                {{crib.address}}
                                <span class="label label-primary label-sm">{{crib.type}}</span>
                            </h4>
                        </div>

                        <button class="btn btn-xs btn-success" 
                                ng-hide="showDetails" 
                                ng-click="showDetails = !showDetails">
                            Details
                        </button>

                        <button class="btn btn-xs btn-primary" 
                                ng-show="showDetails" 
                                ng-click="editCrib(crib)">
                            Edit
                        </button>

                        <button class="btn btn-xs btn-danger" 
                                ng-show="showDetails" 
                                ng-click="showDetails = !showDetails">
                            Close
                        </button>

                        <div class="details" ng-show="showDetails === true">
                            <h4>
                                <span class="label label-primary">Beds: {{crib.details.bedrooms}}</span>
                                <span class="label label-primary">Baths: {{crib.details.bathrooms}}</span>
                                <span class="label label-primary">SqFt: {{crib.details.area}}</span>
                            </h4>
                            <p>{{crib.description}}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>

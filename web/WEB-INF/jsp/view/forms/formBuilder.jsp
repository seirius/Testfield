<%-- 
    Document   : formBuilder
    Created on : 12-may-2017, 21:22:13
    Author     : Andriy
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<form ng-controller="formController" ng-init='${form.ngInit()}'>
    <c:forEach items="${form.inputs}" var="input">
        <div class="row">
            <div class="col-xs-12">
                <div class="form-group">
                    <c:if test="${input.type != 'checkbox'}">
                        <label>${input.label}</label>
                    </c:if>

                    <c:if test="${input.type == 'select'}">
                        <select class="form-control"
                                name="${input.name}"
                                ng-model="form.${input.name}"
                                ng-init="form.${input.name} = ''">
                            <c:forEach items="${input.values}" varStatus="loop" var="value">
                                <option value="${value}">${input.texts[loop.index]}</option>
                            </c:forEach>
                        </select>
                    </c:if>

                    <c:if test="${input.type == 'text'}">
                        <input class="form-control" 
                               type="${input.typeName}" 
                               value="${input.value}" 
                               name="${input.name}"
                               ng-model="form.${input.name}">
                    </c:if>

                    <c:if test="${input.type == 'textarea'}">
                        <textarea 
                            class="form-control"
                            name="${input.name}" 
                            cols="${input.cols}"
                            rows="${input.rows}"
                            ng-model="form.${input.name}">${input.value}</textarea>
                    </c:if>

                    <c:if test="${input.type == 'radio'}">
                        <c:forEach items="${input.values}" varStatus="loop" var="value">
                            <div class="radio">
                                <label>
                                    <input 
                                        type="radio"
                                        name="${input.name}"
                                        value="${input.value}"
                                        ng-model="form.${input.name}">
                                    ${input.texts[loop.index]}
                                </label>
                            </div>
                        </c:forEach>
                    </c:if>

                    <c:if test="${input.type == 'checkbox'}">
                        <div class="checkbox">
                            <label>
                                <input 
                                    type="checkbox"
                                    name="${input.name}"
                                    <c:if test="${input.valueCheckbox}">checked</c:if>
                                    ng-model="form.${input.name}"
                                    ng-click="checkbox()">
                                ${input.label}
                            </label>
                        </div>
                    </c:if>

                    <c:if test="${input.type == 'checkboxGroup'}">
                        <c:forEach items="${input.texts}" varStatus="loop" var="text">
                            <label>
                                <input 
                                    type="checkbox"
                                    name="${input.names[loop.index]}"
                                    class="checkbox-inline"
                                    <c:if test="${input.valueCheckbox}">checked</c:if>
                                    ng-model="form.${input.names[loop.index]}">
                                ${text}
                            </label>
                        </c:forEach>
                    </c:if>
                </div>
            </div>
        </div>
    </c:forEach>

    <div class="row">
        <div class="col-xs-12 text-center">
            <button type="submit" class="btn btn-default">Submit</button>
        </div>
    </div>
</form>


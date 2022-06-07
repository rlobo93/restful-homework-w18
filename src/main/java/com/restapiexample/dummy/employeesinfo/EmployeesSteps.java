package com.restapiexample.dummy.employeesinfo;


import com.restapiexample.dummy.constants.EndPoints;
import com.restapiexample.dummy.model.EmployeesPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;



public class EmployeesSteps {


    @Step("Creating Employee with employee_name : {0}, employee_salary: {1}, employee_age: {2}, profile_image: {3} ")
    public ValidatableResponse createEmployee(String employee_name, String employee_salary, String employee_age, String profile_image) {

        EmployeesPojo employeesPojo = new EmployeesPojo();
        employeesPojo.setEmployee_name(employee_name);
        employeesPojo.setEmployee_salary(employee_salary);
        employeesPojo.setEmployee_age(employee_age);
        employeesPojo.setProfile_image(profile_image);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(employeesPojo)
                .when()
                .post()
                .then();
    }


    @Step("Getting the Employee information with employee_name: {0}")
    public HashMap<String, Object> getEmployeeInfoByFirstName(String employee_name) {
        String p1 = "findAll{it.employee_name=='";
        String p2 = "'}.get(0)";

        HashMap<String, Object> EmployeeMap = SerenityRest.given().log().all()
                .when()
                .get(EndPoints.GET_ALL_EMPLOYEE)
                .then()
                .statusCode(200)
                .extract()
                .path(p1 + employee_name + p2);

        return EmployeeMap;
    }


    @Step("Updating Employee information with employee_name : {0}, employee_salary: {1}, employee_age: {2}, profile_image: {3} ")
    public ValidatableResponse updateEmployee(int employeeid, String employee_name, String employee_salary, String employee_age, String profile_image) {
        EmployeesPojo employeesPojo = new EmployeesPojo();
        employeesPojo.setEmployee_name(employee_name);
        employeesPojo.setEmployee_salary(employee_salary);
        employeesPojo.setEmployee_age(employee_age);
        employeesPojo.setProfile_image(profile_image);
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("employeeid", employeeid)
                .body(employeesPojo)
                .when()
                .put(EndPoints.UPDATE_EMPLOYEE)
                .then();
    }


    @Step("Deleting Employee information with employeeId: {0}")
    public ValidatableResponse deleteEmployee(int employeeid) {

        return SerenityRest.given().log().all()
                .pathParam("Employeeid", employeeid)
                .when()
                .delete(EndPoints.DELETE_EMPLOYEE)
                .then().statusCode(204);

    }

    @Step("Getting Employee information with employeeId: {0}")
    public ValidatableResponse getEmployeeById(int employeeid) {

        return SerenityRest.given().log().all()
                .pathParam("Employeeid", employeeid)
                .get(EndPoints.EMPLOYEE)
                .then()
                .statusCode(404);
    }


}

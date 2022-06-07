package com.restapiexample.dummy.studentinfo;


import com.restapiexample.dummy.constants.EndPoints;
import com.restapiexample.dummy.model.EmployeesPojo;
import com.restapiexample.dummy.testbase.TestBase;
import com.restapiexample.dummy.utils.TestUtils;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;


//@RunWith(SerenityRunner.class)
public class StudentCURDTest extends TestBase {

    static String employee_name = "PrimUser" + TestUtils.getRandomValue();
    static String employee_salary = "25000" + TestUtils.getRandomValue();
    static String employee_age = "27";
    static String profile_image = TestUtils.getRandomValue() + "Image";
    static int employeeid;




    @Title("This will create a new employee")
    @Test
    public void test001() {

        EmployeesPojo employeesPojo = new EmployeesPojo();
        employeesPojo.setEmployee_name(employee_name);
        employeesPojo.setEmployee_salary(employee_salary);
        employeesPojo.setEmployee_age(employee_age);
        employeesPojo.setProfile_image(profile_image);
        SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(employeesPojo)
                .when()
                .post(EndPoints.CREATE_EMPLOYEE)
                .then().log().all().statusCode(200);

    }

    @Title("Verify if the employee was added to the application")
    @Test
    public void test002() {
        String p1 = "data.findAll{it.employee_name=='";
        String p2 = "'}.get(0)";

        HashMap<String, Object> EmployeeMap = SerenityRest.given().log().all()
                .when()
                .get(EndPoints.GET_ALL_EMPLOYEE)
                .then()
                .statusCode(200)
                .extract()
                .path(p1 + employee_name + p2);
        Assert.assertThat(EmployeeMap, hasValue(employee_name));
        employeeid = (int) EmployeeMap.get("id");
        System.out.println(employeeid);

    }


    @Title("Update the employee information and verify the updated information")
    @Test
    public void test003() {

        employee_name = employee_name + "_updated";

        EmployeesPojo employeesPojo = new EmployeesPojo();
        employeesPojo.setEmployee_name(employee_name);
        employeesPojo.setEmployee_salary(employee_salary);
        employeesPojo.setEmployee_age(employee_age);
        employeesPojo.setProfile_image(profile_image);
        SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("Employeeid", employeeid)
                .body(employeesPojo)
                .when()
                .put(EndPoints.UPDATE_EMPLOYEE)
                .then().log().all().statusCode(200);

        String p1 = "data.findAll{it.employee_name=='";
        String p2 = "'}.get(0)";

        HashMap<String, Object> EmployeeMap = SerenityRest.given().log().all()
                .when()
                .get(EndPoints.GET_ALL_EMPLOYEE)
                .then()
                .statusCode(200)
                .extract()
                .path(p1 + employee_name + p2);
        Assert.assertThat(EmployeeMap, hasValue(employee_name));


    }


    @Title("Delete the employee and verify if the employee is deleted")
    @Test
    public void test004(){
        SerenityRest.given().log().all()
                .pathParam("Employeeid", employeeid)
                .when()
                .delete(EndPoints.DELETE_EMPLOYEE)
                .then().statusCode(204);

        SerenityRest.given().log().all()
                .pathParam("Employeeid", employeeid)
                .get(EndPoints.EMPLOYEE)
                .then()
                .statusCode(404);

    }



}

package com.restapiexample.dummy.studentinfo;


import com.restapiexample.dummy.employeesinfo.EmployeesSteps;
import com.restapiexample.dummy.testbase.TestBase;
import com.restapiexample.dummy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasValue;



@RunWith(SerenityRunner.class)
public class StudentCURDTestWithSteps extends TestBase {

    static String employee_name = "PrimUser" + TestUtils.getRandomValue();
    static String employee_salary = "25000" + TestUtils.getRandomValue();
    static String employee_age = "27";
    static String profile_image = TestUtils.getRandomValue() + "Image";
    static int employeeid;


    @Steps
    EmployeesSteps employeesSteps;


    @Title("This will create a new employee")
    @Test
    public void test001() {

        ValidatableResponse response = employeesSteps.createEmployee(employee_name, employee_salary, employee_age, profile_image);
        response.log().all().statusCode(200);

    }




    @Title("Verify if the employee was added to the application")
    @Test
    public void test002() {

        HashMap<String, Object> EmployeeMap = employeesSteps.getEmployeeInfoByFirstName(employee_name);

        Assert.assertThat(EmployeeMap, hasValue(employee_name));
        employeeid = (int) EmployeeMap.get("id");
        System.out.println(employeeid);

    }


    @Title("Update the employee information and verify the updated information")
    @Test
    public void test003() {

        employee_name = employee_name + "_updated";

        employeesSteps.updateEmployee(employeeid,employee_name, employee_salary, employee_age, profile_image).log().all().statusCode(200);

        HashMap<String, Object> studentMap = employeesSteps.getEmployeeInfoByFirstName(employee_name);
        Assert.assertThat(studentMap, hasValue(employee_name));

    }


    @Title("Delete the employee and verify if the employee is deleted")
    @Test
    public void test004() {

        employeesSteps.deleteEmployee(employeeid).log().all().statusCode(204);

        employeesSteps.getEmployeeById(employeeid).statusCode(404);


    }

}

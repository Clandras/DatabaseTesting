package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import utilities.DBUtility;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class DatabaseTests {

    @Test(description = "Get full names and salaries")
    public void test1() throws SQLException {
        ResultSet result = DBUtility.getResult("select * from employees");

        while(result.next()){
            String fullName= result.getString(2)+" "+result.getString(3);
            String salary = result.getString("salary");
            System.out.println(fullName +" "+salary);
        }

    }

    @Test(description = "Verify that Steven King has the highest salary")
    public void test2() throws SQLException {
        ResultSet result = DBUtility.getResult("select * from employees");


        List<Integer> salaries = new ArrayList<>();
        for (int i = 0; result.next(); )
            salaries.add(result.getInt("salary"));


        Collections.sort(salaries);
        System.out.println("Salary list = "+salaries);
        int maxSalary = salaries.get(salaries.size() - 1);
        System.out.println("Maximum salary is "+maxSalary);

        String richestGuy = "";
        result = DBUtility.getResult("select * from employees");
        while (result.next()) {
            int money = result.getInt("salary");
            String fullname = result.getString(2) + " " + result.getString(3);
            if (money == maxSalary) {
                richestGuy = fullname;
            }
        }

        System.out.println("Richest Guy is "+ richestGuy);

        Assert.assertEquals(richestGuy, "Steven King");
    }

    @Test(description = "Verify that maximun salary is 24000")
    public void test3() throws SQLException {
        ResultSet result = DBUtility.getResult("select MAX(salary) from employees");
        ResultSetMetaData rsmd = result.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (result.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = result.getString(i);
                System.out.print(columnValue + " " + rsmd.getColumnName(i));
            }
            System.out.println("");
        }

    }

        @Test(description = "Verify that Lex De Haan has the second highest salary(First method")
        public void test4() throws SQLException {

        ResultSet result = DBUtility.getResult("select * from employees where salary = ( select max(salary) from employees where salary<(select max(salary) from employees) )");
        List<Integer> salaries = new ArrayList<>();
        for (int i = 0; result.next(); )
            salaries.add(result.getInt("salary"));


        Collections.sort(salaries);
        System.out.println("Salary list = " + salaries);
        int maxSalary = salaries.get(salaries.size() - 1);
        System.out.println("Second maximum salary is " + maxSalary);

        String SecondRichestGuys = "";
        result = DBUtility.getResult("select * from employees where salary = ( select max(salary) from employees where salary<(select max(salary) from employees) )");
        while (result.next()) {
            int money = result.getInt("salary");
            String fullname = result.getString(2) + " " + result.getString(3);
            if (money == maxSalary) {
                SecondRichestGuys = fullname;
            }
        }

        System.out.println("Second Richest Guy is " + SecondRichestGuys);

        Assert.assertEquals(SecondRichestGuys, "Lex De Haan");
    }
    @Test(description = "Verify that Lex De Haan has the second highest salary(Second method)")

    public void test5() throws SQLException {
        ResultSet result = DBUtility.getResult("select * from employees");
        List<Integer> salaries = new ArrayList<>();
        for (int i = 0; result.next(); )
            salaries.add(result.getInt("salary"));


        Collections.sort(salaries);
        System.out.println("Salary list = " + salaries);
        int maxSalary = salaries.get(salaries.size() - 2);
        System.out.println("Second maximum salary is " + maxSalary);

        String SecondRichestGuys = "";
        result = DBUtility.getResult("select * from employees where salary = ( select max(salary) from employees where salary<(select max(salary) from employees) )");
        while (result.next()) {
            int money = result.getInt("salary");
            String fullname = result.getString(2) + " " + result.getString(3);
            if (money == maxSalary) {
                SecondRichestGuys = fullname;
            }
        }

        System.out.println("Second Richest Guy is " + SecondRichestGuys);

        Assert.assertEquals(SecondRichestGuys, "Lex De Haan");
    }

    @Test(description = "Verify that TC Olson has the lowest salary")
    public void test6() throws SQLException {
        ResultSet result = DBUtility.getResult("select * from employees");

        List<Integer> salaries = new ArrayList<>();
        for (int i = 0; result.next(); )
            salaries.add(result.getInt("salary"));

        Collections.sort(salaries);
        System.out.println("Salary list = "+salaries);
        int minSalary = salaries.get(0);
        System.out.println("Minimum salary is "+minSalary);

        String poorestGuy = "";
        result = DBUtility.getResult("select * from employees");
        while (result.next()) {
            int money = result.getInt("salary");
            String fullname = result.getString(2) + " " + result.getString(3);
            if (money == minSalary) {
                poorestGuy = fullname;
            }
        }

        System.out.println("Poorest Guy is "+ poorestGuy);

        Assert.assertEquals(poorestGuy, "TJ Olson");
    }


    @Test(description = "Verify that Total Number of Columns is 6 and Third Column Name: POSTAL_CODE")
    public void test7() throws SQLException{

        ResultSet result = DBUtility.getResult("Select * from Locations");
        ResultSetMetaData rsm = result.getMetaData();

        System.out.println("Total Number of Columns: "+rsm.getColumnCount());
        System.out.println("Third Column Name: "+rsm.getColumnName(3));

        String[] ColumnNames =new String[rsm.getColumnCount()];
        for(int i=0; i < ColumnNames.length; i++){
            ColumnNames[i]  = rsm.getColumnName(i+1 );
        }

        System.out.println("Column Names = "+ Arrays.toString(ColumnNames));

    }




}



package HR.DataAccessLayer;

import HR.BusinessLayer.Employee;
import HR.BusinessLayer.JobType;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDAO {

    private Connect conn = Connect.getInstance();

    public EmployeeDAO() {}

    public Employee get_employee(String employee_id) {
        try {
            List<HashMap<String, Object>> personalDetails = conn.executeQuery("SELECT * FROM Employees WHERE id = ?", employee_id);
            if (personalDetails.isEmpty()) {
                return null;
            }
            return employee_record(personalDetails);
        }
        catch (SQLException exception) {
            return null;
        }
    }

    public Map<Employee, String> load_Data() {
        Map<Employee, String> employees = new HashMap<>();
        try {
            List<HashMap<String, Object>> employees_details = conn.executeQuery("SELECT * FROM Employees");
            for (HashMap<String, Object> record: employees_details) {
                String pass = employee_record_password(record);
                Employee employee = employee_record(record);
                employees.put(employee, pass);
            }
            return employees;
        }
        catch (SQLException exception) {
            return null;
        }
    }

    private String employee_record_password(HashMap<String, Object> personalDetails) {
        return (String) personalDetails.get("password");
    }

    private Employee employee_record(HashMap<String, Object> personalDetails) {
        try {
            Integer id = (Integer) personalDetails.get("id");
            String name = (String) personalDetails.get("name");
            Integer bank_account = (Integer) personalDetails.get("bankAccount");
            double salary = (Double) personalDetails.get("salary");
            String terms_of_employment = (String) personalDetails.get("termsOfEmployment");
            long date = (long) personalDetails.get("employmentDate");
            Date employment_date = new Date(date);
            String family_status = (String) personalDetails.get("familyStatus");
            Integer student = (Integer) personalDetails.get("isStudent");
            boolean is_student = student.equals(1);
            double current_monthly_salary = (Double) personalDetails.get("currentSalary");
            Employee employee = new Employee(id, name, bank_account, salary, terms_of_employment, employment_date, family_status, is_student, this);
            List<HashMap<String, Object>> rolesSet = conn.executeQuery("SELECT jobType FROM Roles WHERE id = ?", id);
            List<HashMap<String, Object>> stores = conn.executeQuery("SELECT * FROM Stores WHERE id = ?", id);
            reconstructEmployeeStores(employee, stores);
            reconstructEmployeeRoles(employee, rolesSet);
            employee.set_current_salary(current_monthly_salary);
            return employee;
        } catch (SQLException e) {
            return null;
        }
    }

    private Employee employee_record(List<HashMap<String, Object>> personalDetails) {
        try {
            Integer id = (Integer) personalDetails.get(0).get("id");
            String name = (String) personalDetails.get(0).get("name");
            Integer bank_account = (Integer) personalDetails.get(0).get("bankAccount");
            double salary = (Double) personalDetails.get(0).get("salary");
            String terms_of_employment = (String) personalDetails.get(0).get("termsOfEmployment");
            long date = (long) personalDetails.get(0).get("employmentDate");
            Date employment_date = new Date(date);
            String family_status = (String) personalDetails.get(0).get("familyStatus");
            Integer student = (Integer) personalDetails.get(0).get("isStudent");
            boolean is_student = student.equals(1);
            double current_monthly_salary = (Double) personalDetails.get(0).get("currentSalary");
            Employee employee = new Employee(id, name, bank_account, salary, terms_of_employment, employment_date, family_status, is_student, this);
            List<HashMap<String, Object>> rolesSet = conn.executeQuery("SELECT jobType FROM Roles WHERE id = ?", id);
            List<HashMap<String, Object>> stores = conn.executeQuery("SELECT * FROM Stores WHERE id = ?", id);
            reconstructEmployeeStores(employee, stores);
            reconstructEmployeeRoles(employee, rolesSet);
            employee.set_current_salary(current_monthly_salary);
            return employee;
        } catch (SQLException e) {
            return null;
        }
    }

    private void reconstructEmployeeRoles(Employee employee, List<HashMap<String,Object>> rolesSet) throws SQLException {
        for (int i = 0; i < rolesSet.size(); i++){
            String role = (String) rolesSet.get(i).get("jobType");
            if (role == null)
                return;
            employee.add_role(JobType.valueOf(role));
        }
    }

    private void reconstructEmployeeStores(Employee employee, List<HashMap<String,Object>> storesSet) throws SQLException {
        for (int i = 0; i < storesSet.size(); i++){
            String store = (String) storesSet.get(i).get("store");
            if (store == null)
                return;
            employee.add_store(store);
        }
    }

    public String add_employee(int id_num, String name, int bank_account_num, double salary_num, String terms_of_employment, Date date_object, String family_status, boolean student, String password) {
        try{
            int student_index = 0;
            if (student) {
                student_index = 1;
            }
            conn.executeUpdate("INSERT INTO Employees (id, name, password, salary, termsOfEmployment, familyStatus, isStudent, bankAccount, employmentDate, currentSalary) VALUES(?,?,?,?,?,?,?,?,?,?)",id_num, name, password, salary_num, terms_of_employment, family_status, student_index, bank_account_num, date_object, 0);
            return "";
        } catch (SQLException e) {
            return "Employee with id " + id_num + " is already registered in the system";
        }
    }

    public String remove_employee(int id_num) {
        try {
            conn.executeUpdate("DELETE FROM Employees WHERE id = " + id_num);
            return "";
        }
        catch (SQLException e){
            return "Unable to delete employee.";
        }
    }

    public String certify_role(Integer id, String job) {
        try{
            conn.executeUpdate("INSERT INTO Roles (id, jobType) VALUES(?,?)", id, job);
            return "";
        } catch (SQLException e) {
            return "Employee with id " + id + " is already certified to this role";
        }
    }


    public String remove_role(Integer id, String job) {
        try{
            conn.executeUpdate("DELETE FROM Roles WHERE id = " + id + " AND jobType = '" + job + "'");
            return "";
        } catch (SQLException e) {
            return "Employee with id " + id + " isn't certified to this role";
        }
    }

    public String certify_store(Integer id, String store) {
        try{
            conn.executeUpdate("INSERT INTO Stores (id, store) VALUES(?,?)", id, store);
            return "";
        } catch (SQLException e) {
            return "Employee with id " + id + " is already certified to this store";
        }
    }


    public String remove_store(Integer id, String store) {
        try{
            conn.executeUpdate("DELETE FROM Stores WHERE id = " + id + " AND store = '" + store + "'");
            return "";
        } catch (SQLException e) {
            return "Employee with id " + id + " isn't certified to this store";
        }
    }

    public String change_name(Integer id, String new_name) {
        try{
            conn.executeUpdate("UPDATE Employees SET name = '" + new_name + "' WHERE id = " + id);
            return "";
        } catch (SQLException e) {
            return "Couldn't update the data base";
        }
    }

    public String change_bank_account(Integer id, Integer new_bank_account) {
        try{
            conn.executeUpdate("UPDATE Employees SET bankAccount = " + new_bank_account + " WHERE id = " + id);
            return "";
        } catch (SQLException e) {
            return "Couldn't update the data base";
        }
    }

    public String change_family_status(Integer id, String new_family_status) {
        try{
            conn.executeUpdate("UPDATE Employees SET familyStatus = '" + new_family_status + "' WHERE id = " + id);
            return "";
        } catch (SQLException e) {
            return "Couldn't update the data base";
        }
    }

    public String change_student(Integer id, boolean new_student_status) {
        int student = 0;
        if (new_student_status) {
            student = 1;
        }
        try{
            conn.executeUpdate("UPDATE Employees SET isStudent = " + student + " WHERE id = " + id);
            return "";
        } catch (SQLException e) {
            return "Couldn't update the data base";
        }
    }

    public String change_employee_salary(Integer id, Integer new_salary) {
        try{
            conn.executeUpdate("UPDATE Employees SET salary = " + new_salary + " WHERE id = " + id);
            return "";
        } catch (SQLException e) {
            return "Couldn't update the data base";
        }
    }

    public String change_employee_terms(Integer id, String new_terms) {
        try{
            conn.executeUpdate("UPDATE Employees SET termsOfEmployment = '" + new_terms + "' WHERE id = " + id);
            return "";
        } catch (SQLException e) {
            return "Couldn't update the data base";
        }
    }


}

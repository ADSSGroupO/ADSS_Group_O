package BusinessLayer;
import java.time.LocalDate;
import java.util.*;


public class Worker {
    protected String name;
    protected Integer id;
    protected Integer bank_account;
    protected Integer salary;
    protected String family_status;
    protected boolean is_student;
    protected String terms_of_employment;
    protected LocalDate employment_start_date;
    protected Map<String, Branch> qualified_branches;
    protected List<role_type> roles;
    public enum role_type {
        Cashier,
        Storekeeper,
        Security,
        Cleaning,
        Usher,
        General
    }

    public Worker(String name, Integer id, Integer bank_account, Integer salary, String family_status, boolean is_student, String terms_of_employment, LocalDate employment_start_date) {
        this.name = name;
        this.id = id;
        this.bank_account = bank_account;
        this.salary = salary;
        this.family_status = family_status;
        this.is_student = is_student;
        this.terms_of_employment = terms_of_employment;
        this.employment_start_date = employment_start_date;
        roles = new LinkedList<>();
        qualified_branches = new HashMap<>();
    }

    public boolean available_to_shift(LocalDate date, String branch, Shift.shift_type type) {
        if (qualified_branches.containsKey(branch) && check_manager_constraint(date, branch, type)) {
            qualified_branches.get(branch).add_availability(date, type, this.id);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean add_qualified_branch(String branch_name, Branch branch) {
        if (qualified_branches.containsKey(branch_name)) {
            return false;
        }
        else {
            qualified_branches.put(branch_name, branch);
            return true;
        }
    }

    private boolean check_manager_constraint(LocalDate date, String branch, Shift.shift_type type) {
        return qualified_branches.get(branch).getSchedule().check_manager_constraint(date, type, this.id);
    }

    public boolean add_role(String role) {
        role_type Role = role_type.valueOf(role);
        if (roles.contains(Role)) {
            return false;
        }
        roles.add(Role);
        return true;
    }

    public boolean remove_role(String role) {
        role_type Role = role_type.valueOf(role);
        if (!roles.contains(Role)) {
            return false;
        }
        roles.remove(Role);
        return true;
    }

    public List<role_type> getRoles() {
        return roles;
    }
}

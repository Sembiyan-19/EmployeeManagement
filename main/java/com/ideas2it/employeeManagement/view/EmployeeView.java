//                                                                             ;
package com.ideas2it.employeeManagement.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.ideas2it.employeeManagement.controller.EmployeeController;

/**
 * Class that performs employee view operations
 *
 * @version 2.1 21 April 2021
 * @author Sembiyan
 */
public class EmployeeView {

    private EmployeeController employeeController = new EmployeeController();
    private Scanner scanner = new Scanner(System.in);

    /**
     * Shows the various operations that can be performed
     */
    public void homePage() {
        int userOption;
        String displayMenu = "\n\nSelect the operation to be performed.\n "
                + "1. Add new employee details\n 2. Retrieve employee details"
                + "\n 3. Delete an employee details\n 4. Update an employee "
                + "details\n 5. View all employee details\n "
                + "6. Back to home page";
        do {
            System.out.println(displayMenu);
            userOption = scanner.nextInt();
            decideOperation(userOption);
        } while (6 != userOption);
    }

    /**
     * Fetches the employee details such as ID, name, salary, mobile number,
     * date of birth and addresses
     */
    private void addEmployee() {
        System.out.println("Enter employee ID");
        int id = scanner.nextInt();
        if (employeeController.checkEmployeeIdPresenceIncludingDeleted(id)) {
            System.out.println(getSpacing() + "  ---  Employee Id is "
                   + "already used  ---" + getSpacing());
        } else {
            System.out.println("Enter employee name");
            scanner.skip("[\r\n]+");
            String name = scanner.nextLine();
            System.out.println("Enter employee salary");
            float salary = scanner.nextFloat();
            String mobileNumber = getMobileNumber();
            Date dateOfBirth = getDateOfBirth();
            List<List<String>> addresses = getAddresses();
            if (employeeController.addEmployee(id, name, salary,
                    mobileNumber, dateOfBirth, addresses)) {
                System.out.println(getSpacing() + "  ---  Successfully added"
                        + " new employee details  ---" + getSpacing());
            } else {
                System.out.println(getSpacing() + "  ---  Failed to add "
                        + "employee details  ---" + getSpacing());
            }
        }    
    }

    /**
     * Retrieves the employee details, and display them
     */
    private void retrieveEmployee() {
        System.out.println("\n\nEnter employee ID");
        int id = scanner.nextInt();
        if (employeeController.checkEmployeeIdPresence(id)) {
            System.out.println(employeeController.retrieveEmployee(id));
        } else {
            System.out.println(getSpacing() + "  ---  Employee Id not present"
                    + "  ---" + getSpacing());
        }
    }

    /**
     * Fetches the ID of the employee, whose details should be deleted
     */
    private void deleteEmployee() {
        System.out.println("\n\nEnter employee ID");
        int id = scanner.nextInt();
        if (employeeController.checkEmployeeIdPresence(id)) {
            if (employeeController.deleteEmployee(id)) {
                System.out.println(getSpacing() + "  ---  Successfully removed"
                        + " a employee's details  ---" + getSpacing());
            } else {
                System.out.println(getSpacing() + "  ---  Failed to remove a "
                        + "employee. Try again  ---" + getSpacing());
            }
        } else {
            System.out.println(getSpacing() + "  ---  Employee Id not present"
                    + "  ---" + getSpacing());
        }
    }

    /**
     * Shows and decides which details should be updated
     */
    private void updateEmployee() {
        int userOption;
        String displayMenu = "\n\n1. Update employee's personal details\n"
                + "2. Add new address\n3. Delete existing address\n"
                + "4. Update existing address\n5. Assign projects\n"
                + "6. Unassign projects\n7. Back to main menu";
        System.out.println("Enter employee ID");
        int id = scanner.nextInt();
        if (employeeController.checkEmployeeIdPresence(id)) {
            do {
                System.out.println(displayMenu);
                userOption = scanner.nextInt();
                switch (userOption) {
                    case 1:
                        updateEmployeePersonalDetails(id);
                        break;
                    case 2:
                        addNewAddress(id);
                        break;
                    case 3:
                        deleteExistingAddress(id);
                        break;
                    case 4:
                        updateExistingAddress(id);
                        break;
                    case 5:
                        assignProjects(id);
                        break;
                    case 6:
                        unassignProjects(id);
                        break;
                    case 7:
                        break;
                    default:
                        System.out.println(getSpacing() + "  ---  Ivalid "
                                + "option  ---" + getSpacing());
                        break;
                }
            } while (7 != userOption);
        } else {
            System.out.println(getSpacing() + "  ---  Employee Id not present"
                    + "  ---" + getSpacing());
        }
    }

    /**
     * Shows the options that can be updated in employee's pesrsonal details
     * such as name, salary, mobile number and date of birth
     * @param id             Id of employee whose details should be updated
     */
    private void updateEmployeePersonalDetails(int id) {
        int userOption;
        String name = null;
        float salary = 0;
        String mobileNumber = null;
        Date dateOfBirth = null;
        boolean isInvalid = false;
        String displayMenu = "1. Name\n2. Salary\n3. Mobile number\n"
                + "4. Date of birth"; 
        do {
            System.out.println(displayMenu);
            userOption = scanner.nextInt();
            switch (userOption) {
                case 1:
                    System.out.println("Enter the name");
                    scanner.skip("[\r\n]+");
                    name = scanner.nextLine();
                    break;
                case 2:
                    System.out.println("Enter the salary");
                    salary = scanner.nextFloat();
                    break;
                case 3:
                    System.out.println("Enter the mobile number");
                    scanner.skip("[\r\n]+");
                    mobileNumber = getMobileNumber();
                    break;
                case 4:
                    System.out.println("Enter the date of birth");
                    dateOfBirth = getDateOfBirth();
                    break;
                default:
                    System.out.println(getSpacing() + "  ---  Invalid option"
                            + "  ---" + getSpacing());
                    isInvalid = true;
                    break;
            }
            if ((1 == userOption) || (2 == userOption) || (3 == userOption)
                    || (4 == userOption)) {
                System.out.println("Update any other details?\n1. yes\n"
                        + "2. No");
                userOption = scanner.nextInt();
            }
        } while (1 == userOption || isInvalid);
        if (employeeController.updateEmployee(id, name, salary,
                mobileNumber, dateOfBirth)) {
            System.out.println(getSpacing() + "  ---  Successfully updated "
                    + "employee's details  ---" + getSpacing());
        } else {
            System.out.println(getSpacing() + "  ---  Failed to update "
                    + "employee's details  ---" + getSpacing());
        }
    }

    /**
     * Fetches a new address for the employee
     * @param id         ID of the employee to whom the address should be added
     */
    private void addNewAddress(int id) {
        String addressType = "secondary";
        if (employeeController.addNewAddress(id, getNewAddress(addressType))) {
            System.out.println(getSpacing() + "  ---  Successfully added new"
                    + " address  ---" + getSpacing());
        } else {
            System.out.println(getSpacing() + "  ---  Failed to add address."
                    + " Try again  ---" + getSpacing());
        }
    }

    /**
     * Displays all the addresses of the employee and decides which
     * address should be deleted
     * @param id         ID of the employee whose address should be deleted
     */
    private void deleteExistingAddress(int id) {
        int numberOfAddresses = displayAddresses(id);
        System.out.println("enter the address to be deleted");
        int userOption = scanner.nextInt();
        if ((userOption > 0) && userOption <= numberOfAddresses) {
            userOption = userOption - 1;
            if (employeeController.deleteExistingAddress(id, userOption)) {
                System.out.println(getSpacing() + "  ---  Successfully deleted"
                        + " a adress  ---" + getSpacing());
            } else {
                System.out.println(getSpacing() + "  ---  Failed to delete "
                        + "address. Try again  ---" + getSpacing());
            }
        } else {
            System.out.println(getSpacing() + "  ---  Ivalid address option"
                    + "  ---" + getSpacing());
        }
    }

    /**
     * Shows the options that can be updated in employee's address details
     * such as door number, street, city, state, pincode and country
     * @param id             Id of employee whose details should be updated
     */
    private void updateExistingAddress(int id) {
        int numberOfAddresses = displayAddresses(id);
        System.out.println("Enter the address to be updated");
        int userOption = scanner.nextInt();
        if ((userOption > 0) && (userOption <= numberOfAddresses)) {
            int selectedAddressOption = userOption - 1;
            String doorNumber = null;
            String street = null;
            String city = null;
            String state = null;
            String country = null;
            boolean isInvalid;
            String pincode = null;
            String display = "\n\n1. update door number\n2. update street\n"
                    + "3. update city\n4. update pincode\n5. update state\n"
                    + "6. update country";
            do {
                isInvalid = false;
                System.out.println(display);
                userOption = scanner.nextInt();
                switch (userOption) {
                    case 1:
                        System.out.println("Enter the door number");
                        scanner.skip("[\r\n]+");
                        doorNumber = scanner.nextLine();
                        break;
                    case 2:
                        System.out.println("Enter the street name");
                        scanner.skip("[\r\n]+");
                        street = scanner.nextLine();
                        break;
                    case 3:
                        System.out.println("Enter the city name");
                        scanner.skip("[\r\n]+");
                        city = scanner.nextLine();
                            break;
                    case 4:
                        System.out.println("Enter the pincode");
                        scanner.skip("[\r\n]+");
                        pincode = scanner.nextLine();
                        break;
                    case 5:
                        System.out.println("Enter the state name");
                        scanner.skip("[\r\n]+");
                        state= scanner.nextLine();
                        break;
                    case 6:
                        System.out.println("Enter the country name");
                        scanner.skip("[\r\n]+");
                        country = scanner.nextLine();
                        break;
                    default:
                        System.out.println(getSpacing() + "  ---  Invalid "
                                + "option  ---" + getSpacing());
                        isInvalid = true;
                        break;
                }
                if (userOption >= 1 && userOption <= 6) {
                    System.out.println("Update further project details?\n"
                            + "1. yes\n2. No");
                    userOption = scanner.nextInt();
                }
            } while (1 == userOption || isInvalid);
            if (employeeController.updateExistingAddress(id,
                    selectedAddressOption, doorNumber, street, city, pincode,
                    state, country, "secondary")) {
                System.out.println(getSpacing() + "  ---  Successfully "
                        + "updated address details  ---" + getSpacing());
            } else {
                System.out.println(getSpacing() + "  ---  Failed to update "
                        + "address details" + getSpacing());
            }
        } else {
            System.out.println(getSpacing() + "  ---  Ivalid address option"
                    + "  ---" + getSpacing());
        }
    }

    /**
     * Retrieves all employees' details and display them
     */
    private void showAllEmployeesDetails() {
        List<String> employees = employeeController.getAllEmployees();

        for (String employee : employees) {
            System.out.println(employee);
        }   
    }

    /**
     * Fetches various project Ids to be assigned for the employee
     * @param employeeId            
     *     Id of the employee to whom the projects should be assigned
     */
    private void assignProjects(int employeeId) {
        System.out.println("Enter the number of projects to be assigned");
        int numberOfProjectIds = scanner.nextInt();
        List<Integer> projectIds = new ArrayList<Integer>();
        for (int i = 1; i <= numberOfProjectIds; i++) {
            System.out.println("Enter project Id (" + i + ")");
            projectIds.add(scanner.nextInt());
        }
        List<Integer> invalidIds
                = employeeController.checkProjectIdPresence(projectIds);
        if (0 == invalidIds.size()) {
            List<List<Integer>> list
                    = employeeController.checkProjectInEmployee(projectIds,
                    employeeId);
            List<Integer> idsPresent = list.get(0);
            List<Integer> idsNotPresent = list.get(1);
            if (null != idsNotPresent) {
                if (employeeController.assignProjects(idsNotPresent, employeeId)) {
                    System.out.println(getSpacing() + "  ---  Successfully "
                            + "assigned projects " + idsNotPresent + " for the employee"
                            + "  ---" + getSpacing());
                    if (0 != idsPresent.size()) {
                        System.out.println(getSpacing() + "  ---  Projects " 
                                + idsPresent + " are already assigned for the employee"
                                + "  ---" + getSpacing());
                    }
                } else {
                    System.out.println(getSpacing() + "  ---  Failed to assign "
                            + "projects for the employee  ---" + getSpacing());
                }
            } else {
                System.out.println(getSpacing() + "  ---  The projects already"
                        + " assigned for the employee  ---" + getSpacing());
            }
        } else {
            System.out.println(getSpacing() + "  ---  Project Ids " + invalidIds
                    + " are not present  ---" + getSpacing());
        }
    }

    /**
     * Fetches various project Ids to be unassigned for the employee
     * @param employeeId            
     *     Id of the employee from whom the projects should be assigned
     */
    private void unassignProjects(int employeeId) {
        System.out.println("Enter the number of projects to be unassigned");
        int numberOfProjectIds = scanner.nextInt();
        List<Integer> projectIds = new ArrayList<Integer>();
        for (int i = 1; i <= numberOfProjectIds; i++) {
            System.out.println("Enter project Id (" + i + ")");
            projectIds.add(scanner.nextInt());
        }
        List<List<Integer>> list = employeeController.checkProjectInEmployee(projectIds,
                employeeId);
        List<Integer> idsPresent = list.get(0);
        if (idsPresent != null) {
            if (employeeController.unassignProjects(idsPresent, employeeId)) {
                System.out.println(getSpacing() + "  ---  Successfully "
                        + "unassigned projects " + idsPresent + " for the employee  ---" 
                        + getSpacing());
            }
        } else {
            System.out.println(getSpacing() + "  ---  Employee is not present"
                    + " in the projects  ---" + getSpacing());
        }
        List<Integer> invalidIds = list.get(1);
        if (0 != invalidIds.size()) {
            System.out.println(getSpacing() + "  ---  Employee is not present"
                    + " in the following projects  ---\n" + invalidIds
                    + getSpacing());
        }
    }

    /**
     * Displays all the addresses of the employee
     * @param id         ID of the employee whose addresses should be displayed
     * @return           number of addresses present
     */
    private int displayAddresses(int id) {
        int addressCount = 0;
        int numberOfAddresses = 0;
        List<String> listOfAddress = new ArrayList<String>();
        listOfAddress = employeeController.getAllAddress(id);
        numberOfAddresses = listOfAddress.size();
        System.out.println(getSpacing() + "  ---  The employee have the "
                + "following addresses  ---" + getSpacing());

        for (String address : listOfAddress) {
            addressCount = addressCount + 1;
            System.out.println(" " + addressCount + ". " + address);
        }
        return numberOfAddresses;
    }

    /**
     * Gets the  list number of addresses such as permanent address,
     * current address
     */
    private List<List<String>> getAddresses() {
        List<List<String>> addresses = new ArrayList<List<String>>();
        int userOption;
        boolean isPermanentAdddress = false;
        String addressType;
        do {
            addressType = "secondary";
            if (false == isPermanentAdddress) {
                System.out.println("  ---  Enter the permanent address  ---");
                isPermanentAdddress = true;
                addressType = "permanent";
            }
            addresses.add(getNewAddress(addressType));
            System.out.println("Add another address?\n  1. Yes\n  2. No");
            userOption = scanner.nextInt();
        } while (userOption == 1);
        return addresses;
    }

    /**
     * Fetches the details of address such as door number, street, city,
     * pincode, state and country
     */
    private List<String> getNewAddress(String addressType) {
        List<String> addressDetails = new ArrayList<String>();
        System.out.println("Please enter the following address details");
        System.out.println("Door number:");
        scanner.skip("[\r\n]+");
        addressDetails.add(scanner.nextLine());
        System.out.println("Street name:");
        addressDetails.add(scanner.nextLine());
        System.out.println("City name:");
        addressDetails.add(scanner.nextLine());
        System.out.println("Pincode:");
        addressDetails.add(scanner.nextLine());
        System.out.println("State name:");
        addressDetails.add(scanner.nextLine());
        System.out.println("Country:");
        addressDetails.add(scanner.nextLine());
        addressDetails.add(addressType);
        return addressDetails;
    }

    /**
     * Fetches the date for validation and returns the proper date
     * @return                validated date 
     */
    private Date getDateOfBirth() {
        System.out.println("Enter employee date of birth (dd/mm/yyyy)");
        String uncheckedDateOfBirth = scanner.next();
        Date dateOfBirth = employeeController.getDateOfBirth(uncheckedDateOfBirth);
        
        if (null == dateOfBirth) {
            System.out.println(getSpacing() + "  ---  Ivalid date/date format"
                    + "  ---" + getSpacing());
            dateOfBirth = getDateOfBirth();
        }
        return dateOfBirth;
    }

    /**
     * Fetches the mobile number for validation and returns the 
     * proper mobile number
     * @return               validated mobile number
     */
    private String getMobileNumber() {
        System.out.println("Enter employee mobile number");
        String mobileNumber = scanner.next();
        
        if (employeeController.validateMobileNumber(mobileNumber)) {
            
        } else {
            System.out.println(getSpacing() + "  ---  Ivalid mobile number"
                    + "  ---" + getSpacing());
            mobileNumber = getMobileNumber();
        }
        return mobileNumber;
    }

    private void decideOperation(int userOption) {
        switch(userOption) {
            case 1:
                addEmployee();
                break;
            case 2:
                retrieveEmployee();
                break;
            case 3:
                deleteEmployee();
                break;
            case 4:
                updateEmployee();
                break;
            case 5:
                showAllEmployeesDetails();
                break;
            case 6:
                System.out.println(getSpacing() + "  ---  Thank you  ---"
                        + getSpacing());
                break;
            default:             
                System.out.println(getSpacing() + "  ---  Please provide a "
                        + "valid input  ---" + getSpacing());
                break;
        }
    }

    private String getSpacing() {
        return ("\n\n--------------------------------------------" 
                + "------------------------------------------\n\n");
    }
}




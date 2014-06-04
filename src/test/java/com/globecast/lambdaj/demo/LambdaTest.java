package com.globecast.lambdaj.demo;

import static ch.lambdaj.Lambda.by;
import static ch.lambdaj.Lambda.group;
import static ch.lambdaj.Lambda.index;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.sort;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import ch.lambdaj.group.Group;

public class LambdaTest {
	private static List<Employee>	employees	= Collections.unmodifiableList(Arrays.asList(new Employee(1, "Christophe", "Consultant", 45), null,
														new Employee(2, "Alex", "Consultant", 25), new Employee(3, "Guillaume", "Commercial", 26)));

	@Test
	public void testSort() {
		// Standard way
		List<Employee> sortedEmployees = triParNomClassique(new ArrayList<Employee>(employees));
		testIfSortedByName(sortedEmployees);

		// LambdaJ way
		sortedEmployees = triParNomLambdaJ(new ArrayList<Employee>(employees));
		testIfSortedByName(sortedEmployees);
	}

	private void testIfSortedByName(List<Employee> sortedEmployees) {
		assertNotNull(sortedEmployees);
		assertEquals(4, sortedEmployees.size());
		assertEquals(2, sortedEmployees.get(0));
		assertEquals(1, sortedEmployees.get(1));
		assertEquals(3, sortedEmployees.get(2));
		assertNull(sortedEmployees.get(3));
	}

	private List<Employee> triParNomClassique(List<Employee> employees) {
		Collections.sort(employees, new Comparator<Employee>() {
			public int compare(Employee o1, Employee o2) {
				if (o1 == null) {
					return o2 == null ? 0 : 1;
				}
				if (o2 == null) {
					return -1;
				}
				if (o1.getFirstName() == null) {
					return o2.getFirstName() == null ? 0 : 1;
				}
				if (o2.getFirstName() == null) {
					return -1;
				}
				return o1.getFirstName().compareTo(o2.getFirstName());
			}
		});
		return employees;
	}

	private List<Employee> triParNomLambdaJ(List<Employee> employees) {
		return sort(employees, on(Employee.class).getFirstName());
	}

	/**
	 * Ventiler les employ&eacute;s par fonction, i.e. group by dans le langage SQL
	 */
	@Test
	public void testGroup() {
		Map<String, List<Employee>> employeesGroupByFunction = getEmployeesGroupByFunction();
		assertNotNull(employeesGroupByFunction);
		assertEquals(2, employeesGroupByFunction.get("Consultant").size());
		Group<Employee> employeesGroupByConsultant = getEmployeesGroupByFunctionLambdaJ();
		assertEquals(2, employeesGroupByConsultant.findGroup("Consultant").getSize());
	}

	private Map<String, List<Employee>> getEmployeesGroupByFunction() {
		Map<String, List<Employee>> employeesGroupByFunction = new HashMap<String, List<Employee>>();
		for (Employee employee : employees) {
			if (employee != null) {
				if (!employeesGroupByFunction.containsKey(employee.getFunction())) {
					employeesGroupByFunction.put(employee.getFunction(), new ArrayList<Employee>());
				}
				employeesGroupByFunction.get(employee.getFunction()).add(employee);
			}
		}
		return employeesGroupByFunction;
	}

	private Group<Employee> getEmployeesGroupByFunctionLambdaJ() {
		return group(employees, by(on(Employee.class).getFunction()));
	}

	private Map<Integer, Employee> getIndexedEmployees() {
		Map<Integer, Employee> employeesIndex = new HashMap<Integer, Employee>();
		for (Employee employee : employees) {
			if (employee != null) {
				employeesIndex.get(employee.getId());
			}
		}
		return employeesIndex;
	}

	private Map<Integer, Employee> getIndexedEmployeesLambdaJ() {
		return index(employees, on(Employee.class).getId());
	}
}

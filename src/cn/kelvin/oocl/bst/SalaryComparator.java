package cn.kelvin.oocl.bst;

import java.util.Comparator;

public class SalaryComparator implements Comparator<Customer>{

	@Override
	public int compare(Customer o1, Customer o2) {
		return (o1.getSal() - o2.getAge());
	}

}

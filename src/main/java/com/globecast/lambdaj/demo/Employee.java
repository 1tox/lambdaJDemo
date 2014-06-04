package com.globecast.lambdaj.demo;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;

@Data
@RequiredArgsConstructor
public class Employee {
	private final int		id;
	@NonNull
	private String	firstName;
	@NonNull
	private String	function;
	private final int		age;

}

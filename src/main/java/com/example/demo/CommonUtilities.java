package com.example.demo;

import java.time.LocalDate;

public class CommonUtilities {
	
	public static LocalDate year2LocalDate(int year){
		return LocalDate.ofYearDay(year, 1);
	}

}

package jmorea;
import java.util.List;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
	public static void main(String [] args) {

		System.out.println("_________DoorTest_________");
		Result result1 = JUnitCore.runClasses(DoorTest.class);
		System.out.println("Failed Tests:");
        //Print out failure tests
        List <Failure> failedList1 = result1.getFailures();
		failedList1.forEach(f->{System.out.println(f);});
		System.out.println("Number of Failed Tests = " + failedList1.size());
		
        System.out.println("_________ChamberTest_________");
		Result result2 = JUnitCore.runClasses(ChamberTest.class);
		System.out.println("Failed Tests:");
		List <Failure> failedList2 = result2.getFailures();
		failedList2.forEach(f->{System.out.println(f);});
		System.out.println("Number of Failed Tests = " + failedList2.size());

		System.out.println("_________PassageTest_________");
		Result result3 = JUnitCore.runClasses(PassageTest.class);
		System.out.println("Failed Tests:");
		List <Failure> failedList3 = result3.getFailures();
		System.out.println("Number of Failed Tests = " + failedList3.size());
		failedList3.forEach(f->{System.out.println(f);});

		System.out.println("_________PassageSectionTest_________ ");

		Result result4 = JUnitCore.runClasses(PassageSectionTest.class);
		List <Failure> failedList4 = result4.getFailures();
		System.out.println("Failed Tests:");
		System.out.println("Number of Failed Tests = " + failedList4.size());
		failedList4.forEach(f->{System.out.println(f);});
		
		System.out.println("_________LevelTest_________");

		Result result5 = JUnitCore.runClasses(LevelTest.class);
		System.out.println("Failed Tests:");
		List<Failure> failedList5 = result5.getFailures();
		System.out.println("Number of Failed Tests = " + failedList5.size());
		failedList5.forEach(f -> { System.out.println(f);});
        
	}
}
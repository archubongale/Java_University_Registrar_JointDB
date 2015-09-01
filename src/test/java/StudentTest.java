import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class StudentTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

//Test whether the array is empty or nor
  @Test
  public void all_emptyAtFirst() {
    assertEquals(Student.all().size(), 0);
  }

// Test for override objects
  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Student firstStudent = new Student("Sally", "2015-09-01");
    Student secondStudent = new Student("Sally", "2015-09-01");
    assertTrue(firstStudent.equals(secondStudent));
  }

  @Test
  public void save_savesObjectIntoDatabase() {
    Student myStudent = new Student("Sally", "2015-09-01");
    myStudent.save();
    Student savedStudent = Student.all().get(0);
    assertTrue(savedStudent.equals(myStudent));
  }

  @Test
  public void save_assignsIdToObject() {
    Student myStudent = new Student("Sally", "2015-09-01");
    myStudent.save();
    Student savedStudent = Student.all().get(0);
    assertEquals(myStudent.getId(), savedStudent.getId());
  }

//   @Test
//   public void find_findsStudentInDatabase_true() {
//     Student myStudent = new Student("Sally");
//     myStudent.save();
//     Student savedStudent = Student.find(myStudent.getId());
//     assertTrue(myStudent.equals(savedStudent));
//   }
//
//   @Test
//   public void addCourse_addsCourseToStudent() {
//     Course myCourse = new Course("Household chores");
//     myCourse.save();
//
//     Student myStudent = new Student("Sally");
//     myStudent.save();
//
//     myStudent.addCourse(myCourse);
//     Course savedCourse = myStudent.getCategories().get(0);
//     assertTrue(myCourse.equals(savedCourse));
//   }
//
//   @Test
//   public void getCategories_returnsAllCategories_ArrayList() {
//     Course myCourse = new Course("Household chores");
//     myCourse.save();
//
//     Student myStudent = new Student("Sally");
//     myStudent.save();
//
//     myStudent.addCourse(myCourse);
//     List savedCategories = myStudent.getCategories();
//     assertEquals(savedCategories.size(), 1);
//   }
//
//   @Test
//   public void delete_deletesAllStudentsAndListsAssoicationes() {
//     Course myCourse = new Course("Household chores");
//     myCourse.save();
//
//     Student myStudent = new Student("Sally");
//     myStudent.save();
//
//     myStudent.addCourse(myCourse);
//     myStudent.delete();
//     assertEquals(myCourse.getStudents().size(), 0);
//   }
 }

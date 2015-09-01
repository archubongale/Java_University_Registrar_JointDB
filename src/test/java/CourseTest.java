import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class CourseTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Course.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Course firstCourse = new Course("History200");
    Course secondCourse = new Course("History200");
    assertTrue(firstCourse.equals(secondCourse));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Course myCourse = new Course("History200");
    myCourse.save();
    assertTrue(Course.all().get(0).equals(myCourse));
  }

  @Test
  public void find_findCourseInDatabase_true() {
    Course myCourse = new Course("History200");
    myCourse.save();
    Course savedCourse = Course.find(myCourse.getId());
    assertTrue(myCourse.equals(savedCourse));
  }

  @Test
  public void addStudent_addsStudentToCourse() {
    Course myCourse = new Course("History200");
    myCourse.save();

    Student myStudent = new Student("Sally");
    myStudent.save();

    myCourse.addStudent(myStudent);
    Student savedStudent = myCourse.getStudents().get(0);
    assertTrue(myStudent.equals(savedStudent));
  }

  @Test
  public void getStudents_returnsAllStudents_ArrayList() {
    Course myCourse = new Course("History200");
    myCourse.save();

    Student myStudent = new Student("Sally");
    myStudent.save();

    myCourse.addStudent(myStudent);
    List savedStudents = myCourse.getStudents();
    assertEquals(savedStudents.size(), 1);
  }

  @Test
  public void delete_deletesAllStudentsAndListsAssoications() {
    Course myCourse = new Course("History200");
    myCourse.save();

    Student myStudent = new Student("Sally");
    myStudent.save();

    myCourse.addStudent(myStudent);
    myCourse.delete();
    assertEquals(myStudent.getCourses().size(), 0);
  }
}

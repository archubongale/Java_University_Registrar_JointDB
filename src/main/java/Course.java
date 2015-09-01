import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Course {
  private int id;
  private String name_number;

  public int getId() {
    return id;
  }

  public String getName() {
    return name_number;
  }

  public Course(String name_number) {
    this.name_number = name_number;
  }

  public static List<Course> all() {
    String sql = "SELECT id, name_number FROM Courses";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Course.class);
    }
  }

  @Override
  public boolean equals(Object otherCourse){
    if (!(otherCourse instanceof Course)) {
      return false;
    } else {
      Course newCourse = (Course) otherCourse;
      return this.getName().equals(newCourse.getName());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO Courses(name_number) VALUES (:name_number)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name_number", this.name_number)
      .executeUpdate()
      .getKey();
    }
  }

  public static Course find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM Courses WHERE id=:id";
      Course Course = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Course.class);
      return Course;
    }
  }

  public void addStudent(Student student) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students_courses (student_id, course_id) VALUES (:student_id, :course_id)";
      con.createQuery(sql)
      .addParameter("student_id", student.getId())
      .addParameter("course_id", this.getId())
      .executeUpdate();
    }
  }

  public ArrayList<Student> getStudents() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT student_id FROM students_courses WHERE course_id = :course_id";
      List<Integer> studentIds = con.createQuery(sql)
      .addParameter("course_id", this.getId())
      .executeAndFetch(Integer.class);

      ArrayList<Student> students = new ArrayList<Student>();

      for (Integer studentId : studentIds) {
        String studentQuery = "Select * From students WHERE id = :studentId";
        Student student = con.createQuery(studentQuery)
        .addParameter("studentId", studentId)
        .executeAndFetchFirst(Student.class);
        students.add(student);
      }
      return students;
    }
  }

  public void update(String name_number) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE courses SET name_number = :name_number WHERE id = :id";
      con.createQuery(sql)
      .addParameter("name_number", name_number)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM courses WHERE id = :id;";
      con.createQuery(deleteQuery)
      .addParameter("id", id)
      .executeUpdate();

      String joinDeleteQuery = "DELETE FROM students_courses WHERE course_id = :courseId";
      con.createQuery(joinDeleteQuery)
      .addParameter("courseId", this.getId())
      .executeUpdate();
    }
  }
}

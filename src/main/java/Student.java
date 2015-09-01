import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Student {
  private int id;
  private String name;
  private String enrollment_date;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEnrollmentDate(){
    return enrollment_date;
  }

  public Student(String name, String enrollment_date) {
    this.name = name;
    this.enrollment_date = enrollment_date;
  }

  @Override
  public boolean equals(Object otherStudent){
    if (!(otherStudent instanceof Student)) {
      return false;
    } else {
      Student newStudent = (Student) otherStudent;
      return this.getName().equals(newStudent.getName()) &&
      this.getId() == newStudent.getId();
    }
  }

  public void delete(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM students WHERE id = :id";
      con.createQuery(sql)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void update(String name, String enrollment_date) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE students SET name = :name, enrollment_date =:enrollment_date WHERE id = :id";
      con.createQuery(sql)
      .addParameter("name", name)
      .addParameter("enrollment_date", enrollment_date)
      .addParameter("id", id)
      .executeUpdate();
    }
  }


  public static List<Student> all() {
    String sql = "SELECT * FROM students ORDER BY name ASC";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Student.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students (name, enrollment_date) VALUES (:name, :enrollment_date)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", name)
      .addParameter("enrollment_date", enrollment_date)
      .executeUpdate()
      .getKey();
    }
  }

  public static Student find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM students where id=:id";
      Student student = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Student.class);
      return student;
    }
  }

  public void addCourse(Course course) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students_courses (course_id, student_id) VALUES (:course_id, :student_id)";
      con.createQuery(sql)
      .addParameter("course_id", course.getId())
      .addParameter("student_id", this.getId())
      .executeUpdate();
    }
  }

  public ArrayList<Course> getCourses() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT course_id FROM students_courses WHERE student_id = :student_id";
      List<Integer> courseIds = con.createQuery(sql)
      .addParameter("student_id", this.getId())
      .executeAndFetch(Integer.class);

      ArrayList<Course> courses = new ArrayList<Course>();

      for (Integer courseId : courseIds) {
        String studentQuery = "Select * From courses WHERE id = :courseId";
        Course course = con.createQuery(studentQuery)
        .addParameter("courseId", courseId)
        .executeAndFetchFirst(Course.class);
        courses.add(course);
      }
      return courses;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM students WHERE id = :id;";
      con.createQuery(deleteQuery)
      .addParameter("id", id)
      .executeUpdate();

      String joinDeleteQuery = "DELETE FROM students_courses WHERE student_id = :studentId";
      con.createQuery(joinDeleteQuery)
      .addParameter("studentId", this.getId())
      .executeUpdate();
    }
  }


}

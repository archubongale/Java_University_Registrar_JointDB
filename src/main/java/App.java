import java.util.HashMap;
import java.util.List;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;


public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/students", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      List<Student> students = Student.all();
      model.put("students", students);
      model.put("allCourses", Course.all());
      model.put("template", "templates/students.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/courses", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      List<Course> courses = Course.all();
      model.put("courses", courses);
      model.put("template", "/templates/courses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/students/:id/edit", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Student editStudent = Student.find(id);
      model.put("editStudent", editStudent);
      List<Student> students = Student.all();
      model.put("students", students);
      model.put("allCourses", Course.all());
      model.put("template", "templates/students.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/courses/:id/edit", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Course editCourse = Course.find(id);
      model.put("editCourse", editCourse);
      List<Course> courses = Course.all();
      model.put("courses", courses);
      model.put("allStudents", Student.all());
      model.put("template", "templates/courses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/courses/:id/edit", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Course editCourse = Course.find(id);
      String name_number = request.queryParams("name_number");
      editCourse.update(name_number);
      List<Course> courses = Course.all();
      model.put("courses", courses);
      model.put("allStudents", Student.all());
      model.put("template", "templates/courses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    get("/students/:id/delete", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Student deleteStudent = Student.find(id);
      deleteStudent.delete(id);
      List<Student> students = Student.all();
      model.put("students", students);
      model.put("allCourses", Course.all());
      model.put("template", "templates/students.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/students/:id/edit", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Student editStudent = Student.find(id);
      String name = request.queryParams("name");
      String enrollmentDate = request.queryParams("enrollmentDate");
      editStudent.update(name, enrollmentDate);
      List<Student> students = Student.all();
      model.put("students", students);
      model.put("allCourses", Course.all());
      model.put("template", "templates/students.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    get("/students/:id", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Student student = Student.find(id);
      model.put("student", student);
      model.put("allCourses", Course.all());
      model.put("template", "templates/student.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/courses/:id", (request,response) ->{
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Course course = Course.find(id);
      model.put("course", course);
      model.put("allStudents", Student.all());
      model.put("template", "templates/course.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/students", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String enrollmentDate = request.queryParams("enrollmentDate");
      Student newStudent = new Student(name, enrollmentDate);
      newStudent.save();
      response.redirect("/students");
      return null;
    });

    post("/courses", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name_number = request.queryParams("name_number");
      Course newCourse = new Course(name_number);
      newCourse.save();
      response.redirect("/courses");
      return null;
    });

    post("/add_students", (request, response) -> {
      int studentId = Integer.parseInt(request.queryParams("student_id"));
      int courseId = Integer.parseInt(request.queryParams("course_id"));
      Course course = Course.find(courseId);
      Student student = Student.find(studentId);
      course.addStudent(student);
      response.redirect("/courses/" + courseId);
      return null;
    });

    post("/add_courses", (request, response) -> {
      int studentId = Integer.parseInt(request.queryParams("student_id"));
      int courseId = Integer.parseInt(request.queryParams("course_id"));
      Course course = Course.find(courseId);
      Student student = Student.find(studentId);
      student.addCourse(course);
      response.redirect("/students/" + studentId);
      return null;
    });



  }
}

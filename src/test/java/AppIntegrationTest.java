import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.assertj.core.api.Assertions.assertThat;


public class AppIntegrationTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();


  @Override
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @ClassRule
  public static DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("University Registrar");
  }

  @Test
  public void courseIsDisplayedWhenCreated() {
    goTo("http://localhost:4567/");
    click("a", withText("Add or view a course"));
    fill("#name_number").with("History100");
    submit(".btn");
    assertThat(pageSource()).contains("History100");
  }

  // @Test
  //  public void courseIsDeleted() {
  //   Course myCourse = new Course("History 100");
  //   myCourse.save();
  //   goTo("http://localhost:4567/");
  //   click("a", withText("Delete course"));
  //   assertThat(pageSource()).doesNotContain("History 100");
  //  }

  //  @Test
  //  public void courseIsUpdated() {
  //    goTo("http://localhost:4567/");
  //    click("a", withText("Add or view a course"));
  //    fill("#name_number").with("History100");
  //    Course myCourse = new Course("History 100");
  //    myCourse.save();
  //    click("a", withText("History 100"));
  //    click("a", withText("Edit this course"));
  //    fill("#name_number").with("History");
  //    submit(".btn");
  //    assertThat(pageSource()).contains("History");
  //  }
   //
  //  @Test
  //  public void studentIsDisplayedWhenCreated() {
  //    Course myCourse = new Course("Math");
  //    myCourse.save();
  //    goTo("http://localhost:4567/");
  //    click("a", withText("Math"));
  //    fill("#name").with("Derrick");
  //    submit(".btn");
  //    assertThat(pageSource()).contains("Derrick");
  //  }

  //  @Test
  //  public void allstudentsDisplayOnCoursePage() {
  //    Course myCourse = new Course("History 100");
  //    myCourse.save();
  //    goTo("http://localhost:4567/");
  //    click("a", withText("History 100"));
  //    fill("#studentName").with("Derrick");
  //    submit(".btn");
  //    fill("#studentName").with("Marty");
  //    submit(".btn");
  //    assertThat(pageSource()).contains("Derrick");
  //    assertThat(pageSource()).contains("Marty");
  //  }
  //
  //  @Test
  //  public void studentIsDeleted() {
  //    Course myCourse = new Course("History 100");
  //    myCourse.save();
  //    goTo("http://localhost:4567/");
  //    click("a", withText("History 100"));
  //    fill("#studentName").with("Derrick");
  //    submit(".btn");
  //    click("a", withText("Delete student"));
  //    assertThat(pageSource()).doesNotContain("Derrick");
  //  }
  //
  //  @Test
  //  public void studentIsUpdated(){
  //    Course myCourse = new Course("History 100");
  //    myCourse.save();
  //    goTo("http://localhost:4567/");
  //    click("a", withText("History 100"));
  //    fill("#studentName").with("Derrick");
  //    submit(".btn");
  //    click("a", withText("Edit student"));
  //    fill("#studentName").with("Derrick");
  //    assertThat(pageSource()).contains("Derrick");
  //  }
}

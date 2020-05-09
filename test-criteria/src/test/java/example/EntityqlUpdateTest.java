package example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.criteria.Entityql;

@ExtendWith(Env.class)
public class EntityqlUpdateTest {

  private final Entityql entityql;

  public EntityqlUpdateTest(Config config) {
    this.entityql = new Entityql(config);
  }

  @Test
  void test() {
    Employee_ e = new Employee_();

    Employee employee = entityql.from(e).where(c -> c.eq(e.employeeId, 5)).fetchOne();
    employee.setEmployeeName("aaa");
    employee.setSalary(new Salary("2000"));

    Employee result = entityql.update(e, employee).execute();
    assertEquals(employee, result);

    Employee employee2 = entityql.from(e).where(c -> c.eq(e.employeeId, 5)).fetchOne();
    assertNotNull(employee2);
    assertEquals("aaa", employee2.getEmployeeName());
    assertEquals(0, new BigDecimal("2000").compareTo(employee2.getSalary().getValue()));
  }
}

package learning;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BlahTest {
  @Test
  void name() {
    Blah blah = new Blah();
    String execute = blah.execute();
    assertThat(execute).isEqualTo("Hello");
  }
}
package driver.liveSystem.modelTest;

/**
 * Used to pass the classification task to connectModelTest
 * if the classification value is in nominalValues
 */
public final class ConnectModelTest {
   private final ModelTest connectModelTest;
   private final String[] nominalValues;

   public ConnectModelTest(ModelTest connectModelTest, String... nominalValues) {
      this.connectModelTest = connectModelTest;
      this.nominalValues = nominalValues;
   }

   public ModelTest getConnectModelTest() {
      return connectModelTest;
   }

   public String[] getNominalValues() {
      return nominalValues;
   }
}
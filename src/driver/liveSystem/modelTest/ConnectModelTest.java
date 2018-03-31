package driver.liveSystem.modelTest;

/**
 * Used to pass the classification task to connectModelTest
 * if the classification value is in nominalValues
 */
public final class ConnectModelTest {
   private final ModelTest connectingModel;
   private final String[] nominalValues;

   public ConnectModelTest(ModelTest connectModelTest, String... nominalValues) {
      this.connectingModel = connectModelTest;
      this.nominalValues = nominalValues;
   }

   public ModelTest getConnectingModel() {
      return connectingModel;
   }

   public String[] getNominalValues() {
      return nominalValues;
   }
}
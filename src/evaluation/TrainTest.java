package evaluation;

public final class TrainTest extends AbstractTrainTest{
   @Override
   public String getType() {
      return "Train-Test";
   }

   @Override
   public String evaluateUsing(String name) {
      return super.TEST_PATH;
   }
}
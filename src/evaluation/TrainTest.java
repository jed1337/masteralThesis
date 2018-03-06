package evaluation;

public final class TrainTest extends AbstractTrainTest{
   @Override
   public String getType() {
      return "Train-Test";
   }

   @Override
   public String evaluateUsing() {
      return super.TEST_PATH;
   }
}
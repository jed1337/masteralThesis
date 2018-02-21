package evaluation;

public final class TrainValidation extends AbstractTrainTest{
   @Override
   public String getType() {
      return "Train-Validation";
   }

   @Override
   public String evaluateUsing(String name) {
      return super.VALIDATION_PATH;
   }
}
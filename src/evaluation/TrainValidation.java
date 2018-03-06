package evaluation;

public final class TrainValidation extends AbstractTrainTest{
   @Override
   public String getType() {
      return "Train-Validation";
   }

   @Override
   public String evaluateUsing() {
      return super.VALIDATION_PATH;
   }
}